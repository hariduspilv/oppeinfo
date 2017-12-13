package ee.hitsa.ois.web.commandobject.report;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class TeacherLoadCommand {

    @NotNull
    private Long studyYear;
    private Long studyPeriod;
    private EntityConnectionCommand module;
    private EntityConnectionCommand subject;
    private EntityConnectionCommand teacher;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public EntityConnectionCommand getModule() {
        return module;
    }

    public void setModule(EntityConnectionCommand module) {
        this.module = module;
    }

    public EntityConnectionCommand getSubject() {
        return subject;
    }

    public void setSubject(EntityConnectionCommand subject) {
        this.subject = subject;
    }

    public EntityConnectionCommand getTeacher() {
        return teacher;
    }

    public void setTeacher(EntityConnectionCommand teacher) {
        this.teacher = teacher;
    }
}
