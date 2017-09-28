package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByGroupDto {
    private final GeneralTimetableDto generalTimetable;
    private final List<TimetableEventSearchDto> timetableEvents;
    
    public TimetableByGroupDto(GeneralTimetableDto generalTimetable, List<TimetableEventSearchDto> timetableEvents) {
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
