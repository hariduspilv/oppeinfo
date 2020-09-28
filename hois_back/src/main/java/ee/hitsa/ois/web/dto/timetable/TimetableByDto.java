package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByDto {
    private final String studyPeriods;
    private final List<TimetableEventSearchDto> timetableEvents;
    private final Boolean isHigher;
    private Long schoolId;
    private String personalParam;

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

    public Boolean getHigher() {
        return isHigher;
    }

    public String getPersonalParam() {
        return personalParam;
    }

    public void setPersonalParam(String personalParam) {
        this.personalParam = personalParam;
    }
}
