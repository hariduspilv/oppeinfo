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

@Entity
public class TimetableEventTime extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    private TimetableEvent timetableEvent;
 
    private LocalDateTime start;
    //FIXME: rename in database
    @Column(name = "\"end\"")
    private LocalDateTime end;
    private String otherTeacher;
    private String otherRoom;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_event_time_id", nullable = false, updatable = false)
    private List<TimetableEventTeacher> timetableEventTeachers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_event_time_id", nullable = false, updatable = false)
    private List<TimetableEventStudent> timetableEventStudents = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_event_time_id", nullable = false, updatable = false)
    private List<TimetableEventRoom> timetableEventRooms = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_event_time_id", nullable = false, updatable = false)
    private List<TimetableEventStudentGroup> timetableEventStudentGroups = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_event_time_id", nullable = false, updatable = false)
    private List<TimetableEventSubgroup> timetableEventSubgroups = new ArrayList<>();

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

    public List<TimetableEventTeacher> getTimetableEventTeachers() {
        return timetableEventTeachers;
    }

    public void setTimetableEventTeachers(List<TimetableEventTeacher> timetableEventTeachers) {
        this.timetableEventTeachers = timetableEventTeachers;
    }

    public List<TimetableEventStudent> getTimetableEventStudents() {
        return timetableEventStudents;
    }

    public void setTimetableEventStudents(List<TimetableEventStudent> timetableEventStudents) {
        this.timetableEventStudents = timetableEventStudents;
    }

    public List<TimetableEventRoom> getTimetableEventRooms() {
        return timetableEventRooms;
    }

    public void setTimetableEventRooms(List<TimetableEventRoom> timetableEventRooms) {
        this.timetableEventRooms = timetableEventRooms;
    }

    public List<TimetableEventStudentGroup> getTimetableEventStudentGroups() {
        return timetableEventStudentGroups;
    }

    public void setTimetableEventStudentGroups(List<TimetableEventStudentGroup> timetableEventStudentGroups) {
        this.timetableEventStudentGroups = timetableEventStudentGroups;
    }

    public List<TimetableEventSubgroup> getTimetableEventSubgroups() {
        return timetableEventSubgroups;
    }

    public void setTimetableEventSubgroups(List<TimetableEventSubgroup> timetableEventSubgroups) {
        this.timetableEventSubgroups = timetableEventSubgroups;
    }
}
