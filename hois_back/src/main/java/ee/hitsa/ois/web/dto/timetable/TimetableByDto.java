package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByDto {
    private final String studyPeriods;
    private final List<TimetableEventSearchDto> timetableEvents;
    private final Boolean isHigher;
    private Long schoolId;

    public TimetableByDto(String studyPeriods, List<TimetableEventSearchDto> timetableEvents, Boolean isHigher) {
        this.studyPeriods = studyPeriods;
        this.timetableEvents = timetableEvents;
        this.isHigher = isHigher;
    }

    public String getStudyPeriods() {
        return studyPeriods;
    }

    public List<TimetableEventSearchDto> getTimetableEvents() {
        return timetableEvents;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

}
