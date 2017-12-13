package ee.hitsa.ois.web.commandobject.ehis;

import java.time.LocalDate;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

@DateRange(from = "from", thru = "thru")
public class EhisLogCommand {

    private String messageType;
    private LocalDate from;
    private LocalDate thru;
    private Boolean errors;
    private EntityConnectionCommand teacher;

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

    public EntityConnectionCommand getTeacher() {
        return teacher;
    }

    public void setTeacher(EntityConnectionCommand teacher) {
        this.teacher = teacher;
    }
}
