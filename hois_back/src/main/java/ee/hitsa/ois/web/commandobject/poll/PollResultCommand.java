package ee.hitsa.ois.web.commandobject.poll;

public class PollResultCommand {
    
    private Long journalId;
    private Long subjectId;
    private Long subjectStudyPeriodId;
    
    public Long getJournalId() {
        return journalId;
    }
    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }
    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    public Long getSubjectStudyPeriodId() {
        return subjectStudyPeriodId;
    }
    public void setSubjectStudyPeriodId(Long subjectStudyPeriodId) {
        this.subjectStudyPeriodId = subjectStudyPeriodId;
    }
}
