package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.application.ApplicationFile;
import ee.hitsa.ois.domain.application.ApplicationOccupationModuleTheme;
import ee.hitsa.ois.domain.application.ApplicationPlannedSubject;
import ee.hitsa.ois.domain.application.ApplicationPlannedSubjectEquivalent;
import ee.hitsa.ois.domain.application.ApplicationSupportService;
import ee.hitsa.ois.domain.application.ApplicationSupportServiceModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.AcademicLeaveReason;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.message.ConfirmationNeededMessage;
import ee.hitsa.ois.message.StudentApplicationConfirmed;
import ee.hitsa.ois.message.StudentApplicationCreated;
import ee.hitsa.ois.message.StudentApplicationRejectedMessage;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ApplicationUtil;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
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
import ee.hitsa.ois.web.commandobject.application.ApplicationConfirmConfirmationForm;
import ee.hitsa.ois.web.commandobject.application.ApplicationForm;
import ee.hitsa.ois.web.commandobject.application.ApplicationRejectForm;
import ee.hitsa.ois.web.commandobject.application.ApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.application.ApplicationSubjectForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.application.ApplicationApplicableDto;
import ee.hitsa.ois.web.dto.application.ApplicationDto;
import ee.hitsa.ois.web.dto.application.ApplicationOccupationModuleThemeDto;
import ee.hitsa.ois.web.dto.application.ApplicationPlannedSubjectDto;
import ee.hitsa.ois.web.dto.application.ApplicationPlannedSubjectEquivalentDto;
import ee.hitsa.ois.web.dto.application.ApplicationSearchDto;
import ee.hitsa.ois.web.dto.application.ApplicationSupportServiceModuleDto;
import ee.hitsa.ois.web.dto.application.ApplicationThemeReplacementDto;
import ee.hitsa.ois.web.dto.application.ApplicationThemeReplacementModuleDto;
import ee.hitsa.ois.web.dto.application.ValidAcademicLeaveDto;

@Transactional
@Service
public class ApplicationService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String APPLICATION_FROM = "from application a inner join student student on a.student_id = student.id "+
            "inner join person person on student.person_id = person.id inner join classifier type on a.type_code = type.code "+
            "inner join classifier status on a.status_code = status.code";
    private static final String APPLICATION_SELECT = "a.id, a.type_code, a.status_code, a.inserted, "+
            "a.submitted, a.student_id, person.firstname, person.lastname, a.reject_reason, student.type_code as studentType";

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EntityManager em;
    @Autowired
    private Validator validator;
    @Autowired
    private ApelSchoolService apelSchoolService;

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
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("(exists (select c.id from curriculum c "
                    + "join curriculum_version cv on cv.curriculum_id = c.id join user_curriculum uc on uc.curriculum_id = c.id "
                    + "where cv.id = student.curriculum_version_id and uc.user_id = :userId) "
                    + "or exists(select 1 from committee_member cm where cm.committee_id = a.committee_id "
                    + "and cm.person_id = :personId))", "userId", user.getUserId());
            qb.parameter("personId", user.getPersonId());
        }

        qb.optionalCriteria("a.type_code in (:type)", "type", criteria.getType());
        qb.optionalCriteria("a.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("a.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("a.submitted >= :submittedFrom", "submittedFrom", criteria.getSubmittedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("a.submitted <= :submittedThru", "submittedThru", criteria.getSubmittedThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("a.status_code in (:status)", "status", criteria.getStatus());
        qb.optionalCriteria("a.student_id in (:studentId)", "studentId", criteria.getStudent());

        qb.optionalContains(Arrays.asList("person.firstname", "person.lastname", "person.firstname || ' ' || person.lastname"), "name", criteria.getStudentName());

        qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getStudentIdCode());
        
        if (user.isSchoolAdmin()) {
            if (Boolean.TRUE.equals(criteria.getConnectedByCommittee())) {
                qb.requiredCriteria("exists(select 1 from committee_member cm where cm.committee_id = a.committee_id and cm.person_id = :personId)", "personId", user.getPersonId());
            }
        } else if (user.isTeacher()) {
            if (Boolean.TRUE.equals(criteria.getConnectedByCommittee())) {
                qb.requiredCriteria("(exists(select 1 from student_group sg where student.student_group_id = sg.id and sg.teacher_id = :teacherId) or exists(select 1 from committee_member cm where cm.committee_id = a.committee_id and"
                        + " (cm.person_id = :personId or cm.teacher_id = :teacherId)))", "personId", user.getPersonId());
                qb.parameter("teacherId", user.getTeacherId());
            }
        }

        Page<ApplicationSearchDto> applications = JpaQueryUtil.pagingResult(qb, APPLICATION_SELECT, em, pageable).map(r -> {
            ApplicationSearchDto dto = new ApplicationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setStatus(resultAsString(r, 2));
            dto.setInserted(resultAsLocalDateTime(r, 3));
            dto.setSubmitted(resultAsLocalDateTime(r, 4));
            Long studentId = resultAsLong(r, 5);
            String name = PersonUtil.fullnameOptionalGuest(resultAsString(r, 6), resultAsString(r, 7), resultAsString(r, 9));
            dto.setStudent(new AutocompleteResult(studentId, name, name));
            dto.setRejectReason(resultAsString(r, 8));
            return dto;
        });
        setApplicationSearchDtoRights(user, applications.getContent());
        return applications;
    }

    private void setApplicationSearchDtoRights(HoisUserDetails user, List<ApplicationSearchDto> applications) {
        Map<Long, List<ApplicationSearchDto>> applicationsByStudent = StreamUtil.nullSafeList(applications).stream()
                .collect(Collectors.groupingBy(a -> a.getStudent().getId(),
                        Collectors.mapping(a -> a, Collectors.toList())));
        if (!applicationsByStudent.isEmpty()) {
            List<Student> students = em.createQuery("select s from Student s where s.id in (:studentIds)", Student.class)
                    .setParameter("studentIds", applicationsByStudent.keySet())
                    .getResultList();

            for (Student student : students) {
                List<ApplicationSearchDto> studentApplications = applicationsByStudent.get(student.getId());
                Boolean canEditStudent = Boolean.valueOf(StudentUtil.canBeEdited(student));
                for (ApplicationSearchDto dto : studentApplications) {
                    dto.setCanViewStudent(Boolean.TRUE);
                    dto.setCanEditStudent(canEditStudent);
                }
            }
            if (user.isLeadingTeacher()) {
                setCanViewStudent(user, applicationsByStudent);
            }
        }
    }

    // leading teacher can see only his/her curriculum student group students
    private void setCanViewStudent(HoisUserDetails user, Map<Long, List<ApplicationSearchDto>> applicationsByStudent) {
        List<?> data = em.createNativeQuery("select s.id from student s"
                + " join curriculum_version cv on cv.id = s.curriculum_version_id"
                + " where cv.curriculum_id in (?1) and s.id in (?2)")
                .setParameter(1, user.getCurriculumIds())
                .setParameter(2, applicationsByStudent.keySet())
                .getResultList();
        Set<Long> studentIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);

        for (Long studentId : applicationsByStudent.keySet()) {
            if (!studentIds.contains(studentId)) {
                for (ApplicationSearchDto dto : applicationsByStudent.get(studentId)) {
                    dto.setCanViewStudent(Boolean.FALSE);
                }
            }
        }
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
        if(!(UserUtil.isStudent(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool()))) {
            if (!ApplicationType.AVALDUS_LIIK_TUGI.name().equals(applicationForm.getType()) || !(UserUtil.isStudentRepresentative(user, student) || UserUtil.isStudentGroupTeacher(user, student))) {
                throw new ValidationFailedException(String.format("user %s is not allowed to create application", user.getUsername()));
            }
        }

        Map<String, ApplicationApplicableDto> applicable = applicableApplicationTypes(student);

        if (Boolean.FALSE.equals(applicable.get(applicationForm.getType()).getIsAllowed())) {
            throw new ValidationFailedException(applicable.get(applicationForm.getType()).getReason());
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
                "studyPeriodStart", "studyPeriodStart", "accademicApplication", "newCurriculumVersion", "oldCurriculumVersion",
                "submitted", "studentGroup", "committee", "supportServices", "isDecided", "selectedModules", "themeMatches",
                "isAbroad", "ehisSchool", "abroadSchool", "apelSchool");
        application.setStudyPeriodStart(EntityUtil.getOptionalOne(StudyPeriod.class, applicationForm.getStudyPeriodStart(), em));
        application.setStudyPeriodEnd(EntityUtil.getOptionalOne(StudyPeriod.class, applicationForm.getStudyPeriodEnd(), em));
        application.setOldCurriculumVersion(EntityUtil.getOptionalOne(CurriculumVersion.class, applicationForm.getOldCurriculumVersion(), em));
        application.setNewCurriculumVersion(EntityUtil.getOptionalOne(CurriculumVersion.class, applicationForm.getNewCurriculumVersion(), em));
        application.setStudent(EntityUtil.getOptionalOne(Student.class, applicationForm.getStudent(), em));
        if (applicationForm.getCommittee() != null && application.getCommitteeAdded() == null) {
            application.setCommitteeAdded(LocalDateTime.now());
            ApplicationType type = EnumUtil.valueOf(ApplicationType.class, application.getType());
            if (ApplicationUtil.SEND_MESSAGE_CALL.containsKey(type)) {
                ApplicationUtil.SEND_MESSAGE_CALL.get(type).get(ApplicationUtil.OperationType.SAVE).accept(application, automaticMessageService);
            }
        }
        application.setCommittee(EntityUtil.getOptionalOne(Committee.class, applicationForm.getCommittee() != null ? applicationForm.getCommittee().getId() : null, em));
        if (ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_OVERSKAVA, application.getType()) || ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_RAKKAVA, application.getType())) {
            application.setStudentGroup(EntityUtil.getOptionalOne(StudentGroup.class, applicationForm.getStudentGroup(), em));
        }
        if (ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_TUGI, application.getType())) {
            application.setIsDecided(applicationForm.getIsDecided());
            EntityUtil.bindEntityCollection(application.getSupportServices(), s -> s.getSupportService().getCode(),
                applicationForm.getSupportServices(), s -> s.getCode(),
                s -> {
                    ApplicationSupportService entity = new ApplicationSupportService();
                    entity.setApplication(application);
                    entity.setSupportService(em.getReference(Classifier.class, s.getCode()));
                    return entity;
                });
            Optional<ApplicationSupportService> opt = application.getSupportServices().stream().filter(s -> ClassifierUtil.equals(SupportServiceType.TUGITEENUS_1, s.getSupportService())).findFirst();
            if (opt.isPresent()) {
                EntityUtil.bindEntityCollection(opt.get().getModules(), m -> m.getId(),
                        applicationForm.getSelectedModules() == null ? Collections.emptyList() : applicationForm.getSelectedModules(), m -> m.getId(),
                        dto -> {
                            ApplicationSupportServiceModule entity = new ApplicationSupportServiceModule();
                            entity.setModule(em.getReference(CurriculumVersionOccupationModule.class, dto.getModule().getId()));
                            entity.setAddInfo(dto.getAddInfo());
                            entity.setSupportService(opt.get());
                            return entity;
                        }, (nValue, oValue) -> {
                            oValue.setAddInfo(nValue.getAddInfo());
                        });
            }
        }

        if (ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_RAKKAVA, application.getType())) {
            EntityUtil.bindEntityCollection(application.getThemeMatches(), ApplicationOccupationModuleTheme::getId,
                    applicationForm.getThemeReplacements(), ApplicationOccupationModuleThemeDto::getId, form -> {
                        ApplicationOccupationModuleTheme entity = new ApplicationOccupationModuleTheme();
                        entity.setApplication(application);
                        entity.setCurriculumVersionOmoduleTheme(em.getReference(
                                CurriculumVersionOccupationModuleTheme.class, form.getCurriculumVersionOmoduleTheme()));
                        entity.setCurriculumModule(em.getReference(CurriculumModule.class, form.getCurriculumModule()));
                        entity.setIsOld(form.getIsOld());
                        entity.setJournal(EntityUtil.getOptionalOne(Journal.class, form.getJournal(), em));
                        entity.setPracticeJournal(EntityUtil.getOptionalOne(PracticeJournal.class, 
                                form.getPracticeJournal(), em));
                        return entity;
                    });
        }

        if (ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_VALIS, application.getType())) {
            ApelSchool apelSchool = EntityUtil.getOptionalOne(ApelSchool.class, applicationForm.getApelSchool(), em);
            if (applicationForm.getNewApelSchool() != null) {
                apelSchool = apelSchoolService.create(user, applicationForm.getNewApelSchool());
            }
            boolean isAbroad = !ClassifierUtil.isEstonia(apelSchool.getCountry());
            application.setIsAbroad(Boolean.valueOf(isAbroad));
            application.setCountry(apelSchool.getCountry());
            application.setEhisSchool(apelSchool.getEhisSchool());
            application.setAbroadSchool(isAbroad ? apelSchool.getNameEt() : null);
            application.setApelSchool(apelSchool);
        }

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
            ApplicationUtil.assertValisConstraints(application, em, false);
            break;
        case AVALDUS_LIIK_OVERSKAVA:
        case AVALDUS_LIIK_RAKKAVA:
            ApplicationUtil.assertKavaConstraints(application);
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
        updatePlannedSubject(application, applicationForm.getPlannedSubjects());
    }
    
    private void updatePlannedSubject(Application application, Set<ApplicationPlannedSubjectDto> plannedSubjects) {
        EntityUtil.bindEntityCollection(application.getPlannedSubjects(), ApplicationPlannedSubject::getId, plannedSubjects, ApplicationPlannedSubjectDto::getId, dto -> {
            ApplicationPlannedSubject plannedSubject = EntityUtil.bindToEntity(dto, new ApplicationPlannedSubject(), "equivalents");
            updateEquivalents(dto, plannedSubject);
            return plannedSubject;
        }, (dto, plannedSubject) -> {
            EntityUtil.bindToEntity(dto, plannedSubject, "equivalents");
            updateEquivalents(dto, plannedSubject);
        });
    }
    
    public Application updatePlannedSubject(Application application, ApplicationSubjectForm applicationSubjectForm) {
        updatePlannedSubject(application, applicationSubjectForm.getPlannedSubjects());
        return EntityUtil.save(application, em);
    }

    private void updateEquivalents(ApplicationPlannedSubjectDto plannedSubjectDto, ApplicationPlannedSubject plannedSubject) {
        EntityUtil.bindEntityCollection(plannedSubject.getEquivalents(), ApplicationPlannedSubjectEquivalent::getId, plannedSubjectDto.getEquivalents(), ApplicationPlannedSubjectEquivalentDto::getId, dto -> {
            ApplicationPlannedSubjectEquivalent plannedSubjectEquivalent = new ApplicationPlannedSubjectEquivalent();
            EntityUtil.bindToEntity(dto, plannedSubjectEquivalent, "subject");
            plannedSubjectEquivalent.setSubject(EntityUtil.getOptionalOne(Subject.class, dto.getSubject(), em));
            plannedSubjectEquivalent.setCurriculumVersionOmodule(EntityUtil.getOptionalOne(CurriculumVersionOccupationModule.class, dto.getModuleId(), em));
            plannedSubjectEquivalent.setCurriculumVersionOmoduleTheme(EntityUtil.getOptionalOne(CurriculumVersionOccupationModuleTheme.class, dto.getThemeId(), em));
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

    private List<String> existingApplicationsTypes(Long studentId) {
        List<?> data = em.createNativeQuery("select distinct a.type_code from application a where a.student_id = ?1 and a.status_code in (?2)")
                .setParameter(1, studentId)
                .setParameter(2, EnumUtil.toNameList(ApplicationStatus.AVALDUS_STAATUS_KOOST, ApplicationStatus.AVALDUS_STAATUS_ESIT,
                            ApplicationStatus.AVALDUS_STAATUS_YLEVAAT, ApplicationStatus.AVALDUS_STAATUS_KINNITAM))
                .getResultList();
        return StreamUtil.toMappedList(r -> resultAsString(r, 0), data);
    }

    public Application submit(HoisUserDetails user, Application application) {
        Student student = application.getStudent();
        if(UserUtil.isSchoolAdmin(user, student.getSchool())) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT);
            application.setSubmitted(LocalDateTime.now());
        } else if (UserUtil.isStudentGroupTeacher(user, student)) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_ESIT);
            application.setSubmitted(LocalDateTime.now());
        } else if (UserUtil.isAdultStudent(user, student) || UserUtil.isStudentRepresentative(user, student)) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_ESIT);
            application.setSubmitted(LocalDateTime.now());
            application.setNeedsRepresentativeConfirm(Boolean.FALSE);
        } else {
            application.setNeedsRepresentativeConfirm(Boolean.TRUE);
        }
        validateSubmission(application);
        application = EntityUtil.save(application, em);
        
        if (!UserUtil.isStudent(user, student) && !ApplicationUtil.SEND_MESSAGE_CALL.containsKey(EnumUtil.valueOf(ApplicationType.class, application.getType()))) {
            StudentApplicationCreated data = new StudentApplicationCreated(application);
            automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OP_AVALDUS, student, data);
        }
        return application;
    }

    private void validateSubmission(Application application) {
        ApplicationType applicationType = ApplicationType.valueOf(EntityUtil.getCode(application.getType()));
        switch (applicationType) {
            case AVALDUS_LIIK_VALIS:
                ApplicationUtil.assertValisConstraints(application, em, true);
                break;
            default:
                break;
        }
    }

    public Application reject(Application application, ApplicationRejectForm applicationRejectForm) {
        setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_TAGASI);
        application.setRejectReason(applicationRejectForm.getReason());
        return EntityUtil.save(application, em);
    }

    public Application confirm(Application application) {
        if (ApplicationUtil.REQUIRE_REPRESENTATIVE_CONFIRM.contains(EnumUtil.valueOf(ApplicationType.class, application.getType())) &&
                !ClassifierUtil.equals(ApplicationStatus.AVALDUS_STAATUS_KINNITAM, application.getStatus())) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_KINNITAM);
            if (ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_TUGI, application.getType())) {
                application.setCommitteeDecisionAdded(LocalDateTime.now());
                if (Boolean.FALSE.equals(application.getIsDecided())) {
                    ApplicationRejectForm form = new ApplicationRejectForm();
                    // FIXME Decision has max-length 10000 while Reason has 4000.
                    form.setReason(application.getDecision() != null && application.getDecision().length() > 4000 ? application.getDecision().substring(0, 3998) + "..." : application.getDecision());
                    return reject(application, form);
                }
            }
        } else {
            proccessApplicationConfirmation(application);
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_KINNITATUD);
        }
        return EntityUtil.save(application, em);
    }

    public ApplicationDto get(HoisUserDetails user, Application application, boolean updateStatus) {
        // XXX Can be OptimisticLock!
        ApplicationStatus oldStatus = EnumUtil.valueOf(ApplicationStatus.class, application.getStatus());
        if (updateStatus) {
            application = setSeenBySchoolAdmin(user, application);
            ApplicationDto dto = ApplicationDto.of(application);
            if (oldStatus != null && oldStatus.equals(ApplicationStatus.AVALDUS_STAATUS_ESIT) && !oldStatus.equals(EnumUtil.valueOf(ApplicationStatus.class, application.getStatus()))) {
                dto.setHasBeenSeenByAdmin(Boolean.TRUE);
            }
            setUserRights(user, application, dto);
            return dto;
        }
        ApplicationDto dto = ApplicationDto.of(application);
        setUserRights(user, application, dto);
        return dto;
    }

    private void setUserRights(HoisUserDetails user, Application application, ApplicationDto dto) {
        dto.setCanViewStudent(Boolean.valueOf(UserUtil.canViewStudent(user, application.getStudent())));
        dto.setCanRemoveConfirmation(Boolean.valueOf(canRemoveApplicationConfirmation(user, application)));
        dto.setCanChangeThemeReplacements(Boolean.valueOf(canChangeThemeReplacements(user, application)));
        if (user.isStudent() && ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_VALIS, application.getType())) {
            dto.setCanEditPlannedSubjects(Boolean.valueOf(canEditPlannedSubjects(user, application)));
        }
    }

    private boolean canEditPlannedSubjects(HoisUserDetails user, Application application) {
        // TODO Auto-generated method stub
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds"
                + " join student s on ds.student_id = s.id"
                + " join directive d on d.id = ds.directive_id"
                + " join application a on a.id = ds.application_id"
                + " left join study_period sp_start on sp_start.id = ds.study_period_start_id"
                + " left join study_period sp_end on sp_end.id = ds.study_period_end_id"
                + " left join (directive_student ds_katk join directive d_katk on d_katk.id = ds_katk.directive_id"
                    + " and d_katk.type_code = :katkDirectiveType and d_katk.status_code = :directiveStatus)"
                    + " on ds_katk.directive_student_id = ds.id "
                + " where ds.student_id = a.student_id"
                + " and a.id = :applicationId"
                + " and ds.student_id = :studentId"
                + " and s.status_code = :studentStatus"
                + " and d.type_code = :directiveType"
                + " and d.status_code = :directiveStatus"
                + " and coalesce(ds_katk.start_date, sp_end.end_date, ds.end_date) >= :today"
                + " and coalesce(sp_start.start_date, ds.start_date) <= :today");
        qb.parameter("applicationId", EntityUtil.getId(application));
        qb.parameter("studentId", user.getStudentId());
        qb.parameter("studentStatus", StudentStatus.OPPURSTAATUS_V.name());
        qb.parameter("directiveType", DirectiveType.KASKKIRI_VALIS.name());
        qb.parameter("katkDirectiveType", DirectiveType.KASKKIRI_VALISKATK.name());
        qb.parameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.parameter("today", LocalDate.now());
        List<?> result = qb.select("1", em).getResultList();
        return result.size() != 0;
    }

    /**
     * XXX NB! Version is not changed until transaction is finished. So be careful of OptimisticLock!!!
     * 
     * @param user
     * @param application
     * @return
     */
    private Application setSeenBySchoolAdmin(HoisUserDetails user, Application application) {
        if (UserUtil.isSchoolAdmin(user, application.getStudent().getSchool()) &&
                ClassifierUtil.equals(ApplicationStatus.AVALDUS_STAATUS_ESIT, application.getStatus())) {
            setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT);
            return EntityUtil.save(application, em);
        }
        return application;
    }
    
    /**
     * Checks user rights for removing confirmation.
     * If we find a directive with this application then we should check if the directive not "deleted".
     * In this case we cannot remove confirmation for this application. 
     * 
     * @param user
     * @param application
     * @return
     */
    public boolean canRemoveApplicationConfirmation(HoisUserDetails user, Application application) {
        boolean rolesAndRights = UserUtil.canRemoveApplicationConfirmation(user, application); // default
        if (rolesAndRights) {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from application a "
                    + "join directive_student ds on ds.application_id = a.id "
                    + "join directive d on d.id = ds.directive_id");
            qb.requiredCriteria("a.id = :applicationId", "applicationId", application.getId());
            qb.requiredCriteria("a.type_code = :applicationType", "applicationType", ApplicationType.AVALDUS_LIIK_TUGI.name());
            qb.requiredCriteria("a.status_code = :applicationStatus", "applicationStatus", ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name());
            qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_TUGI.name());
            qb.requiredCriteria("d.status_code in (:directiveStatus)", "directiveStatus", EnumUtil.toNameList(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL,
                    DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL));
            qb.filter("ds.canceled is not true");
            return qb.select("1", em).setMaxResults(1).getResultList().isEmpty();
        }
        return rolesAndRights; // a.k.a. false.
    }

    public boolean canChangeThemeReplacements(HoisUserDetails user, Application application) {
        ApplicationType type = EnumUtil.valueOf(ApplicationType.class, application.getType());
        Student student = application.getStudent();

        if (ApplicationType.AVALDUS_LIIK_RAKKAVA.equals(type) && UserUtil.isSchoolAdmin(user, student.getSchool())) {
            ApplicationStatus status = EnumUtil.valueOf(ApplicationStatus.class, application.getStatus());

            if (ApplicationStatus.AVALDUS_STAATUS_KOOST.equals(status)
                    || ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.equals(status)) {
                return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_AVALDUS);
            } else if (ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.equals(status)) {
                Long studentCurriculumVersion = EntityUtil.getId(student.getCurriculumVersion());
                Long applicationCurriculumVersion = EntityUtil.getId(application.getNewCurriculumVersion());
                return UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_AVALDUS)
                        && studentCurriculumVersion.equals(applicationCurriculumVersion);
            }
        }
        return false;
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

    public Map<String, ApplicationApplicableDto> applicableApplicationTypes(Student student) {
        List<String> existingApplications = existingApplicationsTypes(EntityUtil.getId(student));
        boolean isHigher = StudentUtil.isHigher(student);
        Map<String, ApplicationApplicableDto> result = new HashMap<>();
        rulesByApplicationType(student, existingApplications, result);
        rulesByApplicationClassifier(isHigher, result);
        return result;
    }

    private void rulesByApplicationClassifier(boolean isHigher, Map<String, ApplicationApplicableDto> result) {
        List<Classifier> allowedApplicationTypes = classifierService.findAllByMainClassCode(MainClassCode.AVALDUS_LIIK);
        for (Classifier allowedApplicationType : allowedApplicationTypes) {
            if ((isHigher && !allowedApplicationType.isHigher()) || (!isHigher && !allowedApplicationType.isVocational())) {
                result.remove(allowedApplicationType.getCode());
            }
        }
    }

    private void rulesByApplicationType(Student student, List<String> existingApplications,
            Map<String, ApplicationApplicableDto> result) {
        boolean isActive = StudentUtil.isActive(student);
        boolean isStudying = StudentUtil.isStudying(student);

        for (Classifier classifier : classifierService.findAllByMainClassCode(MainClassCode.AVALDUS_LIIK)) {
            String type = classifier.getCode();
            if (existingApplications.contains(type) && !ApplicationType.AVALDUS_LIIK_TUGI.name().equals(type)) {
                result.put(type, new ApplicationApplicableDto("application.messages.applicationAlreadyExists"));
            } else {
                if (ApplicationType.AVALDUS_LIIK_AKAD.name().equals(type)) {
                    if (!isActive) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentNotActive"));
                    } else if (!StudentUtil.isNominalStudy(student)) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentNotNominalStudy"));
                    }
                } else if (ApplicationType.AVALDUS_LIIK_AKADK.name().equals(type)) {
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
                } else if (ApplicationType.AVALDUS_LIIK_VALIS.name().equals(type)) {
                    if (!isStudying) {
                        result.put(type, new ApplicationApplicableDto("application.messages.studentNotStudying"));
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
        ApplicationType type = EnumUtil.valueOf(ApplicationType.class, application.getType());
        if (ApplicationUtil.SEND_MESSAGE_CALL.containsKey(type)) {
            ApplicationUtil.SEND_MESSAGE_CALL.get(type).get(ApplicationUtil.OperationType.REJECT).accept(application, automaticMessageService);
        } else {
            StudentApplicationRejectedMessage data = new StudentApplicationRejectedMessage(application);
            automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OP_AVALDUS_TL, application.getStudent(), data, user);
        }
    }

    public void sendConfirmationNotificationMessage(Application application, HoisUserDetails user) {
        log.info("confirmation notification message sent to student {}", EntityUtil.getId(application.getStudent()));
        ApplicationType type = EnumUtil.valueOf(ApplicationType.class, application.getType());
        // TODO: Make so that if there is no type then run default (let's say, key is null) operations.
        if (ApplicationUtil.SEND_MESSAGE_CALL.containsKey(type)) {
            ApplicationUtil.SEND_MESSAGE_CALL.get(type).get(ApplicationUtil.OperationType.CONFIRM).accept(application, automaticMessageService);
        } else {
            StudentApplicationConfirmed data = new StudentApplicationConfirmed(application);
            automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OP_AVALDUS_KINNIT, application.getStudent(), data, user);
        }
    }

    private void setApplicationStatus(Application application, ApplicationStatus status) {
        application.setStatus(em.getReference(Classifier.class, status.name()));
    }
    
    private void proccessApplicationConfirmation(Application application) {
        if (ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_OVERSKAVA, application.getType()) || ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_RAKKAVA, application.getType())) {
            Student student = application.getStudent();
            studentService.saveWithHistory(student);
            student.setCurriculumVersion(application.getNewCurriculumVersion());
            student.setStudentGroup(application.getStudentGroup());
            EntityUtil.save(student, em);
        }
    }

    public List<ApplicationSupportServiceModuleDto> individualCurriculumModules(Student student, Long applicationId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s"
                + " join curriculum_version cv on s.curriculum_version_id = cv.id"
                + " join curriculum_version_omodule cvo on cv.id = cvo.curriculum_version_id"
                + " join curriculum_module cm on cvo.curriculum_module_id = cm.id"
                + " join classifier mcl on cm.module_code = mcl.code");

        qb.requiredCriteria("s.id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("cm.module_code in (:moduleCodes)", "moduleCodes",
                EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_P, CurriculumModuleType.KUTSEMOODUL_Y));

        qb.filter("(not exists (select 1 from student_vocational_result svr"
                + " where svr.curriculum_version_omodule_id = cvo.id and svr.student_id = :studentId"
                + " and svr.grade_code in (:positiveGradeCodes))"
                + (applicationId != null ? " or exists (select 1 from application_support_service_module assm"
                    + " join application_support_service ass on assm.application_support_service_id = ass.id"
                    + " join application a on a.id = ass.application_id"
                    + " and s.id = a.student_id and a.id = :applicationId"
                    + " where assm.curriculum_version_omodule_id = cvo.id))" : ")"));

        qb.parameter("studentId", EntityUtil.getId(student));
        if (applicationId != null) {
            qb.parameter("applicationId", applicationId);
        }
        qb.parameter("positiveGradeCodes", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        List<?> data = qb.select("cvo.id, cm.name_et, mcl.name_et mcl_name_et, cm.name_en, mcl.name_en mcl_name_en", em)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            ApplicationSupportServiceModuleDto dto = new ApplicationSupportServiceModuleDto();
            String moduleEt = resultAsString(r, 1);
            String moduleEn = resultAsString(r, 3);
            String clEt = resultAsString(r, 2);
            String clEn = resultAsString(r, 4);
            dto.setModule(new AutocompleteResult(resultAsLong(r, 0),
                CurriculumUtil.moduleName(moduleEt, clEt),
                CurriculumUtil.moduleName(moduleEn != null ? moduleEn : moduleEt, clEn != null ? clEn : clEt)));
            return dto;
        }, data);
    }

    public ApplicationThemeReplacementDto curriculumVersionThemeReplacements(Student student, Long applicationId,
            Long curriculumVersionId) {
        ApplicationThemeReplacementDto dto = new ApplicationThemeReplacementDto();

        List<ApplicationThemeReplacementModuleDto> modules = curriculumVersionModules(curriculumVersionId);
        Map<Long, List<ApplicationOccupationModuleThemeDto>> newModuleThemeMap = newModuleThemes(student, applicationId,
                curriculumVersionId);
        Map<Long, List<ApplicationOccupationModuleThemeDto>> oldModuleThemeMap = oldModuleThemes(student, applicationId,
                curriculumVersionId);

        Iterator<ApplicationThemeReplacementModuleDto> iterator = modules.iterator();
        while (iterator.hasNext()) {
            ApplicationThemeReplacementModuleDto module = iterator.next();
            List<ApplicationOccupationModuleThemeDto> newModuleThemes = newModuleThemeMap.get(module.getId());
            List<ApplicationOccupationModuleThemeDto> oldModuleThemes = oldModuleThemeMap.get(module.getId());

            if (newModuleThemes != null && oldModuleThemes != null) {
                module.getNewThemes().addAll(newModuleThemes);
                module.getOldThemes().addAll(oldModuleThemes);
            } else {
                iterator.remove();
            }
        }

        dto.getModules().addAll(modules);
        return dto;
    }

    private List<ApplicationThemeReplacementModuleDto> curriculumVersionModules(Long curriculumVersionId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version cv"
                + " join curriculum c on c.id = cv.curriculum_id"
                + " join curriculum_module cm on cm.curriculum_id = c.id");
        qb.requiredCriteria("cv.id = :curriculumVersionId", "curriculumVersionId", curriculumVersionId);

        List<?> data = qb.select("cm.id cm_id, cm.name_et, cm.name_en, cm.module_code", em).getResultList();

        return StreamUtil.toMappedList(r -> new ApplicationThemeReplacementModuleDto(resultAsLong(r, 0),
                resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3)), data);
    }

    private Map<Long, List<ApplicationOccupationModuleThemeDto>> newModuleThemes(Student student, Long applicationId,
            Long curriculumVersionId) {
        String from = "from curriculum_version cv"
                + " join curriculum_version_omodule cvo on cvo.curriculum_version_id = cv.id"
                + " join curriculum_version_omodule_theme cvot on cvot.curriculum_version_omodule_id = cvo.id";
        if (applicationId != null) {
            from += " left join (application_omodule_theme aot join application a on aot.application_id = a.id)"
                    + " on aot.curriculum_version_omodule_theme_id = cvot.id and a.student_id = :studentId"
                    + " and a.id = :applicationId";
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("cv.id = :curriculumVersionId", "curriculumVersionId", curriculumVersionId);
        if (applicationId != null) {
            qb.parameter("studentId", EntityUtil.getId(student));
            qb.parameter("applicationId", applicationId);
        }

        List<?> data = qb.select("cvo.curriculum_module_id, cvot.id cvot_id, cvot.name_et"
                + (applicationId != null ? ", aot.id aot_id" : ", null aot_id"), em).getResultList();
        return StreamUtil.nullSafeList(data).stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    ApplicationOccupationModuleThemeDto theme = new ApplicationOccupationModuleThemeDto();
                    theme.setCurriculumModule(resultAsLong(r, 0));
                    theme.setCurriculumVersionOmoduleTheme(resultAsLong(r, 1));
                    theme.setTheme(new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2),
                            resultAsString(r, 2)));
                    theme.setIsOld(Boolean.FALSE);
                    theme.setId(resultAsLong(r, 3));
                    theme.setCovering(Boolean.valueOf(theme.getId() != null)); 
                    return theme;
                }, Collectors.toList())));
    }

    private Map<Long, List<ApplicationOccupationModuleThemeDto>> oldModuleThemes(Student student, Long applicationId,
            Long newCurriculumVersionId) {
        Application application = EntityUtil.getOptionalOne(Application.class, applicationId, em);

        String journalFrom = "from journal_student js"
                + " join journal_omodule_theme jot on jot.journal_id = js.journal_id"
                + " join journal_entry je on je.journal_id = js.journal_id" 
                + " join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id"
                + " join classifier cl on cl.code = jes.grade_code"
                + " join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id"
                + " join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id"
                + " join curriculum_version cv2 on cv2.id = :oldCurriculumVersionId"
                + " join curriculum_version cv on cv.id = cvo.curriculum_version_id and cv2.curriculum_id = cv.curriculum_id"
                + " and cv.id != :newCurriculumVersionId"
                + " join student s on s.id = js.student_id";
        if (applicationId != null) {
            journalFrom += " join application_omodule_theme aot on aot.curriculum_version_omodule_theme_id = cvot.id"
                    + " and aot.journal_id = js.journal_id";
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(journalFrom);
        qb.requiredCriteria("js.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("je.entry_type_code = :entryType", "entryType", JournalEntryType.SISSEKANNE_L);
        qb.requiredCriteria("jes.grade_code in (:positiveGrades)", "positiveGrades",
                OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        if (applicationId != null) {
            qb.requiredCriteria("aot.application_id = :applicationId", "applicationId", applicationId);
            qb.parameter("oldCurriculumVersionId", EntityUtil.getId(application.getOldCurriculumVersion()));
        } else {
            qb.parameter("oldCurriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));
        }
        qb.parameter("newCurriculumVersionId", newCurriculumVersionId);

        String journalResults = qb.querySql("cvo.curriculum_module_id, cvot.id, cvot.name_et, js.journal_id,"
                + " null practice_journal_id, jes.grade_code, cl.value, jes.grade_inserted,"
                + (applicationId != null ? " aot.id aot_id" : " null"), false);
        Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

        String practiceJournalFrom = "from practice_journal pj"
                + " join classifier cl2 on cl2.code = pj.grade_code"
                + " join practice_journal_module_subject pjms on pjms.practice_journal_id = pj.id"
                + " join curriculum_version_omodule_theme cvot2 on cvot2.id = pjms.curriculum_version_omodule_theme_id"
                + " join curriculum_version_omodule cvo2 on cvo2.id = cvot2.curriculum_version_omodule_id"
                + " join curriculum_version cv4 on cv4.id = :oldCurriculumVersionId"
                + " join curriculum_version cv3 on cv3.id = cvo2.curriculum_version_id and cv4.curriculum_id = cv3.curriculum_id"
                + " and cv3.id != :newCurriculumVersionId"
                + " join student s2 on s2.id = pj.student_id";
        if (applicationId != null) {
            practiceJournalFrom += " join application_omodule_theme aot2 on aot2.curriculum_version_omodule_theme_id = cvot2.id"
                    + " and aot2.practice_journal_id = pj.id";
        }

        qb = new JpaNativeQueryBuilder(practiceJournalFrom);
        qb.requiredCriteria("pj.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("pj.grade_code in (:positiveGrades)", "positiveGrades",
                OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        if (applicationId != null) {
            qb.requiredCriteria("aot2.application_id = :applicationId", "applicationId", applicationId);
        }

        String practiceJournalResults = qb.querySql("cvo2.curriculum_module_id, cvot2.id, cvot2.name_et, null journal_id,"
                + " pj.id practice_journal_id, pj.grade_code, cl2.value, pj.grade_inserted,"
                + (applicationId != null ? " aot2.id aot_id" : " null"), false);
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + journalResults + " union all " + practiceJournalResults + ") as r");
        List<?> data = qb.select("*", em, parameters).getResultList();

        return StreamUtil.nullSafeList(data).stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    ApplicationOccupationModuleThemeDto theme = new ApplicationOccupationModuleThemeDto();
                    theme.setCurriculumModule(resultAsLong(r, 0));
                    theme.setCurriculumVersionOmoduleTheme(resultAsLong(r, 1));
                    theme.setTheme(new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2),
                            resultAsString(r, 2)));
                    theme.setJournal(resultAsLong(r, 3));
                    theme.setPracticeJournal(resultAsLong(r, 4));
                    theme.setGradeCode(resultAsString(r, 5));
                    theme.setGrade(resultAsString(r, 6));
                    theme.setGradeInserted(resultAsLocalDate(r, 7));
                    theme.setIsOld(Boolean.TRUE);
                    theme.setId(resultAsLong(r, 8));
                    return theme;
                }, Collectors.toList())));
    }

    public void confirmConfirmation(HoisUserDetails user, Application application, ApplicationConfirmConfirmationForm form) {
        if (Boolean.TRUE.equals(form.getConfirm())) {
            confirm(application);
            application.setIsRepresentativeConfirmed(Boolean.TRUE);
        } else if (Boolean.FALSE.equals(form.getConfirm())) {
            ApplicationRejectForm rejectForm = new ApplicationRejectForm();
            rejectForm.setReason(form.getRepresentativeDecisionAddInfo());
            reject(application, rejectForm);
            application.setIsRepresentativeConfirmed(Boolean.FALSE);
        }
        
        application.setRepresentativeDecisionAddInfo(form.getRepresentativeDecisionAddInfo());
        if (user.isSchoolAdmin() && form.getOisFile() != null) {
            ApplicationFile file = new ApplicationFile();
            file.setOisFile(EntityUtil.bindToEntity(form.getOisFile(), new OisFile()));
            application.getFiles().add(file);
        }
        
        application.setRepresentativeConfirmed(LocalDateTime.now());
    }

    public void removeConfirmation(Application application) {
        // reset confirmation info
        application.setIsRepresentativeConfirmed(null);
        application.setRepresentativeDecisionAddInfo(null);
        // reset dates
        application.setRepresentativeConfirmed(null);
        application.setCommitteeDecisionAdded(null);
        // reset committee decision "NO"
        application.setRejectReason(null);
        setApplicationStatus(application, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT);
    }
}
