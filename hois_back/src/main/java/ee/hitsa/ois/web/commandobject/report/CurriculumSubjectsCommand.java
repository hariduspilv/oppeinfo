package ee.hitsa.ois.web.commandobject.report;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class CurriculumSubjectsCommand {

    @NotNull
    private Long studyYear;
    private Long curriculum;
    private String iscedVald;
    private EntityConnectionCommand subject;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public String getIscedVald() {
        return iscedVald;
    }

    public void setIscedVald(String iscedVald) {
        this.iscedVald = iscedVald;
    }

    public EntityConnectionCommand getSubject() {
        return subject;
    }

    public void setSubject(EntityConnectionCommand subject) {
        this.subject = subject;
    }
}
