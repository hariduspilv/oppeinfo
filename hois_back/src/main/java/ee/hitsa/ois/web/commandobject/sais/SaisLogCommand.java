package ee.hitsa.ois.web.commandobject.sais;

import java.time.LocalDate;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "from", thru = "thru")
public class SaisLogCommand {

    private String messageType;
    private LocalDate from;
    private LocalDate thru;
    private Boolean errors;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

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

    public Boolean getErrors() {
        return errors;
    }

    public void setErrors(Boolean errors) {
        this.errors = errors;
    }
}
