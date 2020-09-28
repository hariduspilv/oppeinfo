package ee.hitsa.ois.web.dto.timetable;

public class JournalAutomaticAddStudentsResult {

    private Long journalId;
    private Long addedStudents;

    public JournalAutomaticAddStudentsResult(Long journalId, Long addedStudents) {
        this.journalId = journalId;
        this.addedStudents = addedStudents;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public Long getAddedStudents() {
        return addedStudents;
    }

    public void setAddedStudents(Long addedStudents) {
        this.addedStudents = addedStudents;
    }
}
