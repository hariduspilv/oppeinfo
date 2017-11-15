package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByTeacherDto extends TimetableByDto {
    private final Long teacherId;
    private final String firstname;
    private final String lastname;
    
    public TimetableByTeacherDto(GeneralTimetableDto generalTimetable, List<TimetableEventSearchDto> timetableEvents,
            Long teacherId, String firstname, String lastname) {
        super(generalTimetable, timetableEvents);
        this.teacherId = teacherId;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    public Long getTeacherId() {
        return teacherId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    
}
