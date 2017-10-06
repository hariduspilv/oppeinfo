package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByGroupDto {
    private final GeneralTimetableDto generalTimetable;
    private final GeneralTimetableCurriculumDto generalTimetableCurriculum;
    private final List<TimetableEventSearchDto> timetableEvents;
    
    public TimetableByGroupDto(GeneralTimetableCurriculumDto generalTimetableCurriculum, GeneralTimetableDto generalTimetable,
            List<TimetableEventSearchDto> timetableEvents) {
        this.generalTimetable = generalTimetable;
        this.generalTimetableCurriculum = generalTimetableCurriculum;
        this.timetableEvents = timetableEvents;
    }
    
    public GeneralTimetableDto getGeneralTimetable() {
        return generalTimetable;
    }
    
    public GeneralTimetableCurriculumDto getGeneralTimetableCurriculum() {
        return generalTimetableCurriculum;
    }
    
    public List<TimetableEventSearchDto> getTimetableEvents() {
        return timetableEvents;
    }
}
