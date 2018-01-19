package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.datatype.XMLGregorianCalendar;

public abstract class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter SHORT_YEAR_FORMATTER = DateTimeFormatter.ofPattern("YY");

    public static String nullableDate(LocalDate date) {
        return date != null ? date(date) : null;
    }

    public static String date(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "-";
    }

    public static String dateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : "-";
    }

    public static String shortYear(LocalDate date) {
        return SHORT_YEAR_FORMATTER.format(date);
    }

    public static LocalDateTime parseDateTime(String value) {
        return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
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

    public static LocalDate toLocalDate(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }
}
