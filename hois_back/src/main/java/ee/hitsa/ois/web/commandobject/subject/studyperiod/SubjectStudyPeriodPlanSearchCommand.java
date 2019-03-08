package ee.hitsa.ois.web.commandobject.subject.studyperiod;

public class SubjectStudyPeriodPlanSearchCommand {
    
    private Long studyPeriod;
    private Long curriculum;
    private Long subject;

    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public Long getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }
    public Long getSubject() {
        return subject;
    }
    public void setSubject(Long subject) {
        this.subject = subject;
    }    
}
