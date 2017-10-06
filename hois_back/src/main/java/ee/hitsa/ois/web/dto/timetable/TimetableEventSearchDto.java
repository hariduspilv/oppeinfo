package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TimetableEventSearchDto {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private List<TimetableEventSearchTeacherDto> teachers;
    private List<TimetableEventSearchRoomDto> rooms;
    private List<TimetableEventSearchGroupDto> studentGroups;
    private Boolean singleEvent;

    public TimetableEventSearchDto(Long id, String name, LocalDate date, LocalTime timeStart, LocalTime timeEnd, Boolean singleEvent) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.singleEvent = singleEvent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }
    
    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public List<TimetableEventSearchTeacherDto> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TimetableEventSearchTeacherDto> teachers) {
        this.teachers = teachers;
    }

    public List<TimetableEventSearchRoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<TimetableEventSearchRoomDto> rooms) {
        this.rooms = rooms;
    }

    public List<TimetableEventSearchGroupDto> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<TimetableEventSearchGroupDto> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Boolean getSingleEvent() {
        return singleEvent;
    }

    public void setSingleEvent(Boolean singleEvent) {
        this.singleEvent = singleEvent;
    }

}
