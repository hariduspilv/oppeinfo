package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class JournalStudentHasAbsenceCommand {
    
    @NotNull
    private LocalDate entryDate;

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }
}
