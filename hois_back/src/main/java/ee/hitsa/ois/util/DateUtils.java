package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtils {

    public static LocalDateTime lastMomentOfDay(LocalDate date) {
        LocalDateTime lastMomentOfDay = LocalDateTime.of(date, LocalTime.MAX);
        return lastMomentOfDay;
    }

    public static LocalDateTime lastMomentOfDay(LocalDateTime dateTime) {
        return lastMomentOfDay(dateTime.toLocalDate());
    }

    public static LocalDateTime firstMomentOfDay(LocalDate date) {
        LocalDateTime firstMomentOfDay = LocalDateTime.of(date, LocalTime.MIN);
        return firstMomentOfDay;
    }

    public static LocalDateTime firstMomentOfDay(LocalDateTime dateTime) {
        return firstMomentOfDay(dateTime.toLocalDate());
    }

    public static LocalDate periodStart(Period p) {
        return Boolean.TRUE.equals(p.getIsPeriod()) ? p.getStudyPeriodStart().getStartDate() : p.getStartDate();
    }

    public static LocalDate periodEnd(Period p) {
        return Boolean.TRUE.equals(p.getIsPeriod()) ? p.getStudyPeriodEnd().getEndDate() : p.getEndDate();
    }
}
