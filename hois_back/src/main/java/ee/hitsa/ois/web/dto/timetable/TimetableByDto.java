package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByDto {
    private final GeneralTimetableDto generalTimetable;
    private final List<TimetableEventSearchDto> timetableEvents;
    
    public TimetableByDto(GeneralTimetableDto generalTimetable, List<TimetableEventSearchDto> timetableEvents) {
        this.generalTimetable = generalTimetable;
        this.timetableEvents = timetableEvents;
    }
    
    public GeneralTimetableDto getGeneralTimetable() {
        return generalTimetable;
    }
    public List<TimetableEventSearchDto> getTimetableEvents() {
        return timetableEvents;
    }
    
}
