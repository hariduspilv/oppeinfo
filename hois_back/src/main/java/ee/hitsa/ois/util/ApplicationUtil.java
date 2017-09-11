package ee.hitsa.ois.util;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.enums.AcademicLeaveReason;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.validation.ValidationFailedException;

public class ApplicationUtil {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final long DAYS_IN_YEAR = 365L;

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

    public static LocalDate getEndDate(Application application) {
        LocalDate date = DateUtils.periodEnd(application);
        if (date == null && Boolean.TRUE.equals(application.getIsPeriod())) {
            throw new ValidationFailedException("application.messages.endPeriodMissing");
        } else if (date == null && Boolean.FALSE.equals(application.getIsPeriod())) {
            throw new ValidationFailedException("application.messages.endDateMissing");
        }
        return date;
    }

    public static LocalDate getStartDate(Application application) {
        LocalDate date = DateUtils.periodStart(application);
        if (date == null && Boolean.TRUE.equals(application.getIsPeriod())) {
            throw new ValidationFailedException("application.messages.startPeriodMissing");
        } else if (date == null && Boolean.FALSE.equals(application.getIsPeriod())) {
            throw new ValidationFailedException("application.messages.startDateMissing");
        }
        return date;
    }

    public static long daysUsed(Long studentId, AcademicLeaveReason reason, ApplicationRepository applicationRepository) {
        List<Application> previousSameTypeAcademicLeaves = applicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("student").get("id"), studentId));
            filters.add(cb.equal(root.get("type").get("code"), ApplicationType.AVALDUS_LIIK_AKAD.name()));
            filters.add(cb.equal(root.get("status").get("code"), ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name()));
            filters.add(cb.equal(root.get("reason").get("code"), reason.name()));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });

        Map<Long, Application> academicLeaveRevocations;
        if (CollectionUtils.isEmpty(previousSameTypeAcademicLeaves)) {
            academicLeaveRevocations = Collections.emptyMap();
        } else {
            // FIXME this is probably wrong source - date can be different in directive
            academicLeaveRevocations = StreamUtil.toMap(Application::getId, applicationRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                filters.add(cb.equal(root.get("student").get("id"), studentId));
                filters.add(cb.equal(root.get("type").get("code"), ApplicationType.AVALDUS_LIIK_AKADK.name()));
                filters.add(cb.equal(root.get("status").get("code"), ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name()));
                filters.add(root.get("academicApplication").get("id")
                        .in(StreamUtil.toMappedList(Application::getId, previousSameTypeAcademicLeaves)));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            }));
        }

        long daysUsed = 0;
        for (Application academicLeaveApplication : previousSameTypeAcademicLeaves) {
            LocalDate start = getStartDate(academicLeaveApplication);
            LocalDate end = getAcademicLeaveActualEnd(academicLeaveRevocations, academicLeaveApplication);
            daysUsed += ChronoUnit.DAYS.between(start, end);
        }
        return daysUsed;
    }

    private static LocalDate getAcademicLeaveActualEnd(Map<Long, Application> academicLeaveRevocations, Application academicLeaveApplication) {
        LocalDate end;
        Application revocation = academicLeaveRevocations.get(academicLeaveApplication.getId());
        if (revocation != null) {
            //if academic leave is cancelled by revocation application then its date is end date
            //TODO: consider using directive date
            end = getEndDate(revocation);
        } else {
            end = getEndDate(academicLeaveApplication);
        }
        return end;
    }

    public static void assertAkadConstraints(Application application, ApplicationRepository applicationRepository) {
        assertStartAfterToday(application);

        String reason = EntityUtil.getCode(application.getReason());
        if (AcademicLeaveReason.AKADPUHKUS_POHJUS_T.name().equals(reason)) {
          long daysUsed = daysUsed(EntityUtil.getId(application.getStudent()), AcademicLeaveReason.AKADPUHKUS_POHJUS_T, applicationRepository);
          assertPeriod(application, 2, daysUsed);
        } else if (AcademicLeaveReason.AKADPUHKUS_POHJUS_A.name().equals(reason)) {
            long daysUsed = daysUsed(EntityUtil.getId(application.getStudent()), AcademicLeaveReason.AKADPUHKUS_POHJUS_A, applicationRepository);
            assertPeriod(application, 1, daysUsed);
        } else if (AcademicLeaveReason.AKADPUHKUS_POHJUS_L.name().equals(reason)) {
            assertPeriod(application, 3, 0);
        } else if (AcademicLeaveReason.AKADPUHKUS_POHJUS_O.name().equals(reason)) {
            // TODO: algusega mitte varem kui esimese õppeaasta teisest semestrist
            if (!CurriculumUtil.isHigher(application.getStudent().getCurriculumVersion().getCurriculum())) {
                throw new ValidationFailedException("application.messages.studentIsNotHigher");
            }

            long daysUsed = daysUsed(EntityUtil.getId(application.getStudent()), AcademicLeaveReason.AKADPUHKUS_POHJUS_O, applicationRepository);
            assertPeriod(application, 1, daysUsed);
        }
    }

    public static void assertValisConstraints(Application application) {
        assertStartAfterToday(application);
    }

    public static void assertAkadkConstraints(Application application) {

        if (!ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_AKAD, application.getAcademicApplication().getType())) {
            throw new ValidationFailedException("application.messages.wrongAcademicApplicationType");
        }

        LocalDate academicLeaveStart = getStartDate(application.getAcademicApplication());
        LocalDate revocationStart = getStartDate(application);

        if (revocationStart == null) {
            throw new ValidationFailedException("application.messages.revocationStart.isNull");
        } else if (revocationStart.isBefore(academicLeaveStart)) {
            throw new ValidationFailedException("application.messages.revocationStartDateBeforeAcademicLeaveStartDate");
        }

        LocalDate academicLeaveEnd = getEndDate(application.getAcademicApplication());

        if (revocationStart.isAfter(academicLeaveEnd)) {
            throw new ValidationFailedException("application.messages.revocationStartDateAfterAcademicLeaveEndDate");
        }
    }
}
