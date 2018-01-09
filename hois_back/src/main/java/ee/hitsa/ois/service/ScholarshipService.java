package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
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
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Priority;
import ee.hitsa.ois.enums.ScholarshipStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationListSubmitForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipStudentApplicationForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipTermForm;
import ee.hitsa.ois.web.dto.ScholarshipTermApplicationSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipStudentDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermStudentDto;

@Transactional
@Service
public class ScholarshipService {

    @Autowired
    ClassifierRepository classifierRepository;
    @Autowired
    EntityManager em;

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
        // TODO: find out why this property isnt binding
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
        Student student = em.getReference(Student.class, studentId);
        // TODO add check for which stipendium terms the student can apply to
        List<ScholarshipTerm> result = em.createQuery(
                "SELECT st FROM ScholarshipTerm st JOIN st.scholarshipTermCurriculums stc WHERE stc.curriculum.id = (?1) and st.isOpen = true",
                ScholarshipTerm.class).setParameter(1, EntityUtil.getId(student.getCurriculumVersion().getCurriculum()))
                .getResultList();
        return StreamUtil.toMappedList(st -> ScholarshipTermStudentDto.of(st), result);
    }

    public List<ScholarshipTermStudentDto> studentStipends(Long studentId) {
        Student student = em.getReference(Student.class, studentId);
        List<ScholarshipApplication> result = em
                .createQuery("SELECT sa FROM ScholarshipApplication sa WHERE sa.student.id = (?1)",
                        ScholarshipApplication.class)
                .setParameter(1, EntityUtil.getId(student)).getResultList();
        return StreamUtil.toMappedList(sa -> {
            ScholarshipTermStudentDto dto = ScholarshipTermStudentDto.of(sa.getScholarshipTerm());
            dto.setStatus(EntityUtil.getCode(sa.getStatus()));
            dto.setDecisionDate(sa.getDecisionDate());
            return dto;
        }, result);
    }

    public Map<String, Object> getStudentApplicationView(HoisUserDetails user, ScholarshipTerm term) {
        Map<String, Object> result = new HashMap<>();
        Student student = em.getReference(Student.class, user.getStudentId());
        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        result.put("stipend", ScholarshipTermApplicationDto.of(term));
        result.put("studentInfo", ScholarshipStudentDto.of(student));
        result.put("studentSubmitData", ScholarshipApplicationDto.of(application));
        return result;
    }

    public Map<String, Object> getApplicationView(ScholarshipApplication application) {
        Map<String, Object> result = new HashMap<>();
        result.put("stipend", ScholarshipTermApplicationDto.of(application.getScholarshipTerm()));
        result.put("studentInfo", ScholarshipStudentDto.of(application));
        result.put("studentSubmitData", ScholarshipApplicationDto.of(application));
        return result;
    }

    public ScholarshipApplication saveApplication(HoisUserDetails user, ScholarshipTerm term,
            ScholarshipStudentApplicationForm form) {
        Student student = em.getReference(Student.class, user.getStudentId());
        if (getApplicationForTermAndStudent(term, student) != null) {
            return null;
        }
        // TODO: add validation that this STUDENT qualifies for this SCHOLARSHIP
        // TERM
        ScholarshipApplication application = new ScholarshipApplication();
        application.setScholarshipTerm(term);
        application.setStatus(em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_K.name()));
        application.setStudent(student);
        application.setStudentGroup(student.getStudentGroup());
        //TODO: replace the default value with an error
        application.setCredits(
                student.getStudentCurriculumCompletion() != null ? student.getStudentCurriculumCompletion().getCredits()
                        : BigDecimal.ONE);
        application.setCurriculumVersion(student.getCurriculumVersion());
        application = bindApplicationFormToApplication(application, form);
        return EntityUtil.save(application, em);
    }

    public ScholarshipApplication updateApplication(HoisUserDetails user, ScholarshipStudentApplicationForm form,
            ScholarshipApplication application) {
        // TODO: check if this application can be updated at the current moment
        // and by this user
        if (application == null || !EntityUtil.getId(application.getStudent()).equals(user.getStudentId())) {
            return null;
        }
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
        if (form.getFamily() != null && !form.getFamily().isEmpty()) {
            List<ScholarshipApplicationFamily> families = application.getScholarshipApplicationFamilies();
            EntityUtil.bindEntityCollection(families, EntityUtil::getId, form.getFamily(), f -> f.getId(), f -> {
                ScholarshipApplicationFamily fam = new ScholarshipApplicationFamily();
                fam.setScholarshipApplication(application);
                return EntityUtil.bindToEntity(f, fam);
            });
        } else {
            application.getScholarshipApplicationFamilies().clear();
        }
        return application;
    }

    private ScholarshipApplication getApplicationForTermAndStudent(ScholarshipTerm term, Student student) {
        List<ScholarshipApplication> result = em.createQuery(
                "SELECT sa FROM ScholarshipApplication sa WHERE sa.scholarshipTerm.id = (?1) AND sa.student.id = (?2)",
                ScholarshipApplication.class).setParameter(1, EntityUtil.getId(term))
                .setParameter(2, EntityUtil.getId(student)).setMaxResults(1).getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    public ScholarshipApplicationDto getStudentApplicationDto(ScholarshipApplication application) {
        return ScholarshipApplicationDto.of(application);
    }

    public ScholarshipApplication apply(HoisUserDetails user, ScholarshipApplication application) {
        // TODO: add logic to see if application can be applied for (check term
        // application dates)
        if (user.getStudentId().equals(EntityUtil.getId(application.getStudent()))) {
            application.setStatus(em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_E.name()));
            return EntityUtil.save(application, em);
        }
        return application;
    }

    public List<ScholarshipTermApplicationSearchDto> applications(ScholarshipApplicationSearchCommand command,
            HoisUserDetails user) {
        List<ScholarshipApplicationSearchDto> applications = applicationsForCommand(command, user);
        
        Map<Long, List<ScholarshipApplicationSearchDto>> applicationsByTerms = applications.stream()
                .collect(Collectors.groupingBy(r -> r.getTerm()));
        Map<Long, ScholarshipTerm> termsById = StreamUtil.toMap(r -> EntityUtil.getId(r),
                getTerms(applicationsByTerms.keySet()));
        sortScholarshipApplicationsByTerms(applicationsByTerms, termsById);
        
        List<ScholarshipTermApplicationSearchDto> result = new ArrayList<>();
        for(Map.Entry<Long, List<ScholarshipApplicationSearchDto>> entry : applicationsByTerms.entrySet()) {
            result.add(new ScholarshipTermApplicationSearchDto(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    private List<ScholarshipApplicationSearchDto> applicationsForCommand(ScholarshipApplicationSearchCommand command, HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from scholarship_application sa" + " inner join scholarship_term st on st.id = sa.scholarship_term_id"
                        + " left join scholarship_term_course stc on stc.scholarship_term_id = st.id"
                        + " left join scholarship_term_curriculum stcu on stcu.scholarship_term_id = st.id"
                        + " inner join student s on s.id = sa.student_id" + " inner join person p on p.id = s.person_id"
                        + " inner join student_group sg on sg.id = sa.student_group_id"
                        + " inner join curriculum c on c.id = sg.curriculum_id");

        qb.requiredCriteria("st.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains("st.name_et", "nameEt", command.getNameEt());
        qb.optionalCriteria("st.type_code = :typeCode", "typeCode", command.getType());
        qb.optionalCriteria("sa.status_code = :status", "status", command.getStatus());
        qb.optionalCriteria("st.study_period_id = :studyPeriod", "studyPeriod", command.getStudyPeriod());
        qb.optionalCriteria("stc.course_code in (:courseCodes)", "courseCodes", command.getCourses());
        qb.optionalCriteria("stcu.curriculum_id in (:curriculumIds)", "curriculumIds", command.getCurriculum());
        qb.optionalContains(Arrays.asList("sg.code"), "studentGroup", command.getStudentGroup());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname"), "personName", command.getStudentName());

        qb.requiredCriteria("sa.status_code != :compositionStatus", "compositionStatus",
                ScholarshipStatus.STIPTOETUS_STAATUS_K.name());

        String select = "distinct sa.id as application_id, st.type_code, st.id as term_id, st.name_et, c.code, s.id as student_id"
                + ", p.firstname, p.lastname, p.idcode, sa.average_mark, sa.last_period_mark , sa.curriculum_completion"
                + ", st.is_teacher_confirm, sa.status_code, sa.compensation_reason_code, sa.compensation_frequency_code, sa.credits";
        List<?> data = qb.select(select, em).getResultList();
        return StreamUtil.toMappedList(r -> new ScholarshipApplicationSearchDto(r), data);
    }

    private static void sortScholarshipApplicationsByTerms(Map<Long, List<ScholarshipApplicationSearchDto>> applicationsByTerms,
            Map<Long, ScholarshipTerm> termsById) {
        for (Map.Entry<Long, ScholarshipTerm> entry : termsById.entrySet()) {
            ScholarshipTerm term = entry.getValue();
            List<ScholarshipApplicationSearchDto> applications = applicationsByTerms.get(EntityUtil.getId(term));
            Collections.sort(applications, comparatorForTerm(term));
        }
    }

    public HttpStatus acceptApplications(ScholarshipApplicationListSubmitForm form) {
        List<ScholarshipApplication> result = getApplications(form.getApplications());
        Classifier status = em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_A.name());
        updateApplicationStatuses(result, status);
        return HttpStatus.OK;
    }

    public HttpStatus annulApplications(ScholarshipApplicationListSubmitForm form) {
        List<ScholarshipApplication> result = getApplications(form.getApplications());
        Classifier status = em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_T.name());
        updateApplicationStatuses(result, status);
        return HttpStatus.OK;
    }

    private List<ScholarshipApplication> getApplications(List<Long> applications) {
        return em.createQuery("SELECT sa FROM ScholarshipApplication sa WHERE sa.id in (?1)",
                ScholarshipApplication.class).setParameter(1, applications).getResultList();
    }
    
    private List<ScholarshipTerm> getTerms(Set<Long> terms) {
        return em.createQuery("SELECT st FROM ScholarshipTerm st WHERE st.id in (?1)",
                ScholarshipTerm.class).setParameter(1, terms).getResultList();
    }

    private void updateApplicationStatuses(List<ScholarshipApplication> entities, Classifier status) {
        for (ScholarshipApplication application : entities) {
            application.setStatus(status);
            application.setDecisionDate(LocalDate.now());
            EntityUtil.save(application, em);
        }
    }
    
    private static Comparator<ScholarshipApplicationSearchDto> comparatorForTerm(ScholarshipTerm term) {
        Map<String, Function<ScholarshipApplicationSearchDto, BigDecimal>> priorityMapForTerm = new HashMap<>();

        if (term.getAverageMarkPriority() != null) {
            priorityMapForTerm.put(EntityUtil.getCode(term.getAverageMarkPriority()),
                    ScholarshipApplicationSearchDto::getAverageMark);
        }
        if (term.getCurriculumCompletionPriority() != null) {
            priorityMapForTerm.put(EntityUtil.getCode(term.getCurriculumCompletionPriority()),
                    ScholarshipApplicationSearchDto::getCurriculumCompletion);
        }
        if (term.getLastPeriodMarkPriority() != null) {
            priorityMapForTerm.put(EntityUtil.getCode(term.getLastPeriodMarkPriority()),
                    ScholarshipApplicationSearchDto::getLastPeriodMark);
        }

        Comparator<ScholarshipApplicationSearchDto> comparator = Comparator
                .comparing(priorityMapForTerm.get(Priority.PRIORITEET_1.name()), Comparator.reverseOrder());
        if (priorityMapForTerm.get(Priority.PRIORITEET_2.name()) != null) {
            comparator.thenComparing(priorityMapForTerm.get(Priority.PRIORITEET_2.name()), Comparator.reverseOrder());
        }
        if (priorityMapForTerm.get(Priority.PRIORITEET_3.name()) != null) {
            comparator.thenComparing(priorityMapForTerm.get(Priority.PRIORITEET_3.name()), Comparator.reverseOrder());
        }
        return comparator;
    }

}
