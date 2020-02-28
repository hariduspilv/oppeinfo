package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TimetablePlanExcelEventDto {

    private LocalDateTime start;
    private LocalDateTime end;
    private AutocompleteResult name;
    private String studentGroups;
    private String subgroups;
    private String rooms;
    private String teachers;
    private String capacityType;

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

    public AutocompleteResult getName() {
        return name;
    }

    public void setName(AutocompleteResult name) {
        this.name = name;
    }

    public String getStudentGroups() {
        return studentGroups != null ? studentGroups : "";
    }

    public void setStudentGroups(String studentGroups) {
        this.studentGroups = studentGroups;
    }

    public String getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(String subgroups) {
        this.subgroups = subgroups;
    }

    public String getRooms() {
        return rooms != null ? rooms : "";
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getTeachers() {
        return teachers != null ? teachers : "";
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

}
