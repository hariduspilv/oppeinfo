package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.PracticeJournalEntry;
import ee.hitsa.ois.domain.PracticeJournalEvaluation;
import ee.hitsa.ois.domain.PracticeJournalFile;
import ee.hitsa.ois.domain.PracticeJournalModuleSubject;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.enterprise.PracticeEvaluation;
import ee.hitsa.ois.domain.enterprise.PracticeEvaluationCriteria;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.PracticeJournalUserRights;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.PracticeJournalValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntriesStudentForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntriesSupervisorForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntriesTeacherForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntryStudentForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntrySupervisorForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntryTeacherForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEvaluationForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalModuleSubjectForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.PracticeJournalDto;
import ee.hitsa.ois.web.dto.PracticeJournalSearchDto;
import ee.hitsa.ois.web.dto.PracticeJournalSearchModuleSubjectDto;
import ee.hitsa.ois.web.dto.practice.PracticeEvaluationCriteriaDto;

@Transactional
@Service
public class PracticeJournalService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private ModuleProtocolService moduleProtocolService;
    @Autowired
    private Validator validator;

    private static final String SEARCH_FROM = "from practice_journal pj "
            + "inner join student student on pj.student_id = student.id "
            + "inner join person student_person on student.person_id = student_person.id "
            + "left join student_group student_group on student_group.id = student.student_group_id "
            + "left join contract contract on contract.id = pj.contract_id "
            + "left join contract_supervisor cs on cs.contract_id = contract.id "
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
            + "student.id studentId, student_person.firstname student_person_firstname, student_person.lastname student_person_lastname, student_group.code, "
            + "teacher.id teacherId, teacher_person.firstname teacher_person_firstname, teacher_person.lastname teacher_person_lastname, "
            + "enterprise.name, string_agg(cs.supervisor_name, ', '), "
            + "(select max(pje.inserted) from practice_journal_entry pje where pje.practice_journal_id = pj.id) as student_last_entry_date, "
            + "cvo.id as cvo_id, cv.code as cv_code, cm.name_et as cm_name_et, mcl.name_et as mcl_name_et, cm.name_en as cm_name_en, mcl.name_en as mcl_name_en, "
            + "cvot.id as cvot_id, cvot.name_et as cvot_name_et, length(trim(coalesce(pj.supervisor_opinion, ''))) > 0 as has_supervisor_opinion, "
            + "subject.id as subjectId, subject.name_et as subject_name_et, subject.name_en as subject_name_en, student.status_code as student_status, student.type_code as studentType";
    
    private static final String GROUP_BY = "pj.id, pj.start_date, pj.end_date, pj.practice_place, pj.status_code, "
            + "student.id, student_person.firstname, student_person.lastname, student_group.code, "
            + "teacher.id, teacher_person.firstname, teacher_person.lastname, "
            + "enterprise.name, "
            + "cvo.id, cv.code, cm.name_et, mcl.name_et, cm.name_en, mcl.name_en, "
            + "cvot.id, cvot.name_et, "
            + "subject.id, subject.name_et, subject.name_en, student.type_code";

    public Page<PracticeJournalSearchDto> search(HoisUserDetails user, PracticeJournalSearchCommand command,
            Pageable pageable) {
        if (user.isStudent() || user.isRepresentative()) {
            command.setStudent(user.getStudentId());
        } else if (user.isTeacher()) {
            command.setTeacher(user.getTeacherId());
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable).groupBy(GROUP_BY);
        qb.requiredCriteria("pj.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria(
                    "exists (select cur.id from curriculum_version cv2 join curriculum cur on cur.id = cv2.curriculum_id"
                            + " where student.curriculum_version_id = cv2.id and cur.id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        }

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
        qb.optionalCriteria("pj.status_code in (:status)", "status", command.getStatus());

        Page<Object> result = JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable);
        Map<Long, List<PracticeJournalSearchModuleSubjectDto>> moduleSubjects = getModuleSubjects(
                StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result.getContent()));
        return result.map(r -> {
            PracticeJournalSearchDto dto = new PracticeJournalSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStartDate(resultAsLocalDate(r, 1));
            dto.setEndDate(resultAsLocalDate(r, 2));
            dto.setPracticePlace(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));

            Boolean hasSupervisorOpinion = resultAsBoolean(r, 23);
            dto.setCanStudentAddEntries(Boolean.valueOf(PracticeJournalUserRights.canStudentAddEntries(dto.getStatus(),
                    dto.getEndDate(), hasSupervisorOpinion)));
            String studentStatus = resultAsString(r, 27);
            dto.setCanEdit(Boolean.valueOf(PracticeJournalUserRights.canEdit(user, dto.getEndDate(), studentStatus)));
            dto.setCanConfirm(
                    Boolean.valueOf(PracticeJournalUserRights.canConfirm(user, dto.getEndDate(), studentStatus)));
            
            String studentName = PersonUtil.fullnameOptionalGuest(resultAsString(r, 6), resultAsString(r, 7), resultAsString(r, 28));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 5), studentName, studentName));
            dto.setStudentGroup(resultAsString(r, 8));

            String teacherName = PersonUtil.fullname(resultAsString(r, 10), resultAsString(r, 11));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 9), teacherName, teacherName));

            dto.setEnterpriseName(resultAsString(r, 12));
            dto.setEnterpriseSupervisors(resultAsString(r, 13));

            dto.setStudentLastEntryDate(resultAsLocalDateTime(r, 14));

            dto.setCanAddEntries(Boolean.valueOf(
                    PracticeJournalUserRights.canAddEntries(user, dto.getCanStudentAddEntries(), studentStatus)));

            dto.setModuleSubjects(moduleSubjects.get(dto.getId()));
            return dto;
        });
    }
    
    private Map<Long, List<PracticeJournalSearchModuleSubjectDto>> getModuleSubjects(Set<Long> practiceJournalIds) {
        if (practiceJournalIds.isEmpty()) {
            return Collections.emptyMap();
        }
        
        List<?> result = em.createNativeQuery("select pjms.practice_journal_id, "
                + " cvo.id as cvo_id, cv.code as cv_code, cm.name_et as cm_name_et, mcl.name_et as mcl_name_et, cm.name_en as cm_name_en, mcl.name_en as mcl_name_en, "
                + " cvot.id as cvot_id, cvot.name_et as cvot_name_et, "
                + " subject.id as subject_id, subject.name_et as subject_name_et, subject.name_en as subject_name_en"
                + " from practice_journal_module_subject pjms "
                + " left join curriculum_version_omodule cvo on pjms.curriculum_version_omodule_id = cvo.id "
                + " left join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + " left join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + " left join classifier mcl on mcl.code = cm.module_code "
                + " left join curriculum_version_omodule_theme cvot on cvot.id = pjms.curriculum_version_omodule_theme_id "
                + " left join subject subject on subject.id = pjms.subject_id "
                + " where pjms.practice_journal_id in ?1"
                + " order by cm.name_et, cvot.name_et, subject.name_et")
                .setParameter(1, practiceJournalIds)
                .getResultList();
        return result.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> {
                    PracticeJournalSearchModuleSubjectDto dto = new PracticeJournalSearchModuleSubjectDto();
                    dto.setId(resultAsLong(r, 0));
                    AutocompleteResult module = new AutocompleteResult(resultAsLong(r, 1),
                            CurriculumUtil.moduleName(resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 2)),
                            CurriculumUtil.moduleName(resultAsString(r, 5), resultAsString(r, 6), resultAsString(r, 2)));
                    if (module.getId() != null) {
                        dto.setModule(module);
                        AutocompleteResult theme = new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 8), resultAsString(r, 8));
                        if (theme.getId() != null) {
                            dto.setTheme(theme);
                        }
                    }
                    AutocompleteResult subject = new AutocompleteResult(resultAsLong(r, 9), resultAsString(r, 10), resultAsString(r, 11));
                    if (subject.getId() != null) {
                        dto.setSubject(subject);
                    }
                    return dto;
                }, Collectors.toList())));
    }
    
    private List<PracticeEvaluationCriteriaDto> setEvalValues(List<PracticeEvaluationCriteriaDto> criteria, PracticeJournal practiceJournal) {
        for (PracticeEvaluationCriteriaDto dto : criteria) {
            List<PracticeJournalEvaluation> evals = em.createQuery("select pje from PracticeJournalEvaluation pje where pje.practiceJournal.id = ?1 "
                    + "and pje.practiceEvaluationCriteria.id = ?2", PracticeJournalEvaluation.class)
                    .setParameter(1, EntityUtil.getId(practiceJournal))
                    .setParameter(2, dto.getCriteriaId())
                    .setMaxResults(1).getResultList();
            if (evals != null && !evals.isEmpty()) {
                PracticeJournalEvaluation eval = evals.get(0);
                dto.setId(EntityUtil.getId(eval));
                if (eval.getValueClf() != null) {
                    dto.setValueClf(eval.getValueClf().getCode());
                }
                if (eval.getValueNr() != null) {
                    dto.setValueNr(eval.getValueNr().toString());
                }
                dto.setValueTxt(eval.getValueTxt());
            }
        }
        return criteria;
    }
    
    public PracticeJournalDto get(PracticeJournal practiceJournal) {
        PracticeJournalDto dto = PracticeJournalDto.of(practiceJournal);
        if (practiceJournal.getContract() != null) {
            if (practiceJournal.getContract().getStudentPracticeEvaluation() != null && practiceJournal.getContract().getStudentPracticeEvaluation().getCriteria() != null) {
                dto.setStudentPracticeEvalCriteria(setEvalValues(practiceJournal.getContract().getStudentPracticeEvaluation().getCriteria()
                        .stream().map(PracticeEvaluationCriteriaDto::of).sorted((o1,o2)-> o1.getOrderNr().compareTo(o2.getOrderNr())).collect(Collectors.toList()), practiceJournal));
            }
            if (practiceJournal.getContract().getPracticeEvaluation() != null && practiceJournal.getContract().getPracticeEvaluation().getCriteria() != null) {
                dto.setSupervisorPracticeEvalCriteria(setEvalValues(practiceJournal.getContract().getPracticeEvaluation().getCriteria()
                        .stream().map(PracticeEvaluationCriteriaDto::of).sorted((o1,o2)-> o1.getOrderNr().compareTo(o2.getOrderNr())).collect(Collectors.toList()), practiceJournal));
            }
        } else {
            if (practiceJournal.getPracticeEvaluation() != null && practiceJournal.getPracticeEvaluation().getCriteria() != null) {
                dto.setStudentPracticeEvalCriteria(setEvalValues(practiceJournal.getPracticeEvaluation().getCriteria()
                        .stream().map(PracticeEvaluationCriteriaDto::of).sorted((o1,o2)-> o1.getOrderNr().compareTo(o2.getOrderNr())).collect(Collectors.toList()), practiceJournal));
            }
        }
        dto.setCanAddEntries(Boolean.valueOf(StudentUtil.isActive(dto.getStudentStatus())));
        dto.setLetterGrades(practiceJournal.getSchool().getIsLetterGrade());
        return dto;
    }

    public PracticeJournalDto get(HoisUserDetails user, PracticeJournal practiceJournal) {
        PracticeJournalDto dto = get(practiceJournal);
        dto.setCanEdit(Boolean.valueOf(PracticeJournalUserRights.canEdit(user, practiceJournal)));
        dto.setCanConfirm(Boolean.valueOf(PracticeJournalUserRights.canConfirm(user, practiceJournal)));
        dto.setCanDelete(Boolean.valueOf(PracticeJournalUserRights.canDelete(user, practiceJournal)));
        dto.setCanAddEntries(Boolean.valueOf(PracticeJournalUserRights.canAddEntries(user, dto)));
        return dto;
    }

    public PracticeJournal create(Long schoolId, PracticeJournalForm practiceJournalForm) {
        PracticeJournal practiceJournal = new PracticeJournal();
        practiceJournal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
        practiceJournal.setSchool(em.getReference(School.class, schoolId));
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if(studyYear == null) {
            throw new ValidationFailedException("studyYear.missingCurrent");
        }
        practiceJournal.setStudyYear(studyYear);

        // credits and hours on journal entity are required but not used
        practiceJournal.setCredits(BigDecimal.ZERO);
        practiceJournal.setHours(Short.valueOf((short) 0));
        
        return save(practiceJournal, practiceJournalForm);
    }

    public PracticeJournal save(PracticeJournal practiceJournal, PracticeJournalForm practiceJournalForm) {
        assertValidationRules(practiceJournalForm);
        PracticeJournal changedPracticeJournal = EntityUtil.bindToEntity(practiceJournalForm, practiceJournal,
                "student", "module", "theme", "teacher", "subject", "moduleSubjects", "practiceEvaluation");
        if (!StringUtils.isEmpty(practiceJournalForm.getGrade())) {
            changedPracticeJournal.setGrade(em.getReference(Classifier.class, practiceJournalForm.getGrade()));
        }
        changedPracticeJournal.setPracticeEvaluation(EntityUtil.getOptionalOne(PracticeEvaluation.class, practiceJournalForm.getPracticeEvaluation(), em));
        changedPracticeJournal.setStudent(EntityUtil.getOptionalOne(Student.class, practiceJournalForm.getStudent(), em));
        changedPracticeJournal.setTeacher(EntityUtil.getOptionalOne(Teacher.class, practiceJournalForm.getTeacher(), em));
        EntityUtil.bindEntityCollection(changedPracticeJournal.getModuleSubjects(), moduleSubject -> EntityUtil.getId(moduleSubject),
                practiceJournalForm.getModuleSubjects(), PracticeJournalModuleSubjectForm::getId, dto -> {
                    PracticeJournalModuleSubject moduleSubject = new PracticeJournalModuleSubject();
                    moduleSubject.setPracticeJournal(changedPracticeJournal);
                    return updateModuleSubject(dto, moduleSubject);
                }, this::updateModuleSubject);
        return EntityUtil.save(changedPracticeJournal, em);
    }

    private PracticeJournalModuleSubject updateModuleSubject(PracticeJournalModuleSubjectForm form, PracticeJournalModuleSubject moduleSubject) {
        moduleSubject.setModule(EntityUtil.getOptionalOne(CurriculumVersionOccupationModule.class, form.getModule(), em));
        moduleSubject.setTheme(EntityUtil.getOptionalOne(CurriculumVersionOccupationModuleTheme.class, form.getTheme(), em));
        moduleSubject.setSubject(EntityUtil.getOptionalOne(Subject.class, form.getSubject(), em));
        moduleSubject.setCredits(form.getCredits());
        moduleSubject.setHours(form.getHours());
        return moduleSubject;
    }

    public PracticeJournal confirm(PracticeJournal practiceJournal, PracticeJournalForm practiceJournalForm) {
        practiceJournal = save(practiceJournal, practiceJournalForm);
        practiceJournal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_K.name()));
        return EntityUtil.save(practiceJournal, em);
    }

    private void assertValidationRules(PracticeJournalForm practiceJournalForm) {
        if (Boolean.TRUE.equals(practiceJournalForm.getIsHigher())) {
            if (!validator.validate(practiceJournalForm, PracticeJournalValidation.Higher.class).isEmpty()) {
                throw new ValidationFailedException("practiceJournal.messages.subjectRequired");
            }
            Set<Long> subjects = new HashSet<>();
            for (PracticeJournalModuleSubjectForm moduleSubjectForm : practiceJournalForm.getModuleSubjects()) {
                if (!subjects.add(moduleSubjectForm.getSubject())) {
                    throw new ValidationFailedException("practiceJournal.messages.duplicateSubject");
                }
            }
        } else if (Boolean.FALSE.equals(practiceJournalForm.getIsHigher())) {
            if (!validator.validate(practiceJournalForm, PracticeJournalValidation.Vocational.class).isEmpty()) {
                throw new ValidationFailedException("practiceJournal.messages.moduleRequired");
            }
            Map<Long, Set<Long>> moduleThemes = new HashMap<>();
            for (PracticeJournalModuleSubjectForm moduleSubjectForm : practiceJournalForm.getModuleSubjects()) {
                Set<Long> themes = moduleThemes.computeIfAbsent(moduleSubjectForm.getModule(), k -> new HashSet<>());
                if (!themes.add(moduleSubjectForm.getTheme())) {
                    throw new ValidationFailedException("practiceJournal.messages.duplicateModuleTheme");
                }
            }
        }
    }

    public void delete(HoisUserDetails user, PracticeJournal practiceJournal) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(practiceJournal, em);
    }

    public PracticeJournal saveEntriesStudent(PracticeJournal practiceJournal,
            PracticeJournalEntriesStudentForm practiceJournalEntriesStudentForm) {
        assertStudentSaveEntries(practiceJournal);
        EntityUtil.bindToEntity(practiceJournalEntriesStudentForm, practiceJournal, "practiceJournalEntries", "studentPracticeEvalCriteria", "practiceJournalStudentFiles");
        updatePracticeJournalStudentEvaluations(practiceJournal, practiceJournalEntriesStudentForm);
        updatePracticeJournalStudentEntries(practiceJournal, practiceJournalEntriesStudentForm);
        updatePracticeJournalStudentFiles(practiceJournal, practiceJournalEntriesStudentForm);
        return EntityUtil.save(practiceJournal, em);
    }

    private void updatePracticeJournalStudentEvaluations(PracticeJournal practiceJournal,
            PracticeJournalEntriesStudentForm practiceJournalEntriesStudentForm) {
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalEvaluations(), eval -> EntityUtil.getId(eval),
                practiceJournalEntriesStudentForm.getStudentPracticeEvalCriteria(), PracticeJournalEvaluationForm::getId, dto -> {
                    PracticeJournalEvaluation eval = new PracticeJournalEvaluation();
                    eval.setPracticeJournal(practiceJournal);
                    return updateEvals(dto, eval);
                }, this::updateEvals);
    }
    
    private void updatePracticeJournalSupervisorEvaluations(PracticeJournal practiceJournal,
            PracticeJournalEntriesSupervisorForm practiceJournalEntriesSupervisorForm) {
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalEvaluations(), eval -> EntityUtil.getId(eval),
                practiceJournalEntriesSupervisorForm.getSupervisorPracticeEvalCriteria(), PracticeJournalEvaluationForm::getId, dto -> {
                    PracticeJournalEvaluation eval = new PracticeJournalEvaluation();
                    eval.setPracticeJournal(practiceJournal);
                    return updateEvals(dto, eval);
                }, this::updateEvals);
    }
    
    private PracticeJournalEvaluation updateEvals(PracticeJournalEvaluationForm form, PracticeJournalEvaluation eval) {
        eval = EntityUtil.bindToEntity(form, eval, "criteriaId", "id", "valueClf");
        eval.setPracticeEvaluationCriteria(EntityUtil.getOptionalOne(PracticeEvaluationCriteria.class, form.getCriteriaId(), em));
        eval.setPracticeEvaluation(eval.getPracticeEvaluationCriteria().getPracticeEvaluation());
        if (form.getValueClf() != null) {
            eval.setValueClf(em.getReference(Classifier.class, form.getValueClf()));
        } else {
            eval.setValueClf(null);
        }
        return eval;
    }

    private static void updatePracticeJournalStudentEntries(PracticeJournal practiceJournal,
            PracticeJournalEntriesStudentForm practiceJournalEntriesStudentForm) {
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalEntries(), PracticeJournalEntry::getId,
            practiceJournalEntriesStudentForm.getPracticeJournalEntries(), PracticeJournalEntryStudentForm::getId, dto -> {
                return EntityUtil.bindToEntity(dto, new PracticeJournalEntry());
            }, EntityUtil::bindToEntity);
    }

    public PracticeJournal saveEntriesTeacher(HoisUserDetails user, PracticeJournal practiceJournal,
            PracticeJournalEntriesTeacherForm practiceJournalEntriesTeacherForm) {
        assertTeacherSaveEntries(practiceJournal);
        EntityUtil.setUsername(user.getUsername(), em);
        
        if (practiceJournalEntriesTeacherForm.getGrade() != null && !practiceJournalEntriesTeacherForm.getGrade()
                .equals(EntityUtil.getNullableCode(practiceJournal.getGrade()))) {
            practiceJournal.setGradeInserted(LocalDateTime.now());
        }
        EntityUtil.bindToEntity(practiceJournalEntriesTeacherForm, practiceJournal, classifierRepository,
                "practiceJournalEntries", "practiceJournalFiles");
        updatePracticeJournalTeacherEntries(practiceJournal, practiceJournalEntriesTeacherForm);
        updatePracticeJournalFiles(practiceJournal, practiceJournalEntriesTeacherForm);
        return EntityUtil.save(practiceJournal, em);
    }
    public PracticeJournal saveEntriesSupervisor(PracticeJournal practiceJournal,
            PracticeJournalEntriesSupervisorForm practiceJournalEntriesSupervisorForm) {
        assertSupervisorSaveEntries(practiceJournal);
        EntityUtil.bindToEntity(practiceJournalEntriesSupervisorForm, practiceJournal, classifierRepository,
                "practiceJournalEntries", "practiceJournalFiles", "supervisorPracticeEvalCriteria");
        updatePracticeJournalSupervisorEvaluations(practiceJournal, practiceJournalEntriesSupervisorForm);
        updatePracticeJournalSupervisorEntries(practiceJournal, practiceJournalEntriesSupervisorForm);
        updatePracticeJournalSupervisorFiles(practiceJournal, practiceJournalEntriesSupervisorForm);
        return EntityUtil.save(practiceJournal, em);
    }

    private static void updatePracticeJournalSupervisorFiles(PracticeJournal practiceJournal,
            PracticeJournalEntriesSupervisorForm practiceJournalEntriesSupervisorForm) {
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalFiles(), EntityUtil::getId,
                practiceJournalEntriesSupervisorForm.getPracticeJournalFiles(), OisFileForm::getId, dto -> {
                    PracticeJournalFile file = new PracticeJournalFile();
                    file.setPracticeJournal(practiceJournal);
                    file.setIsStudent(dto.getIsStudent());
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
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalFiles(), EntityUtil::getId,
                practiceJournalEntriesForm.getPracticeJournalFiles(), OisFileForm::getId, dto -> {
                    PracticeJournalFile file = new PracticeJournalFile();
                    file.setPracticeJournal(practiceJournal);
                    file.setIsStudent(dto.getIsStudent());
                    file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
                    return file;
                });
    }

    private static void updatePracticeJournalStudentFiles(PracticeJournal practiceJournal,
            PracticeJournalEntriesStudentForm practiceJournalEntriesForm) {
        EntityUtil.bindEntityCollection(practiceJournal.getPracticeJournalFiles(), EntityUtil::getId,
                practiceJournalEntriesForm.getPracticeJournalStudentFiles(), OisFileForm::getId, dto -> {
                    PracticeJournalFile file = new PracticeJournalFile();
                    file.setPracticeJournal(practiceJournal);
                    file.setIsStudent(dto.getIsStudent());
                    file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
                    return file;
                });
    }

    public PracticeJournal getFromSupervisorUrl(String uuid) {
        List<?> data = em.createNativeQuery("select c.id from contract c "
                + "join contract_supervisor cs on cs.contract_id = c.id where cs.supervisor_url = ?1")
                .setParameter(1, uuid)
                .setMaxResults(1).getResultList();
        if (data.isEmpty()) {
            log.error("no contract found. uuid={}", uuid);
            return null;
        }
        Long contractId = resultAsLong(data.get(0), 0);
        PracticeJournal practiceJournal = findByContractId(contractId);
        if (practiceJournal == null) {
            log.error("no practice journal found. uuid={}, contractId={}", uuid, contractId);
        }

        return practiceJournal;
    }

    public PracticeJournal findByContractId(Long contractId) {
        List<PracticeJournal> result = em.createQuery("select pj from PracticeJournal pj where pj.contract.id = ?1", PracticeJournal.class)
                .setParameter(1, contractId)
                .setMaxResults(1).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public PracticeJournal open(PracticeJournal practiceJournal, PracticeJournalForm practiceJournalForm) {
        save(practiceJournal, practiceJournalForm);
        practiceJournal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
        return EntityUtil.save(practiceJournal, em);
    }
}
