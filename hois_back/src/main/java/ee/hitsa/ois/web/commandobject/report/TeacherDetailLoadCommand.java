package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "from", thru = "thru")
public class TeacherDetailLoadCommand {

    @NotNull
    private Long studyYear;
    private LocalDate studyYearStart;
    private LocalDate studyYearEnd;
    private Long studyPeriod;
    private LocalDate studyPeriodStart;
    private LocalDate studyPeriodEnd;
    private Long teacher;
    private LocalDate from;
    private LocalDate thru;

    private Boolean isHigher;
    private Boolean byStudyPeriods;
    private Boolean byWeeks;
    private Boolean byMonths;
    private Boolean byCapacities;
    private Boolean showPlannedLessons;
    private Boolean showSingleEvents;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public LocalDate getStudyYearStart() {
        return studyYearStart;
    }

    public void setStudyYearStart(LocalDate studyYearStart) {
        this.studyYearStart = studyYearStart;
    }

    public LocalDate getStudyYearEnd() {
        return studyYearEnd;
    }

    public void setStudyYearEnd(LocalDate studyYearEnd) {
        this.studyYearEnd = studyYearEnd;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public LocalDate getStudyPeriodStart() {
        return studyPeriodStart;
    }

    public void setStudyPeriodStart(LocalDate studyPeriodStart) {
        this.studyPeriodStart = studyPeriodStart;
    }

    public LocalDate getStudyPeriodEnd() {
        return studyPeriodEnd;
    }

    public void setStudyPeriodEnd(LocalDate studyPeriodEnd) {
        this.studyPeriodEnd = studyPeriodEnd;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public Boolean getByStudyPeriods() {
        return byStudyPeriods;
    }

    public void setByStudyPeriods(Boolean byStudyPeriods) {
        this.byStudyPeriods = byStudyPeriods;
    }

    public Boolean getByWeeks() {
        return byWeeks;
    }

    public void setByWeeks(Boolean byWeeks) {
        this.byWeeks = byWeeks;
    }

    public Boolean getByMonths() {
        return byMonths;
    }

    public void setByMonths(Boolean byMonths) {
        this.byMonths = byMonths;
    }

    public Boolean getByCapacities() {
        return byCapacities;
    }

    public void setByCapacities(Boolean byCapacities) {
        this.byCapacities = byCapacities;
    }

    public Boolean getShowPlannedLessons() {
        return showPlannedLessons;
    }

    public void setShowPlannedLessons(Boolean showPlannedLessons) {
        this.showPlannedLessons = showPlannedLessons;
    }

    public Boolean getShowSingleEvents() {
        return showSingleEvents;
    }

    public void setShowSingleEvents(Boolean showSingleEvents) {
        this.showSingleEvents = showSingleEvents;
    }

}
