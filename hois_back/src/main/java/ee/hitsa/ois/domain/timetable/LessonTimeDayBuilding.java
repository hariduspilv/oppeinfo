package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class LessonTimeDayBuilding extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Building building;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier day;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private LessonTime lessonTime;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Classifier getDay() {
        return day;
    }

    public void setDay(Classifier day) {
        this.day = day;
    }

    public LessonTime getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(LessonTime lessonTime) {
        this.lessonTime = lessonTime;
    }
}
