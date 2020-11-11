package ee.hitsa.ois.web.dto.timetable;

public class TimetableEventSearchStudentDto {
    private final Long id;
    private final String name;

    public TimetableEventSearchStudentDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
