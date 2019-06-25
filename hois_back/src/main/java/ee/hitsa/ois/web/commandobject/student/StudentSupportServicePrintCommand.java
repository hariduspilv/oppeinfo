package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from="from", thru="thru")
public class StudentSupportServicePrintCommand {
    
    private LocalDate from;
    private LocalDate thru;

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
}
