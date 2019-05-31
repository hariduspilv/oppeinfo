package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class WeekDto {

    private Short nr;
    private LocalDate weekStart;
    private LocalDate weekEnd;

    public WeekDto(Short nr, LocalDate weekStart, LocalDate weekEnd) {
        this.nr = nr;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
    }

    public Short getNr() {
        return nr;
    }

    public void setNr(Short nr) {
        this.nr = nr;
    }

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public LocalDate getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(LocalDate weekEnd) {
        this.weekEnd = weekEnd;
    }

}
