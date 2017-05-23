package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

public class SaisAdmissionImportForm extends VersionedCommand {
    private LocalDate createDateFrom;
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
