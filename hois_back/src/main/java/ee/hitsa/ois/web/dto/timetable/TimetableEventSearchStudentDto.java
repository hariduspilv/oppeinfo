package ee.hitsa.ois.web.dto.timetable;

public class TimetableEventSearchStudentDto {
    private final Long id;
    private final String name;
    private final String studentGroup;

    public TimetableEventSearchStudentDto(Long id, String name, String studentGroup) {
        this.id = id;
        this.name = name;
        this.studentGroup = studentGroup;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStudentGroup() {
        return studentGroup;
    }
}
