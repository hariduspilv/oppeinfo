package ee.hitsa.ois.web.commandobject.timetable;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class LessonPlanSearchTeacherCommand {

    @NotNull
    private Long studyYear;
    private EntityConnectionCommand teacher;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public EntityConnectionCommand getTeacher() {
        return teacher;
    }

    public void setTeacher(EntityConnectionCommand teacher) {
        this.teacher = teacher;
    }
}
