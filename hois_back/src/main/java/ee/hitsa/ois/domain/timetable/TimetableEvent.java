package ee.hitsa.ois.domain.timetable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class TimetableEvent extends BaseEntityWithId {

    private LocalDateTime start;
    private LocalDateTime end;
    private Long lessons;
    private Boolean considerBreak;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
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

    public Long getLessons() {
        return lessons;
    }

    public void setLessons(Long lessons) {
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
