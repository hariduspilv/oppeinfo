package ee.hitsa.ois.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.validation.ValidationFailedException;

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
    
    @OneToMany(mappedBy="studyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyPeriodEvent> events = new ArrayList<>();

    @Transient
    private LocalDate spStart;

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
    
    public List<StudyPeriodEvent> getEvents() {
        return events;
    }

    public void setEvents(List<StudyPeriodEvent> events) {
        this.events = events;
    }

    private short firstWeekNr() {
        LocalDate yearStart = studyYear.getStartDate();
        if (yearStart.getDayOfWeek() != DayOfWeek.MONDAY) {
            yearStart = yearStart.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        }
        spStart = startDate;
        if (startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            spStart = startDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        }
        
        if (spStart.isBefore(yearStart)) {
            throw new ValidationFailedException("Study period's start date cannot be before study year's start date");
        }

        //find the first week in the current study period
        short weekNr = 1;
        while (!spStart.isEqual(yearStart)) {
            yearStart = yearStart.plusDays(7);
            weekNr++;
        }

        //search for another study period in the first week of this study period
        //if such a study period exists, add a week
        Set<StudyPeriod> periods = studyYear.getStudyPeriods();
        for (StudyPeriod period : periods) {
            if (period.getStartDate().isBefore(spStart) && period.getEndDate().isAfter(spStart) && period != this) {
                spStart = spStart.plusDays(7);
                weekNr++;
            }
        }
        return weekNr;
    }

    /**
     * @return
     * returns all the week nrs in this study period
     */
    @Transient
    public LinkedList<Short> getWeekNrs() {
        short weekNr = firstWeekNr();
        //get all the week nrs from the first until the end
        LinkedList<Short> weekNrs = new LinkedList<>();
        while (endDate.isAfter(spStart) || endDate.isEqual(spStart)) {
            weekNrs.add(Short.valueOf(weekNr++));
            spStart = spStart.plusDays(7);
        }
        return weekNrs;
    }
    
    /**
     * @return
     * returns all the week beginning dates in this study period
     */
    @Transient
    public List<LocalDate> getWeekBeginningDates() {
        firstWeekNr();
        List<LocalDate> weekBeginnings = new ArrayList<>();
        while (endDate.isAfter(spStart) || endDate.isEqual(spStart)) {
            weekBeginnings.add(spStart);
            spStart = spStart.plusDays(7);
        }
        return weekBeginnings;
    }

    public Integer getWeekNrForDate(LocalDate date) {
        if((date.isAfter(startDate) || date.isEqual(startDate)) && (date.isBefore(endDate) || endDate.isEqual(date))) {
            int weekNr = firstWeekNr();
            while ((spStart.isBefore(date) || spStart.isEqual(date)) && (endDate.isAfter(date) || endDate.isEqual(date))) {
                if(spStart.get(ChronoField.ALIGNED_WEEK_OF_YEAR) == date.get(ChronoField.ALIGNED_WEEK_OF_YEAR)) {
                    break;
                }
                spStart = spStart.plusDays(7);
                weekNr++;
            }
            return Integer.valueOf(weekNr);
        }
        return null;
    }
}
