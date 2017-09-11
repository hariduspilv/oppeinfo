package ee.hitsa.ois.domain.timetable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class TimetableEvent extends BaseEntityWithId {

    private LocalDateTime start;
    // FIXME: rename in database
    @Column(name = "\"end\"")
    private LocalDateTime end;
    private Short lessons;
    private Boolean considerBreak;
    private String name;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier repeat;
    private Short lessonNr;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier capacityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private TimetableObject timetableObject;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_event_id", nullable = false, updatable = false)
    private List<TimetableEventTime> timetableEventTimes = new ArrayList<>();

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Short getLessons() {
        return lessons;
    }

    public void setLessons(Short lessons) {
        this.lessons = lessons;
    }

    public Boolean getConsiderBreak() {
        return considerBreak;
    }

    public void setConsiderBreak(Boolean considerBreak) {
        this.considerBreak = considerBreak;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Classifier getRepeatCode() {
        return repeat;
    }

    public void setRepeatCode(Classifier repeat) {
        this.repeat = repeat;
    }

    public Short getLessonNr() {
        return lessonNr;
    }

    public void setLessonNr(Short lessonNr) {
        this.lessonNr = lessonNr;
    }

    public Classifier getRepeat() {
        return repeat;
    }

    public void setRepeat(Classifier repeat) {
        this.repeat = repeat;
    }

    public Classifier getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(Classifier capacityType) {
        this.capacityType = capacityType;
    }

    public TimetableObject getTimetableObject() {
        return timetableObject;
    }

    public void setTimetableObject(TimetableObject timetableObject) {
        this.timetableObject = timetableObject;
    }

    public List<TimetableEventTime> getTimetableEventTimes() {
        return timetableEventTimes;
    }

    public void setTimetableEventTimes(List<TimetableEventTime> timetableEventTimes) {
        this.timetableEventTimes = timetableEventTimes;
    }

}
