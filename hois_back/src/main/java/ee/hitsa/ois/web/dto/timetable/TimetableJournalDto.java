package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

public class TimetableJournalDto {
    private Long id;
    private String name;
    private Long room;
    private List<String> teacherNames = new ArrayList<>();

    public TimetableJournalDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Long getRoom() {
        return room;
    }

    public void setRoom(Long room) {
        this.room = room;
    }

    public List<String> getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(List<String> teacherNames) {
        this.teacherNames = teacherNames;
    }

}
