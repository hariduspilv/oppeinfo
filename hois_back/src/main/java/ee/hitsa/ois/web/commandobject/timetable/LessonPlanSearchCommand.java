package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;

import javax.validation.constraints.NotNull;

public class LessonPlanSearchCommand {

    @NotNull
    private Long studyYear;
    private Long schoolDepartment;
    private Long curriculumVersion;
    private List<Long> studentGroup;
    private List<Long> teacher;
    private Boolean byTeacher;

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

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public List<Long> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<Long> teacher) {
        this.teacher = teacher;
    }

    public List<Long> getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(List<Long> studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Boolean getByTeacher() {
        return byTeacher;
    }

    public void setByTeacher(Boolean byTeacher) {
        this.byTeacher = byTeacher;
    }
    
}
