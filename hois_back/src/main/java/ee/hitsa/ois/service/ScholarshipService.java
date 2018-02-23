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
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Priority;
import ee.hitsa.ois.enums.ScholarshipStatus;
import ee.hitsa.ois.enums.ScholarshipType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
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
import ee.hitsa.ois.web.dto.scholarship.ScholarshipStudentDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipStudentRejectionDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermStudentDto;
import ee.hitsa.ois.web.dto.student.StudentHigherResultDto;

@Transactional
@Service
public class ScholarshipService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private StudentResultHigherService studentResultHigherService;
    
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
        Student student = em.getReference(Student.class, studentId);
        List<ScholarshipTerm> result = em.createQuery(
                "SELECT st FROM ScholarshipTerm st JOIN st.scholarshipTermCurriculums stc WHERE stc.curriculum.id = (?1) and"
                        + " st.isOpen = true and st.school.id = (?2) and ((st.applicationStart <= (?3) and st.applicationEnd >= (?3))"
                        + " or st.applicationStart is null and st.applicationEnd is null)",
                ScholarshipTerm.class).setParameter(1, EntityUtil.getId(student.getCurriculumVersion().getCurriculum()))
                .setParameter(2, EntityUtil.getId(student.getSchool())).setParameter(3, LocalDate.now())
                .getResultList();
        result.removeIf(it -> !studentCompliesTerm(student, it));
        return StreamUtil.toMappedList(st -> ScholarshipTermStudentDto.of(st), result);
    }

    public List<ScholarshipTermStudentDto> scholarshipTermStudentDtos(Long studentId) {
        List<ScholarshipApplication> stipends = studentStipends(studentId);
        return StreamUtil.toMappedList(sa -> {
            ScholarshipTermStudentDto dto = ScholarshipTermStudentDto.of(sa.getScholarshipTerm());
            dto.setStatus(EntityUtil.getCode(sa.getStatus()));
            dto.setDecisionDate(sa.getDecisionDate());
            dto.setApplicationId(EntityUtil.getId(sa));
            dto.setRejectComment(sa.getRejectComment());
            return dto;
        }, stipends);
    }

    private List<ScholarshipApplication> studentStipends(Long studentId) {
        return em.createQuery("SELECT sa FROM ScholarshipApplication sa WHERE sa.student.id = (?1)",
                ScholarshipApplication.class).setParameter(1, studentId).getResultList();
    }

    public Map<String, Object> getStudentApplicationView(HoisUserDetails user, ScholarshipTerm term) {
        Map<String, Object> result = new HashMap<>();
        Student student = em.getReference(Student.class, user.getStudentId());
        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        result.put("stipend", ScholarshipTermApplicationDto.of(term));
        ScholarshipStudentDto studentDto = ScholarshipStudentDto.of(student, getStudentCurriculumCompletion(student));
        result.put("studentInfo", studentDto);
        ScholarshipApplicationDto studentSubmitData = ScholarshipApplicationDto.of(application);
        if (application == null) {
            studentSubmitData.setPhone(studentDto.getPhone());
            studentSubmitData.setEmail(studentDto.getEmail());
        }
        List<ScholarshipApplication> prevApplications = studentStipends(user.getStudentId());
        if (!prevApplications.isEmpty()) {
            prevApplications = prevApplications.stream().filter(a -> a.getBankAccount() != null)
                    .sorted(Comparator.comparing(a -> a.getInserted(), Comparator.reverseOrder()))
                    .collect(Collectors.toList());
            if (!prevApplications.isEmpty()) {
                studentSubmitData.setBankAccount(prevApplications.get(0).getBankAccount());
            }
        }
        result.put("studentSubmitData", studentSubmitData);
        return result;
    }

    public Map<String, Object> getApplicationView(ScholarshipApplication application) {
        Map<String, Object> result = new HashMap<>();
        result.put("stipend", ScholarshipTermApplicationDto.of(application.getScholarshipTerm()));
        result.put("studentInfo", ScholarshipStudentDto.of(application, getStudentCurriculumCompletion(application.getStudent())));
        result.put("studentSubmitData", ScholarshipApplicationDto.of(application));
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
        application.setCurriculumVersion(student.getCurriculumVersion());
        Person person = student.getPerson();
        application.setAddress(person.getAddress());
        application.setAddressAds(person.getAddressAds());
        application.setAddressAdsOid(person.getAddressAdsOid());
        application = bindApplicationFormToApplication(application, form);
        return EntityUtil.save(application, em);
    }

    private void refreshCompletionWithApplication(ScholarshipApplication application) {
        StudentCurriculumCompletion completion = getStudentCurriculumCompletion(application.getStudent());
        ScholarshipTerm term = application.getScholarshipTerm();
        if(completion != null) {
            application.setCredits(completion.getCredits());
        } else {
            application.setCredits(BigDecimal.ZERO);
        }

        if (term.getCurriculumCompletion() != null && completion != null) {
            application.setCurriculumCompletion(BigDecimal.ONE);
        } else if (term.getCurriculumCompletion() != null) {
            application.setCurriculumCompletion(BigDecimal.ZERO);
        }
        if (term.getAverageMark() != null && completion != null) {
            application.setAverageMark(completion.getAverageMark());
        } else if (term.getAverageMark() != null) {
            application.setAverageMark(BigDecimal.ZERO);
        }
        if (term.getMaxAbsences() != null && completion != null) {
            // TODO: replace with real absences
            application.setAbsences(Long.valueOf(0L));
        } else if (term.getMaxAbsences() != null) {
            application.setAbsences(Long.valueOf(0L));
        }
        if (term.getLastPeriodMark() != null && completion != null) {
            application.setLastPeriodMark(completion.getAverageMarkLastStudyPeriod());
        } else if (term.getLastPeriodMark() != null) {
            application.setLastPeriodMark(BigDecimal.ZERO);
        }
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

    public ScholarshipApplicationDto getStudentApplicationDto(ScholarshipApplication application) {
        return ScholarshipApplicationDto.of(application);
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
                ScholarshipStatus.STIPTOETUS_STAATUS_K);

        String select = "distinct sa.id as application_id, st.type_code, st.id as term_id, st.name_et, c.code, s.id as student_id"
                + ", p.firstname, p.lastname, p.idcode, sa.average_mark, sa.last_period_mark , sa.curriculum_completion"
                + ", st.is_teacher_confirm, sa.status_code, sa.compensation_reason_code, sa.compensation_frequency_code, sa.credits, sa.reject_comment";
        List<?> data = qb.select(select, em).getResultList();
        return StreamUtil.toMappedList(r -> new ScholarshipApplicationSearchDto(r), data);
    }

    public HttpStatus acceptApplications(ScholarshipApplicationListSubmitForm form, HoisUserDetails user) {
        List<ScholarshipApplication> result = getApplications(StreamUtil.toMappedList(a -> a.getId(), form.getApplications()));
        result.removeIf(a -> EntityUtil.getId(a.getScholarshipTerm().getSchool()).longValue() != user.getSchoolId().longValue());
        updateApplicationStatuses(result, ScholarshipStatus.STIPTOETUS_STAATUS_A);
        return HttpStatus.OK;
    }

    public HttpStatus annulApplications(ScholarshipApplicationListSubmitForm form, HoisUserDetails user) {
        List<ScholarshipApplication> result = getApplications(StreamUtil.toMappedList(a -> a.getId(), form.getApplications()));
        result = setRejectionComments(form, result);
        result.removeIf(a -> EntityUtil.getId(a.getScholarshipTerm().getSchool()).longValue() != user.getSchoolId().longValue());
        updateApplicationStatuses(result, ScholarshipStatus.STIPTOETUS_STAATUS_T);
        return HttpStatus.OK;
    }

    public HttpStatus rejectApplications(ScholarshipApplicationListSubmitForm form, HoisUserDetails user) {
        List<ScholarshipApplication> result = getApplications(StreamUtil.toMappedList(a -> a.getId(), form.getApplications()));
        result = setRejectionComments(form, result);
        result.removeIf(a -> EntityUtil.getId(a.getScholarshipTerm().getSchool()).longValue() != user.getSchoolId().longValue());
        updateApplicationStatuses(result, ScholarshipStatus.STIPTOETUS_STAATUS_L);
        return HttpStatus.OK;
    }
    
    private StudentCurriculumCompletion getStudentCurriculumCompletion(Student student) {
        List<StudentCurriculumCompletion> result = em.createQuery("SELECT scc FROM StudentCurriculumCompletion scc WHERE scc.student.id = (?1)",
                StudentCurriculumCompletion.class).setParameter(1, EntityUtil.getId(student)).setMaxResults(1).getResultList();
        return result.isEmpty() ? null : result.get(0);
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

    private void updateApplicationStatuses(List<ScholarshipApplication> entities, ScholarshipStatus status) {
        Classifier statusCl = em.getReference(Classifier.class, status.name());
        for (ScholarshipApplication application : entities) {
            application.setStatus(statusCl);
            application.setDecisionDate(LocalDate.now());
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
        StudentCurriculumCompletion completion = getStudentCurriculumCompletion(student);
        if (Boolean.FALSE.equals(term.getIsAcademicLeave()) && ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_A, student.getStatus())) {
            return false;
        }
        if (!term.getScholarshipTermCourses().isEmpty() && !StreamUtil
                .toMappedList(c -> Short.valueOf(c.getCourse().getValue()), term.getScholarshipTermCourses())
                .contains(student.getStudentGroup().getCourse())) {
            return false;
        }
        if (!term.getScholarshipTermStudyLoads().isEmpty() && !StreamUtil
                .toMappedList(l -> EntityUtil.getCode(l.getStudyLoad()), term.getScholarshipTermStudyLoads())
                .contains(EntityUtil.getCode(student.getStudyLoad()))) {
            return false;
        }
        if (!term.getScholarshipTermStudyForms().isEmpty() && !StreamUtil
                .toMappedList(f -> EntityUtil.getCode(f.getStudyForm()), term.getScholarshipTermStudyForms())
                .contains(EntityUtil.getCode(student.getStudyForm()))) {
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
        if(completion == null) {
            return false;
        }
        if (term.getAverageMark() != null && completion.getAverageMark().compareTo(term.getAverageMark()) == 1) {
            return false;
        }
        if (term.getLastPeriodMark() != null
                && completion.getAverageMarkLastStudyPeriod().compareTo(term.getLastPeriodMark()) == 1) {
            return false;
        }
        if (term.getCurriculumCompletion() != null
                && curriculumCompletion(student).compareTo(term.getCurriculumCompletion()) == 1) {
            return false;
        }
        return true;
    }

    private BigDecimal curriculumCompletion(Student student) {
        if (Boolean.TRUE.equals(student.getCurriculumVersion().getCurriculum().getHigher())) {
            StudentHigherResultDto results = studentResultHigherService.higherResults(student);
            return results.getCreditsSubmittedConsidered().divide(results.getCreditsSubmitted(), 1,
                    BigDecimal.ROUND_HALF_UP);
        }
        //TODO: missing curriculumCompletion for higher = false students
        return new BigDecimal(100);
    }

    private static final Map<Function<ScholarshipTerm, Classifier>, Function<ScholarshipApplicationSearchDto, BigDecimal>> COMPARATOR = new HashMap<>();
    static {
        COMPARATOR.put(ScholarshipTerm::getAverageMarkPriority, ScholarshipApplicationSearchDto::getAverageMark);
        COMPARATOR.put(ScholarshipTerm::getCurriculumCompletionPriority, ScholarshipApplicationSearchDto::getCurriculumCompletion);
        COMPARATOR.put(ScholarshipTerm::getLastPeriodMarkPriority, ScholarshipApplicationSearchDto::getLastPeriodMark);
    }

    private static Comparator<ScholarshipApplicationSearchDto> comparatorForTerm(ScholarshipTerm term) {
        //TODO: missing for absences
        List<Function<ScholarshipApplicationSearchDto, BigDecimal>> comparators = new ArrayList<>(Collections.nCopies(Priority.values().length, null));
        for(Map.Entry<Function<ScholarshipTerm, Classifier>, Function<ScholarshipApplicationSearchDto, BigDecimal>> me : COMPARATOR.entrySet()) {
            Classifier priority = me.getKey().apply(term);
            if(priority != null) {
                comparators.set(Priority.valueOf(EntityUtil.getCode(priority)).ordinal(), me.getValue());
            }
        }

        comparators = StreamUtil.toFilteredList(r -> r != null, comparators);
        if (comparators.isEmpty()) {
            return Comparator.comparing(ScholarshipApplicationSearchDto::getLastName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(ScholarshipApplicationSearchDto::getFirstName, String.CASE_INSENSITIVE_ORDER);
        }

        Comparator<ScholarshipApplicationSearchDto> comparator = null;
        for(Function<ScholarshipApplicationSearchDto, BigDecimal> c : comparators) {
            if(comparator == null) {
                comparator = Comparator.comparing(c, Comparator.reverseOrder());
            } else {
                comparator = comparator.thenComparing(c, Comparator.reverseOrder());
            }
        }
        return comparator;
    }

    public void delete(ScholarshipTerm term) {
        if(!term.getIsOpen().booleanValue() && term.getScholarshipApplications().isEmpty()) {
            EntityUtil.deleteEntity(term, em);
        }
    }
}
