package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;

public class StudentMovementCommand {
    
    private LocalDate from;
    private LocalDate thru;
    private String queryType;
    
    public LocalDate getFrom() {
        return from;
    }
    public void setFrom(LocalDate from) {
        this.from = from;
    }
    public LocalDate getThru() {
        return thru;
    }
    public void setThru(LocalDate thru) {
        this.thru = thru;
    }
    public String getQueryType() {
        return queryType;
    }
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

}
