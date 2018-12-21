package ee.hitsa.ois.web.commandobject.studymaterial;

public class SubjectStudyPeriodSearchCommand {

    private Long teacher;
    private Long studyPeriod;
    private Long studentGroup;
    private Long subject;
    
    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    
    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    
    public Long getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
    public Long getSubject() {
        return subject;
    }
    public void setSubject(Long subject) {
        this.subject = subject;
    }

}
