package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.Required;

public class ModuleProtocolCommand {

    @Required
    private List<Long> students;
    @NotNull
    private Long curriculumVersionOccupationModule;
    @NotNull
    private Long curriculumVersion;
    @NotNull
    private Long studyYear;
    @NotNull
    private Long teacher;

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
    }

    public Long getCurriculumVersionOccupationModule() {
        return curriculumVersionOccupationModule;
    }

    public void setCurriculumVersionOccupationModule(Long curriculumVersionOccupationModule) {
        this.curriculumVersionOccupationModule = curriculumVersionOccupationModule;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

}
