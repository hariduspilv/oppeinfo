package ee.hitsa.ois.domain.timetable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.school.School;

@Entity
public class Timetable extends BaseEntityWithId {

    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @JoinColumn(nullable = false, updatable = false)
    private StudyPeriod studyPeriod;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier status;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_id", nullable = false, updatable = false)
    private Set<TimetableObject> timetableObjects = new HashSet<>();

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
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

    public Set<TimetableObject> getTimetableObjects() {
        return timetableObjects;
    }

    public void setTimetableObjects(Set<TimetableObject> timetableObjects) {
        this.timetableObjects = timetableObjects;
    }
}
