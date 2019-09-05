package ee.hitsa.ois.web.commandobject.subject.studyperiod;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class SubjectStudyPeriodPlanSearchCommand {
    
    private Long studyPeriod;
    private EntityConnectionCommand curriculum;
    private Long subject;

    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public EntityConnectionCommand getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(EntityConnectionCommand curriculum) {
        this.curriculum = curriculum;
    }
    public Long getSubject() {
        return subject;
    }
    public void setSubject(Long subject) {
        this.subject = subject;
    }    
}
