package ee.hitsa.ois.web.commandobject.sais;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

@DateRange(from = "createDateFrom", thru = "createDateTo")
public class SaisAdmissionImportForm extends VersionedCommand {

    @NotNull
    private LocalDate createDateFrom;
    @NotNull
    private LocalDate createDateTo;

    public LocalDate getCreateDateFrom() {
        return createDateFrom;
    }

    public void setCreateDateFrom(LocalDate createDateFrom) {
        this.createDateFrom = createDateFrom;
    }

    public LocalDate getCreateDateTo() {
        return createDateTo;
    }

    public void setCreateDateTo(LocalDate createDateTo) {
        this.createDateTo = createDateTo;
    }
}
