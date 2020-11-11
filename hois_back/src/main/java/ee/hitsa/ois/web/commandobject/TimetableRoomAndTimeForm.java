package ee.hitsa.ois.web.commandobject;

import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.TimeRange;
import ee.hitsa.ois.web.dto.RoomDto;

@TimeRange(from = "startTime", thru = "endTime")
public class TimetableRoomAndTimeForm {
    @NotNull
    private Long timetableEventId;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<RoomDto> rooms;
    private List<Long> teachers;
    private List<Long> students;
    private List<Long> studentGroups;
    private List<Long> subgroups;
    private Boolean changeUpcomingEvents = Boolean.FALSE;

    public Long getTimetableEventId() {
        return timetableEventId;
    }

    public void setTimetableEventId(Long timetableEventId) {
        this.timetableEventId = timetableEventId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public List<Long> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Long> teachers) {
        this.teachers = teachers;
    }

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
    }

    public List<Long> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<Long> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(List<Long> subgroups) {
        this.subgroups = subgroups;
    }

    public Boolean getChangeUpcomingEvents() {
        return changeUpcomingEvents;
    }

    public void setChangeUpcomingEvents(Boolean changeUpcomingEvents) {
        this.changeUpcomingEvents = changeUpcomingEvents;
    }

}
