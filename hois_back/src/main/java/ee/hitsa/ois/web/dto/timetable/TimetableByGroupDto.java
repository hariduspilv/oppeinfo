package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByGroupDto extends TimetableByDto{
    private final GeneralTimetableCurriculumDto generalTimetableCurriculum;
    
    public TimetableByGroupDto(String studyPeriods, List<TimetableEventSearchDto> timetableEvents,
            GeneralTimetableCurriculumDto generalTimetableCurriculum) {
        super(studyPeriods, timetableEvents);
        this.generalTimetableCurriculum = generalTimetableCurriculum;
    }
    
    public GeneralTimetableCurriculumDto getGeneralTimetableCurriculum() {
        return generalTimetableCurriculum;
    }
    
}
