package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.application.ApplicationFile;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.commandobject.ApplicationSearchCommand;
import ee.hitsa.ois.web.dto.ApplicationDto;

@Transactional
@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassifierRepository classifierRepository;

    @Autowired
    private StudyPeriodRepository studyPeriodRepository;

    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;


    public Page<ApplicationDto> search(HoisUserDetails user, ApplicationSearchCommand criteria, Pageable pageable) {
        String ehisSchool = EntityUtil.getCode(schoolRepository.getOne(user.getSchoolId()).getEhisSchool());

        return applicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("ehisSchool").get("code"), ehisSchool));

            if(!CollectionUtils.isEmpty(criteria.getType())) {
                filters.add(root.get("type").get("code").in(criteria.getType()));
            }
            if(criteria.getInsertedFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("insertedFrom"), criteria.getInsertedFrom()));
            }
            if(criteria.getInsertedThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("insertedThru"), criteria.getInsertedThru()));
            }
            if(criteria.getSubmitedFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("submitedFrom"), criteria.getSubmitedFrom()));
            }
            if(criteria.getSubmitedThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("submitedThru"), criteria.getSubmitedThru()));
            }
            if(!StringUtils.isEmpty(criteria.getStatus())) {
                filters.add(cb.equal(root.get("status").get("code"), criteria.getStatus()));
            }
            if (criteria.getStudent() != null) {
                filters.add(cb.equal(root.get("student").get("id"), criteria.getStudent()));
            }
            if(!StringUtils.isEmpty(criteria.getStudentName())) {
                List<Predicate> name = new ArrayList<>();
                propertyContains(() -> root.get("student").get("person").get("firstname"), cb, criteria.getStudentName(), name::add);
                propertyContains(() -> root.get("student").get("person").get("lastname"), cb, criteria.getStudentName(), name::add);
                if(!name.isEmpty()) {
                    filters.add(cb.or(name.toArray(new Predicate[name.size()])));
                }
            }
            if(!StringUtils.isEmpty(criteria.getStudentIdCode())) {
                filters.add(cb.equal(root.get("student").get("person").get("idcode"), criteria.getStudentIdCode()));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(ApplicationDto::of);
    }

    public ApplicationDto create(HoisUserDetails user, ApplicationForm applicationForm) {
        return save(user, new Application(), applicationForm);
    }

    public ApplicationDto save(HoisUserDetails user, Application application, ApplicationForm applicationForm) {
        EntityUtil.bindToEntity(applicationForm, application, classifierRepository, "student", "files",
                "studyPeriodStart", "studyPeriodStart", "accademicApplication", "newCurriculumVersion", "oldCurriculumVersion");
        EntityUtil.setEntityFromRepository(applicationForm, application, studyPeriodRepository, "studyPeriodStart", "studyPeriodEnd");
        EntityUtil.setEntityFromRepository(applicationForm, application, curriculumVersionRepository, "newCurriculumVersion", "oldCurriculumVersion");
        EntityUtil.setEntityFromRepository(applicationForm, application, studentRepository, "student");

        application.setEhisSchool(schoolRepository.getOne(user.getSchoolId()).getEhisSchool());
        if (applicationForm.getAcademicApplication() != null) {
            application.setAcademicApplication(applicationRepository.getOne(applicationForm.getAcademicApplication()));
        }
        updateFiles(application, applicationForm);

        if (application.getType().getCode().equals(ApplicationType.AVALDUS_LIIK_AKAD.toString()) && existsValidAcademicLeaveApplication(application.getStudent(), application.getEhisSchool())) {
            throw new ValidationFailureException("Student already has valid academic leave application");
        } else if (application.getType().getCode().equals(ApplicationType.AVALDUS_LIIK_AKADK.toString()) && !existsValidAcademicLeaveApplication(application.getStudent(), application.getEhisSchool())) {
            throw new ValidationFailureException("Student has no valid academic leave application");
        }

        return ApplicationDto.of(applicationRepository.save(application));
    }

    private boolean existsValidAcademicLeaveApplication(Student student, Classifier ehisSchool) {
        return findValidAcademicLeave(EntityUtil.getNullableId(student), EntityUtil.getNullableCode(ehisSchool)) != null;
    }

    private static void updateFiles(Application application, ApplicationForm applicationForm) {
        List<ApplicationFile> files = applicationForm.getFiles().stream().map(it -> {
            ApplicationFile file = new ApplicationFile();
            file.setOisFile(EntityUtil.bindToEntity(it.getOisFile(), new OisFile()));
            return file;
        }).collect(Collectors.toList());
        application.getFiles().clear();
        application.getFiles().addAll(files);
    }

    public void delete(Application application) {
        EntityUtil.deleteEntity(applicationRepository, application);
    }

    public Application findValidAcademicLeave(Long student, String ehisSchool) {
        return applicationRepository.findOne((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("ehisSchool").get("code"), ehisSchool));
            filters.add(cb.equal(root.get("student").get("id"), student));
            filters.add(cb.equal(root.get("type").get("code"), ApplicationType.AVALDUS_LIIK_AKAD.toString()));
            filters.add(cb.notEqual(root.get("status").get("code"), ApplicationStatus.AVALDUS_STAATUS_TAGASI.toString()));

            root.join("studyPeriodEnd", JoinType.LEFT);
            filters.add(cb.or(
                cb.and(cb.equal(root.get("isPeriod"), Boolean.FALSE), cb.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now())),
                cb.and(cb.equal(root.get("isPeriod"), Boolean.TRUE), cb.greaterThanOrEqualTo(root.get("studyPeriodEnd").get("endDate"), LocalDate.now()))
            ));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
    }

    public Application findValidAcademicLeaveRevocation(Long applicationId) {
        return applicationRepository.findOne((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("academicApplication"), applicationId));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
    }

}
