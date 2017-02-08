package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.NotEmpty;

@DateRange
public class StudentAbsenceForm extends VersionedCommand {

    @NotNull
    private LocalDate validFrom;
    private LocalDate validThru;
    @NotEmpty
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
