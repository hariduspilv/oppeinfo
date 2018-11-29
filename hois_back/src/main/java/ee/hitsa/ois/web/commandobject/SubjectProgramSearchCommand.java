package ee.hitsa.ois.web.commandobject;

public class SubjectProgramSearchCommand {
    
    private Long subject;
    private Long studyPeriod;
    private EntityConnectionCommand teacher;
    private String status;
    
    public Long getSubject() {
        return subject;
    }
    public void setSubject(Long subject) {
        this.subject = subject;
    }
    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public EntityConnectionCommand getTeacher() {
        return teacher;
    }
    public void setTeacher(EntityConnectionCommand teacher) {
        this.teacher = teacher;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
