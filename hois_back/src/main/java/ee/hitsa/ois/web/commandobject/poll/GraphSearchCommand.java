package ee.hitsa.ois.web.commandobject.poll;

public class GraphSearchCommand {
    
    private Long pollId;
    private Long responseId;
    private Long journalId;
    private Long subjectId;
    private Boolean themes;
    
    public Long getResponseId() {
        return responseId;
    }
    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }
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
    public Boolean getThemes() {
        return themes;
    }
    public void setThemes(Boolean themes) {
        this.themes = themes;
    }
    public Long getPollId() {
        return pollId;
    }
    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

}
