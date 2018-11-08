package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class StudyWeekDto {

    private Long nr;
    private LocalDate start;
    private LocalDate end;

    public Long getNr() {
        return nr;
    }

    public void setNr(Long nr) {
        this.nr = nr;
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
