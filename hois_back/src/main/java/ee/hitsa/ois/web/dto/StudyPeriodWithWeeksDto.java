package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudyPeriod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudyPeriodWithWeeksDto {

    private final Long id;
    private final String nameEt;
    private final String nameEn;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<Short> weekNrs;
    private List<LocalDate> weekBeginningDates;
    // list of nrs that are added to weekNrs list so that study period weeks can go from 1 to last period's week
    private List<Short> externalWeeks = new ArrayList<>();

    public StudyPeriodWithWeeksDto(StudyPeriod studyPeriod) {
        id = studyPeriod.getId();
        nameEt = studyPeriod.getNameEt();
        nameEn = studyPeriod.getNameEn();
        weekNrs = studyPeriod.getWeekNrs();
        weekBeginningDates = studyPeriod.getWeekBeginningDates();
        startDate = studyPeriod.getStartDate();
        endDate = studyPeriod.getEndDate();
    }

    public Long getId() {
        return id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Short> getWeekNrs() {
        return weekNrs;
    }

    public void setWeekNrs(List<Short> weekNrs) {
        this.weekNrs = weekNrs;
    }

    public List<LocalDate> getWeekBeginningDates() {
        return weekBeginningDates;
    }

    public void setWeekBeginningDates(List<LocalDate> weekBeginningDates) {
        this.weekBeginningDates = weekBeginningDates;
    }

    public List<Short> getExternalWeeks() {
        return externalWeeks;
    }

    public void setExternalWeeks(List<Short> externalWeeks) {
        this.externalWeeks = externalWeeks;
    }
}