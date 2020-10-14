package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.enums.ScholarshipType;

public abstract class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter SHORT_YEAR_FORMATTER = DateTimeFormatter.ofPattern("YY");
    private static final DateTimeFormatter SHORT_DAY_MONTH_FORMATTER = DateTimeFormatter.ofPattern("dd.MM");
    private static final DateTimeFormatter ISO8601_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public static String nullableDate(LocalDate date) {
        return date != null ? date(date) : null;
    }

    public static String date(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "-";
    }

    public static String dateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : "-";
    }
    
    public static String shortDayMonth(LocalDate date) {
        return SHORT_DAY_MONTH_FORMATTER.format(date);
    }

    public static String shortYear(LocalDate date) {
        return SHORT_YEAR_FORMATTER.format(date);
    }

    public static LocalDateTime parseDateTime(String value) {
        return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
    }
    
    public static LocalDate parseDate(String value) {
        return StringUtils.isNotBlank(value) && !"-".equals(value) ? LocalDate.parse(value, DATE_FORMATTER) : null;
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

    public static LocalDateTime startOfMonth(LocalDateTime dateTime) {
        return dateTime.truncatedTo(ChronoUnit.DAYS).withDayOfMonth(1);
    }

    public static LocalDate periodStart(Period p) {
        return Boolean.TRUE.equals(p.getIsPeriod()) ? (p.getStudyPeriodStart() != null ? p.getStudyPeriodStart().getStartDate() : null) : p.getStartDate();
    }

    public static LocalDate periodEnd(Period p) {
        return Boolean.TRUE.equals(p.getIsPeriod()) ? (p.getStudyPeriodEnd() != null ? p.getStudyPeriodEnd().getEndDate() : null) : p.getEndDate();
    }

    public static LocalDate startDate(ScholarshipApplication application) {
        ScholarshipTerm term = application.getScholarshipTerm();
        return ClassifierUtil.equals(ScholarshipType.STIPTOETUS_ERI, term.getType()) ? application.getScholarshipFrom() : term.getPaymentStart();
    }

    public static LocalDate endDate(ScholarshipApplication application) {
        ScholarshipTerm term = application.getScholarshipTerm();
        return ClassifierUtil.equals(ScholarshipType.STIPTOETUS_ERI, term.getType()) ? application.getScholarshipThru() : term.getPaymentEnd();
    }

    public static LocalDate toLocalDate(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }

    public static LocalDate toLocalDate(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String toISOString(LocalDateTime dateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
        return zonedDateTime.format(ISO8601_FORMATTER);
    }

    public static boolean periodsOverlap(LocalDate firstPeriodStart, LocalDate firstPeriodEnd,
            LocalDate secondPeriodStart, LocalDate secondPeriodEnd) {
        return (firstPeriodStart.isEqual(secondPeriodEnd) || firstPeriodStart.isBefore(secondPeriodEnd))
                && (firstPeriodEnd.isEqual(secondPeriodStart) || firstPeriodEnd.isAfter(secondPeriodStart));
    }

    public static boolean isValid(LocalDate from, LocalDate thru) {
        LocalDate now = LocalDate.now();
        return (from == null || !from.isAfter(now)) && (thru == null || !thru.isBefore(now));
    }
}
