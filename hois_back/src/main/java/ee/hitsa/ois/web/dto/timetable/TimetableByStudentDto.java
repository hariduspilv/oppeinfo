package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByStudentDto extends TimetableByDto{
    private final Long studentId;
    private final String firstname;
    private final String lastname;
    private final Boolean isHigher;
    private String personalUrl;

    public TimetableByStudentDto(String studyPeriods, List<TimetableEventSearchDto> timetableEvents, Long studentId,
            String firstname, String lastname, Boolean isHigher) {
        super(studyPeriods, timetableEvents);
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isHigher = isHigher;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public String getPersonalUrl() {
        return personalUrl;
    }

    public void setPersonalUrl(String personalUrl) {
        this.personalUrl = personalUrl;
    }

}
