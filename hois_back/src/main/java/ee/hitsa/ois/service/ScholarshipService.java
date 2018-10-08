package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplicationFamily;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplicationFile;
import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.domain.scholarship.ScholarshipTermCourse;
import ee.hitsa.ois.domain.scholarship.ScholarshipTermCurriculum;
import ee.hitsa.ois.domain.scholarship.ScholarshipTermStudyForm;
import ee.hitsa.ois.domain.scholarship.ScholarshipTermStudyLoad;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Priority;
import ee.hitsa.ois.enums.ScholarshipStatus;
import ee.hitsa.ois.enums.ScholarshipType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshiApplicationRejectionForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationListSubmitForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipStudentApplicationForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipTermForm;
import ee.hitsa.ois.web.dto.ScholarshipTermApplicationSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationStudentDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipStudentRejectionDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermStudentDto;

@Transactional
@Service
public class ScholarshipService {

    private static final int SAIS_POINTS_MONTHS = 6;
    private static final int RESULT_PERIOD_MONTHS = 5;
    private static final String STUDENT_JOURNAL_RESULTS = " from journal_entry_student jes"
            + " join journal_entry je on je.id = jes.journal_entry_id"
            + " join journal_student js on js.id = jes.journal_student_id"
            + " join classifier grade on grade.code = jes.grade_code"
            + " where js.student_id = ?1 and grade.value ~ '^[0-9]' and jes.grade_inserted between ?2 and ?3"
            + " and je.entry_type_code in ?4";
    
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudyYearService studyYearService;
    
    /**
     * Create scholarship term
     *
     * @param user
     * @param form
     * @return 
     */
    public ScholarshipTerm create(HoisUserDetails user, ScholarshipTermForm form) {
        ScholarshipTerm term = new ScholarshipTerm();
        term.setSchool(em.getReference(School.class, user.getSchoolId()));
        term.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        return save(term, form);
    }

    public Page<ScholarshipTermSearchDto> list(HoisUserDetails user, ScholarshipSearchCommand command,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from scholarship_term st").sort(pageable);

        qb.requiredCriteria("st.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains("st.name_et", "nameEt", command.getNameEt());
        qb.optionalCriteria("st.type_code = :typeCode", "typeCode", command.getType());
        qb.optionalCriteria("st.study_period_id = :studyPeriod", "studyPeriod", command.getStudyPeriod());
        qb.optionalCriteria("st.type_code in (:typeCodes)", "typeCodes", command.getAllowedStipendTypes());
        qb.optionalCriteria("st.is_open = :isOpen", "isOpen", command.getIsOpen() == null ? null : Boolean.valueOf(command.getIsOpen().longValue() == 1L));

        String select = "st.id, st.name_et, st.type_code, st.application_start, st.application_end, st.places, st.is_open";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new ScholarshipTermSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                    resultAsLocalDate(r, 3), resultAsLocalDate(r, 4), resultAsLong(r, 5), resultAsBoolean(r, 6));
        });
    }

    public ScholarshipTermDto get(ScholarshipTerm term) {
        return ScholarshipTermDto.of(term);
    }

    public ScholarshipTerm save(ScholarshipTerm scholarshipTerm, ScholarshipTermForm form) {
        EntityUtil.bindToEntity(form, scholarshipTerm, classifierRepository, "curriculums", "studyLoads", "courses");
        scholarshipTerm.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        bindFormArraysToEntity(form, scholarshipTerm);
        return EntityUtil.save(scholarshipTerm, em);
    }

    private ScholarshipTerm bindFormArraysToEntity(ScholarshipTermForm form, ScholarshipTerm scholarshipTerm) {
        if (form.getCourses() != null) {
            List<ScholarshipTermCourse> courses = scholarshipTerm.getScholarshipTermCourses();
            EntityUtil.bindEntityCollection(courses, c -> EntityUtil.getCode(c.getCourse()), form.getCourses(), c -> {
                ScholarshipTermCourse course = new ScholarshipTermCourse();
                course.setScholarshipTerm(scholarshipTerm);
                course.setCourse(
                        EntityUtil.validateClassifier(em.getReference(Classifier.class, c), MainClassCode.KURSUS));
                return course;
            });
        } else if (scholarshipTerm.getScholarshipTermCourses() != null) {
            scholarshipTerm.getScholarshipTermCourses().clear();
        }

        if (form.getCurriculums() != null) {
            List<ScholarshipTermCurriculum> curriculums = scholarshipTerm.getScholarshipTermCurriculums();
            EntityUtil.bindEntityCollection(curriculums, c -> EntityUtil.getId(c.getCurriculum()),
                    form.getCurriculums(), c -> c.getId(), c -> {
                        ScholarshipTermCurriculum curriculum = new ScholarshipTermCurriculum();
                        curriculum.setScholarshipTerm(scholarshipTerm);
                        curriculum.setCurriculum(em.getReference(Curriculum.class, c.getId()));
                        return curriculum;
                    });
        } else if (scholarshipTerm.getScholarshipTermCurriculums() != null) {
            scholarshipTerm.getScholarshipTermCurriculums().clear();
        }

        if (form.getStudyForms() != null) {
            List<ScholarshipTermStudyForm> studyForms = scholarshipTerm.getScholarshipTermStudyForms();
            EntityUtil.bindEntityCollection(studyForms, c -> EntityUtil.getCode(c.getStudyForm()), form.getStudyForms(),
                    c -> {
                        ScholarshipTermStudyForm studyForm = new ScholarshipTermStudyForm();
                        studyForm.setScholarshipTerm(scholarshipTerm);
                        studyForm.setStudyForm(EntityUtil.validateClassifier(em.getReference(Classifier.class, c),
                                MainClassCode.OPPEVORM));
                        return studyForm;
                    });
        } else if (scholarshipTerm.getScholarshipTermStudyForms() != null) {
            scholarshipTerm.getScholarshipTermStudyForms().clear();
        }

        if (form.getStudyLoads() != null) {
            List<ScholarshipTermStudyLoad> studyLoads = scholarshipTerm.getScholarshipTermStudyLoads();
            EntityUtil.bindEntityCollection(studyLoads, c -> EntityUtil.getCode(c.getStudyLoad()), form.getStudyLoads(),
                    c -> {
                        ScholarshipTermStudyLoad studyLoad = new ScholarshipTermStudyLoad();
                        studyLoad.setScholarshipTerm(scholarshipTerm);
                        studyLoad.setStudyLoad(EntityUtil.validateClassifier(em.getReference(Classifier.class, c),
                                MainClassCode.OPPEKOORMUS));
                        return studyLoad;
                    });
        } else if (scholarshipTerm.getScholarshipTermStudyLoads() != null) {
            scholarshipTerm.getScholarshipTermStudyLoads().clear();
        }

        return scholarshipTerm;
    }

    public ScholarshipTerm publish(ScholarshipTerm scholarshipTerm) {
        scholarshipTerm.setIsOpen(Boolean.TRUE);
        return EntityUtil.save(scholarshipTerm, em);
    }

    public List<ScholarshipTermStudentDto> availableStipends(Long studentId) {
        return availableStipends(studentId, Boolean.FALSE);
    }

    public List<ScholarshipTermStudentDto> availableDrGrants(Long studentId) {
        return availableStipends(studentId, Boolean.TRUE);
    }
    
    public List<ScholarshipApplicationStudentDto> studentStipends(Long studentId) {
        return scholarshipApplicationStudentDtos(studentStipends(studentId, Boolean.FALSE));
    }

    public List<ScholarshipApplicationStudentDto> studentDrGrants(Long studentId) {
        return scholarshipApplicationStudentDtos(studentStipends(studentId, Boolean.TRUE));
    }

    private List<ScholarshipTermStudentDto> availableStipends(Long studentId, Boolean drGrants) {
        Student student = em.getReference(Student.class, studentId);
        TypedQuery<ScholarshipTerm> query = em.createQuery(
                "SELECT st FROM ScholarshipTerm st JOIN st.scholarshipTermCurriculums stc WHERE stc.curriculum.id = (?1) and"
                        + " st.isOpen = true and st.school.id = (?2) and ((st.applicationStart <= (?3) and st.applicationEnd >= (?3))"
                        + " or st.applicationStart is null and st.applicationEnd is null)"
                        + (Boolean.TRUE.equals(drGrants) ? " and st.type.code = ?4" : 
                            (Boolean.FALSE.equals(drGrants) ? " and st.type.code != ?4" : "")), 
                        ScholarshipTerm.class)
                .setParameter(1, EntityUtil.getId(student.getCurriculumVersion().getCurriculum()))
                .setParameter(2, EntityUtil.getId(student.getSchool()))
                .setParameter(3, LocalDate.now());
        if (drGrants != null) {
            query = query.setParameter(4, ScholarshipType.STIPTOETUS_DOKTOR.name());
        }
        List<ScholarshipTerm> result = query.getResultList();
        result.removeIf(it -> !studentCompliesTerm(student, it));
        return StreamUtil.toMappedList(st -> ScholarshipTermStudentDto.of(st), result);
    }

    private List<ScholarshipApplication> studentStipends(Long studentId, Boolean drGrants) {
        TypedQuery<ScholarshipApplication> query = em.createQuery("SELECT sa FROM ScholarshipApplication sa"
                + " WHERE sa.student.id = (?1)"
                + (Boolean.TRUE.equals(drGrants) ? " and sa.scholarshipTerm.type.code = ?2" : 
                    (Boolean.FALSE.equals(drGrants) ? " and sa.scholarshipTerm.type.code != ?2" : "")),
                ScholarshipApplication.class)
                .setParameter(1, studentId);
        if (drGrants != null) {
            query = query.setParameter(2, ScholarshipType.STIPTOETUS_DOKTOR.name());
        }
        return query.getResultList();
    }

    private static List<ScholarshipApplicationStudentDto> scholarshipApplicationStudentDtos(List<ScholarshipApplication> stipends) {
        return StreamUtil.toMappedList(sa -> {
            ScholarshipApplicationStudentDto dto = new ScholarshipApplicationStudentDto();
            dto.setId(EntityUtil.getId(sa));
            ScholarshipTerm term = sa.getScholarshipTerm();
            dto.setTermId(EntityUtil.getId(term));
            dto.setType(EntityUtil.getCode(term.getType()));
            dto.setAverageMark(sa.getAverageMark());
            dto.setLastPeriodMark(sa.getLastPeriodMark());
            dto.setCurriculumCompletion(sa.getCurriculumCompletion());
            dto.setAbsences(sa.getAbsences());
            dto.setStatus(EntityUtil.getCode(sa.getStatus()));
            dto.setDecisionDate(sa.getDecisionDate());
            dto.setRejectComment(sa.getRejectComment());
            dto.setIsTeacherConfirm(sa.getIsTeacherConfirmed());
            dto.setNeedsConfirm(sa.getScholarshipTerm().getIsTeacherConfirm());
            return dto;
        }, stipends);
    }

    public Map<String, Object> getStudentApplicationView(HoisUserDetails user, ScholarshipTerm term) {
        Map<String, Object> result = new HashMap<>();
        result.put("stipend", ScholarshipTermApplicationDto.of(term));
        result.put("application", getStudentApplicationDto(user, term));
        return result;
    }

    public ScholarshipApplicationDto getStudentApplicationDto(HoisUserDetails user, ScholarshipTerm term) {
        Student student = em.getReference(Student.class, user.getStudentId());
        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        ScholarshipApplicationDto applicationDto = application == null ? 
                getApplicationDto(student, term) : getApplicationDto(application);
        applicationDto.setAddress(student.getPerson().getAddress());
        StudentResults results = getStudentResults(term, student);
        BigDecimal credits = results.getCredits();
        applicationDto.setCredits(credits);
        applicationDto.setAverageMark(results.getAverageMark());
        applicationDto.setLastPeriodMark(results.getLastPeriodMark());
        applicationDto.setCurriculumCompletion(StudentUtil.getCurriculumCompletion(credits, student));
        if (application == null) {
            applicationDto.setPhone(student.getPerson().getPhone());
            applicationDto.setEmail(student.getEmail());
        }
        List<ScholarshipApplication> prevApplications = studentStipends(user.getStudentId(), null);
        if (!prevApplications.isEmpty()) {
            prevApplications = prevApplications.stream()
                    .filter(a -> a.getBankAccount() != null)
                    .sorted(Comparator.comparing(a -> a.getInserted(), Comparator.reverseOrder()))
                    .collect(Collectors.toList());
            if (!prevApplications.isEmpty()) {
                applicationDto.setBankAccount(prevApplications.get(0).getBankAccount());
            }
        }
        return applicationDto;
    }

    public Map<String, Object> getApplicationView(HoisUserDetails user, ScholarshipApplication application) {
        Student student = application.getStudent();
        if (user.isStudent()) {
            UserUtil.throwAccessDeniedIf(!user.getStudentId().equals(EntityUtil.getId(student)), 
                    "Student can only view his or her own application");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("stipend", ScholarshipTermApplicationDto.of(application.getScholarshipTerm()));
        result.put("application", getApplicationDto(application));
        return result;
    }

    public ScholarshipApplication saveApplication(HoisUserDetails user, ScholarshipTerm term,
            ScholarshipStudentApplicationForm form) {
        Student student = em.getReference(Student.class, user.getStudentId());
        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        if (application != null) {
            return application;
        }
        if(!studentCompliesTerm(student, term)) {
            return null;
        }
        application = new ScholarshipApplication();
        application.setScholarshipTerm(term);
        application.setStatus(em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_K.name()));
        application.setStudent(student);
        application.setStudentGroup(student.getStudentGroup());
        refreshCompletionWithApplication(application);
        refreshAddressWithApplication(application);
        application.setCurriculumVersion(student.getCurriculumVersion());
        application = bindApplicationFormToApplication(application, form);
        return EntityUtil.save(application, em);
    }

    private void refreshCompletionWithApplication(ScholarshipApplication application) {
        ScholarshipTerm term = application.getScholarshipTerm();
        Student student = application.getStudent();
        StudentResults results = getStudentResults(term, student);
        
        application.setCredits(results.getCredits());
        
        application.setCurriculumCompletion(term.getCurriculumCompletion() == null ? null : 
            StudentUtil.getCurriculumCompletion(results.getCredits(), student));
        application.setAverageMark(term.getAverageMark() == null ? null : 
            useSaisPoints(term, student) ? getSaisPoints(student) : results.getAverageMark());
        application.setLastPeriodMark(term.getLastPeriodMark() == null ? null : results.getLastPeriodMark());
        application.setAbsences(term.getMaxAbsences() == null ? null : results.getAbsences());
    }

    private StudentResults getHigherResults(Student student) {
        StudentResults results = new StudentResults();
        BigDecimal credits = getCredits(student);
        results.setCredits(credits);
        Long schoolId = EntityUtil.getId(student.getSchool());
        results.setAverageMark(getAverageGrade(student, studyYearService.getCurrentStudyPeriod(schoolId), credits));
        results.setLastPeriodMark(getAverageGrade(student, studyYearService.getPreviousStudyPeriod(schoolId), credits));
        return results;
    }

    private StudentResults getVocationalResults(Student student) {
        StudentResults results = new StudentResults();
        StudentCurriculumCompletion studentCurriculumCompletion = studentService.getStudentCurriculumCompletion(student);
        BigDecimal credits = studentCurriculumCompletion == null ? null : studentCurriculumCompletion.getCredits();
        results.setCredits(credits == null ? BigDecimal.ZERO : credits);
        LocalDateTime currentTime = LocalDateTime.now();
        results.setAverageMark(getAverageGrade(student, currentTime));
        results.setLastPeriodMark(getAverageGrade(student, currentTime.minusMonths(RESULT_PERIOD_MONTHS)));
        results.setAbsences(getAbsences(student, currentTime));
        return results;
    }

    private StudentResults getStudentResults(ScholarshipTerm term, Student student) {
        Classifier termType = term.getType();
        if (termType.isHigher()) {
            return getHigherResults(student);
        } else if (termType.isVocational()) {
            return getVocationalResults(student);
        }
        throw new AssertionFailedException("Scholarship term type must be higher or vocational");
    }

    private BigDecimal getAverageGrade(Student student, LocalDateTime periodEnd) {
        LocalDateTime truncatedPeriodEnd = DateUtils.startOfMonth(periodEnd);
        return getAverageGrade(student, getResultPeriodStart(truncatedPeriodEnd), truncatedPeriodEnd);
    }

    private Long getAbsences(Student student, LocalDateTime periodEnd) {
        LocalDateTime truncatedPeriodEnd = DateUtils.startOfMonth(periodEnd);
        return getAbsences(student, getResultPeriodStart(truncatedPeriodEnd), truncatedPeriodEnd);
    }

    private BigDecimal getAverageGrade(Student student, LocalDateTime periodStart, LocalDateTime periodEnd) {
        Number result = (Number) em.createNativeQuery("select avg(grade_value) as avg_grade from ("
                    + "select CAST(grade.value AS integer) as grade_value"
                    + " from student_vocational_result svr"
                    + " join classifier grade on grade.code = grade_code"
                    + " where svr.student_id = ?1 and grade.value ~ '^[0-9]' and svr.grade_date between ?2 and ?3"
                + " union"
                    + " select CAST(g.grade_value AS integer) as grade_value"
                    + " from (select js.journal_id, jes.grade_inserted, grade.value as grade_value"
                        + STUDENT_JOURNAL_RESULTS + ") g"
                    + " join (select je.journal_id, max(jes.grade_inserted) as max_grade_inserted"
                        + STUDENT_JOURNAL_RESULTS + " group by je.journal_id) lg"
                    + " on g.journal_id = lg.journal_id and g.grade_inserted = lg.max_grade_inserted"
                + ") results")
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(2, JpaQueryUtil.parameterAsTimestamp(periodStart))
                .setParameter(3, JpaQueryUtil.parameterAsTimestamp(periodEnd))
                .setParameter(4, EnumUtil.toNameList(JournalEntryType.SISSEKANNE_O, JournalEntryType.SISSEKANNE_R,
                        JournalEntryType.SISSEKANNE_L))
                .getSingleResult();
        return result == null ? null : BigDecimal.valueOf(result.doubleValue());
    }

    private BigDecimal getAverageGrade(Student student, Long studyPeriodId, BigDecimal credits) {
        Number result = (Number) em.createNativeQuery("select sum(grade_mark * credits) as weighted_grade_sum"
                + " from student_higher_result shr"
                + " where shr.student_id = ?1 and shr.study_period_id = ?2")
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(2, studyPeriodId)
                .getSingleResult();
        return result == null ? null : BigDecimal.valueOf(result.doubleValue()).divide(credits, 3, RoundingMode.HALF_UP);
    }

    private BigDecimal getCredits(Student student) {
        Number result = (Number) em.createNativeQuery("select sum(credits) as total_credits"
                + " from student_higher_result shr"
                + " where shr.student_id = ?1")
                .setParameter(1, EntityUtil.getId(student))
                .getSingleResult();
        return result == null ? BigDecimal.ZERO : BigDecimal.valueOf(result.doubleValue());
    }

    private static boolean useSaisPoints(ScholarshipTerm term, Student student) {
        return term.getType().isHigher() && student.getStudyStart() != null && 
                LocalDate.now().minusMonths(SAIS_POINTS_MONTHS).isBefore(student.getStudyStart());
    }
    
    private BigDecimal getSaisPoints(Student student) {
        List<?> result = em.createNativeQuery("select sa.points"
                + " from directive_student ds"
                + " join sais_application sa on sa.id = ds.sais_application_id"
                + " where ds.canceled = false and ds.student_id = ?1")
                .setParameter(1, EntityUtil.getId(student))
                .setMaxResults(1)
                .getResultList();
        return result.isEmpty() ? null : BigDecimal.valueOf(((Number) result.get(0)).doubleValue());
    }

    private Long getAbsences(Student student, LocalDateTime periodStart, LocalDateTime periodEnd) {
        Number result = (Number) em.createNativeQuery("select sum(1) as absences"
                + " from journal_entry_student jes"
                + " join journal_student js on js.id = jes.journal_student_id"
                + " join journal_entry je on je.id = jes.journal_entry_id"
                + " where js.student_id = ?1 and jes.absence_code = ?2"
                + " and je.entry_date between ?3 and ?4")
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(2, Absence.PUUDUMINE_P.name())
                .setParameter(3, JpaQueryUtil.parameterAsTimestamp(periodStart))
                .setParameter(4, JpaQueryUtil.parameterAsTimestamp(periodEnd))
                .getSingleResult();
        return result == null ? Long.valueOf(0) : Long.valueOf(result.longValue());
    }

    private static LocalDateTime getResultPeriodStart(LocalDateTime periodEnd) {
        LocalDateTime periodStart = periodEnd.minusMonths(RESULT_PERIOD_MONTHS);
        int endMonth = periodEnd.getMonthValue();
        if (endMonth >= 9 || endMonth <= (RESULT_PERIOD_MONTHS - 4)) {
            periodStart = periodStart.minusMonths(2); // skip July and August
        }
        return periodStart;
    }
    
    private static void refreshAddressWithApplication(ScholarshipApplication application) {
        Person person = application.getStudent().getPerson();
        application.setAddress(person.getAddress());
        application.setAddressAds(person.getAddressAds());
        application.setAddressAdsOid(person.getAddressAdsOid());
    }

    public ScholarshipApplication updateApplication(HoisUserDetails user, ScholarshipStudentApplicationForm form,
            ScholarshipApplication application) {
        if (application == null || !EntityUtil.getId(application.getStudent()).equals(user.getStudentId())) {
            return null;
        }
        if (!ClassifierUtil.oneOf(application.getStatus(), ScholarshipStatus.STIPTOETUS_STAATUS_K, ScholarshipStatus.STIPTOETUS_STAATUS_T)) {
            return application;
        }
        refreshCompletionWithApplication(application);
        refreshAddressWithApplication(application);
        bindApplicationFormToApplication(application, form);
        return EntityUtil.save(application, em);
    }

    private ScholarshipApplication bindApplicationFormToApplication(ScholarshipApplication application,
            ScholarshipStudentApplicationForm form) {
        EntityUtil.bindToEntity(form, application, classifierRepository, "files", "family");
        if (form.getFiles() != null) {
            List<ScholarshipApplicationFile> files = application.getScholarshipApplicationFiles();
            EntityUtil.bindEntityCollection(files, EntityUtil::getId, form.getFiles(), f -> f.getId(), f -> {
                ScholarshipApplicationFile file = new ScholarshipApplicationFile();
                file.setScholarshipApplication(application);
                file.setOisFile(EntityUtil.bindToEntity(f.getOisFile(), new OisFile()));
                EntityUtil.save(file.getOisFile(), em);
                return file;
            });
        } else {
            if (application.getScholarshipApplicationFiles() != null) {
                application.getScholarshipApplicationFiles().clear();
            }
        }
        List<ScholarshipApplicationFamily> families = application.getScholarshipApplicationFamilies();
        EntityUtil.bindEntityCollection(families, EntityUtil::getId, form.getFamily(), f -> f.getId(), f -> {
            ScholarshipApplicationFamily fam = new ScholarshipApplicationFamily();
            fam.setScholarshipApplication(application);
            return EntityUtil.bindToEntity(f, fam);
        });
        return application;
    }

    private ScholarshipApplication getApplicationForTermAndStudent(ScholarshipTerm term, Student student) {
        List<ScholarshipApplication> result = em.createQuery(
                "SELECT sa FROM ScholarshipApplication sa WHERE sa.scholarshipTerm.id = (?1) AND sa.student.id = (?2)",
                ScholarshipApplication.class).setParameter(1, EntityUtil.getId(term))
                .setParameter(2, EntityUtil.getId(student)).setMaxResults(1).getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    private ScholarshipApplicationDto getApplicationDto(Student student, ScholarshipTerm term) {
        ScholarshipApplicationDto dto = new ScholarshipApplicationDto();
        dto.setFiles(new ArrayList<>());
        dto.setCanApply(Boolean.TRUE);
        dto.setUseSaisPoints(Boolean.valueOf(useSaisPoints(term, student)));
        if (Boolean.TRUE.equals(dto.getUseSaisPoints())) {
            dto.setSaisPoints(getSaisPoints(student));
        }
        return dto;
    }

    public ScholarshipApplicationDto getApplicationDto(ScholarshipApplication application) {
        ScholarshipApplicationDto dto = ScholarshipApplicationDto.of(application);
        Student student = application.getStudent();
        dto.setUseSaisPoints(Boolean.valueOf(useSaisPoints(application.getScholarshipTerm(), student)));
        if (Boolean.TRUE.equals(dto.getUseSaisPoints())) {
            dto.setSaisPoints(getSaisPoints(student));
        }
        return dto;
    }

    public ScholarshipApplication apply(HoisUserDetails user, ScholarshipApplication application) {
        if (user.getStudentId().equals(EntityUtil.getId(application.getStudent()))
                && studentCompliesTerm(application.getStudent(), application.getScholarshipTerm())) {
            application.setStatus(em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_E.name()));
            return EntityUtil.save(application, em);
        }
        throw new ValidationFailedException("stipend.messages.error.studentDoesntComply");
    }

    public ScholarshipTermApplicationSearchDto applications(ScholarshipApplicationSearchCommand command,
            HoisUserDetails user) {
        List<ScholarshipApplicationSearchDto> applications = applicationsForCommand(command, user);
        
        ScholarshipTerm term;
        if(!applications.isEmpty()) {
            term = em.getReference(ScholarshipTerm.class, applications.get(0).getTerm());
        } else {
            return null;
        }
        Collections.sort(applications, comparatorForTerm(term));
        
        return new ScholarshipTermApplicationSearchDto(term.getPlaces(), applications);
    }

    private List<ScholarshipApplicationSearchDto> applicationsForCommand(ScholarshipApplicationSearchCommand command, HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from scholarship_application sa" 
                        + " join scholarship_term st on st.id = sa.scholarship_term_id"
                        + " join student s on s.id = sa.student_id" 
                        + " join person p on p.id = s.person_id"
                        + " join student_group sg on sg.id = sa.student_group_id"
                        + " join curriculum_version cv on sa.curriculum_version_id = cv.id"
                        + " join curriculum c on c.id = cv.curriculum_id");

        qb.requiredCriteria("st.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isTeacher()) {
            qb.requiredCriteria("sa.student_group_id in (select sg.id from student_group sg"
                    + " where sg.teacher_id = :teacherId)", "teacherId", user.getTeacherId());
        }
        
        qb.optionalContains("st.name_et", "nameEt", command.getNameEt());
        qb.optionalCriteria("st.type_code = :typeCode", "typeCode", command.getType());
        qb.optionalCriteria("sa.status_code = :status", "status", command.getStatus());
        qb.optionalCriteria("st.study_period_id = :studyPeriod", "studyPeriod", command.getStudyPeriod());
        qb.optionalCriteria("sa.scholarship_term_id in (select scholarship_term_id from scholarship_term_course where " 
                + "course_code in (:courseCodes))", "courseCodes", command.getCourses());
        qb.optionalCriteria("c.id in (:curriculumIds)", "curriculumIds", command.getCurriculum());
        /*qb.optionalCriteria("sa.scholarship_term_id in (select scholarship_term_id from scholarship_term_curriculum where "
                + "curriculum_id in (:curriculumIds))", "curriculumIds", command.getCurriculum());*/
        qb.optionalContains(Arrays.asList("sg.code"), "studentGroup", command.getStudentGroup());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname"), "personName", command.getStudentName());

        qb.requiredCriteria("sa.status_code != :compositionStatus", "compositionStatus",
                ScholarshipStatus.STIPTOETUS_STAATUS_K);

        String select = "sa.id as application_id, st.type_code, st.id as term_id, st.name_et, c.code, s.id as student_id"
                + ", p.firstname, p.lastname, p.idcode, sa.average_mark, sa.last_period_mark , sa.curriculum_completion"
                + ", sa.is_teacher_confirmed, sa.status_code, sa.compensation_reason_code, sa.compensation_frequency_code"
                + ", sa.credits, sa.absences, sa.reject_comment, st.is_teacher_confirm"
                + ", (select case when s.study_start > date(now()) - interval '" + SAIS_POINTS_MONTHS + " months'"
                + " then sais.points else null end"
                + " from directive_student ds"
                + " join sais_application sais on sais.id = ds.sais_application_id"
                + " where ds.canceled = false and ds.student_id = s.id) as sais_points";
        List<?> data = qb.select(select, em).getResultList();
        return StreamUtil.toMappedList(r -> {
        	ScholarshipApplicationSearchDto dto = new ScholarshipApplicationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setTerm(resultAsLong(r, 2));
            dto.setTermNameEt(resultAsString(r, 3));
            dto.setCurriculumCode(resultAsString(r, 4));
            dto.setStudentId(resultAsLong(r, 5));
            dto.setStudentName(PersonUtil.fullname(resultAsString(r, 6), resultAsString(r, 7)));
            dto.setFirstName(resultAsString(r, 6));
            dto.setLastName(resultAsString(r, 7));
            dto.setIdcode(resultAsString(r, 8));
            dto.setAverageMark(resultAsDecimal(r, 9));
            dto.setLastPeriodMark(resultAsDecimal(r, 10));
            dto.setCurriculumCompletion(resultAsDecimal(r, 11));
            dto.setIsTeacherConfirm(resultAsBoolean(r, 12));
            dto.setStatus(resultAsString(r, 13));
            dto.setCompensationReason(resultAsString(r, 14));
            dto.setCompensationFrequency(resultAsString(r, 15));
            dto.setCredits(resultAsDecimal(r, 16));
            dto.setAbsences(resultAsLong(r, 17));
            dto.setRejectComment(resultAsString(r, 18));
            dto.setNeedsConfirm(resultAsBoolean(r, 19));
            dto.setSaisPoints(resultAsDecimal(r, 20));
            return dto;
        }, data);
    }

    public HttpStatus acceptApplications(HoisUserDetails user, List<Long> applicationIds) {
        List<ScholarshipApplication> result = getApplications(applicationIds);
        result.removeIf(a -> !UserUtil.isSameSchool(user, a.getScholarshipTerm().getSchool()));
        updateApplicationStatuses(result, ScholarshipStatus.STIPTOETUS_STAATUS_A);
        return HttpStatus.OK;
    }

    public HttpStatus annulApplications(ScholarshipApplicationListSubmitForm form, HoisUserDetails user) {
        List<ScholarshipApplication> result = getApplications(form);
        result = setRejectionComments(form, result);
        result.removeIf(a -> !UserUtil.isSameSchool(user, a.getScholarshipTerm().getSchool()));
        updateApplicationStatuses(result, ScholarshipStatus.STIPTOETUS_STAATUS_T);
        return HttpStatus.OK;
    }

    public HttpStatus rejectApplications(ScholarshipApplicationListSubmitForm form, HoisUserDetails user) {
        List<ScholarshipApplication> result = getApplications(form);
        result = setRejectionComments(form, result);
        result.removeIf(a -> !UserUtil.isSameSchool(user, a.getScholarshipTerm().getSchool()));
        updateApplicationStatuses(result, ScholarshipStatus.STIPTOETUS_STAATUS_L);
        return HttpStatus.OK;
    }

    public HttpStatus refreshResults(HoisUserDetails user, List<Long> applicationIds) {
        List<ScholarshipApplication> applications = getApplications(applicationIds);
        checkAccess(user, applications);
        for (ScholarshipApplication application : applications) {
            refreshCompletionWithApplication(application);
            EntityUtil.save(application, em);
        }
        return HttpStatus.OK;
    }

    public Map<Long, Boolean> checkComplies(HoisUserDetails user, List<Long> applicationIds) {
        List<ScholarshipApplication> applications = getApplications(applicationIds);
        checkAccess(user, applications);
        Map<Long, Boolean> result = new HashMap<>();
        for (ScholarshipApplication application : applications) {
            result.put(EntityUtil.getId(application), Boolean.valueOf(
                    studentCompliesTerm(application.getStudent(), application.getScholarshipTerm())));
        }
        return result;
    }

    public HttpStatus teacherConfirmApplications(HoisUserDetails user, List<Long> applicationIds, Boolean isTeacherConfirmed) {
        List<ScholarshipApplication> applications = getApplications(applicationIds);
        checkTeacherAccess(user, applications);
        updateApplicationTeacherConfirms(applications, isTeacherConfirmed);
        return HttpStatus.OK;
    }

    private static void checkAccess(HoisUserDetails user, List<ScholarshipApplication> applications) {
        for (ScholarshipApplication application : applications) {
            UserUtil.throwAccessDeniedIf(!UserUtil.isSameSchool(user, application.getScholarshipTerm().getSchool()), 
                    "User has no right to edit these applications");
        }
    }

    private static void checkTeacherAccess(HoisUserDetails user, List<ScholarshipApplication> applications) {
        for (ScholarshipApplication application : applications) {
            UserUtil.throwAccessDeniedIf(!UserUtil.isStudentGroupTeacher(user, application.getStudentGroup()), 
                    "User has no right to edit these applications");
        }
    }

    private static List<ScholarshipApplication> setRejectionComments(ScholarshipApplicationListSubmitForm form,
            List<ScholarshipApplication> applications) {
        Map<Long, ScholarshipApplication> applicationMap = StreamUtil.toMap(a -> EntityUtil.getId(a), applications);
        for(ScholarshiApplicationRejectionForm rej : form.getApplications()) {
            ScholarshipApplication app = applicationMap.get(rej.getId());
            app.setRejectComment(rej.getRejectComment());
        }
        return new ArrayList<>(applicationMap.values());
    }

    private List<ScholarshipApplication> getApplications(List<Long> applications) {
        return em.createQuery("SELECT sa FROM ScholarshipApplication sa WHERE sa.id in (?1)",
                ScholarshipApplication.class).setParameter(1, applications).getResultList();
    }

    private List<ScholarshipApplication> getApplications(ScholarshipApplicationListSubmitForm form) {
        return getApplications(StreamUtil.toMappedList(ScholarshiApplicationRejectionForm::getId, form.getApplications()));
    }

    private void updateApplicationStatuses(List<ScholarshipApplication> entities, ScholarshipStatus status) {
        Classifier statusCl = em.getReference(Classifier.class, status.name());
        for (ScholarshipApplication application : entities) {
            application.setStatus(statusCl);
            application.setDecisionDate(LocalDate.now());
            EntityUtil.save(application, em);
        }
    }

    private void updateApplicationTeacherConfirms(List<ScholarshipApplication> entities, Boolean isTeacherConfirmed) {
        for (ScholarshipApplication application : entities) {
            application.setIsTeacherConfirmed(isTeacherConfirmed);
            EntityUtil.save(application, em);
        }
    }

    public List<ScholarshipStudentRejectionDto> getStudentProfilesForRejection(List<Long> applicationIds) {
        List<ScholarshipApplication> applications = em
                .createQuery("SELECT sa FROM ScholarshipApplication sa where sa.id in (?1)",
                        ScholarshipApplication.class)
                .setParameter(1, applicationIds).getResultList();
        return StreamUtil.toMappedList(s -> ScholarshipStudentRejectionDto.of(s), applications);
    }

    private boolean studentCompliesTerm(Student student, ScholarshipTerm term) {
        if (Boolean.FALSE.equals(term.getIsAcademicLeave()) && ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_A, student.getStatus())) {
            return false;
        }
        if (!term.getScholarshipTermCourses().isEmpty() && (student.getStudentGroup() == null || !StreamUtil
                .toMappedList(c -> Short.valueOf(c.getCourse().getValue()), term.getScholarshipTermCourses())
                .contains(student.getStudentGroup().getCourse()))) {
            return false;
        }
        if (!term.getScholarshipTermStudyLoads().isEmpty() && !StreamUtil
                .toMappedList(l -> EntityUtil.getCode(l.getStudyLoad()), term.getScholarshipTermStudyLoads())
                .contains(EntityUtil.getNullableCode(student.getStudyLoad()))) {
            return false;
        }
        if (!term.getScholarshipTermStudyForms().isEmpty() && !StreamUtil
                .toMappedList(f -> EntityUtil.getCode(f.getStudyForm()), term.getScholarshipTermStudyForms())
                .contains(EntityUtil.getNullableCode(student.getStudyForm()))) {
            return false;
        }
        if (!StreamUtil
                .toMappedList(c -> EntityUtil.getId(c.getCurriculum()), term.getScholarshipTermCurriculums())
                .contains(EntityUtil.getId(student.getCurriculumVersion().getCurriculum()))) {
            return false;
        }
        if (term.getStudyStartPeriodStart() != null && term.getStudyStartPeriodEnd() != null
                && (student.getStudyStart().isBefore(term.getStudyStartPeriodStart())
                        || student.getStudyStart().isAfter(term.getStudyStartPeriodEnd()))
                && (!student.getStudyStart().isEqual(term.getStudyStartPeriodEnd())
                || !student.getStudyStart().isEqual(term.getStudyStartPeriodStart()))) {
            return false;
        }
        if (ClassifierUtil.oneOf(term.getType(), ScholarshipType.STIPTOETUS_DOKTOR, ScholarshipType.STIPTOETUS_POHI)
                && student.getNominalStudyEnd().isBefore(LocalDate.now())) {
            return false;
        }
        //if none of the priorities are defined then we dont need to check the completion for compliance
        if (term.getAverageMarkPriority() == null && term.getCurriculumCompletionPriority() == null
                && term.getLastPeriodMarkPriority() == null && term.getMaxAbsencesPriority() == null) {
            return true;
        }
        StudentResults results = getStudentResults(term, student);
        if (term.getAverageMark() != null && BigDecimal.ZERO.compareTo(term.getAverageMark()) != 0) {
            BigDecimal comparable = useSaisPoints(term, student) ? getSaisPoints(student) : results.getAverageMark();
            if (comparable == null || comparable.compareTo(term.getAverageMark()) < 0) {
                return false;
            }
        }
        if (term.getLastPeriodMark() != null) {
            if (results.getLastPeriodMark() == null || results.getLastPeriodMark().compareTo(term.getLastPeriodMark()) < 0) {
                return false;
            }
        }
        if (term.getCurriculumCompletion() != null) {
            if (StudentUtil.getCurriculumCompletion(results.getCredits(), student).compareTo(term.getCurriculumCompletion()) < 0) {
                return false;
            }
        }
        if (term.getMaxAbsences() != null) {
            if (results.getAbsences() != null && results.getAbsences().compareTo(term.getMaxAbsences()) > 0) {
                return false;
            }
        }
        return true;
    }

    private static final Map<Function<ScholarshipTerm, Classifier>, Comparator<ScholarshipApplicationSearchDto>> COMPARATOR = new HashMap<>();
    static {
        COMPARATOR.put(ScholarshipTerm::getAverageMarkPriority, Comparator.comparing(
                ScholarshipApplicationSearchDto::getAverageMark, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getLastPeriodMarkPriority, Comparator.comparing(
                ScholarshipApplicationSearchDto::getLastPeriodMark, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getCurriculumCompletionPriority, Comparator.comparing(
                ScholarshipApplicationSearchDto::getCurriculumCompletion, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getMaxAbsencesPriority, Comparator.comparing(
                ScholarshipApplicationSearchDto::getAbsences, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    private static Comparator<ScholarshipApplicationSearchDto> comparatorForTerm(ScholarshipTerm term) {
        List<Comparator<ScholarshipApplicationSearchDto>> comparators = new ArrayList<>(Collections.nCopies(Priority.values().length, null));
        for (Entry<Function<ScholarshipTerm, Classifier>, Comparator<ScholarshipApplicationSearchDto>> me : COMPARATOR.entrySet()) {
            Classifier priority = me.getKey().apply(term);
            if (priority != null) {
                comparators.set(Priority.valueOf(EntityUtil.getCode(priority)).ordinal(), me.getValue());
            }
        }

        comparators = StreamUtil.toFilteredList(r -> r != null, comparators);
        if (comparators.isEmpty()) {
            return Comparator.comparing(ScholarshipApplicationSearchDto::getLastName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(ScholarshipApplicationSearchDto::getFirstName, String.CASE_INSENSITIVE_ORDER);
        }

        Comparator<ScholarshipApplicationSearchDto> comparator = null;
        for (Comparator<ScholarshipApplicationSearchDto> c : comparators) {
            if (comparator == null) {
                comparator = c;
            } else {
                comparator = comparator.thenComparing(c);
            }
        }
        return comparator;
    }

    public void delete(ScholarshipTerm term) {
        if(!term.getIsOpen().booleanValue() && term.getScholarshipApplications().isEmpty()) {
            EntityUtil.deleteEntity(term, em);
        }
    }

    private static class StudentResults {
        private BigDecimal averageMark;
        private BigDecimal lastPeriodMark;
        private BigDecimal credits;
        private Long absences;
        
        public StudentResults() {}
        
        public BigDecimal getAverageMark() {
            return averageMark;
        }
        public void setAverageMark(BigDecimal averageMark) {
            this.averageMark = averageMark;
        }
        
        public BigDecimal getLastPeriodMark() {
            return lastPeriodMark;
        }
        public void setLastPeriodMark(BigDecimal lastPeriodMark) {
            this.lastPeriodMark = lastPeriodMark;
        }
        
        public BigDecimal getCredits() {
            return credits;
        }
        public void setCredits(BigDecimal credits) {
            this.credits = credits;
        }
        
        public Long getAbsences() {
            return absences;
        }
        public void setAbsences(Long absences) {
            this.absences = absences;
        }
        
    }
}
