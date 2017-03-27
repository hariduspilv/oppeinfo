package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.school.School;

@Entity
public class StudyYear extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier year;
    @OneToMany(mappedBy = "studyYear")
    private Set<StudyPeriod> studyPeriods;
    @OneToMany(mappedBy = "studyYear")
    private Set<StudyPeriodEvent> studyPeriodEvents;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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

    public Classifier getYear() {
        return year;
    }

    public void setYear(Classifier year) {
        this.year = year;
    }

    public Set<StudyPeriod> getStudyPeriods() {
        return studyPeriods == null ? (studyPeriods = new HashSet<>()) : studyPeriods;
    }

    public void setStudyPeriods(Set<StudyPeriod> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public Set<StudyPeriodEvent> getStudyPeriodEvents() {
        return studyPeriodEvents == null ? (studyPeriodEvents = new HashSet<>()): studyPeriodEvents;
    }

    public void setStudyPeriodEvents(Set<StudyPeriodEvent> studyPeriodEvents) {
        this.studyPeriodEvents = studyPeriodEvents;
    }
}
