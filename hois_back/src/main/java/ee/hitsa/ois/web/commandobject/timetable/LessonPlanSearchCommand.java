package ee.hitsa.ois.web.commandobject.timetable;

import javax.validation.constraints.NotNull;

public class LessonPlanSearchCommand {

    @NotNull
    private Long studyYear;
    private Long schoolDepartment;
    private Long studentGroup;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(Long schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
}
