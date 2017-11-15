package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByStudentDto extends TimetableByDto{
    private final Long studentId;
    private final String firstname;
    private final String lastname;
    
    public TimetableByStudentDto(GeneralTimetableDto generalTimetable, List<TimetableEventSearchDto> timetableEvents,
            Long studentId, String firstname, String lastname) {
        super(generalTimetable, timetableEvents);
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    public Long getStudentId() {
        return studentId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
