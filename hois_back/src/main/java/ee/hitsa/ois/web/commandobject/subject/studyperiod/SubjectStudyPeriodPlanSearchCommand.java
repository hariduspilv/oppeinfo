package ee.hitsa.ois.web.commandobject.subject.studyperiod;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class SubjectStudyPeriodPlanSearchCommand {

    private Long curriculum;
    private Long curriculumVersion;
    private Long subject;

    public Long getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Long getSubject() {
        return subject;
    }
    public void setSubject(Long subject) {
        this.subject = subject;
    }    
}
