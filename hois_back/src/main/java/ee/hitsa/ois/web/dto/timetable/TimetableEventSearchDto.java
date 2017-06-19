package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TimetableEventSearchDto {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private List<String> teachers;
    private List<String> rooms;
    private String studentGroup;
    private Boolean singleEvent;

    public TimetableEventSearchDto(Long id, String name, LocalDate date, LocalTime time, String studentGroup,
            Boolean singleEvent) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.studentGroup = studentGroup;
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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Boolean getSingleEvent() {
        return singleEvent;
    }

    public void setSingleEvent(Boolean singleEvent) {
        this.singleEvent = singleEvent;
    }

}
