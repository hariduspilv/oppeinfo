package ee.hitsa.ois.domain.timetable;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class TimetableEventTime extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private TimetableEvent timetableEvent;

    private LocalDateTime start;
    private LocalDateTime end;
    private String otherTeacher;
    private String otherRoom;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_event_time_id", nullable = false, updatable = false)
    private Set<TimetableEventTeacher> timetableEventTeachers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_event_time_id", nullable = false, updatable = false)
    private Set<TimetableEventRoom> timetableEventRooms = new HashSet<>();

    public TimetableEvent getTimetableEvent() {
        return timetableEvent;
    }

    public void setTimetableEvent(TimetableEvent timetableEvent) {
        this.timetableEvent = timetableEvent;
    }

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

    public String getOtherTeacher() {
        return otherTeacher;
    }

    public void setOtherTeacher(String otherTeacher) {
        this.otherTeacher = otherTeacher;
    }

    public String getOtherRoom() {
        return otherRoom;
    }

    public void setOtherRoom(String otherRoom) {
        this.otherRoom = otherRoom;
    }

    public Set<TimetableEventTeacher> getTimetableEventTeachers() {
        return timetableEventTeachers;
    }

    public void setTimetableEventTeachers(Set<TimetableEventTeacher> timetableEventTeachers) {
        this.timetableEventTeachers = timetableEventTeachers;
    }

    public Set<TimetableEventRoom> getTimetableEventRooms() {
        return timetableEventRooms;
    }

    public void setTimetableEventRooms(Set<TimetableEventRoom> timetableEventRooms) {
        this.timetableEventRooms = timetableEventRooms;
    }
    
    
}
