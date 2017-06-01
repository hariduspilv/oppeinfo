package ee.hitsa.ois.web.commandobject.timetable;

import javax.validation.constraints.NotNull;

public class LessonPlanCreateForm {

    @NotNull
    private Long studyYear;
    @NotNull
    private Long studentGroup;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
}
