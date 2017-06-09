package ee.hitsa.ois.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ee.hitsa.ois.util.Translatable;

@Entity
public class StudyPeriod extends BaseEntityWithId implements Translatable {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyYear studyYear;
    private String nameEt;
    private String nameEn;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;
    private LocalDate startDate;
    private LocalDate endDate;

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Transient
    public List<Integer> getWeekNrs() {
        LocalDate yearStart = studyYear.getStartDate();
        if (yearStart.getDayOfWeek() != DayOfWeek.MONDAY) {
            yearStart = yearStart.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        }
        LocalDate spStart = startDate;
        if (startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            spStart = startDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        }

        int weekNr = 1;
        while (!spStart.isEqual(yearStart)) {
            yearStart = yearStart.plusDays(7);
            weekNr++;
        }

        Set<StudyPeriod> periods = studyYear.getStudyPeriods();
        for (StudyPeriod period : periods) {
            if (period.getStartDate().isBefore(spStart) && period.getEndDate().isAfter(spStart) && period != this) {
                spStart = spStart.plusDays(7);
                weekNr++;
            }
        }

        List<Integer> weekNrs = new ArrayList<>();
        while (endDate.isAfter(spStart) || endDate.isEqual(spStart)) {
            weekNrs.add(Integer.valueOf(weekNr++));
            spStart = spStart.plusDays(7);
        }
        return weekNrs;
    }
}
