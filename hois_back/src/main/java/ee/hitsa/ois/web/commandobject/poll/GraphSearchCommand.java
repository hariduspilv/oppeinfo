package ee.hitsa.ois.web.commandobject.poll;

public class GraphSearchCommand {
    
    private Long pollId;
    private Long responseId;
    private Long journalId;
    private Long subjectId;
    private Long enterpriseId;
    private Boolean students;
    private Boolean teachers;
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
    public Long getEnterpriseId() {
        return enterpriseId;
    }
    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
    public Boolean getStudents() {
        return students;
    }
    public void setStudents(Boolean students) {
        this.students = students;
    }
    public Boolean getTeachers() {
        return teachers;
    }
    public void setTeachers(Boolean teachers) {
        this.teachers = teachers;
    }

}
