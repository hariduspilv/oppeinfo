package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByTeacherDto {
    private final Long teacherId;
    private final String firstname;
    private final String lastname;
    private final GeneralTimetableDto generalTimetable;
    private final List<TimetableEventSearchDto> timetableEvents;
    
    public TimetableByTeacherDto(Long teacherId, String firstname, String lastname, GeneralTimetableDto generalTimetable,
            List<TimetableEventSearchDto> timetableEvents) {
        this.teacherId = teacherId;
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
