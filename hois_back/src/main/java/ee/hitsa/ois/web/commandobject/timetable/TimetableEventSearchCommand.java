package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDateTime;
import java.util.List;

public class TimetableEventSearchCommand {

    private String name;
    private Boolean singleEvent;
    private List<Long> studentGroups;
    private Long studyPeriod;
    private List<Long> teachers;
    private Long room;
    private LocalDateTime from;
    private LocalDateTime thru;
    private String otherTeacher;
    private String otherRoom;
    private Long timetable;
    private Long student;
    private Boolean higher;
    private Boolean vocational;

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

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getThru() {
        return thru;
    }

    public void setThru(LocalDateTime thru) {
        this.thru = thru;
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

    public Long getTimetable() {
        return timetable;
    }

    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }
    
    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getVocational() {
        return vocational;
    }

    public void setVocational(Boolean vocational) {
        this.vocational = vocational;
    }
    
}
