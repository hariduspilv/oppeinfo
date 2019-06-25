package ee.hitsa.ois.web.commandobject.poll;

import java.time.LocalDate;

public class PollDatesCommand {
    
    private LocalDate validFrom;
    private LocalDate validThru;
    private LocalDate reminderDt;
    
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
    public LocalDate getReminderDt() {
        return reminderDt;
    }
    public void setReminderDt(LocalDate reminderDt) {
        this.reminderDt = reminderDt;
    }
    
}
