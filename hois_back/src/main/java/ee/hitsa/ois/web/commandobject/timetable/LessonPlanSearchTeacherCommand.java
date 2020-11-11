package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;

import javax.validation.constraints.NotNull;

public class LessonPlanSearchTeacherCommand {

    @NotNull
    private Long studyYear;
    private List<Long> teacher;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public List<Long> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<Long> teacher) {
        this.teacher = teacher;
    }
}
