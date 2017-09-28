package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class PracticeJournalEntriesStudentForm {

    private String practiceReport;
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
