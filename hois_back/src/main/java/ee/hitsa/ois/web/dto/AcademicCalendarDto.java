package ee.hitsa.ois.web.dto;

import java.util.List;

public class AcademicCalendarDto {
    
    private AutocompleteResult school;
    private String yearCode;
    private List<AcademicCalendarEventDto> events;
    
    public AcademicCalendarDto(AutocompleteResult school, String yearCode, List<AcademicCalendarEventDto> events) {
        this.school = school;
        this.yearCode = yearCode;
        this.events = events;
    }
    
    public AutocompleteResult getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult school) {
        this.school = school;
    }

    public String getYearCode() {
        return yearCode;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public List<AcademicCalendarEventDto> getEvents() {
        return events;
    }

    public void setEvents(List<AcademicCalendarEventDto> events) {
        this.events = events;
    }

}
