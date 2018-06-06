package ee.hitsa.ois.util;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class ApplicationUtil {

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

    public static DirectiveStudent getDirectiveStudent(Application application) {
        return StreamUtil.nullSafeList(application.getDirectiveStudents()).stream()
                .filter(r -> !Boolean.TRUE.equals(r.getCanceled()))
                .max(Comparator.comparingLong(DirectiveStudent::getId)).orElse(null);
    }
}
