package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
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
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.AcademicLeaveReason;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.message.ConfirmationNeededMessage;
import ee.hitsa.ois.message.StudentApplicationCreated;
import ee.hitsa.ois.message.StudentApplicationRejectedMessage;
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
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.commandobject.application.ApplicationForm;
import ee.hitsa.ois.web.commandobject.application.ApplicationRejectForm;
import ee.hitsa.ois.web.commandobject.application.ApplicationSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.application.ApplicationApplicableDto;
import ee.hitsa.ois.web.dto.application.ApplicationDto;
import ee.hitsa.ois.web.dto.application.ApplicationPlannedSubjectDto;
import ee.hitsa.ois.web.dto.application.ApplicationPlannedSubjectEquivalentDto;
import ee.hitsa.ois.web.dto.application.ApplicationSearchDto;
import ee.hitsa.ois.web.dto.application.ValidAcademicLeaveDto;

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

        Application application = new Application();
        application.setNeedsRepresentativeConfirm(Boolean.valueOf(!StudentUtil.isAdultAndDoNotNeedRepresentative(student)));
        return save(user, application, applicationForm);
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

        ValidAcademicLeaveDto validAcademicLeave = applicationForm.getValidAcademicLeave();
        if (validAcademicLeave != null) {
            application.setDirective(em.getReference(DirectiveStudent.class, validAcademicLeave.getId())
                    .getDirective());
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
            assertAkadConstraints(application);
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

    private void assertAkadConstraints(Application application) {
        ApplicationUtil.assertStartAfterToday(application);

        String reason = EntityUtil.getCode(application.getReason());
        if (AcademicLeaveReason.AKADPUHKUS_POHJUS_T.name().equals(reason)) {
            long daysUsed = daysUsed(EntityUtil.getId(application.getStudent()), AcademicLeaveReason.AKADPUHKUS_POHJUS_T);
            ApplicationUtil.assertPeriod(application, 2, daysUsed);
        } else if (AcademicLeaveReason.AKADPUHKUS_POHJUS_A.name().equals(reason)) {
            long daysUsed = daysUsed(EntityUtil.getId(application.getStudent()), AcademicLeaveReason.AKADPUHKUS_POHJUS_A);
            ApplicationUtil.assertPeriod(application, 1, daysUsed);
        } else if (AcademicLeaveReason.AKADPUHKUS_POHJUS_L.name().equals(reason)) {
            ApplicationUtil.assertPeriod(application, 3, 0);
        } else if (AcademicLeaveReason.AKADPUHKUS_POHJUS_O.name().equals(reason)) {
            // TODO: algusega mitte varem kui esimese õppeaasta teisest semestrist
            if (!StudentUtil.isHigher(application.getStudent())) {
                throw new ValidationFailedException("application.messages.studentIsNotHigher");
            }

            long daysUsed = daysUsed(EntityUtil.getId(application.getStudent()), AcademicLeaveReason.AKADPUHKUS_POHJUS_O);
            ApplicationUtil.assertPeriod(application, 1, daysUsed);
        }
    }

    long daysUsed(Long studentId, AcademicLeaveReason reason) {
        List<?> data = em.createNativeQuery("select sum(days_used) from (select (select min(end_date) from"
                + " (select ds.end_date union select ds2.start_date"
                + " from directive d2"
                + " join directive_student ds2 on ds2.directive_id = d2.id"
                + " join application a on a.id = ds2.application_id"
                + " where ds2.student_id = ds.student_id and a.directive_id = ds.directive_id and d2.type_code = ?3"
                + " and d2.status_code = ?4 and ds2.canceled = false) end_dates) - ds.start_date as days_used"
                + " from directive d"
                + " join directive_student ds on ds.directive_id = d.id"
                + " where ds.student_id = ?1 and d.type_code = ?2"
                + " and d.status_code = ?4 and ds.canceled = false and ds.reason_code = ?5) leaves")
            .setParameter(1, studentId)
            .setParameter(2, DirectiveType.KASKKIRI_AKAD.name())
            .setParameter(3, DirectiveType.KASKKIRI_AKADK.name())
            .setParameter(4, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
            .setParameter(5, reason.name())
            .getResultList();
        Long days = resultAsLong(data.get(0), 0);
        return days == null ? 0 : days.longValue();
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
        List<?> data = em.createNativeQuery("select distinct a.type_code from application a where a.student_id = ?1 and a.status_code in (?2)")
            .setParameter(1, studentId)
            .setParameter(2, EnumUtil.toNameList(ApplicationStatus.AVALDUS_STAATUS_KOOST, ApplicationStatus.AVALDUS_STAATUS_ESIT,
                        ApplicationStatus.AVALDUS_STAATUS_YLEVAAT, ApplicationStatus.AVALDUS_STAATUS_KINNITAM))
            .getResultList();
        return StreamUtil.toMappedList(r -> ApplicationType.valueOf(resultAsString(r, 0)), data);
    }

    public Application submit(HoisUserDetails user, Application application) {
        Student student = application.getStudent();
        if(UserUtil.isSchoolAdmin(user, student.getSchool())) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT);
            application.setSubmitted(LocalDateTime.now());
        } if (UserUtil.isAdultStudent(user, student) || UserUtil.isStudentRepresentative(user, student)) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_ESIT);
            application.setSubmitted(LocalDateTime.now());
            application.setNeedsRepresentativeConfirm(Boolean.FALSE);
        } else {
            application.setNeedsRepresentativeConfirm(Boolean.TRUE);
        }
        application = EntityUtil.save(application, em);
        if (!UserUtil.isSame(user, student)) {
            StudentApplicationCreated data = new StudentApplicationCreated(application);
            automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OP_AVALDUS, student, data);
        }
        return application;
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

    public DirectiveStudent findLastValidAcademicLeaveWithoutRevocation(Long studentId) {
      // find last confirmed akad directive
      List<?> data = em.createNativeQuery("select ds.id from directive_student ds join directive d on ds.directive_id = d.id "+
              "left join study_period sp on ds.study_period_start_id = sp.id left join study_period ep on ds.study_period_end_id = ep.id "+
              "where ds.student_id = ?1 and ds.canceled = false and d.type_code = ?2 and d.status_code = ?3 "+
              "and case when ds.is_period then sp.start_date else ds.start_date end <= ?4 and case when ds.is_period then ep.end_date else ds.end_date end >= ?5 "+
              "and not exists(select ds2.id from directive_student ds2 join directive d2 on ds2.directive_id = d2.id and ds2.canceled = false "+
                  "and ds2.student_id = ds.student_id and d2.type_code = ?6 and d2.status_code = ?7 "+
                  "join application a on ds2.application_id = a.id and a.directive_id = ds.directive_id) "+
              "order by d.confirm_date desc")
              .setParameter(1, studentId)
              .setParameter(2, DirectiveType.KASKKIRI_AKAD.name())
              .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
              .setParameter(4, parameterAsTimestamp(LocalDate.now()))
              .setParameter(5, parameterAsTimestamp(LocalDate.now()))
              .setParameter(6, DirectiveType.KASKKIRI_AKADK.name())
              .setParameter(7, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
              .setMaxResults(1).getResultList();

      return data.isEmpty() ? null : em.getReference(DirectiveStudent.class, resultAsLong(data.get(0), 0));
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
                        DirectiveStudent academicLeave = findLastValidAcademicLeaveWithoutRevocation(EntityUtil.getId(student));
                        if (academicLeave == null) {
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
            log.info("confirm needed message sent to student {} representatives", EntityUtil.getId(application.getStudent()));
            automaticMessageService.sendMessageToStudentRepresentatives(MessageType.TEATE_LIIK_AV_KINNIT, student, data);
        } else {
            log.info("confirm needed message sent to student {} school", EntityUtil.getId(application.getStudent()));
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
