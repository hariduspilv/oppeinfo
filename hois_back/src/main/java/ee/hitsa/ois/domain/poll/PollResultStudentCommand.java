package ee.hitsa.ois.domain.poll;

public class PollResultStudentCommand {
    
    private Long pollId;
    private Long subjectId;
    private Long journalId;
    private Long enterpriseId;
    private Boolean students;
    private Boolean teachers;
    private String studentName;
    
    public Long getPollId() {
        return pollId;
    }
    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }
    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    public Long getJournalId() {
        return journalId;
    }
    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
