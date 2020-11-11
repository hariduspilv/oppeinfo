package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.web.dto.RoomAutocompleteResult;

import java.util.ArrayList;
import java.util.List;

public class TimetableJournalDto {
    private Long id;
    private String name;
    private Boolean isIndividual;
    private List<RoomAutocompleteResult> rooms = new ArrayList<>();
    private List<TimetableJournalTeacherDto> teachers = new ArrayList<>();
    private List<TimetableJournalStudentDto> students = new ArrayList<>();

    public TimetableJournalDto(Long id, String name, Boolean isIndividual) {
        this.id = id;
        this.name = name;
        this.isIndividual = isIndividual;
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

    public Boolean getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(Boolean isIndividual) {
        this.isIndividual = isIndividual;
    }

    public List<RoomAutocompleteResult> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomAutocompleteResult> rooms) {
        this.rooms = rooms;
    }

    public List<TimetableJournalTeacherDto> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TimetableJournalTeacherDto> teachers) {
        this.teachers = teachers;
    }

    public List<TimetableJournalStudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<TimetableJournalStudentDto> students) {
        this.students = students;
    }
}
