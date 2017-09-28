package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.PracticeJournalEntry;
import ee.hitsa.ois.domain.PracticeJournalFile;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.ContractRepository;
import ee.hitsa.ois.repository.PracticeJournalRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DataUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.validation.ContractValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntriesStudentForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntriesSupervisorForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntriesTeacherForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntryStudentForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntrySupervisorForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntryTeacherForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.PracticeJournalDto;
import ee.hitsa.ois.web.dto.PracticeJournalSearchDto;

@Transactional
@Service
public class PracticeJournalService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;
    @Autowired
    private PracticeJournalRepository practiceJournalRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private ModuleProtocolService moduleProtocolService;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private Validator validator;

    private static final int DAYS_AFTER_END_CAN_EDIT = 30;

    private static final String SEARCH_FROM = "from practice_journal pj "
            + "inner join student student on pj.student_id = student.id "
            + "inner join person student_person on student.person_id = student_person.id "
            + "left join student_group student_group on student_group.id = student.student_group_id "
            + "left join contract contract on contract.id = pj.contract_id "
            + "left join enterprise enterprise on contract.enterprise_id = enterprise.id "
            + "inner join teacher teacher on pj.teacher_id = teacher.id "
            + "inner join person teacher_person on teacher.person_id = teacher_person.id "
            + "left join curriculum_version_omodule cvo on pj.curriculum_version_omodule_id = cvo.id "
            + "left join curriculum_module cm on cm.id = cvo.curriculum_module_id "
            + "left join curriculum_version cv on cv.id = cvo.curriculum_version_id "
            + "left join classifier mcl on mcl.code = cm.module_code "
            + "left join curriculum_version_omodule_theme cvot on cvot.id = pj.curriculum_version_omodule_theme_id "
            + "left join subject subject on subject.id = pj.subject_id ";

    private static final String SEARCH_SELECT = "pj.id, pj.start_date, pj.end_date, pj.practice_place, pj.status_code, "
            + "student.id student_id, student_person.firstname student_person_firstname, student_person.lastname student_person_lastname, student_group.code, "
            + "teacher.id teacher_id, teacher_person.firstname teacher_person_firstname, teacher_person.lastname teacher_person_lastname, "
            + "enterprise.name, enterprise.contact_person_name, "
            + "(select max(pje.inserted) from practice_journal_entry pje where pje.practice_journal_id = pj.id) as student_last_entry_date, "
            + "cvo.id as cvo_id, cv.code as cv_code, cm.name_et as cm_name_et, mcl.name_et as mcl_name_et, cm.name_en as cm_name_en, mcl.name_en as mcl_name_en, "
            + "cvot.id as cvot_id, cvot.name_et as cvot_name_et, length(trim(coalesce(pj.supervisor_opinion, ''))) > 0 as has_supervisor_opinion, "
            + "exists(select true from protocol_student ps inner join protocol p on p.id = ps.protocol_id inner join protocol_vdata pvd on pvd.protocol_id = p.id "
                + "where ps.grade_code in (" + OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE.stream().map(StringUtils::quote).collect(Collectors.joining(",")) + ") and pvd.curriculum_version_omodule_id = cvo.id "
                + "and ps.student_id = student_id) as has_positive_module_grade, "
            + "subject.id as subject_id, subject.name_et as subject_name_et, subject.name_en as subject_name_en";

    public Page<PracticeJournalSearchDto> search(HoisUserDetails user, PracticeJournalSearchCommand command,
            Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.requiredCriteria("pj.school_id = :schoolId", "schoolId", user.getSchoolId());

        qb.optionalCriteria("pj.study_year_id = :studyYearId", "studyYearId", command.getStudyYear());
        qb.optionalCriteria("student.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
        qb.optionalContains(
                Arrays.asList("student_person.firstname", "student_person.lastname",
                        "student_person.firstname || ' ' || student_person.lastname"),
                "name", command.getStudentName());
        qb.optionalCriteria("student.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                command.getCurriculumVersion());
        qb.optionalCriteria("pj.teacher_id = :teacherId", "teacherId", command.getTeacher());
        qb.optionalCriteria("pj.student_id = :studentId", "studentId", command.getStudent());

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            PracticeJournalSearchDto dto = new PracticeJournalSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStartDate(resultAsLocalDate(r, 1));
            dto.setEndDate(resultAsLocalDate(r, 2));
            dto.setPracticePlace(resultAsString(r, 3));

            Boolean hasSupervisorOpinion = resultAsBoolean(r, 23);
            dto.setCanStudentAddEntries(
                    Boolean.valueOf(JournalStatus.PAEVIK_STAATUS_T.name().equals(resultAsString(r, 4))
                            && Boolean.FALSE.equals(hasSupervisorOpinion)));
            dto.setCanEdit(
                    Boolean.valueOf(LocalDate.now().isBefore(dto.getEndDate().plusDays(DAYS_AFTER_END_CAN_EDIT))));

            Boolean hasPositiveModuleGrade = resultAsBoolean(r, 24);
            dto.setCanTeacherAddEntries(Boolean.valueOf(Boolean.FALSE.equals(hasPositiveModuleGrade)));

            String studentName = PersonUtil.fullname(resultAsString(r, 6), resultAsString(r, 7));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 5), studentName, studentName));
            dto.setStudentGroup(resultAsString(r, 8));

            String teacherName = PersonUtil.fullname(resultAsString(r, 10), resultAsString(r, 11));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 9), teacherName, teacherName));

            dto.setEnterpriseName(resultAsString(r, 12));
            dto.setEnterpriseContactPersonName(resultAsString(r, 13));

            dto.setStudentLastEntryDate(resultAsLocalDateTime(r, 14));

            AutocompleteResult module = new AutocompleteResult(resultAsLong(r, 15),
                    CurriculumUtil.moduleName(resultAsString(r, 17), resultAsString(r, 18), resultAsString(r, 16)),
                    CurriculumUtil.moduleName(resultAsString(r, 19), resultAsString(r, 20), resultAsString(r, 16)));
            if (module.getId() != null) {
                dto.setModule(module);
                AutocompleteResult theme = new AutocompleteResult(resultAsLong(r, 21), resultAsString(r, 22), resultAsString(r, 22));
                if (theme.getId() != null) {
                    dto.setTheme(theme);
                }
            }
            AutocompleteResult subject = new AutocompleteResult(resultAsLong(r, 25), resultAsString(r, 26), resultAsString(r, 27));
            if (subject.getId() != null) {
                dto.setSubject(subject);
            }
            return dto;
        });
    }

    public PracticeJournalDto get(PracticeJournal practiceJournal) {
        PracticeJournalDto dto = PracticeJournalDto.of(practiceJournal);
        dto.setCanDelete(Boolean.valueOf(canDelete(practiceJournal)));
        return dto;
    }

    private static boolean canDelete(PracticeJournal practiceJournal) {
        return !hasPracticeEnded(practiceJournal);
    }

    public PracticeJournal create(Long schoolId, PracticeJournalForm practiceJournalForm) {
        PracticeJournal practiceJournal = new PracticeJournal();
        practiceJournal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
        practiceJournal.setSchool(em.getReference(School.class, schoolId));
        practiceJournal.setStudyYear(studyYearService.getCurrentStudyYear(schoolId));
        return save(practiceJournal, practiceJournalForm);
    }

    public PracticeJournal save(PracticeJournal practiceJournal, PracticeJournalForm practiceJournalForm) {
        assertValidationRules(practiceJournalForm);
        PracticeJournal changedPracticeJournal = EntityUtil.bindToEntity(practiceJournalForm, practiceJournal,
                "student", "module", "theme", "teacher", "subject");
        changedPracticeJournal.setStudent(EntityUtil.getOptionalOne(Student.class, practiceJournalForm.getStudent(), em));
        changedPracticeJournal.setModule(EntityUtil.getOptionalOne(CurriculumVersionOccupationModule.class, practiceJournalForm.getModule(), em));
        changedPracticeJournal.setTheme(EntityUtil.getOptionalOne(CurriculumVersionOccupationModuleTheme.class, practiceJournalForm.getTheme(), em));
        changedPracticeJournal.setTeacher(EntityUtil.getOptionalOne(Teacher.class, practiceJournalForm.getTeacher(), em));
        changedPracticeJournal.setSubject(EntityUtil.getOptionalOne(Subject.class, practiceJournalForm.getSubject(), em));
        return practiceJournalRepository.save(changedPracticeJournal);
    }

    private void assertValidationRules(PracticeJournalForm practiceJournalForm) {
        if (Boolean.TRUE.equals(practiceJournalForm.getIsHigher()) &&
                !validator.validate(practiceJournalForm, ContractValidation.Higher.class).isEmpty()) {
            throw new ValidationFailedException("contract.messages.subjectRequired");
        } else if (Boolean.FALSE.equals(practiceJournalForm.getIsHigher()) &&
                !validator.validate(practiceJournalForm, ContractValidation.Vocational.class).isEmpty()) {
            throw new ValidationFailedException("contract.messages.moduleRequired");
        }
    }

    public void delete(PracticeJournal practiceJournal) {
        if (hasPracticeEnded(practiceJournal)) {
            throw new ValidationFailedException("practiceJournal.messages.deletionNotAllowedPracticeHasEnded");
        }
        practiceJournalRepository.delete(practiceJournal);

    }

    public PracticeJournal saveEntriesStudent(PracticeJournal practiceJournal,
            PracticeJournalEntriesStudentForm practiceJournalEntriesStudentForm) {
        assertStudentSaveEntries(practiceJournal);
        EntityUtil.bindToEntity(practiceJournalEntriesStudentForm, practiceJournal, "practiceJournalEntries");
        updatePracticeJournalStudentEntries(practiceJournal, practiceJournalEntriesStudentForm);
        return practiceJournalRepository.save(practiceJournal);
    }

    private static void updatePracticeJournalStudentEntries(PracticeJournal practiceJournal,
            PracticeJournalEntriesStudentForm practiceJournalEntriesStudentForm) {
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalEntries(), PracticeJournalEntry::getId,
            practiceJournalEntriesStudentForm.getPracticeJournalEntries(), PracticeJournalEntryStudentForm::getId, dto -> {
                return EntityUtil.bindToEntity(dto, new PracticeJournalEntry());
            }, EntityUtil::bindToEntity);
    }

    public PracticeJournal saveEntriesTeacher(PracticeJournal practiceJournal,
            PracticeJournalEntriesTeacherForm practiceJournalEntriesTeacherForm) {
        assertTeacherSaveEntries(practiceJournal);
        EntityUtil.bindToEntity(practiceJournalEntriesTeacherForm, practiceJournal, classifierRepository,
                "practiceJournalEntries", "practiceJournalFiles");
        updatePracticeJournalTeacherEntries(practiceJournal, practiceJournalEntriesTeacherForm);
        updatePracticeJournalFiles(practiceJournal, practiceJournalEntriesTeacherForm);
        return practiceJournalRepository.save(practiceJournal);
    }

    public PracticeJournal saveEntriesSupervisor(PracticeJournal practiceJournal,
            PracticeJournalEntriesSupervisorForm practiceJournalEntriesSupervisorForm) {
        assertSupervisorSaveEntries(practiceJournal);
        EntityUtil.bindToEntity(practiceJournalEntriesSupervisorForm, practiceJournal, classifierRepository,
                "practiceJournalEntries", "practiceJournalFiles");
        updatePracticeJournalSupervisorEntries(practiceJournal, practiceJournalEntriesSupervisorForm);
        updatePracticeJournalSupervisorFiles(practiceJournal, practiceJournalEntriesSupervisorForm);
        return practiceJournalRepository.save(practiceJournal);
    }

    private static void updatePracticeJournalSupervisorFiles(PracticeJournal practiceJournal,
            PracticeJournalEntriesSupervisorForm practiceJournalEntriesSupervisorForm) {
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalFiles(), PracticeJournalFile::getId,
                practiceJournalEntriesSupervisorForm.getPracticeJournalFiles(), OisFileForm::getId, dto -> {
                    PracticeJournalFile file = new PracticeJournalFile();
                    file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
                    return file;
                });
    }

    private static void updatePracticeJournalSupervisorEntries(PracticeJournal practiceJournal,
            PracticeJournalEntriesSupervisorForm practiceJournalEntriesSupervisorForm) {
            EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalEntries(), PracticeJournalEntry::getId,
                    practiceJournalEntriesSupervisorForm.getPracticeJournalEntries(), PracticeJournalEntrySupervisorForm::getId, dto -> {
                        return EntityUtil.bindToEntity(dto, new PracticeJournalEntry());
                    }, EntityUtil::bindToEntity);
    }

    private static void assertSupervisorSaveEntries(PracticeJournal practiceJournal) {
        if (ClassifierUtil.equals(JournalStatus.PAEVIK_STAATUS_K, practiceJournal.getStatus())) {
            throw new ValidationFailedException("practiceJournal.messages.editNotAllowedJournalStatusIsConfirmed");
        }
    }

    private void assertTeacherSaveEntries(PracticeJournal practiceJournal) {
        if (!isHigher(practiceJournal) && moduleProtocolService.hasStudentPositiveGradeInModule(practiceJournal.getStudent(), practiceJournal.getModule())) {
            throw new ValidationFailedException("practiceJournal.messages.editnNotAllowedStudentHasPositiveGradeInOccupationModule");
        }
    }

    private static boolean isHigher(PracticeJournal practiceJournal) {
        return practiceJournal.getModule() == null;
    }

    private static void updatePracticeJournalTeacherEntries(PracticeJournal practiceJournal,
            PracticeJournalEntriesTeacherForm practiceJournalEntriesTeacherForm) {
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalEntries(), PracticeJournalEntry::getId,
                practiceJournalEntriesTeacherForm.getPracticeJournalEntries(), PracticeJournalEntryTeacherForm::getId, dto -> {
                    return EntityUtil.bindToEntity(dto, new PracticeJournalEntry());
                }, EntityUtil::bindToEntity);
    }

    private static void assertStudentSaveEntries(PracticeJournal practiceJournal) {
        if (ClassifierUtil.equals(JournalStatus.PAEVIK_STAATUS_K, practiceJournal.getStatus())) {
            throw new ValidationFailedException("practiceJournal.messages.editNotAllowedJournalStatusIsConfirmed");
        } else if (!StringUtils.isEmpty(practiceJournal.getSupervisorOpinion())) {
            throw new ValidationFailedException("practiceJournal.messages.editNotAllowedSupervisorOpinionExists");
        }
    }

    private static void updatePracticeJournalFiles(PracticeJournal practiceJournal,
            PracticeJournalEntriesTeacherForm practiceJournalEntriesForm) {
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalFiles(), PracticeJournalFile::getId,
                practiceJournalEntriesForm.getPracticeJournalFiles(), OisFileForm::getId, dto -> {
                    PracticeJournalFile file = new PracticeJournalFile();
                    file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
                    return file;
                });
    }

    private static boolean hasPracticeEnded(PracticeJournal practiceJournal) {
        return LocalDate.now().isAfter(practiceJournal.getEndDate());
    }

    public PracticeJournal getFromSupervisorUrl(String uuid) {
        Contract contract = contractRepository.findBySupervisorUrl(uuid);
        if (contract == null) {
            log.error("no contract found. {}", DataUtil.asMap("uuid", uuid));
            return null;
        }
        PracticeJournal practiceJournal = practiceJournalRepository.findByContractId(EntityUtil.getId(contract));
        if (practiceJournal == null) {
            log.error("no practice journal found. {}", DataUtil.asMap("uuid", uuid, "contractId", EntityUtil.getId(contract)));
        }

        return practiceJournal;
    }

}
