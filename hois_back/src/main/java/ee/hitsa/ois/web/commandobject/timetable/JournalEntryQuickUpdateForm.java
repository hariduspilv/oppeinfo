package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;

import ee.hitsa.ois.validation.Required;

public class JournalEntryQuickUpdateForm {

    @Required
    private Long journalEntryId;
    private List<JournalEntryStudentForm> journalEntryStudents;
    private Boolean allStudents;

    public Long getJournalEntryId() {
        return journalEntryId;
    }

    public void setJournalEntryId(Long journalEntryId) {
        this.journalEntryId = journalEntryId;
    }

    public List<JournalEntryStudentForm> getJournalEntryStudents() {
        return journalEntryStudents;
    }

    public void setJournalEntryStudents(List<JournalEntryStudentForm> journalEntryStudents) {
        this.journalEntryStudents = journalEntryStudents;
    }

    public Boolean getAllStudents() {
        return allStudents;
    }

    public void setAllStudents(Boolean allStudents) {
        this.allStudents = allStudents;
    }
}
