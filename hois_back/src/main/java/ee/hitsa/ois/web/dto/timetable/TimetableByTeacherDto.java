package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByTeacherDto extends TimetableByDto {
    private final Long teacherId;
    private final String firstname;
    private final String lastname;
    private String personalUrl;

    public TimetableByTeacherDto(String studyPeriods, List<TimetableEventSearchDto> timetableEvents, Long teacherId,
            String firstname, String lastname) {
        super(studyPeriods, timetableEvents);
        this.teacherId = teacherId;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPersonalUrl() {
        return personalUrl;
    }

    public void setPersonalUrl(String personalUrl) {
        this.personalUrl = personalUrl;
    }

}
