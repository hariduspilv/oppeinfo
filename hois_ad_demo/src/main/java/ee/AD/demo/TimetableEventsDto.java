package ee.AD.demo;

import java.util.List;

public class TimetableEventsDto {
    
    private List<TimetableEventDto> timetableEvents;

    public List<TimetableEventDto> getTimetableEvents() {
        return timetableEvents;
    }

    public void setTimetableEvents(List<TimetableEventDto> timetableEvents) {
        this.timetableEvents = timetableEvents;
    }

}
