package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

public class TimetableStudentStudyYearWeekDto extends TimetableStudyYearWeekDto {

    private Boolean connectedSubjects = Boolean.FALSE;
    
    public TimetableStudentStudyYearWeekDto(Long studyYear, Long weekNr, LocalDate start, LocalDate end) {
        super(studyYear, weekNr, start, end);
    }

    public Boolean getConnectedSubjects() {
        return connectedSubjects;
    }

    public void setConnectedSubjects(Boolean connectedSubjects) {
        this.connectedSubjects = connectedSubjects;
    }

}
