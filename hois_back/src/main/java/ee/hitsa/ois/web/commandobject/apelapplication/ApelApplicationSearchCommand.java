package ee.hitsa.ois.web.commandobject.apelapplication;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "insertedFrom", thru = "insertedThru")
@DateRange(from = "confirmedFrom", thru = "confirmedThru")
public class ApelApplicationSearchCommand {

    private Long student;
    private List<String> status;
    private LocalDate insertedFrom;
    private LocalDate insertedThru;
    private LocalDate confirmedFrom;
    private LocalDate confirmedThru;
    
    public Long getStudent() {
        return student;
    }
    
    public void setStudent(Long student) {
        this.student = student;
    }
    
    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public LocalDate getInsertedFrom() {
        return insertedFrom;
    }
    
    public void setInsertedFrom(LocalDate insertedFrom) {
        this.insertedFrom = insertedFrom;
    }
    
    public LocalDate getInsertedThru() {
        return insertedThru;
    }
    
    public void setInsertedThru(LocalDate insertedThru) {
        this.insertedThru = insertedThru;
    }
    
    public LocalDate getConfirmedFrom() {
        return confirmedFrom;
    }
    
    public void setConfirmedFrom(LocalDate confirmedFrom) {
        this.confirmedFrom = confirmedFrom;
    }
    
    public LocalDate getConfirmedThru() {
        return confirmedThru;
    }
    
    public void setConfirmedThru(LocalDate confirmedThru) {
        this.confirmedThru = confirmedThru;
    }
    
}
