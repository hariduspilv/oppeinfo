package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.ContractModuleSubject;
import ee.hitsa.ois.domain.ContractSupervisor;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.PracticeJournalModuleSubject;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchool;
import ee.hitsa.ois.domain.enterprise.PracticeApplication;
import ee.hitsa.ois.domain.enterprise.PracticeEvaluation;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.PracticeJournalUniqueUrlMessage;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ContractValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ContractAllCommand;
import ee.hitsa.ois.web.commandobject.ContractCancelForm;
import ee.hitsa.ois.web.commandobject.ContractForEkisSearchCommand;
import ee.hitsa.ois.web.commandobject.ContractForm;
import ee.hitsa.ois.web.commandobject.ContractModuleSubjectForm;
import ee.hitsa.ois.web.commandobject.ContractSearchCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterprisePersonCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ContractDto;
import ee.hitsa.ois.web.dto.ContractForEkisDto;
import ee.hitsa.ois.web.dto.ContractNrCommand;
import ee.hitsa.ois.web.dto.ContractSearchDto;
import ee.hitsa.ois.web.dto.ContractStudentHigherModuleDto;
import ee.hitsa.ois.web.dto.ContractStudentModuleDto;
import ee.hitsa.ois.web.dto.ContractStudentSubjectDto;
import ee.hitsa.ois.web.dto.ContractStudentThemeDto;
import ee.hitsa.ois.web.dto.ContractSupervisorDto;
import ee.hitsa.ois.web.dto.ContractToEkisMessageDto;
import ee.hitsa.ois.web.dto.StudentGroupContractSearchCommand;
import ee.hitsa.ois.web.dto.StudentGroupContractSearchDto;

@Transactional
@Service
public class ContractService {

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private EkisService ekisService;
    @Autowired
    private EntityManager em;
    @Autowired
    private MessageTemplateService messageTemplateService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private StudentAbsenceService studentAbsenceService;
    @Autowired
    private Validator validator;
    @Autowired
    private PracticeEnterpriseService enterpriseService;

    @Value("${hois.frontend.baseUrl}")
    private String frontendBaseUrl;
    
    private static final String STUDENTGROUP_CONTRACT_SEARCH_FROM = "from student s "
            + "inner join person student_person on s.person_id = student_person.id "
            + "inner join student_group sg on s.student_group_id = sg.id "
            + "left join contract c on c.student_id = s.id "
            + "left join teacher t on c.teacher_id = t.id "
            + "left join person teacher_person on t.person_id = teacher_person.id "
            + "left join curriculum_version_omodule cvo on c.curriculum_version_omodule_id = cvo.id "
            + "left join contract_supervisor cs on cs.contract_id = c.id ";
    
    private static final String STUDENTGROUP_CONTRACT_SEARCH_SELECT = "c.id as contractId, c.contract_nr, c.status_code, c.confirm_date, "
            + "s.id, student_person.firstname as student_person_firstname, student_person.lastname as student_person_lastname, "
            + "string_agg(cs.supervisor_name, ', '), "
            + "t.id as teacherId, teacher_person.firstname as teacher_person_firstname, teacher_person.lastname as teacher_person_lastname, sg.code, s.status_code as student_status_code, "
            + "c.start_date, c.end_date, s.type_code as studentType";
    
    private static final String STUDENTGROUP_CONTRACT_GROUP_BY = "c.id, c.contract_nr, c.status_code, c.confirm_date, "
            + "s.id, student_person.firstname, student_person.lastname, "
            + "t.id, teacher_person.firstname, teacher_person.lastname, sg.code, s.status_code, c.start_date, c.end_date, s.type_code";

    private static final String SEARCH_FROM = "from contract "
            + "inner join student on contract.student_id = student.id "
            + "left join student_group sg on student.student_group_id = sg.id "
            + "inner join person student_person on student.person_id = student_person.id "
            + "inner join enterprise on contract.enterprise_id = enterprise.id "
            + "inner join teacher on contract.teacher_id = teacher.id "
            + "inner join person teacher_person on teacher.person_id = teacher_person.id "
            + "left join curriculum_version_omodule curriculum_version_omodule on contract.curriculum_version_omodule_id = curriculum_version_omodule.id "
            + "left join contract_supervisor cs on cs.contract_id = contract.id ";

    private static final String SEARCH_SELECT = "contract.id as contractId, contract.contract_nr, contract.status_code, contract.start_date, contract.end_date, contract.confirm_date, "
            + "student.id, student_person.firstname as student_person_firstname, student_person.lastname as student_person_lastname, "
            + "enterprise.name, string_agg(cs.supervisor_name, ', '), "
            + "teacher.id as teacherId, teacher_person.firstname as teacher_person_firstname, teacher_person.lastname as teacher_person_lastname, sg.code, student.type_code as studentType";
    
    private static final String GROUP_BY = "contract.id, contract.contract_nr, contract.status_code, contract.start_date, contract.end_date, contract.confirm_date, "
            + "student.id, student_person.firstname, student_person.lastname, "
            + "enterprise.name, "
            + "teacher.id, teacher_person.firstname, teacher_person.lastname, sg.code";


    private static final String MODULES_FROM = "from student s "
            + "inner join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "inner join curriculum c on c.id = cv.curriculum_id "
            + "inner join curriculum_version_omodule cvo on cvo.curriculum_version_id = s.curriculum_version_id "
            + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id "
            + "inner join classifier mcl on mcl.code = cm.module_code "
            + "left join curriculum_version_omodule_theme cvot on cvot.curriculum_version_omodule_id = cvo.id";
    private static final String MODULES_SELECT = "cvo.id as cvo_id, cv.code, cm.name_et as cm_name_et, mcl.name_et as mcl_name_et, "
            + "cm.name_en as cm_name_en, mcl.name_en as mcl_name_en, cm.credits as cm_credits, cvo.assessment_methods_et, "
            + "cvot.id as cvot_id, cvot.name_et as cvot_name_et, cvot.credits as cvot_credits, cvot.subthemes";

    private static final String ABSENCE_REJECT_CONTRACT_DELETED = "Leping kustutatud";
    private static final String ABSENCE_REJECT_CONTRACT_CANCELED = "Leping tühistatud";
    private static final String ABSENCE_REJECT_CONTRACT_ENDED = "Leping lõpetatud";

    /**
     * Search contracts.
     *
     * @param user
     * @param command
     * @param pageable
     * @return
     */
    public Page<ContractSearchDto> search(HoisUserDetails user, ContractSearchCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable).groupBy(GROUP_BY);
        qb.requiredCriteria("student.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria(
                    "exists (select cur.id from curriculum_version cv join curriculum cur on cur.id = cv.curriculum_id"
                            + " where student.curriculum_version_id = cv.id and cur.id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria("contract.start_date >= :startFrom", "startFrom", command.getStartFrom());
        qb.optionalCriteria("contract.start_date <= :startThru", "startThru", command.getStartThru());
        qb.optionalCriteria("contract.end_date >= :endFrom", "endFrom", command.getEndFrom());
        qb.optionalCriteria("contract.end_date <= :endThru", "endThru", command.getEndThru());
        qb.optionalContains(Arrays.asList("student_person.firstname", "student_person.lastname",
                "student_person.firstname || ' ' || student_person.lastname"), "name", command.getStudentName());
        qb.optionalCriteria("student.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", command.getCurriculumVersion());
        qb.optionalCriteria("student.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
        qb.optionalContains("enterprise.name", "enterpriseName", command.getEnterpriseName());
        qb.optionalContains("cs.supervisor_name", "enterpriseContactPersonName", command.getEnterpriseContactPersonName());
        qb.optionalCriteria("contract.teacher_id = :teacherId", "teacherId", command.getTeacher());
        qb.optionalCriteria("contract.status_code in (:status)", "status", command.getStatus());

        if (user.isStudent()) {
            qb.optionalCriteria("contract.student_id = :studentId", "studentId", user.getStudentId()); 
        } else {
            qb.optionalCriteria("contract.student_id = :studentId", "studentId", command.getStudent());
        }

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            ContractSearchDto dto = new ContractSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setContractNr(resultAsString(r, 1));
            dto.setStatus(resultAsString(r, 2));
            dto.setStartDate(resultAsLocalDate(r, 3));
            dto.setEndDate(resultAsLocalDate(r, 4));
            dto.setConfirmDate(resultAsLocalDate(r, 5));

            String studentName = PersonUtil.fullnameOptionalGuest(resultAsString(r, 7), resultAsString(r, 8), resultAsString(r, 15));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 6), studentName, studentName));

            dto.setEnterpriseName(resultAsString(r, 9));
            dto.setEnterpriseContactPersonName(resultAsString(r, 10));

            String teacherName = PersonUtil.fullname(resultAsString(r, 12), resultAsString(r, 13));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 11), teacherName, teacherName));
            dto.setStudentGroup(resultAsString(r, 14));
            return dto;
        });
    }
    
    /**
     * Search all contracts.
     *
     * @param user
     * @param command
     * @param pageable
     * @return
     */
    public Page<ContractSearchDto> searchAll(HoisUserDetails user, ContractAllCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable).groupBy(GROUP_BY);
        qb.requiredCriteria("student.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria(
                    "exists (select cur.id from curriculum_version cv join curriculum cur on cur.id = cv.curriculum_id"
                            + " where student.curriculum_version_id = cv.id and cur.id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria("student.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
        qb.optionalCriteria("contract.student_id = :studentId", "studentId", command.getStudent());

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            ContractSearchDto dto = new ContractSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setContractNr(resultAsString(r, 1));
            dto.setStatus(resultAsString(r, 2));
            dto.setStartDate(resultAsLocalDate(r, 3));
            dto.setEndDate(resultAsLocalDate(r, 4));
            String studentName = PersonUtil.fullname(resultAsString(r, 7), resultAsString(r, 8));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 6), studentName, studentName));
            dto.setEnterpriseName(resultAsString(r, 9));
            dto.setStudentGroup(resultAsString(r, 14));
            return dto;
        });
    }

    public Collection<ContractStudentModuleDto> studentPracticeModules(HoisUserDetails user, Long studentId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(MODULES_FROM);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.id = :studentId", "studentId", studentId);
        qb.filter("cm.is_practice = true");

        //who has no positive grade in corresponding module
        qb.requiredCriteria(
                "s.id not in (select ps.student_id from protocol_student ps "
                        + "inner join protocol p on p.id = ps.protocol_id "
                        + "inner join protocol_vdata pvd on pvd.protocol_id = p.id "
                        + "where ps.grade_code in :positiveGrades and pvd.curriculum_version_omodule_id = cvo.id)",
                "positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        List<?> data = qb.select(MODULES_SELECT, em).getResultList();

        Map<Long, ContractStudentModuleDto> modulesById = new HashMap<>();
        for (Object r : data) {
            ContractStudentModuleDto dto = modulesById.get(resultAsLong(r, 0));
            if (dto == null) {
                dto = new ContractStudentModuleDto();
                AutocompleteResult module = new AutocompleteResult(resultAsLong(r, 0),
                        CurriculumUtil.moduleName(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 1)),
                        CurriculumUtil.moduleName(resultAsString(r, 4), resultAsString(r, 5), resultAsString(r, 1)));
                dto.setModule(module);
                dto.setCredits(resultAsDecimal(r, 6));
                dto.setAssessmentMethodsEt(resultAsString(r, 7));
                modulesById.put(dto.getModule().getId(), dto);
            }

            if (resultAsLong(r, 8) != null) {
                ContractStudentThemeDto themeDto = new ContractStudentThemeDto();
                themeDto.setTheme(new AutocompleteResult(resultAsLong(r, 8), resultAsString(r, 9), resultAsString(r, 9)));
                themeDto.setCredits((resultAsDecimal(r, 10)));
                themeDto.setSubthemes(resultAsString(r, 11));
                dto.getThemes().add(themeDto);
            }
        }

        return modulesById.values();
    }

    public Collection<ContractStudentHigherModuleDto> studentPracticeHigherModules(HoisUserDetails user, Long studentId) {
        Student student = em.getReference(Student.class, studentId);
        boolean useDeclaration = StudentUtil.isHigher(student) && StudentUtil.isGuestStudent(student);
        String SUBJECTS_FROM = "from student s " 
                + "inner join curriculum_version cv on cv.id = s.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id "
                + "inner join curriculum_version_hmodule cvh on cvh.curriculum_version_id = s.curriculum_version_id "
                + "left join curriculum_version_hmodule_subject cvht on cvht.curriculum_version_hmodule_id = cvh.id "
                + "inner join subject subject on subject.id = cvht.subject_id";
        String SUBJECTS_SELECT = "cvh.id as cvh_id, cvh.name_et as cvh_name_et, cvh.name_en as cvh_name_en, "
                + "subject.id, subject.name_et as subject_name_et, subject.name_en as subject_name_en, "
                + "subject.outcomes_et as subject_outcomes_et, subject.outcomes_en as subject_outcomes_en, "
                + "subject.credits as subject_credits";
        if (useDeclaration) {
            SUBJECTS_FROM = "from subject subject";
            
            SUBJECTS_SELECT = "null as cvh_id, null as cvh_name_et, null as cvh_name_en, "
                    + "subject.id, subject.name_et as subject_name_et, subject.name_en as subject_name_en, "
                    + "subject.outcomes_et as subject_outcomes_et, subject.outcomes_en as subject_outcomes_en, "
                    + "subject.credits as subject_credits";
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBJECTS_FROM);
        if (!useDeclaration) {
            qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
            qb.requiredCriteria("s.id = :studentId", "studentId", studentId);
        } else {
            qb.requiredCriteria("subject.school_id = :subjectSchool", "subjectSchool", user.getSchoolId());
            qb.requiredCriteria("subject.status_code = :subjectStatus", "subjectStatus", SubjectStatus.AINESTAATUS_K.name());
        }
        qb.filter("subject.is_practice = true");

        List<?> data = qb.select(SUBJECTS_SELECT, em).getResultList();
        Map<Long, ContractStudentHigherModuleDto> modulesById = new HashMap<>();
        for (Object r : data) {
            ContractStudentHigherModuleDto dto = modulesById.get(resultAsLong(r, 0));
            if (dto == null) {
                dto = new ContractStudentHigherModuleDto();
                dto.setModule(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)));
                modulesById.put(dto.getModule().getId(), dto);
            }

            if (resultAsLong(r, 3) != null) {
                ContractStudentSubjectDto subjectDto = new ContractStudentSubjectDto();
                subjectDto.setId(resultAsLong(r, 3));
                subjectDto.setNameEt(resultAsString(r, 4));
                subjectDto.setNameEn(resultAsString(r, 5));
                subjectDto.setOutcomesEt(resultAsString(r, 6));
                subjectDto.setOutcomesEn(resultAsString(r, 7));
                subjectDto.setCredits(resultAsDecimal(r, 8));
                dto.getSubjects().add(subjectDto);
            }
        }

        return modulesById.values();
    }

    /**
     * Get contract record.
     *
     * @param contract
     * @return
     */
    public ContractDto get(HoisUserDetails user, Contract contract) {
        ContractDto dto = ContractDto.of(contract);
        boolean userWithRights = UserUtil.isSchoolAdmin(user, contract.getStudent().getSchool())
                || UserUtil.isLeadingTeacher(user, contract.getStudent());
        dto.setCanEdit(Boolean.valueOf(
                userWithRights && ClassifierUtil.equals(ContractStatus.LEPING_STAATUS_S, contract.getStatus())));
        return dto;
    }

    /**
     * Create new contract.
     *
     * @param contractForm
     * @return
     */
    public Contract create(HoisUserDetails user, ContractForm contractForm) {
        Contract contract = new Contract();
        setContractStatus(contract, ContractStatus.LEPING_STAATUS_S);
        //contract.setSupervisorUrl(generateUniqueUrl());

        // credits and hours on contract entity are required but not used
        contract.setCredits(BigDecimal.ZERO);
        contract.setHours(Short.valueOf((short) 0));
        
        return save(user, contract, contractForm);
    }

    /**
     * Save contract.
     *
     * @param contract
     * @param contractForm
     * @return
     */
    public Contract save(HoisUserDetails user, Contract contract, ContractForm contractForm) {
        assertValidationRules(contractForm);

        Contract changedContract = EntityUtil.bindToEntity(contractForm, contract,
                "student", "module", "theme", "enterprise", "teacher", "contractCoordinator", "subject", "practiceApplication", 
                "contractSupervisors", "moduleSubjects", "practiceEvaluation", "studentPracticeEvaluation");
        changedContract.setPracticeEvaluation(EntityUtil.getOptionalOne(PracticeEvaluation.class, contractForm.getPracticeEvaluation(), em));
        changedContract.setStudentPracticeEvaluation(EntityUtil.getOptionalOne(PracticeEvaluation.class, contractForm.getStudentPracticeEvaluation(), em));
        changedContract.setStudent(EntityUtil.getOptionalOne(Student.class, contractForm.getStudent(), em));
        changedContract.setEnterprise(EntityUtil.getOptionalOne(Enterprise.class, contractForm.getEnterprise(), em));
        changedContract.setTeacher(EntityUtil.getOptionalOne(Teacher.class, contractForm.getTeacher(), em));
        changedContract.setContractCoordinator(EntityUtil.getOptionalOne(DirectiveCoordinator.class, contractForm.getContractCoordinator(), em));
        changedContract.setPracticeApplication(EntityUtil.getOptionalOne(PracticeApplication.class, contractForm.getPracticeApplication(), em));
        
        EntityUtil.bindEntityCollection(changedContract.getModuleSubjects(), moduleSubject -> EntityUtil.getId(moduleSubject),
                contractForm.getModuleSubjects(), ContractModuleSubjectForm::getId, dto -> {
                    ContractModuleSubject moduleSubject = new ContractModuleSubject();
                    moduleSubject.setContract(changedContract);
                    return updateModuleSubject(dto, moduleSubject);
                }, this::updateModuleSubject);
        
        List<ContractSupervisor> updatedSupervisors = new ArrayList<>();
        if (contractForm.getSupervisors() != null) {
            for (ContractSupervisorDto supervisor : contractForm.getSupervisors()) {
                if (supervisor.getId() == null) {
                    ContractSupervisor newSupervisor = createContractSupervisor(supervisor, changedContract, user);
                    updatedSupervisors.add(newSupervisor);
                } else {
                    ContractSupervisor oldSupervisor = em.getReference(ContractSupervisor.class, supervisor.getId());
                    oldSupervisor = updateContractSupervisor(supervisor, oldSupervisor, changedContract);
                    updatedSupervisors.add(oldSupervisor);
                }
            }
            changedContract.getContractSupervisors().clear();
            changedContract.getContractSupervisors().addAll(updatedSupervisors);
        }
        return EntityUtil.save(changedContract, em);
    }
    
    private ContractSupervisor createContractSupervisor(ContractSupervisorDto supervisor, Contract changedContract, HoisUserDetails user) {
        ContractSupervisor newSupervisor = new ContractSupervisor();
        EntityUtil.bindToEntity(supervisor, newSupervisor);
        if (StringUtils.isBlank(newSupervisor.getSupervisorName()) && changedContract.getEnterprise() != null && supervisor.getSupervisorFirstname() != null && supervisor.getSupervisorLastname() != null) {
            newSupervisor.setSupervisorName(String.format("%s %s", supervisor.getSupervisorFirstname(), supervisor.getSupervisorLastname()));
            Optional<EnterpriseSchool> enterpriseSchool = changedContract.getEnterprise().getEnterpriseSchools().stream().filter(p->p.getSchool().getId().equals(user.getSchoolId())).findFirst();
            if (enterpriseSchool.isPresent()) {
                PracticeEnterprisePersonCommand cmd = new PracticeEnterprisePersonCommand();
                cmd.setFirstname(supervisor.getSupervisorFirstname());
                cmd.setLastname(supervisor.getSupervisorLastname());
                cmd.setEmail(supervisor.getSupervisorEmail());
                cmd.setPhone(supervisor.getSupervisorPhone());
                cmd.setSupervisor(Boolean.TRUE);
                enterpriseService.createPerson(user, enterpriseSchool.get(), cmd);
            }
        }
        newSupervisor.setContract(changedContract);
        newSupervisor.setSupervisorUrl(generateUniqueUrl());
        return newSupervisor;
    }
    
    private static ContractSupervisor updateContractSupervisor(ContractSupervisorDto supervisor, ContractSupervisor oldSupervisor, Contract changedContract) {
        EntityUtil.bindToEntity(supervisor, oldSupervisor);
        oldSupervisor.setContract(changedContract);
        oldSupervisor.setSupervisorUrl(generateUniqueUrl());
        return oldSupervisor;
    }
    
    private ContractModuleSubject updateModuleSubject(ContractModuleSubjectForm form, ContractModuleSubject moduleSubject) {
        moduleSubject.setModule(EntityUtil.getOptionalOne(CurriculumVersionOccupationModule.class, form.getModule(), em));
        moduleSubject.setTheme(EntityUtil.getOptionalOne(CurriculumVersionOccupationModuleTheme.class, form.getTheme(), em));
        moduleSubject.setSubject(EntityUtil.getOptionalOne(Subject.class, form.getSubject(), em));
        moduleSubject.setCredits(form.getCredits());
        moduleSubject.setHours(form.getHours());
        return moduleSubject;
    }

    /**
     * Delete contract and related practice journal(s).
     *
     * @param user
     * @param contract
     */
    public void delete(HoisUserDetails user, Contract contract) {
        if (contract.getStudentAbsence() != null) {
            contract.getStudentAbsence().setContract(null);
            studentAbsenceService.reject(user, contract.getStudentAbsence(), ABSENCE_REJECT_CONTRACT_DELETED);
        }
        EntityUtil.setUsername(user.getUsername(), em);
        // delete also practice journal(s)
        List<PracticeJournal> journals = em.createQuery("select pj from PracticeJournal pj where pj.contract.id = ?1", PracticeJournal.class)
                .setParameter(1, EntityUtil.getId(contract)).getResultList();
        for(PracticeJournal pj : journals) {
            EntityUtil.deleteEntity(pj, em);
        }

        EntityUtil.deleteEntity(contract, em);
    }

    /**
     * Check if preconditions are ok for sending contract to EKIS
     *
     * @param user
     * @param contractId
     * @return
     */
    public Map<String, ?> checkForEkis(HoisUserDetails user, Long contractId) {
        // is there automatic message template for practice url?
        Map<String, Object> status = new HashMap<>();
        MessageType msgType = MessageType.TEATE_LIIK_PRAKTIKA_URL;
        status.put("templateExists",
                Boolean.valueOf(messageTemplateService.findValidTemplate(msgType, user.getSchoolId()) != null));
        status.put("templateName", em.getReference(Classifier.class, msgType.name()).getNameEt());
        return status;
    }

    /**
     * Send practice contract to EKIS.
     *
     * @param user
     * @param contract
     * @return
     */
    public Contract sendToEkis(HoisUserDetails user, Contract contract) {
        EntityUtil.save(createPracticeJournal(contract, user.getSchoolId()), em);
        contract = EntityUtil.save(contract, em);
        boolean firstSend = contract.getWdId() == null;
        ekisService.registerPracticeContract(EntityUtil.getId(contract));
        if(firstSend) {
            for (ContractSupervisor supervisor : contract.getContractSupervisors()) {
                sendUniqueUrlEmailToEnterpriseSupervisor(user, supervisor);
            }
        }
        return contract;
    }
    
    /**
     * Dto for successful and failed ekis requests.
     * 
     * @param contract
     * @param message
     * @return
     */
    public static ContractToEkisMessageDto createContractToEkisDto(Contract contract, String message) {
        ContractToEkisMessageDto dto = new ContractToEkisMessageDto();
        dto.setId(EntityUtil.getId(contract));
        dto.setStudent(contract.getStudent().getPerson().getFullname());
        dto.setEnterprise(contract.getEnterprise().getName());
        dto.setContractNr(contract.getContractNr());
        dto.setMessage(message);
        return dto;
    }

    /**
     * EKIS has confirmed practice contract.
     *
     * @param contractId
     * @param contractNr
     * @param confirmDate
     * @param wdId
     * @return
     */
    public Contract confirmedByEkis(long contractId, String contractNr, LocalDate confirmDate, long wdId) {
        Contract contract = findContract(contractId, wdId);
        contract.setContractNr(contractNr);
        contract.setConfirmDate(confirmDate);
        setContractStatus(contract, ContractStatus.LEPING_STAATUS_K);
        return EntityUtil.save(contract, em);
    }

    /**
     * Mark contract as ended, if it exists reject contract's absence.
     *
     * @param contractId
     */
    public void endContract(Contract contract, boolean ignoreDate) {
        if (ignoreDate || (contract.getEndDate() != null && contract.getEndDate().isBefore(LocalDate.now()))) {
            setContractStatus(contract, ContractStatus.LEPING_STAATUS_L);
            if (contract.getStudentAbsence() != null) {
                studentAbsenceService.reject("", contract.getStudentAbsence(), ABSENCE_REJECT_CONTRACT_ENDED);
            }
            EntityUtil.save(contract, em);
        }
    }

    public void sendUniqueUrlEmailToEnterpriseSupervisor(HoisUserDetails user, ContractSupervisor supervisor) {
        String url = getPracticeJournalSupervisorUrl(supervisor);
        PracticeJournalUniqueUrlMessage data = new PracticeJournalUniqueUrlMessage(supervisor.getContract().getStudent(), url);
        automaticMessageService.sendMessageToEnterprise(supervisor, em.getReference(School.class, user.getSchoolId()), MessageType.TEATE_LIIK_PRAKTIKA_URL, data);
    }

    private String getPracticeJournalSupervisorUrl(ContractSupervisor supervisor) {
        return frontendBaseUrl + "practiceJournals/supervisor/" + supervisor.getSupervisorUrl();
    }

    private PracticeJournal createPracticeJournal(Contract contract, Long schoolId) {
        PracticeJournal practiceJournal = EntityUtil.bindToEntity(contract, new PracticeJournal(), "contract", "school", "studyYear", "status");
        if (practiceJournal.getPracticePlace() == null) {
            if (contract.getIsPracticeSchool() != null && contract.getIsPracticeSchool().booleanValue()) {
                practiceJournal.setPracticePlace("Praktika sooritatakse koolis");
            } else if (contract.getIsPracticeTelework() != null && contract.getIsPracticeTelework().booleanValue()) {
                practiceJournal.setPracticePlace("Praktika sooritatakse kaugtööna");
            }
        }
        practiceJournal.setContract(contract);
        practiceJournal.setSchool(em.getReference(School.class, schoolId));
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if(studyYear == null) {
            throw new ValidationFailedException("studyYear.missingCurrent");
        }
        practiceJournal.setStudyYear(studyYear);
        practiceJournal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));

        for (ContractModuleSubject contractModuleSubject : contract.getModuleSubjects()) {
            PracticeJournalModuleSubject moduleSubject = EntityUtil.bindToEntity(contractModuleSubject,
                    new PracticeJournalModuleSubject());
            moduleSubject.setPracticeJournal(practiceJournal);
            practiceJournal.getModuleSubjects().add(moduleSubject);
        }

        return practiceJournal;
    }

    private Contract findContract(long contractId, long wdId) {
        try {
            Contract contract = em.getReference(Contract.class, Long.valueOf(contractId));
            if(!ClassifierUtil.equals(ContractStatus.LEPING_STAATUS_Y, contract.getStatus())) {
                throw new HoisException("Leping vale staatusega");
            }
            if(contract.getWdId() == null || contract.getWdId().longValue() != wdId) {
                throw new HoisException("Praktika leping vale ekise id-ga");
            }
            return contract;
        } catch(@SuppressWarnings("unused") EntityNotFoundException e) {
            throw new HoisException("Lepingut ei leitud");
        }
    }

    private void assertValidationRules(ContractForm contractForm) {
        if (Boolean.TRUE.equals(contractForm.getIsHigher())) {
            if (!validator.validate(contractForm, ContractValidation.Higher.class).isEmpty()) {
                throw new ValidationFailedException("contract.messages.subjectRequired");
            }
            Set<Long> subjects = new HashSet<>();
            for (ContractModuleSubjectForm moduleSubjectForm : contractForm.getModuleSubjects()) {
                if (!subjects.add(moduleSubjectForm.getSubject())) {
                    throw new ValidationFailedException("contract.messages.duplicateSubject");
                }
            }
        } else if (Boolean.FALSE.equals(contractForm.getIsHigher())) {
            if (!validator.validate(contractForm, ContractValidation.Vocational.class).isEmpty()) {
                throw new ValidationFailedException("contract.messages.moduleRequired");
            }
            Map<Long, Set<Long>> moduleThemes = new HashMap<>();
            for (ContractModuleSubjectForm moduleSubjectForm : contractForm.getModuleSubjects()) {
                Set<Long> themes = moduleThemes.computeIfAbsent(moduleSubjectForm.getModule(), k -> new HashSet<>());
                if (!themes.add(moduleSubjectForm.getTheme())) {
                    throw new ValidationFailedException("contract.messages.duplicateModuleTheme");
                }
            }
        }
    }

    private void setContractStatus(Contract contract, ContractStatus status) {
        contract.setStatus(em.getReference(Classifier.class, status.name()));
    }

    private static String generateUniqueUrl() {
        return UUID.randomUUID().toString();
    }

    public Contract cancel(HoisUserDetails user, Contract contract, ContractCancelForm contractForm) {
        EntityUtil.bindToEntity(contractForm, contract, "cancelReason");
        contract.setCancelReason(em.getReference(Classifier.class, contractForm.getCancelReason()));
        Person canceler = em.getReference(Person.class, user.getPersonId());
        contract.setCanceledBy(canceler.getFullname());
        contract.setStatus(em.getReference(Classifier.class, ContractStatus.LEPING_STAATUS_T.name()));

        if (contract.getStudentAbsence() != null) {
            studentAbsenceService.reject(user, contract.getStudentAbsence(), ABSENCE_REJECT_CONTRACT_CANCELED);
        }

        EntityUtil.setUsername(user.getUsername(), em);
        return EntityUtil.save(contract, em);
    }

    public Page<StudentGroupContractSearchDto> searchStudentGroupContract(HoisUserDetails user,
            StudentGroupContractSearchCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENTGROUP_CONTRACT_SEARCH_FROM).sort(pageable).groupBy(STUDENTGROUP_CONTRACT_GROUP_BY);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria(
                    "exists (select cur.id from curriculum_version cv join curriculum cur on cur.id = cv.curriculum_id"
                            + " where s.curriculum_version_id = cv.id and cur.id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria("s.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
        qb.filter("s.curriculum_version_id is not null");
        if (command.getActive() != null && command.getActive().booleanValue()) {
            qb.filter("s.status_code in (:studentStatus)");
            qb.parameter("studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        }
        return JpaQueryUtil.pagingResult(qb, STUDENTGROUP_CONTRACT_SEARCH_SELECT, em, pageable).map(r -> {
            StudentGroupContractSearchDto dto = new StudentGroupContractSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setContractNr(resultAsString(r, 1));
            dto.setStatus(resultAsString(r, 2));
            dto.setConfirmDate(resultAsLocalDate(r, 3));
            String studentName = PersonUtil.fullnameOptionalGuest(resultAsString(r, 5), resultAsString(r, 6), resultAsString(r, 15));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 4), studentName, studentName));
            dto.setEnterpriseContactPersonName(resultAsString(r, 7));
            String teacherName = PersonUtil.fullname(resultAsString(r, 9), resultAsString(r, 10));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 8), teacherName, teacherName));
            dto.setStudentGroup(resultAsString(r, 11));
            dto.setActive(Boolean.valueOf(StudentStatus.STUDENT_STATUS_ACTIVE.contains(resultAsString(r, 12))));
            dto.setStartDate(resultAsLocalDate(r, 13));
            dto.setEndDate(resultAsLocalDate(r, 14));
            return dto;
        });
	}

    public Page<ContractForEkisDto> searchContractForEkis(HoisUserDetails user, ContractForEkisSearchCommand command,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable).groupBy(GROUP_BY);

        qb.requiredCriteria("student.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria(
                    "exists (select cur.id from curriculum_version cv join curriculum cur on cur.id = cv.curriculum_id"
                            + " where student.curriculum_version_id = cv.id and cur.id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalContains(Arrays.asList("student_person.firstname", "student_person.lastname",
                "student_person.firstname || ' ' || student_person.lastname"), "name", command.getStudentName());
        qb.optionalCriteria("student.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
        qb.requiredCriteria("contract.status_code = :status", "status", ContractStatus.LEPING_STAATUS_S.name());
        
        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            ContractForEkisDto dto = new ContractForEkisDto();
            Contract contract = em.getReference(Contract.class, resultAsLong(r, 0));
            dto.setId(resultAsLong(r, 0));
            String studentName = PersonUtil.fullname(resultAsString(r, 7), resultAsString(r, 8));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 6), studentName, studentName));
            dto.setEnterpriseName(resultAsString(r, 9));
            dto.setEnterpriseContactPersonName(resultAsString(r, 10));
            String teacherName = PersonUtil.fullname(resultAsString(r, 12), resultAsString(r, 13));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 11), teacherName, teacherName));
            dto.setStudentGroup(resultAsString(r, 14));
            dto.setModuleSubjects(contract.getModuleSubjects().stream()
                    .map(p -> {
                        if (p.getModule() != null && p.getModule().getCurriculumModule() != null && p.getTheme() != null && StringUtils.isNotEmpty(p.getTheme().getNameEt())) {
                            String nameEt = p.getModule().getCurriculumModule().getNameEt() + "(" + p.getTheme().getNameEt() + ")";
                            return new AutocompleteResult(null, nameEt, nameEt);
                        } else if (p.getModule() != null && p.getModule().getCurriculumModule() != null) {
                            return new AutocompleteResult(null, p.getModule().getCurriculumModule().getNameEt(), p.getModule().getCurriculumModule().getNameEn());
                        }
                        return null;
                    }
                    ).collect(Collectors.toList()));
            dto.setHigherModuleSubjects(contract.getModuleSubjects().stream()
                    .map(p -> p.getSubject() != null && p.getSubject().getNameEt() != null ?
                            new AutocompleteResult(null, p.getSubject().getNameEt(), p.getSubject().getNameEt()) 
                            : null)
                    .collect(Collectors.toList()));
            return dto;
        });
	}
	
	/**
     * Set contract to 'checkout'(ÜLEVAATAMISEL) without EKIS.
     *
     * @param user
     * @param contract
     * @return
     */
    public Contract checkout(HoisUserDetails user, Contract contract) {
        contract.setStatus(em.getReference(Classifier.class, ContractStatus.LEPING_STAATUS_Y.name()));
        if (Boolean.TRUE.equals(contract.getIsPracticeAbsence()) || Boolean.TRUE.equals(contract.getIsPracticeHidden())) {
            studentAbsenceService.createContractAbsence(contract);
        }
        EntityUtil.save(createPracticeJournal(contract, user.getSchoolId()), em);
        contract = EntityUtil.save(contract, em);
        // TODO: Send email only once
        for (ContractSupervisor supervisor : contract.getContractSupervisors()) {
            sendUniqueUrlEmailToEnterpriseSupervisor(user, supervisor);
        }
        return contract;
    }
    
    /**
     * Set contract status from ÜLEVAATAMISEL to KEHTIV
     * 
     * @param contract
     * @return
     */
    public Contract confirm(Contract contract) {
        contract.setStatus(em.getReference(Classifier.class, ContractStatus.LEPING_STAATUS_K.name()));
        contract = EntityUtil.save(contract, em);
        return contract;
    }
    
    /**
     * Used to set contract nr when contract is not confirmed by ekis
     * 
     * @param contract
     * @param command
     * @return
     */
    public Contract changeContractNr(Contract contract, ContractNrCommand command) {
        contract.setContractNr(command.getContractNr());
        return EntityUtil.save(contract, em);
    }
}
