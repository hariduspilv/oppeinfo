package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

public class TimetableStudyYearWeekDto {

    private Long studyYear;
    private Long weekNr;
    private LocalDate start;
    private LocalDate end;
    private Boolean connectedSubjects;

    public TimetableStudyYearWeekDto(Long studyYear, Long weekNr, LocalDate start, LocalDate end) {
        super();
        this.studyYear = studyYear;
        this.weekNr = weekNr;
        this.start = start;
        this.end = end;
    }

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getWeekNr() {
        return weekNr;
    }

    public void setWeekNr(Long weekNr) {
        this.weekNr = weekNr;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Boolean getConnectedSubjects() {
        return connectedSubjects;
    }

    public void setConnectedSubjects(Boolean connectedSubjects) {
        this.connectedSubjects = connectedSubjects;
    }

}
