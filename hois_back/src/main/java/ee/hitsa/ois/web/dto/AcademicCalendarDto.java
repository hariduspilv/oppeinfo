package ee.hitsa.ois.web.dto;

import java.util.List;

public class AcademicCalendarDto {
    private String yearCode;
    private List<AcademicCalendarEventDto> events;
    
    public AcademicCalendarDto(String yearCode, List<AcademicCalendarEventDto> events) {
        this.yearCode = yearCode;
        this.events = events;
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
