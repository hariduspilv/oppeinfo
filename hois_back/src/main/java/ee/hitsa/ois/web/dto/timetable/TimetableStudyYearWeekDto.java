package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

public class TimetableStudyYearWeekDto {
    
    private Long weekNr;
    private LocalDate start;
    private LocalDate end;
    
    public TimetableStudyYearWeekDto(Long weekNr, LocalDate start, LocalDate end) {
        super();
        this.weekNr = weekNr;
        this.start = start;
        this.end = end;
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
    
}
