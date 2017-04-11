package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.JoinType;
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

    private static final Logger log = LoggerFactory.getLogger(ApplicationUtil.class);

    private static long DAYS_IN_YEAR = 365L;

    public static void assertPeriod(Application application, int years, long daysUsed) {
        long applicationDays = ChronoUnit.DAYS.between(getStartDate(application), getEndDate(application));
        long daysToUse = DAYS_IN_YEAR * years - daysUsed;

        if (daysToUse < applicationDays) {
            log.debug(String.format("Application period is %d days, maximum time is %d years, but %d days are allready used",
                    Long.valueOf(applicationDays), Long.valueOf(years), Long.valueOf(daysUsed)));
            throw new ValidationFailedException("application.messages.periodIsTooLong");
        }
    }

    public static void assertStartAfterToday(Application application) {
        LocalDate start = getStartDate(application);
        if (start.isBefore(LocalDate.now())) {
            throw new ValidationFailedException("application.messages.startIsEarlierThanToday");
        }
    }

    // TODO replace with DateUtils.periodEnd
    public static LocalDate getEndDate(Application application) {
        if (Boolean.TRUE.equals(application.getIsPeriod())) {
            if (application.getStudyPeriodEnd() == null) {
                throw new ValidationFailedException("application.messages.endPeriodMissing");
            }
            return application.getStudyPeriodEnd().getEndDate();
        }

        if (application.getEndDate() == null) {
            throw new ValidationFailedException("application.messages.endDateMissing");
        }
        return application.getEndDate();
    }

    // TODO replace with DateUtils.periodStart
    public static LocalDate getStartDate(Application application) {
        if (Boolean.TRUE.equals(application.getIsPeriod())) {
            if (application.getStudyPeriodStart() == null) {
                throw new ValidationFailedException("application.messages.startPeriodMissing");
            }
            return application.getStudyPeriodStart().getStartDate();
        }

        if (application.getStartDate() == null) {
            throw new ValidationFailedException("application.messages.startDateMissing");
        }
        return application.getStartDate();
    }

    public static void assertOverLappingDates(Application application, ApplicationRepository applicationRepository) {
        LocalDate start = getStartDate(application);
        LocalDate end = getEndDate(application);
        long overLappingApplicationsCount = applicationRepository.count((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("student").get("id"), EntityUtil.getId(application.getStudent())));
            filters.add(cb.equal(root.get("type").get("code"), EntityUtil.getCode(application.getType())));
            filters.add(cb.equal(root.get("status").get("code"), ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name()));
            root.join("studyPeriodStart", JoinType.LEFT);
            root.join("studyPeriodEnd", JoinType.LEFT);
            filters.add(cb.or(
                cb.and(cb.equal(root.get("isPeriod"), Boolean.FALSE), cb.between(cb.literal(start), root.get("startDate"), root.get("endDate"))),
                cb.and(cb.equal(root.get("isPeriod"), Boolean.FALSE), cb.between(cb.literal(end), root.get("startDate"), root.get("endDate"))),
                cb.and(cb.equal(root.get("isPeriod"), Boolean.TRUE), cb.between(cb.literal(start), root.get("studyPeriodStart").get("startDate"), root.get("studyPeriodEnd").get("endDate"))),
                cb.and(cb.equal(root.get("isPeriod"), Boolean.TRUE), cb.between(cb.literal(end), root.get("studyPeriodStart").get("startDate"), root.get("studyPeriodEnd").get("endDate")))
            ));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });

        if (overLappingApplicationsCount > 0) {
            throw new ValidationFailedException("application.messages.validApplicationExistsWithOverlappingDates");
        }
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
                        .in(previousSameTypeAcademicLeaves.stream().map(Application::getId).collect(Collectors.toList())));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            }));
        }

        long daysUsed = 0;
        for (Application academicLeaveApplication : previousSameTypeAcademicLeaves) {
            LocalDate start = getStartDate(academicLeaveApplication);
            LocalDate end = null;
            Application revocation = academicLeaveRevocations.get(academicLeaveApplication.getId());
            if (revocation != null) {
                // FIXME maybe getStartDate?
                end = getEndDate(revocation);
            } else {
                end = getEndDate(academicLeaveApplication);
            }
            daysUsed += ChronoUnit.DAYS.between(start, end);
        }
        return daysUsed;
    }

    public static void assertAkadConstraints(Application application, ApplicationRepository applicationRepository) {
        assertStartAfterToday(application);
        assertOverLappingDates(application, applicationRepository);

        String reason = EntityUtil.getCode(application.getReason());
        if (reason.equals(AcademicLeaveReason.AKADPUHKUS_POHJUS_T.name())) {
          long daysUsed = daysUsed(EntityUtil.getId(application.getStudent()), AcademicLeaveReason.AKADPUHKUS_POHJUS_T, applicationRepository);
          assertPeriod(application, 2, daysUsed);
        } else if (reason.equals(AcademicLeaveReason.AKADPUHKUS_POHJUS_A.name())) {
            long daysUsed = daysUsed(EntityUtil.getId(application.getStudent()), AcademicLeaveReason.AKADPUHKUS_POHJUS_A, applicationRepository);
            assertPeriod(application, 1, daysUsed);
        } else if (reason.equals(AcademicLeaveReason.AKADPUHKUS_POHJUS_L.name())) {
            assertPeriod(application, 3, 0);
        }
        else if (reason.equals(AcademicLeaveReason.AKADPUHKUS_POHJUS_O.name())) {
            //TODO: algusega mitte varem kui esimese õppeaasta teisest semestrist);
            if (!StudentUtil.isHigher(application.getStudent())) {
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

        if (ApplicationType.valueOf(EntityUtil.getCode(application.getAcademicApplication().getType())) != ApplicationType.AVALDUS_LIIK_AKAD) {
            throw new ValidationFailedException("application.messages.wrongAcademicApplicationType");
        }

        LocalDate academicLeaveStart = getStartDate(application.getAcademicApplication());
        LocalDate revocationStart = getStartDate(application);

        if (revocationStart.isBefore(academicLeaveStart)) {
            throw new ValidationFailedException("application.messages.revocationStartDateBeforeAcademicLeaveStartDate");
        }

        LocalDate academicLeaveEnd = getEndDate(application.getAcademicApplication());

        if (revocationStart.isAfter(academicLeaveEnd)) {
            throw new ValidationFailedException("application.messages.revocationEndDateAfterAcademicLeaveEndDate");
        }

    }

}
