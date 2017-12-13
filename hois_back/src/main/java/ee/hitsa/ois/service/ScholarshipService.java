package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.BaseEntityWithId;
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
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipStudentApplicationForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipTermForm;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipStudentApplicationDto;
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

    public BaseEntityWithId create(HoisUserDetails user, ScholarshipTermForm form) {
        ScholarshipTerm term = new ScholarshipTerm();
        form.setIsFamilyIncomes(Boolean.FALSE);
        form.setIsOpen(Boolean.FALSE);
        form.setIsStudyBacklog(Boolean.FALSE);
        form.setIsTeacherConfirm(Boolean.FALSE);
        EntityUtil.bindToEntity(form, term, classifierRepository, "curriculums", "studyLoads", "courses");
        term.setSchool(em.getReference(School.class, user.getSchoolId()));
        // TODO: find out why this property isnt binding
        term.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        term = bindFormArraysToEntity(form, term);
        return EntityUtil.save(term, em);
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
        scholarshipTerm.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        bindFormArraysToEntity(form, scholarshipTerm);
        return EntityUtil.save(scholarshipTerm, em);
    }

    private ScholarshipTerm bindFormArraysToEntity(ScholarshipTermForm form, ScholarshipTerm scholarshipTerm) {
        List<ScholarshipTermCourse> courses = scholarshipTerm.getScholarshipTermCourses();
        EntityUtil.bindEntityCollection(courses, c -> EntityUtil.getCode(c.getCourse()), form.getCourses(), c -> {
            ScholarshipTermCourse course = new ScholarshipTermCourse();
            course.setScholarshipTerm(scholarshipTerm);
            course.setCourse(EntityUtil.validateClassifier(em.getReference(Classifier.class, c), MainClassCode.KURSUS));
            return course;
        });

        List<ScholarshipTermCurriculum> curriculums = scholarshipTerm.getScholarshipTermCurriculums();
        EntityUtil.bindEntityCollection(curriculums, c -> EntityUtil.getId(c.getCurriculum()), form.getCurriculums(),
                c -> c.getId(), c -> {
                    ScholarshipTermCurriculum curriculum = new ScholarshipTermCurriculum();
                    curriculum.setScholarshipTerm(scholarshipTerm);
                    curriculum.setCurriculum(em.getReference(Curriculum.class, c.getId()));
                    return curriculum;
                });

        if (!form.getStudyForms().isEmpty()) {
            List<ScholarshipTermStudyForm> studyForms = scholarshipTerm.getScholarshipTermStudyForms();
            EntityUtil.bindEntityCollection(studyForms, c -> EntityUtil.getCode(c.getStudyForm()), form.getStudyForms(),
                    c -> {
                        ScholarshipTermStudyForm studyForm = new ScholarshipTermStudyForm();
                        studyForm.setScholarshipTerm(scholarshipTerm);
                        studyForm.setStudyForm(EntityUtil.validateClassifier(em.getReference(Classifier.class, c),
                                MainClassCode.OPPEVORM));
                        return studyForm;
                    });
        } else {
            scholarshipTerm.getScholarshipTermStudyForms().clear();
        }

        if (!form.getStudyLoads().isEmpty()) {
            List<ScholarshipTermStudyLoad> studyLoads = scholarshipTerm.getScholarshipTermStudyLoads();
            EntityUtil.bindEntityCollection(studyLoads, c -> EntityUtil.getCode(c.getStudyLoad()), form.getStudyLoads(),
                    c -> {
                        ScholarshipTermStudyLoad studyLoad = new ScholarshipTermStudyLoad();
                        studyLoad.setScholarshipTerm(scholarshipTerm);
                        studyLoad.setStudyLoad(EntityUtil.validateClassifier(em.getReference(Classifier.class, c),
                                MainClassCode.OPPEKOORMUS));
                        return studyLoad;
                    });
        } else {
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
                "SELECT st FROM ScholarshipTerm st JOIN st.scholarshipTermCurriculums stc WHERE stc.curriculum.id = (?1) and st.isOpen = true",
                ScholarshipTerm.class).setParameter(1, EntityUtil.getId(student.getCurriculumVersion().getCurriculum()))
                .getResultList().stream().collect(Collectors.toList());
        return StreamUtil.toMappedList(st -> ScholarshipTermStudentDto.of(st), result);
    }

    public List<ScholarshipTermStudentDto> studentStipends(Long studentId) {
        Student student = em.getReference(Student.class, studentId);
        List<ScholarshipApplication> result = em
                .createQuery("SELECT sa FROM ScholarshipApplication sa WHERE sa.student.id = (?1)",
                        ScholarshipApplication.class)
                .setParameter(1, EntityUtil.getId(student)).getResultList().stream().collect(Collectors.toList());
        return StreamUtil.toMappedList(sa -> {
            ScholarshipTermStudentDto dto = ScholarshipTermStudentDto.of(sa.getScholarshipTerm());
            dto.setStatus(EntityUtil.getCode(sa.getStatus()));
            dto.setDecisionDate(sa.getDecisionDate());
            return dto;
        }, result);
    }

    public Map<String, Object> applicationView(HoisUserDetails user, ScholarshipTerm term) {
        Map<String, Object> result = new HashMap<>();
        Student student = em.getReference(Student.class, user.getStudentId());
        result.put("stipend", ScholarshipTermApplicationDto.of(term));
        result.put("studentInfo", ScholarshipStudentDto.of(student));
        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        result.put("studentSubmitData", ScholarshipStudentApplicationDto.of(application));
        return result;
    }

    public ScholarshipApplication saveApplication(HoisUserDetails user, ScholarshipTerm term,
            ScholarshipStudentApplicationForm form) {
        Student student = em.getReference(Student.class, user.getStudentId());
        if (getApplicationForTermAndStudent(term, student) != null) {
            return null;
        }
        // TODO: add validation that this STUDENT qualifies for this SCHOLARSHIP TERM
        ScholarshipApplication application = new ScholarshipApplication();
        application.setScholarshipTerm(term);
        application.setStatus(classifierRepository.getOne("STIPTOETUS_STAATUS_K"));
        application.setStudent(student);
        application.setStudentGroup(student.getStudentGroup());
        application.setCredits(Long.valueOf(666));
        application.setCurriculumVersion(student.getCurriculumVersion());
        application = bindApplicationFormToApplication(application, form);
        return EntityUtil.save(application, em);
    }

    public ScholarshipApplication updateApplication(HoisUserDetails user, ScholarshipStudentApplicationForm form,
            ScholarshipApplication application) {
        // TODO: check if this application can be updated at the current moment and by this user
        if (application == null || !EntityUtil.getId(application.getStudent()).equals(user.getStudentId())) {
            return null;
        }
        bindApplicationFormToApplication(application, form);
        return EntityUtil.save(application, em);
    }

    private ScholarshipApplication bindApplicationFormToApplication(ScholarshipApplication application,
            ScholarshipStudentApplicationForm form) {
        EntityUtil.bindToEntity(form, application, classifierRepository, "files", "family");
        if (!form.getFiles().isEmpty()) {
            List<ScholarshipApplicationFile> files = application.getScholarshipApplicationFiles();
            EntityUtil.bindEntityCollection(files, f -> EntityUtil.getId(f), form.getFiles(), f -> f.getId(), f -> {
                ScholarshipApplicationFile file = new ScholarshipApplicationFile();
                file.setScholarshipApplication(application);
                file.setOisFile(EntityUtil.bindToEntity(f.getOisFile(), new OisFile()));
                EntityUtil.save(file.getOisFile(), em);
                return file;
            });
        } else {
            application.getScholarshipApplicationFiles().clear();
        }
        if(form.getFamily() != null && !form.getFamily().isEmpty()) {
            List<ScholarshipApplicationFamily> families = application.getScholarshipApplicationFamilies();
            EntityUtil.bindEntityCollection(families, f -> EntityUtil.getId(f), form.getFamily(), f -> f.getId(), f -> {
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
                .setParameter(2, EntityUtil.getId(student)).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public ScholarshipStudentApplicationDto getStudentApplicationDto(ScholarshipApplication application) {
        return ScholarshipStudentApplicationDto.of(application);
    }

    public ScholarshipApplication apply(HoisUserDetails user, ScholarshipApplication application) {
        //TODO: add logic to see if application can be applied for (check term application dates)
        if(user.getStudentId().equals(EntityUtil.getId(application.getStudent()))) {
            application.setStatus(classifierRepository.getOne("STIPTOETUS_STAATUS_E"));
            return EntityUtil.save(application, em);
        }
        return application;
    }

}
