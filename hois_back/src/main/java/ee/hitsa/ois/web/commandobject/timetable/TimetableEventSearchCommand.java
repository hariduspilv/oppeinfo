package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;
import java.util.List;

public class TimetableEventSearchCommand {

    private String name;
    private Boolean singleEvent;
    private List<Long> studentGroups;
    private Long studyPeriod;
    private List<Long> teachers;
    private Long room;
    private LocalDate from;
    private LocalDate thru;
    private Long otherTeacher;
    private Long otherRoom;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSingleEvent() {
        return singleEvent;
    }

    public void setSingleEvent(Boolean singleEvent) {
        this.singleEvent = singleEvent;
    }

    public List<Long> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public List<Long> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Long> teachers) {
        this.teachers = teachers;
    }

    public Long getRoom() {
        return room;
    }

    public void setRoom(Long room) {
        this.room = room;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

    public Long getOtherTeacher() {
        return otherTeacher;
    }

    public void setOtherTeacher(Long otherTeacher) {
        this.otherTeacher = otherTeacher;
    }

    public Long getOtherRoom() {
        return otherRoom;
    }

    public void setOtherRoom(Long otherRoom) {
        this.otherRoom = otherRoom;
    }

}
