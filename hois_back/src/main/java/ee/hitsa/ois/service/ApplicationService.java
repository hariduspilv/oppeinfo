package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.application.ApplicationFile;
import ee.hitsa.ois.domain.application.ApplicationPlannedSubject;
import ee.hitsa.ois.domain.application.ApplicationPlannedSubjectEquivalent;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.message.ConfirmationNeededMessage;
import ee.hitsa.ois.message.StudentApplicationRejectedMessage;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ApplicationUtil;
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
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.commandobject.ApplicationRejectForm;
import ee.hitsa.ois.web.commandobject.ApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.dto.ApplicationApplicableDto;
import ee.hitsa.ois.web.dto.ApplicationDto;
import ee.hitsa.ois.web.dto.ApplicationPlannedSubjectDto;
import ee.hitsa.ois.web.dto.ApplicationPlannedSubjectEquivalentDto;
import ee.hitsa.ois.web.dto.ApplicationSearchDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@Transactional
@Service
public class ApplicationService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String APPLICATION_FROM = "from application a inner join student student on a.student_id = student.id "+
            "inner join person person on student.person_id = person.id inner join classifier type on a.type_code = type.code "+
            "inner join classifier status on a.status_code = status.code";
    private static final String APPLICATION_SELECT = "a.id, a.type_code, a.status_code, a.inserted, "+
            "a.submitted, a.student_id, person.firstname, person.lastname, a.reject_reason";

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private EntityManager em;
    @Autowired
    private Validator validator;

    /**
     * Search student applications
     *
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<ApplicationSearchDto> search(HoisUserDetails user, ApplicationSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(APPLICATION_FROM).sort(pageable);

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

    /**
     * Create new student application
     *
     * @param user
     * @param applicationForm
     * @return
     * @throws ValidationFailedException if user is not allowed to create application
     */
    public Application create(HoisUserDetails user, ApplicationForm applicationForm) {
        Student student = em.getReference(Student.class, applicationForm.getStudent().getId());
        if(!(UserUtil.isSame(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool()))) {
            throw new ValidationFailedException(String.format("user %s is not allowed to create application", user.getUsername()));
        }

        Map<ApplicationType, ApplicationApplicableDto> applicable = applicableApplicationTypes(student);
        ApplicationType type = ApplicationType.valueOf(applicationForm.getType());
        if (Boolean.FALSE.equals(applicable.get(type).getIsAllowed())) {
            throw new ValidationFailedException(applicable.get(type).getReason());
        }

        return save(user, new Application(), applicationForm);
    }

    /**
     * Store student application
     *
     * @param user
     * @param application
     * @param applicationForm
     * @return
     * @throws ValidationFailedException if application type specific validation fails
     */
    public Application save(HoisUserDetails user, Application application, ApplicationForm applicationForm) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(applicationForm, application, classifierRepository, "student", "files", "plannedSubjects",
                "studyPeriodStart", "studyPeriodStart", "accademicApplication", "newCurriculumVersion", "oldCurriculumVersion", "submitted");

        application.setStudyPeriodStart(EntityUtil.getOptionalOne(StudyPeriod.class, applicationForm.getStudyPeriodStart(), em));
        application.setStudyPeriodEnd(EntityUtil.getOptionalOne(StudyPeriod.class, applicationForm.getStudyPeriodEnd(), em));
        application.setOldCurriculumVersion(EntityUtil.getOptionalOne(CurriculumVersion.class, applicationForm.getOldCurriculumVersion(), em));
        application.setNewCurriculumVersion(EntityUtil.getOptionalOne(CurriculumVersion.class, applicationForm.getNewCurriculumVersion(), em));
        application.setStudent(EntityUtil.getOptionalOne(Student.class, applicationForm.getStudent(), em));

        if (applicationForm.getAcademicApplication() != null) {
            application.setAcademicApplication(em.getReference(Application.class, applicationForm.getAcademicApplication().getId()));
        }
        updateFiles(application, applicationForm);
        updatePlannedSubjects(application, applicationForm);
        validateEntity(application);
        return EntityUtil.save(application, em);
    }

    private void validateEntity(Application application) {
        ApplicationType applicationType = ApplicationType.valueOf(EntityUtil.getCode(application.getType()));
        ValidationFailedException.throwOnError(validator.validate(application, applicationType.validationGroup()));

        switch (applicationType) {
        case AVALDUS_LIIK_AKAD:
            ApplicationUtil.assertAkadConstraints(application, applicationRepository);
            break;
        case AVALDUS_LIIK_AKADK:
            ApplicationUtil.assertAkadkConstraints(application);
            break;
        case AVALDUS_LIIK_VALIS:
            ApplicationUtil.assertValisConstraints(application);
            break;
        default:
            break;
        }
    }

    private void updatePlannedSubjects(Application application, ApplicationForm applicationForm) {
        EntityUtil.bindEntityCollection(application.getPlannedSubjects(), ApplicationPlannedSubject::getId, applicationForm.getPlannedSubjects(), ApplicationPlannedSubjectDto::getId, dto -> {
            ApplicationPlannedSubject plannedSubject = EntityUtil.bindToEntity(dto, new ApplicationPlannedSubject(), "equivalents");
            updateEquivalents(dto, plannedSubject);
            return plannedSubject;
        }, (dto, plannedSubject) -> {
            EntityUtil.bindToEntity(dto, plannedSubject, "equivalents");
            updateEquivalents(dto, plannedSubject);
        });
    }

    private void updateEquivalents(ApplicationPlannedSubjectDto plannedSubjectDto, ApplicationPlannedSubject plannedSubject) {
        EntityUtil.bindEntityCollection(plannedSubject.getEquivalents(), ApplicationPlannedSubjectEquivalent::getId, plannedSubjectDto.getEquivalents(), ApplicationPlannedSubjectEquivalentDto::getId, dto -> {
            ApplicationPlannedSubjectEquivalent plannedSubjectEquivalent = new ApplicationPlannedSubjectEquivalent();
            EntityUtil.bindToEntity(dto, plannedSubjectEquivalent, "subject");
            plannedSubjectEquivalent.setSubject(EntityUtil.getOptionalOne(Subject.class, dto.getSubject(), em));
            return plannedSubjectEquivalent;
        }, null);
    }

    private static void updateFiles(Application application, ApplicationForm applicationForm) {
        EntityUtil.bindEntityCollection(application.getFiles(), ApplicationFile::getId, applicationForm.getFiles(), OisFileForm::getId, dto -> {
            ApplicationFile file = new ApplicationFile();
            file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
            return file;
        }, null);
    }

    /**
     * Delete student application
     *
     * @param user
     * @param application
     * @throws EntityRemoveExceptionif there are references to application
     */
    public void delete(HoisUserDetails user, Application application) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(application, em);
    }

    private List<ApplicationType> existingApplicationsTypes(Long studentId) {
        List<Application> existingApplications = applicationRepository.findDistinctTypeByStudentIdAndStatusCodeIn(studentId,
                EnumUtil.toNameList(ApplicationStatus.AVALDUS_STAATUS_KOOST, ApplicationStatus.AVALDUS_STAATUS_ESIT,
                        ApplicationStatus.AVALDUS_STAATUS_YLEVAAT, ApplicationStatus.AVALDUS_STAATUS_KINNITAM));
        return StreamUtil.toMappedList(application -> ApplicationType.valueOf(EntityUtil.getCode(application.getType())), existingApplications);
    }

    public Application submit(HoisUserDetails user, Application application) {
        Student student = application.getStudent();
        if(UserUtil.isSchoolAdmin(user, student.getSchool())) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT);
            application.setSubmitted(LocalDateTime.now());
        } if (UserUtil.isAdultStudent(user, student) || UserUtil.isStudentRepresentative(user, student)) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_ESIT);
            application.setSubmitted(LocalDateTime.now());
        } else {
            application.setNeedsRepresentativeConfirm(Boolean.TRUE);
        }
        return EntityUtil.save(application, em);
    }

    public Application reject(Application application, ApplicationRejectForm applicationRejectForm) {
        setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_TAGASI);
        application.setRejectReason(applicationRejectForm.getReason());
        return EntityUtil.save(application, em);
    }

    public ApplicationDto get(HoisUserDetails user, Application application) {
        return ApplicationDto.of(setSeenBySchoolAdmin(user, application));
    }

    private Application setSeenBySchoolAdmin(HoisUserDetails user, Application application) {
        if (UserUtil.isSchoolAdmin(user, application.getStudent().getSchool()) &&
                ClassifierUtil.equals(ApplicationStatus.AVALDUS_STAATUS_ESIT, application.getStatus())) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT);
            return EntityUtil.save(application, em);
        }
        return application;
    }

    public Application findLastValidAcademicLeaveWithoutRevocation(Long studentId) {
      List<Application> validAcademicLeaves = applicationRepository.findAll((root, query, cb) -> {
          List<Predicate> filters = new ArrayList<>();
          filters.add(cb.equal(root.get("student").get("id"), studentId));
          filters.add(cb.equal(root.get("type").get("code"), ApplicationType.AVALDUS_LIIK_AKAD.name()));
          filters.add(cb.equal(root.get("status").get("code"), ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name()));

          Subquery<Long> revocationQuery = query.subquery(Long.class);
          Root<Application> revocationRoot = revocationQuery.from(Application.class);
          revocationQuery
              .select(revocationRoot.get("id"))
              .where(cb.and(
                      cb.equal(revocationRoot.get("type").get("code"), ApplicationType.AVALDUS_LIIK_AKADK.name()),
                      cb.equal(revocationRoot.get("academicApplication").get("id"), root.get("id"))));
          filters.add(cb.not(cb.exists(revocationQuery)));

          root.join("studyPeriodEnd", JoinType.LEFT);
          filters.add(cb.or(
              cb.and(cb.equal(root.get("isPeriod"), Boolean.FALSE), cb.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now())),
              cb.and(cb.equal(root.get("isPeriod"), Boolean.TRUE), cb.greaterThanOrEqualTo(root.get("studyPeriodEnd").get("endDate"), LocalDate.now()))
          ));
          return cb.and(filters.toArray(new Predicate[filters.size()]));
      });

      return validAcademicLeaves.stream().collect(Collectors.maxBy(Comparator.comparing(Application::getChanged))).orElse(null);
    }

    public Map<ApplicationType, ApplicationApplicableDto> applicableApplicationTypes(Student student) {
        List<ApplicationType> existingApplications = existingApplicationsTypes(EntityUtil.getId(student));
        boolean isHigher = StudentUtil.isHigher(student);
        Map<ApplicationType, ApplicationApplicableDto> result = new HashMap<>();
        rulesByApplicationType(student, existingApplications, isHigher, result);
        rulesByApplicationClassifier(isHigher, result);
        return result;
    }

    private void rulesByApplicationClassifier(boolean isHigher, Map<ApplicationType, ApplicationApplicableDto> result) {
        List<Classifier> allowedApplicationTypes = classifierService.findAllByMainClassCode(MainClassCode.AVALDUS_LIIK);
        for (Classifier allowedApplicationType : allowedApplicationTypes) {
            if ((isHigher && !allowedApplicationType.isHigher()) || (!isHigher && !allowedApplicationType.isVocational())) {
                result.remove(ApplicationType.valueOf(allowedApplicationType.getCode()));
            }
        }
    }

    private void rulesByApplicationType(Student student, List<ApplicationType> existingApplications, boolean isHigher,
            Map<ApplicationType, ApplicationApplicableDto> result) {
        boolean isActive = StudentUtil.isActive(student);
        boolean isStudying = StudentUtil.isStudying(student);

        for (ApplicationType type : ApplicationType.values()) {
            if (existingApplications.contains(type)) {
                result.put(type, new ApplicationApplicableDto("application.messages.applicationAlreadyExists"));
            } else {
                if (ApplicationType.AVALDUS_LIIK_AKAD.equals(type)) {
                    if (!isActive) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentNotActive"));
                    } else if (!StudentUtil.isNominalStudy(student)) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentNotNominalStudy"));
                    }
                } else if (ApplicationType.AVALDUS_LIIK_AKADK.equals(type)) {
                    if (!isActive) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentNotActive"));
                    } else if (!StudentUtil.isOnAcademicLeave(student)) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentNotOnAcademicLeave"));
                    } else {
                        Application academicLeaveApplication = findLastValidAcademicLeaveWithoutRevocation(EntityUtil.getId(student));
                        if (academicLeaveApplication == null) {
                            result.put(type, new ApplicationApplicableDto("application.messages.noValidAcademicLeaveApplicationFound"));
                        }
                    }
                } else if (ApplicationType.AVALDUS_LIIK_VALIS.equals(type)) {
                    if (!isStudying) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentNotStudying"));
                    } else if (!isHigher) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentIsNotHigher"));
                    }
                } else {
                    if (!isStudying) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentNotStudying"));
                    }
                }
                if (!result.containsKey(type)) {
                    result.put(type, ApplicationApplicableDto.trueValue());
                }
            }
        }
    }

    public void sendConfirmNeededNotificationMessage(Application application) {
        Student student = application.getStudent();
        ConfirmationNeededMessage data = new ConfirmationNeededMessage(application);
        if (StudentUtil.hasRepresentatives(student)) {
            log.info("rejection notification message sent to student {} representatives", EntityUtil.getId(application.getStudent()));
            automaticMessageService.sendMessageToStudentRepresentatives(MessageType.TEATE_LIIK_AV_KINNIT, student, data);
        } else {
            log.info("rejection notification message sent to student {} school", EntityUtil.getId(application.getStudent()));
            automaticMessageService.sendMessageToSchoolAdmins(MessageType.TEATE_LIIK_AV_KINNIT, student.getSchool(), data);
        }
    }

    public void sendRejectionNotificationMessage(Application application, HoisUserDetails user) {
        log.info("rejection notification message sent to student {}", EntityUtil.getId(application.getStudent()));
        StudentApplicationRejectedMessage data = new StudentApplicationRejectedMessage(application);
        automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OP_AVALDUS_TL, application.getStudent(), data, user);
    }

    private void setApplicationStatus(Application application, ApplicationStatus status) {
        application.setStatus(em.getReference(Classifier.class, status.name()));
    }
}
