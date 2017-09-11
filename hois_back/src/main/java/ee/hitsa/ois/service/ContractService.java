package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.Enterprise;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.message.PracticeJournalUniqueUrlMessage;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.validation.ContractValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ContractForm;
import ee.hitsa.ois.web.commandobject.ContractSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ContractDto;
import ee.hitsa.ois.web.dto.ContractSearchDto;
import ee.hitsa.ois.web.dto.ContractStudentHigherModuleDto;
import ee.hitsa.ois.web.dto.ContractStudentModuleDto;
import ee.hitsa.ois.web.dto.ContractStudentSubjectDto;
import ee.hitsa.ois.web.dto.ContractStudentThemeDto;

@Transactional
@Service
public class ContractService {

    @Autowired
    private EntityManager em;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private Validator validator;

    @Value("${hois.frontend.baseUrl}")
    private String frontendBaseUrl;

    private static final String SEARCH_FROM = "from contract contract "
            + "inner join student student on contract.student_id = student.id "
            + "inner join person student_person on student.person_id = student_person.id "
            + "inner join enterprise enterprise on contract.enterprise_id = enterprise.id "
            + "inner join teacher teacher on contract.teacher_id = teacher.id "
            + "inner join person teacher_person on teacher.person_id = teacher_person.id "
            + "left join curriculum_version_omodule curriculum_version_omodule on contract.curriculum_version_omodule_id = curriculum_version_omodule.id ";

    private static final String SEARCH_SELECT = "contract.id contract_id, contract.contract_nr, contract.status_code, contract.start_date, contract.end_date, contract.confirm_date, "
            + "student.id student_id, student_person.firstname student_person_firstname, student_person.lastname student_person_lastname, "
            + "enterprise.name, enterprise.contact_person_name, "
            + "teacher.id teacher_id, teacher_person.firstname teacher_person_firstname, teacher_person.lastname teacher_person_lastname";


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

    private static final String SUBJECTS_FROM = "from student s "
            + "inner join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "inner join curriculum c on c.id = cv.curriculum_id "
            + "inner join curriculum_version_hmodule cvh on cvh.curriculum_version_id = s.curriculum_version_id "
            + "left join curriculum_version_hmodule_subject cvht on cvht.curriculum_version_hmodule_id = cvh.id "
            + "inner join subject subject on subject.id = cvht.subject_id";
    private static final String SUBJECTS_SELECT = "cvh.id as cvh_id, cvh.name_et as cvh_name_et, cvh.name_en as cvh_name_en, "
            + "subject.id, subject.name_et as subject_name_et, subject.name_en as subject_name_en, "
            + "subject.outcomes_et as subject_outcomes_et, subject.outcomes_en as subject_outcomes_en, "
            + "subject.credits as subject_credits";

    public Page<ContractSearchDto> search(HoisUserDetails user, ContractSearchCommand command, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.requiredCriteria("student.school_id = :schoolId", "schoolId", user.getSchoolId());

        qb.optionalCriteria("contract.start_date >= :startFrom", "startFrom", command.getStartFrom());
        qb.optionalCriteria("contract.start_date <= :startThru", "startThru", command.getStartThru());
        qb.optionalCriteria("contract.end_date >= :endFrom", "endFrom", command.getEndFrom());
        qb.optionalCriteria("contract.end_date <= :endThru", "endThru", command.getEndThru());
        qb.optionalContains(Arrays.asList("student_person.firstname", "student_person.lastname",
                "student_person.firstname || ' ' || student_person.lastname"), "name", command.getStudentName());
        qb.optionalCriteria("curriculum_version_omodule.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", command.getCurriculumVersion());
        qb.optionalCriteria("student.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
        qb.optionalContains("enterprise.name", "enterpriseName", command.getEnterpriseName());
        qb.optionalContains("enterprise.contact_person_name", "enterpriseContactPersonName", command.getEnterpriseContactPersonName());
        qb.optionalCriteria("contract.teacher_id = :teacherId", "teacherId", command.getTeacher());
        qb.optionalCriteria("contract.status_code = :status", "status", command.getStatus());
        qb.optionalCriteria("contract.student_id = :studentId", "studentId", command.getStudent());

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            ContractSearchDto dto = new ContractSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setContractNr(resultAsString(r, 1));
            dto.setStatus(resultAsString(r, 2));
            dto.setStartDate(resultAsLocalDate(r, 3));
            dto.setEndDate(resultAsLocalDate(r, 4));
            dto.setConfirmDate(resultAsLocalDate(r, 5));

            String studentName = PersonUtil.fullname(resultAsString(r, 7), resultAsString(r, 8));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 6), studentName, studentName));

            dto.setEnterpriseName(resultAsString(r, 9));
            dto.setEnterpriseContactPersonName(resultAsString(r, 10));

            String teacherName = PersonUtil.fullname(resultAsString(r, 12), resultAsString(r, 13));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 11), teacherName, teacherName));

            return dto;
        });
    }

    public Collection<ContractStudentModuleDto> studentPracticeModules(HoisUserDetails user, Long studentId) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(MODULES_FROM);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.id = :studentId", "studentId", studentId);
        qb.filter("cm.is_practice = true");

        //kellel puudub vastavas moodulis positiivne tulemus.
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
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(SUBJECTS_FROM);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.id = :studentId", "studentId", studentId);
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


    public ContractDto get(Contract contract) {
        return ContractDto.of(contract);
    }

    public Contract create(ContractForm contractForm) {
        Contract contract = new Contract();
        setContractStatus(contract, ContractStatus.LEPING_STAATUS_S);
        contract.setSupervisorUrl(generateUniqueUrl());
        return save(contract, contractForm);
    }

    private static String generateUniqueUrl() {
        return UUID.randomUUID().toString();
    }

    public Contract save(Contract contract, ContractForm contractForm) {
        assertValidationRules(contractForm);

        Contract changedContract = EntityUtil.bindToEntity(contractForm, contract,
                "student", "module", "theme", "enterprise", "teacher", "contractCoordinator", "subject");
        changedContract.setStudent(EntityUtil.getOptionalOne(Student.class, contractForm.getStudent(), em));
        changedContract.setModule(EntityUtil.getOptionalOne(CurriculumVersionOccupationModule.class, contractForm.getModule(), em));
        changedContract.setTheme(EntityUtil.getOptionalOne(CurriculumVersionOccupationModuleTheme.class, contractForm.getTheme(), em));
        changedContract.setEnterprise(EntityUtil.getOptionalOne(Enterprise.class, contractForm.getEnterprise(), em));
        changedContract.setTeacher(EntityUtil.getOptionalOne(Teacher.class, contractForm.getTeacher(), em));
        changedContract.setContractCoordinator(EntityUtil.getOptionalOne(DirectiveCoordinator.class, contractForm.getContractCoordinator(), em));
        changedContract.setSubject(EntityUtil.getOptionalOne(Subject.class, contractForm.getSubject(), em));
        return EntityUtil.save(changedContract, em);
    }

    private void assertValidationRules(ContractForm contractForm) {
        if (Boolean.TRUE.equals(contractForm.getIsHigher()) &&
                !validator.validate(contractForm, ContractValidation.Higher.class).isEmpty()) {
            throw new ValidationFailedException("contract.messages.subjectRequired");
        } else if (Boolean.FALSE.equals(contractForm.getIsHigher()) &&
                !validator.validate(contractForm, ContractValidation.Vocational.class).isEmpty()) {
            throw new ValidationFailedException("contract.messages.moduleRequired");
        }
    }

    public void delete(Contract contract) {
        EntityUtil.deleteEntity(contract, em);
    }

    public Contract sendToEkis(HoisUserDetails user, Contract contract) {
        setContractStatus(contract, ContractStatus.LEPING_STAATUS_Y);
        EntityUtil.save(createPracticeJournal(contract, schoolRepository.getOne(user.getSchoolId())), em);
        return EntityUtil.save(contract, em);
    }

    private PracticeJournal createPracticeJournal(Contract contract, School school) {
        PracticeJournal practiceJournal = EntityUtil.bindToEntity(contract, new PracticeJournal(), "contract", "school", "studyYear", "status");
        practiceJournal.setContract(contract);
        practiceJournal.setSchool(school);
        practiceJournal.setStudyYear(studyYearService.getCurrentStudyYear(school));
        practiceJournal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
        return practiceJournal;
    }

    public void sendUniqueUrlEmailToEnterpriseSupervisor(HoisUserDetails user, Contract contract) {
        PracticeJournalUniqueUrlMessage data = new PracticeJournalUniqueUrlMessage();
        data.setUrl(getPracticeJournalSupervisorUrl(contract));
        automaticMessageService.sendMessageToEnterprise(contract.getEnterprise(), schoolRepository.getOne(user.getSchoolId()), MessageType.TEATE_LIIK_PRAKTIKA_URL, data);
    }

    public String getPracticeJournalSupervisorUrl(Contract contract) {
        return frontendBaseUrl + "practiceJournals/supervisor/" + contract.getSupervisorUrl();
    }

    private void setContractStatus(Contract contract, ContractStatus status) {
        contract.setStatus(em.getReference(Classifier.class, status.name()));
    }
}
