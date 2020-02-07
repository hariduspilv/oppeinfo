package ee.hitsa.ois.web.commandobject.poll;

public class PollCommentCommand {
    
    private Long journalId;
    private Long subjectStudyPeriodId;
    private String comment;
    private Long commentRef;
    
    public Long getJournalId() {
        return journalId;
    }
    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Long getCommentRef() {
        return commentRef;
    }
    public void setCommentRef(Long commentRef) {
        this.commentRef = commentRef;
    }
    public Long getSubjectStudyPeriodId() {
        return subjectStudyPeriodId;
    }
    public void setSubjectStudyPeriodId(Long subjectStudyPeriodId) {
        this.subjectStudyPeriodId = subjectStudyPeriodId;
    }

}
