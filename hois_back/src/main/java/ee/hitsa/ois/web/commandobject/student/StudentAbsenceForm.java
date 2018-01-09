package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

@DateRange
public class StudentAbsenceForm extends VersionedCommand {

    @Required
    private LocalDate validFrom;
    private LocalDate validThru;
    @Required
    @Size(max = 1000)
    private String cause;

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

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
