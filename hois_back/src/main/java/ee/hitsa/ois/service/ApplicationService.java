package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.application.ApplicationFile;
import ee.hitsa.ois.domain.application.ApplicationPlannedSubject;
import ee.hitsa.ois.domain.application.ApplicationPlannedSubjectEquivalent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.commandobject.ApplicationRejectForm;
import ee.hitsa.ois.web.commandobject.ApplicationSearchCommand;
import ee.hitsa.ois.web.dto.ApplicationDto;
import ee.hitsa.ois.web.dto.ApplicationPlannedSubjectDto;
import ee.hitsa.ois.web.dto.ApplicationSearchDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@Transactional
@Service
public class ApplicationService {
    private static final String APPLICATION_FROM = "from application a inner join student student on a.student_id = student.id "+
            "inner join person person on student.person_id = person.id inner join classifier type on a.type_code = type.code "+
            "inner join classifier status on a.status_code = status.code";
    private static final String APPLICATION_SELECT = "a.id, a.type_code, a.status_code, a.inserted, "+
            "a.submitted, a.student_id, person.firstname, person.lastname, a.reject_reason";

    private static final List<String> VALID_APPLICATION_STATUSES = Arrays.asList(ApplicationStatus.AVALDUS_STAATUS_KOOST.name(), ApplicationStatus.AVALDUS_STAATUS_ESIT.name(),
            ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name(), ApplicationStatus.AVALDUS_STAATUS_KINNITAM.name());

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public Page<ApplicationSearchDto> search(HoisUserDetails user, ApplicationSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(APPLICATION_FROM, pageable);

        qb.requiredCriteria("student.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("a.type_code in (:type)", "type", criteria.getType());
        qb.optionalCriteria("a.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("a.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("a.submitted >= :submittedFrom", "submittedFrom", criteria.getSubmittedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("a.submitted <= :submittedThru", "submittedThru", criteria.getSubmittedThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("a.status_code in (:status)", "status", criteria.getStatus());
        qb.optionalCriteria("a.student_id in (:studentId)", "studentId", criteria.getStudent());

        qb.optionalContains(Arrays.asList("person.firstname", "person.lastname", "person.firstname || ' ' || person.lastname"), "name", criteria.getStudentName());

        qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getStudentIdCode());

        return JpaQueryUtil.pagingResult(qb, APPLICATION_SELECT, em, pageable).map(r -> {
            ApplicationSearchDto dto = new ApplicationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setStatus(resultAsString(r, 2));
            dto.setInserted(resultAsLocalDateTime(r, 3));
            dto.setSubmitted(resultAsLocalDateTime(r, 4));
            String name = PersonUtil.fullname(resultAsString(r, 6), resultAsString(r, 7));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 5), name, name));
            dto.setRejectReason(resultAsString(r, 8));
            return dto;
        });
    }

    public Application create(ApplicationForm applicationForm) {
//        if (ApplicationType.AVALDUS_LIIK_AKAD.name().equals(applicationForm.getType())
//                && existsValidAcademicLeaveApplication(applicationForm.getStudent().getId())) {
//            throw new ValidationFailureException("Student already has valid academic leave application");
//        } else if (ApplicationType.AVALDUS_LIIK_AKADK.name().equals(applicationForm.getType())
//                && !existsValidAcademicLeaveApplication(applicationForm.getStudent().getId())) {
//            throw new ValidationFailureException("Student has no valid academic leave application");
//        }
        return save(new Application(), applicationForm);
    }

    public Application save(Application application, ApplicationForm applicationForm) {
        EntityUtil.bindToEntity(applicationForm, application, classifierRepository, "student", "files", "plannedSubjects",
                "studyPeriodStart", "studyPeriodStart", "accademicApplication", "newCurriculumVersion", "oldCurriculumVersion", "submitted");
        EntityUtil.setEntityFromRepository(applicationForm, application, studyPeriodRepository, "studyPeriodStart", "studyPeriodEnd");
        EntityUtil.setEntityFromRepository(applicationForm, application, curriculumVersionRepository, "newCurriculumVersion", "oldCurriculumVersion");
        EntityUtil.setEntityFromRepository(applicationForm, application, studentRepository, "student");

        if (applicationForm.getAcademicApplication() != null) {
            application.setAcademicApplication(applicationRepository.getOne(applicationForm.getAcademicApplication()));
        }
        updateFiles(application, applicationForm);
        updatePlannedSubjects(application, applicationForm);

        return applicationRepository.save(application);
    }

    private void updatePlannedSubjects(Application application, ApplicationForm applicationForm) {
        Set<ApplicationPlannedSubject> newPlannedSubjects = new HashSet<>();
        if(applicationForm.getPlannedSubjects() != null) {
            applicationForm.getPlannedSubjects().forEach(dto -> {
                ApplicationPlannedSubject plannedSubject = dto.getId() == null ? new ApplicationPlannedSubject() :
                    application.getPlannedSubjects().stream().filter(p -> p.getId().equals(dto.getId())).findFirst().get();
                EntityUtil.bindToEntity(dto, plannedSubject, "equivalents");
                updateEquivalents(dto, plannedSubject);
                newPlannedSubjects.add(plannedSubject);
            });
        }
        application.getPlannedSubjects().clear();
        application.getPlannedSubjects().addAll(newPlannedSubjects);

    }

    private void updateEquivalents(ApplicationPlannedSubjectDto plannedSubjectDto, ApplicationPlannedSubject plannedSubject) {
        Set<ApplicationPlannedSubjectEquivalent> newPlannedSubjectEquivalents = new HashSet<>();
        if(plannedSubjectDto.getEquivalents() != null) {
            plannedSubjectDto.getEquivalents().forEach(dto -> {
                ApplicationPlannedSubjectEquivalent plannedSubjectEquivalent = dto.getId() == null ? new ApplicationPlannedSubjectEquivalent() :
                    plannedSubject.getEquivalents().stream().filter(e -> e.getId().equals(dto.getId())).findFirst().get();
                if (dto.getId() == null) {
                    EntityUtil.bindToEntity(dto, plannedSubjectEquivalent, "subject");
                    EntityUtil.setEntityFromRepository(dto, plannedSubjectEquivalent, subjectRepository, "subject");
                }
                newPlannedSubjectEquivalents.add(plannedSubjectEquivalent);
            });
        }
        plannedSubject.getEquivalents().clear();
        plannedSubject.getEquivalents().addAll(newPlannedSubjectEquivalents);
    }

    private boolean existsValidAcademicLeaveApplication(Long studentId) {
        return findValidAcademicLeave(studentId) != null;
    }

    private static void updateFiles(Application application, ApplicationForm applicationForm) {
        Set<ApplicationFile> newFiles = new HashSet<>();
        if(applicationForm.getFiles() != null) {
            applicationForm.getFiles().forEach(dto -> {
                ApplicationFile file = dto.getId() == null ? new ApplicationFile() :
                    application.getFiles().stream().filter(f -> f.getId().equals(dto.getId())).findFirst().get();
                if (dto.getId() == null) {
                    file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
                }
                newFiles.add(file);
            });
        }
        application.getFiles().clear();
        application.getFiles().addAll(newFiles);
    }

    public void delete(Application application) {
        EntityUtil.deleteEntity(applicationRepository, application);
    }

    //TODO: rewrite this
    public Application findValidAcademicLeave(Long student) {
        List<Application> validAcademicLeaves =  applicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("student").get("id"), student));
            filters.add(cb.equal(root.get("type").get("code"), ApplicationType.AVALDUS_LIIK_AKAD.name()));

            filters.add(cb.equal(root.get("status").get("code"), ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name()));



            root.join("studyPeriodEnd", JoinType.LEFT);
            filters.add(cb.or(
                cb.and(cb.equal(root.get("isPeriod"), Boolean.FALSE), cb.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now())),
                cb.and(cb.equal(root.get("isPeriod"), Boolean.TRUE), cb.greaterThanOrEqualTo(root.get("studyPeriodEnd").get("endDate"), LocalDate.now()))
            ));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });

        if (CollectionUtils.isEmpty(validAcademicLeaves)) {
            return null;
        }

        Application academicLeaveWithoutRevocation = null;
        for (Application application : validAcademicLeaves) {
            Application validAcademicLeaveRevocation = findValidAcademicLeaveRevocation(application.getId());
            if(validAcademicLeaveRevocation == null) {
                academicLeaveWithoutRevocation = application;
            }
        }

        return academicLeaveWithoutRevocation;


    }

    public Application findValidAcademicLeaveRevocation(Long applicationId) {
        return applicationRepository.findOne((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("academicApplication"), applicationId));
            //filters.add(root.get("status").get("code").in(VALID_APPLICATION_STATUSES));
            filters.add(cb.equal(root.get("status").get("code"), ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name()));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
    }

    public Map<ApplicationType, Boolean> applicableApplications(Long studentId, Long schoolId) {
        Map<ApplicationType, Boolean> result = new HashMap<>();
        for (ApplicationType type : ApplicationType.values()) {
            result.put(type, Boolean.TRUE);
        }
        //Kui õppur on juba koostanud seda liiki avalduse, mille staatus on „Koostamisel“, „Esitatud“, „Ülevaatamisel“ või „Kinnitamisel“;
        List<Application> existingApplications = applicationRepository.findDistinctTypeByStudentIdAndStatusCodeIn(studentId, VALID_APPLICATION_STATUSES);

        for (Application application : existingApplications) {
            result.put(ApplicationType.valueOf(EntityUtil.getNullableCode(application.getType())), Boolean.FALSE);
        }

        //Kui õppur ei viibi akadeemilisel puhkusel, siis ei ole tal võimalik esitada taotlust „akadeemilise puhkuse katkestamiseks“;
        //boolean studentOnAcademicLeave = findValidAcademicLeave(studentId) != null;
        //result.put(ApplicationType.AVALDUS_LIIK_AKADK, Boolean.valueOf(studentOnAcademicLeave));

        return result;
    }

    public Application submit(HoisUserDetails user, Application application) {
        Student student = application.getStudent();
        if(UserUtil.isSchoolAdmin(user, student.getSchool())) {
            application.setStatus(classifierRepository.findOne(ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name()));
            application.setSubmitted(LocalDateTime.now());
        } if (UserUtil.isAdultStudent(user, student) || UserUtil.isStudentRepresentative(user, student)) {
            application.setStatus(classifierRepository.findOne(ApplicationStatus.AVALDUS_STAATUS_ESIT.name()));
            application.setSubmitted(LocalDateTime.now());
        } else {
            application.setNeedsRepresentativeConfirm(Boolean.TRUE);
        }
        return applicationRepository.save(application);
    }

    public Application reject(Application application, ApplicationRejectForm applicationRejectForm) {
        application.setStatus(classifierRepository.findOne(ApplicationStatus.AVALDUS_STAATUS_TAGASI.name()));
        application.setRejectReason(applicationRejectForm.getReason());
        return applicationRepository.save(application);
    }

    public ApplicationDto get(HoisUserDetails user, Application application) {
        setSeenBySchoolAdmin(user, application);
        return ApplicationDto.of(application);
    }

    private void setSeenBySchoolAdmin(HoisUserDetails user, Application application) {
        if (UserUtil.isSchoolAdmin(user, application.getStudent().getSchool()) &&
                EntityUtil.getCode(application.getStatus()).equals(ApplicationStatus.AVALDUS_STAATUS_ESIT.name())) {
            application.setStatus(classifierRepository.findOne(ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name()));
            applicationRepository.save(application);
        }
    }
}
