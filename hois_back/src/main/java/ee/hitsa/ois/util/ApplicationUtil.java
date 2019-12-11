package ee.hitsa.ois.util;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.AbroadProgramme;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.ConfirmationNeededMessage;
import ee.hitsa.ois.message.StudentApplicationChosenCommitteeMessage;
import ee.hitsa.ois.message.StudentApplicationCreated;
import ee.hitsa.ois.message.StudentApplicationRejectedMessage;
import ee.hitsa.ois.service.AutomaticMessageService;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.application.ApplicationForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm.DirectiveFormStudent;

public abstract class ApplicationUtil {
    
    public enum OperationType {
        SAVE,
        SUBMIT,
        CONFIRM,
        CONFIRM_CONFIRMATION,
        REJECT;
    }

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final long DAYS_IN_YEAR = 365L;
    
    private static final long MAX_ABROAD_PERIOD_DAYS_UNDER_5_YEARS_CURRICULUM_PERIOD = 365L;
    
    private static final long MAX_ABROAD_PERIOD_DAYS_OVER_5_YEARS_CURRICULUM_PERIOD = 730L;
    
    private static final long FIVE_YEARS_IN_MONTHS = 60;

    public static final Set<ApplicationType> CAN_BE_CONFIRMED = new HashSet<>();
    public static final Set<ApplicationType> REQUIRE_REPRESENTATIVE_CONFIRM = new HashSet<>();
    public static final Map<ApplicationType, Map<OperationType, BiConsumer<Application, AutomaticMessageService>>> SEND_MESSAGE_CALL = new HashMap<>(); 
    
    static {
        CAN_BE_CONFIRMED.add(ApplicationType.AVALDUS_LIIK_MUU);
        CAN_BE_CONFIRMED.add(ApplicationType.AVALDUS_LIIK_OVERSKAVA);
        CAN_BE_CONFIRMED.add(ApplicationType.AVALDUS_LIIK_RAKKAVA);
        CAN_BE_CONFIRMED.add(ApplicationType.AVALDUS_LIIK_TUGI);
        REQUIRE_REPRESENTATIVE_CONFIRM.add(ApplicationType.AVALDUS_LIIK_TUGI);
        
        BiConsumer<Application, AutomaticMessageService> emptyConsumer = (e, s) -> {};
        
        Map<OperationType, BiConsumer<Application, AutomaticMessageService>> defaultMessageCalls = new HashMap<>();
        defaultMessageCalls.put(OperationType.SAVE, emptyConsumer);
        defaultMessageCalls.put(OperationType.SUBMIT, emptyConsumer);
        defaultMessageCalls.put(OperationType.CONFIRM, emptyConsumer);
        defaultMessageCalls.put(OperationType.CONFIRM_CONFIRMATION, emptyConsumer);
        defaultMessageCalls.put(OperationType.REJECT, emptyConsumer);
        
        SEND_MESSAGE_CALL.put(ApplicationType.AVALDUS_LIIK_TUGI, new HashMap<>(defaultMessageCalls));
        SEND_MESSAGE_CALL.get(ApplicationType.AVALDUS_LIIK_TUGI).put(OperationType.SUBMIT, (application, service) -> {
            StudentApplicationCreated dataBean = new StudentApplicationCreated(application);
            service.sendMessageToStudentAndRepresentativeAndSchoolAdmins(MessageType.TEATE_LIIK_OP_AVALDUS, application.getStudent(), dataBean);
        });
        SEND_MESSAGE_CALL.get(ApplicationType.AVALDUS_LIIK_TUGI).put(OperationType.REJECT, (application, service) -> {
            StudentApplicationRejectedMessage dataBean = new StudentApplicationRejectedMessage(application);
            service.sendMessageToStudent(MessageType.TEATE_LIIK_OP_AVALDUS_TL, application.getStudent(), dataBean);
        });
        SEND_MESSAGE_CALL.get(ApplicationType.AVALDUS_LIIK_TUGI).put(OperationType.SAVE, (application, service) -> {
            StudentApplicationChosenCommitteeMessage dataBean = new StudentApplicationChosenCommitteeMessage(application);
            service.sendMessageToStudentAndRepresentativeAndSchoolAdmins(MessageType.TEATE_LIIK_OP_AVALDUS_YL, application.getStudent(), dataBean);
        });
        SEND_MESSAGE_CALL.get(ApplicationType.AVALDUS_LIIK_TUGI).put(OperationType.CONFIRM, (application, service) -> {
            if (Boolean.FALSE.equals(application.getIsDecided())) {
                SEND_MESSAGE_CALL.get(ApplicationType.AVALDUS_LIIK_TUGI).get(OperationType.REJECT).accept(application, service);
            } else {
                ConfirmationNeededMessage dataBean = new ConfirmationNeededMessage(application);
                service.sendMessageToStudentAndRepresentativeAndSchoolAdmins(MessageType.TEATE_LIIK_AV_KINNIT, application.getStudent(), dataBean);
            }
        });
    }

    public static void assertPeriod(Application application, int years, long daysUsed) {
        long applicationDays = ChronoUnit.DAYS.between(getStartDate(application), getEndDate(application));
        long daysToUse = DAYS_IN_YEAR * years - daysUsed;

        if (daysToUse < applicationDays) {
            log.debug("Application period is {} days, maximum time is {} years, but {} days are already used",
                    Long.valueOf(applicationDays), Long.valueOf(years), Long.valueOf(daysUsed));
            throw new ValidationFailedException("application.messages.periodIsTooLong");
        }
    }

    public static void assertStartAfterToday(Application application) {
        LocalDate start = getStartDate(application);
        if (LocalDate.now().isAfter(start)) {
            throw new ValidationFailedException("application.messages.startIsEarlierThanToday");
        }
    }

    public static LocalDate getEndDate(Period application) {
        LocalDate date = DateUtils.periodEnd(application);
        if (date == null) {
            boolean isPeriod = Boolean.TRUE.equals(application.getIsPeriod());
            throw new ValidationFailedException(isPeriod ? "application.messages.endPeriodMissing" : "application.messages.endDateMissing");
        }
        return date;
    }

    public static LocalDate getStartDate(Period application) {
        LocalDate date = DateUtils.periodStart(application);
        if (date == null) {
            boolean isPeriod = Boolean.TRUE.equals(application.getIsPeriod());
            throw new ValidationFailedException(isPeriod ? "application.messages.startPeriodMissing" : "application.messages.startDateMissing");
        }
        return date;
    }

    public static void assertValisConstraints(Application application) {
        assertStartAfterToday(application);
    }
    
    public static void assertValidationRulesSave(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2,
            String abroadProgramme, Long studentId, EntityManager em) {
        if (AbroadProgramme.VALISKOOL_PROGRAMM_1_1.name().equals(abroadProgramme)) {
            Student student = em.getReference(Student.class, studentId);
            CurriculumVersion version = student.getCurriculumVersion();
            if (version == null || version.getCurriculum().getStudyPeriod().intValue() < FIVE_YEARS_IN_MONTHS) {
                long previousDaysBeenAbroad = daysBeenAbroad(studentId, em);
                long applicationDuration = ChronoUnit.DAYS.between(startDate1, endDate1);
                if (startDate2 != null && endDate2 != null) {
                    applicationDuration += ChronoUnit.DAYS.between(startDate2, endDate2);
                }
                if (applicationDuration + previousDaysBeenAbroad > MAX_ABROAD_PERIOD_DAYS_UNDER_5_YEARS_CURRICULUM_PERIOD) {
                    throw new HoisException("application.messages.periodTooLargeUnderFive");
                }
            }
        }
    }
    
    public static void assertValidationRulesSave(DirectiveFormStudent formStudent, Long studentId, EntityManager em) {
        LocalDate startDate1;
        LocalDate endDate1;
        LocalDate startDate2 = null;
        LocalDate endDate2 = null;
        if (Boolean.TRUE.equals(formStudent.getIsPeriod())) {
            StudyPeriod startPeriod = em.getReference(StudyPeriod.class, formStudent.getStudyPeriodStart());
            StudyPeriod endPeriod = em.getReference(StudyPeriod.class, formStudent.getStudyPeriodEnd());
            startDate1 = startPeriod.getStartDate();
            endDate1 = startPeriod.getEndDate();
            startDate2 = endPeriod.getStartDate();
            endDate2 = endPeriod.getEndDate();
            if (startDate1.isAfter(endDate2)) throw new HoisException("application.messages.startIsEarlierThanEnd");
        } else {
            startDate1 = formStudent.getStartDate();
            endDate1 = formStudent.getEndDate();
            if (startDate1.isAfter(endDate1)) throw new HoisException("application.messages.startIsEarlierThanEnd");
        }
        if (startDate1.isBefore(LocalDate.now())) throw new HoisException("application.messages.startIsEarlierThanToday");
        assertValidationRulesSave(startDate1, endDate1, startDate2, endDate2, formStudent.getAbroadProgramme(), studentId, em);
    }
    
    public static void assertValidationRulesSave(ApplicationForm applicationForm, Long studentId, EntityManager em) {
        LocalDate startDate1;
        LocalDate endDate1;
        LocalDate startDate2 = null;
        LocalDate endDate2 = null;
        if (Boolean.TRUE.equals(applicationForm.getIsPeriod())) {
            StudyPeriod startPeriod = em.getReference(StudyPeriod.class, applicationForm.getStudyPeriodStart());
            StudyPeriod endPeriod = em.getReference(StudyPeriod.class, applicationForm.getStudyPeriodEnd());
            startDate1 = startPeriod.getStartDate();
            endDate1 = startPeriod.getEndDate();
            startDate2 = endPeriod.getStartDate();
            endDate2 = endPeriod.getEndDate();
            if (startDate1.isAfter(endDate2)) throw new HoisException("application.messages.startIsEarlierThanEnd");
        } else {
            startDate1 = applicationForm.getStartDate();
            endDate1 = applicationForm.getEndDate();
            if (startDate1.isAfter(endDate1)) throw new HoisException("application.messages.startIsEarlierThanEnd");
        }
        if (startDate1.isBefore(LocalDate.now())) throw new HoisException("application.messages.startIsEarlierThanToday");
        assertValidationRulesSave(startDate1, endDate1, startDate2, endDate2, applicationForm.getAbroadProgramme(), studentId, em);
    }
    
    public static void assertValidationRulesConfirm(LocalDate startDate, LocalDate endDate, LocalDate startDate2, LocalDate endDate2, 
            String abroadProgramme, Long studentId, EntityManager em) throws HoisException {
        if (AbroadProgramme.VALISKOOL_PROGRAMM_1_1.name().equals(abroadProgramme)) {
            Student student = em.getReference(Student.class, studentId);
            CurriculumVersion version = student.getCurriculumVersion();
            long previousDaysBeenAbroad = daysBeenAbroad(studentId, em);
            long applicationDuration = ChronoUnit.DAYS.between(startDate, endDate);
            if (startDate2 != null && endDate2 != null) {
                applicationDuration += ChronoUnit.DAYS.between(startDate2, endDate2);
            }
            long totalAbroad = applicationDuration + previousDaysBeenAbroad;
            if (version == null || version.getCurriculum().getStudyPeriod().intValue() < FIVE_YEARS_IN_MONTHS) {
                if (totalAbroad > MAX_ABROAD_PERIOD_DAYS_UNDER_5_YEARS_CURRICULUM_PERIOD) {
                    throw new HoisException("application.messages.periodTooLargeUnderFive");
                }
            } else if (version.getCurriculum().getStudyPeriod().intValue() >= FIVE_YEARS_IN_MONTHS) {
                if (totalAbroad > MAX_ABROAD_PERIOD_DAYS_OVER_5_YEARS_CURRICULUM_PERIOD) {
                    throw new HoisException("application.messages.periodTooLargeOverFive");
                }
            }
        }
    }
    
    public static void assertValidationRulesConfirm(DirectiveStudent directiveStudent, Long studentId, EntityManager em) {
        LocalDate startDate1;
        LocalDate endDate1;
        LocalDate startDate2 = null;
        LocalDate endDate2 = null;
        if (Boolean.TRUE.equals(directiveStudent.getIsPeriod())) {
            StudyPeriod startPeriod = directiveStudent.getStudyPeriodStart();
            StudyPeriod endPeriod = directiveStudent.getStudyPeriodEnd();
            startDate1 = startPeriod.getStartDate();
            endDate1 = startPeriod.getEndDate();
            startDate2 = endPeriod.getStartDate();
            endDate2 = endPeriod.getEndDate();
            if (startDate1.isAfter(endDate2)) throw new HoisException("application.messages.startIsEarlierThanEnd");
        } else {
            startDate1 = directiveStudent.getStartDate();
            endDate1 = directiveStudent.getEndDate();
            if (startDate1.isAfter(endDate1)) throw new HoisException("application.messages.startIsEarlierThanEnd");
        }
        if (startDate1.isBefore(LocalDate.now())) throw new HoisException("application.messages.startIsEarlierThanToday");
        assertValidationRulesConfirm(startDate1, endDate1, startDate2, endDate2, 
                EntityUtil.getNullableCode(directiveStudent.getAbroadProgramme()), studentId, em);
    }
    
    private static long daysBeenAbroad(Long studentId, EntityManager em) {
        if (studentId == null) return 0;
        List<?> data = em.createNativeQuery("select sum(ds.start_date - ds.end_date)"
                + " from directive d"
                + " join directive_student ds on ds.directive_id = d.id"
                + " join application a on a.id = ds.application_id"
                + " where ds.student_id = ?1 and a.directive_id = ds.directive_id and d.type_code = ?2"
                + " and d.status_code = ?3 and ds.canceled = false")
            .setParameter(1, studentId)
            .setParameter(2, DirectiveType.KASKKIRI_VALIS.name())
            .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
            .getResultList();
        Long days = resultAsLong(data.get(0), 0);
        return days == null ? 0 : days.longValue();
    }

    public static void assertAkadkConstraints(Application application) {
        Directive academicDirective = application.getDirective();
        if (!ClassifierUtil.equals(DirectiveType.KASKKIRI_AKAD, academicDirective.getType())) {
            throw new ValidationFailedException("application.messages.wrongAcademicApplicationType");
        }

        DirectiveStudent directiveStudent = DirectiveUtil.getDirectiveStudent(academicDirective, 
                EntityUtil.getId(application.getStudent()));
        LocalDate revocationStart = getStartDate(application);

        LocalDate academicLeaveStart = getStartDate(directiveStudent);
        if (revocationStart.isBefore(academicLeaveStart)) {
            throw new ValidationFailedException("application.messages.revocationStartDateBeforeAcademicLeaveStartDate");
        }

        LocalDate academicLeaveEnd = getEndDate(directiveStudent);
        if (revocationStart.isAfter(academicLeaveEnd)) {
            throw new ValidationFailedException("application.messages.revocationStartDateAfterAcademicLeaveEndDate");
        }
    }

    public static void assertKavaConstraints(Application application) {
        ValidationFailedException.throwIf(application.getNewCurriculumVersion().equals(application.getOldCurriculumVersion()), "application.messages.sameCurriculumVersion");
        if (Boolean.TRUE.equals(application.getNewCurriculumVersion().getCurriculum().getHigher())) {
            ValidationFailedException.throwIf(application.getStudentGroup().getCurriculum() == null || !application.getStudentGroup()
                    .getCurriculum().equals(application.getNewCurriculumVersion().getCurriculum()),
                    "application.messages.noConnectionBetweenGroupAndVersion");
        } else {
            ValidationFailedException.throwIf(application.getStudentGroup().getCurriculumVersion() == null || !application.getStudentGroup()
                    .getCurriculumVersion().equals(application.getNewCurriculumVersion()),
                    "application.messages.noConnectionBetweenGroupAndVersion");
        }
    }

    public static DirectiveStudent getDirectiveStudent(Application application) {
        return StreamUtil.nullSafeList(application.getDirectiveStudents()).stream()
                .filter(r -> !Boolean.TRUE.equals(r.getCanceled()))
                .max(Comparator.comparingLong(DirectiveStudent::getId)).orElse(null);
    }
}
