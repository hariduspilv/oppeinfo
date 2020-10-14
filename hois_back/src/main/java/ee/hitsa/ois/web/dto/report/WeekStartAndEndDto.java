package ee.hitsa.ois.web.dto.report;

import java.time.LocalDate;

public class WeekStartAndEndDto {
    
    private Short weekNr;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean vacation;
    
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Short getWeekNr() {
        return weekNr;
    }
    public void setWeekNr(Short weekNr) {
        this.weekNr = weekNr;
    }
    public Boolean getVacation() {
        return vacation;
    }
    public void setVacation(Boolean vacation) {
        this.vacation = vacation;
    }

}
