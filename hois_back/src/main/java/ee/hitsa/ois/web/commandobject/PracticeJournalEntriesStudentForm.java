package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class PracticeJournalEntriesStudentForm {

    @Size(max=10000)
    private String practiceReport;
    @Valid
    private List<PracticeJournalEntryStudentForm> practiceJournalEntries;

    public String getPracticeReport() {
        return practiceReport;
    }

    public void setPracticeReport(String practiceReport) {
        this.practiceReport = practiceReport;
    }

    public List<PracticeJournalEntryStudentForm> getPracticeJournalEntries() {
        return practiceJournalEntries;
    }

    public void setPracticeJournalEntries(List<PracticeJournalEntryStudentForm> practiceJournalEntries) {
        this.practiceJournalEntries = practiceJournalEntries;
    }

}
