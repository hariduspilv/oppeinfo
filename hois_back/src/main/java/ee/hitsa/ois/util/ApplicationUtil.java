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
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.ConfirmationNeededMessage;
import ee.hitsa.ois.message.StudentApplicationChosenCommitteeMessage;
import ee.hitsa.ois.message.StudentApplicationCreated;
import ee.hitsa.ois.message.StudentApplicationRejectedMessage;
import ee.hitsa.ois.service.AutomaticMessageService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

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
        assertStartAfterToday(getStartDate(application));
    }

    private static void assertStartAfterToday(LocalDate start) {
        if (LocalDate.now().isAfter(start)) {
            throw new ValidationFailedException("application.messages.startIsEarlierThanToday");
        }
    }

    private static void assertStartBeforeEnd(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new HoisException("application.messages.startIsEarlierThanEnd");
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

    public static void assertValisConstraints(Application application, EntityManager em, boolean isSubmitted) {
        Long studentId = EntityUtil.getId(application.getStudent());
        LocalDate start = getStartDate(application);
        LocalDate end = getEndDate(application);
        assertStartAfterToday(start);
        assertStartBeforeEnd(start, end);
        assertValisPeriodUniqueness(studentId, null, start, end, em);
        if (isSubmitted) {
            assertValisPeriodLength(studentId, start, end, EntityUtil.getNullableCode(application.getAbroadProgramme()), em);
        }
    }

    public static void assertValisPeriodUniqueness(Long studentId, Long directiveStudentId, LocalDate startDate,
            LocalDate endDate, EntityManager em) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " left join study_period sp_start on sp_start.id = ds.study_period_start_id"
                + " left join study_period sp_end on sp_end.id = ds.study_period_end_id"
                + " left join (directive_student ds_katk join directive d_katk on d_katk.id = ds_katk.directive_id"
                    + " and d_katk.type_code = :katkDirectiveType and d_katk.status_code = :directiveStatus)"
                    + " on ds_katk.directive_student_id = ds.id and ds_katk.canceled = false");
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", studentId);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_VALIS);
        qb.requiredCriteria("d.status_code = :directiveStatus and ds.canceled = false", "directiveStatus",
                DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.parameter("katkDirectiveType", DirectiveType.KASKKIRI_VALISKATK.name());
        qb.requiredCriteria(
                "coalesce(case when ds_katk.start_date is not null then ds_katk.start_date - interval '1 day' else null end, sp_end.end_date, ds.end_date) >= :start",
                "start", startDate);
        qb.requiredCriteria("coalesce(sp_start.start_date, ds.start_date) <= :end", "end", endDate);
        qb.optionalCriteria("ds.id != :directiveStudentId", "directiveStudentId", directiveStudentId);

        List<?> data = qb.select("ds.id", em).setMaxResults(1).getResultList();
        if (!data.isEmpty()) {
            throw new HoisException("application.messages.foreignStudyInPeriod");
        }
    }

    public static void assertValisPeriodLength(Long studentId, LocalDate startDate, LocalDate endDate,
            String abroadProgramme, EntityManager em) throws HoisException {
        if (AbroadProgramme.VALISKOOL_PROGRAMM_1_1.name().equals(abroadProgramme)) {
            Student student = em.getReference(Student.class, studentId);
            CurriculumVersion version = student.getCurriculumVersion();
            long previousDaysBeenAbroad = daysBeenAbroad(studentId, em);
            long applicationDuration = ChronoUnit.DAYS.between(startDate, endDate) + 1;
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

    public static void assertValisDirectiveConstraints(DirectiveStudent directiveStudent, EntityManager em) {
        Long studentId = EntityUtil.getId(directiveStudent.getStudent());
        LocalDate start = getStartDate(directiveStudent);
        LocalDate end = getEndDate(directiveStudent);
        assertStartAfterToday(start);
        assertStartBeforeEnd(start, end);
        assertValisPeriodLength(studentId, start, end, EntityUtil.getNullableCode(directiveStudent.getAbroadProgramme()), em);
        assertValisPeriodUniqueness(studentId, directiveStudent.getId(), start, end, em);
    }
    
    private static long daysBeenAbroad(Long studentId, EntityManager em) {
        if (studentId == null) return 0;
        List<?> data = em.createNativeQuery("select sum(coalesce(case when KATK.start_date is not null then (KATK.start_date - interval '1 day')\\:\\:date else null end, ds.end_date, spEnd.end_date) - coalesce(ds.start_date, spStart.start_date) + 1)"
                + " from directive_student ds "
                + " join directive d on ds.directive_id = d.id"
                + " join application a on a.id = ds.application_id"
                + " left join study_period spStart on spStart.id = ds.study_period_start_id"
                + " left join study_period spEnd on spEnd.id = ds.study_period_end_id"
                + " left join (select ds1.start_date, ds1.directive_student_id, ds1.student_id from directive_student ds1"
                    + " join directive d1 on ds1.directive_id = d1.id"
                    + " where d1.type_code = ?4"
                    + " and d1.status_code = ?3) KATK"
                    + " on KATK.directive_student_id = ds.id"
                + " where ds.student_id = ?1 and d.type_code = ?2"
                + " and d.status_code = ?3"
                + " and (ds.canceled is null or ds.canceled = false)")
            .setParameter(1, studentId)
            .setParameter(2, DirectiveType.KASKKIRI_VALIS.name())
            .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
            .setParameter(4, DirectiveType.KASKKIRI_VALISKATK.name())
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

    public static void assertIsStudentValis(HoisUserDetails user, Application application, EntityManager em) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds "
                + "join directive d on d.id = ds.directive_id "
                + "left join study_period spStart on spStart.id = ds.study_period_start_id "
                + "left join study_period spEnd on spEnd.id = ds.study_period_end_id "
                + "left join (select ds1.start_date, ds1.directive_student_id, ds1.student_id from directive_student ds1 "
                    + "join directive d1 on ds1.directive_id = d1.id "
                    + "where d1.type_code = '" + DirectiveType.KASKKIRI_VALISKATK.name() + "') KATK "
                    + "on KATK.directive_student_id = ds.id");
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", EntityUtil.getId(application.getStudent()));
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_VALIS.name());
        qb.requiredCriteria("coalesce(ds.start_date, spStart.start_date) <= :now", "now", LocalDate.now());
        qb.requiredCriteria("coalesce(case when KATK.start_date is not null then KATK.start_date - interval '1 day' else null end, ds.end_date, spEnd.end_date) >= :now", "now", LocalDate.now());
        // id, startDate, endDate
        List<?> data = qb.select("ds.id", em).getResultList();
        if(!user.isStudent() || !UserUtil.isStudent(user, application.getStudent()) || 
                !ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_V, application.getStudent().getStatus())
                || data.isEmpty()) {
            throw new HoisException("application.messages.subjectChangeNotAllowed");
        }
    }

}
