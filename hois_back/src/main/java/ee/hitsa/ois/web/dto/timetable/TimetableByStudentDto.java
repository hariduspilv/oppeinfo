package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByStudentDto {
    private final Long studentId;
    private final String firstname;
    private final String lastname;
    private final GeneralTimetableDto generalTimetable;
    private final List<TimetableEventSearchDto> timetableEvents;
    
    public TimetableByStudentDto(Long studentId, String firstname, String lastname, GeneralTimetableDto generalTimetable,
            List<TimetableEventSearchDto> timetableEvents) {
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.generalTimetable = generalTimetable;
        this.timetableEvents = timetableEvents;
    }
    
    public GeneralTimetableDto getGeneralTimetable() {
        return generalTimetable;
    }
    
    public List<TimetableEventSearchDto> getTimetableEvents() {
        return timetableEvents;
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
