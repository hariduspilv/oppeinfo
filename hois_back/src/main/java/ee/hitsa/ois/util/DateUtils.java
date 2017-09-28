package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    
    public static String nullableDate(LocalDate date) {
        return date != null ? date(date) : null;
    }
    
    public static String date(LocalDate date) {
        return date != null ? date.format(dateFormatter) : "-";
    }

    public static String dateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(dateTimeFormatter) : "-";
    }

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
