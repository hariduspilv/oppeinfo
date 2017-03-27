package ee.hitsa.ois.web.commandobject.student;

import java.util.List;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class StudentGroupSearchCommand {

    private String code;
    private EntityConnectionCommand curriculum;
    private List<Long> curriculums;
    private List<Long> curriculumVersion;
    private List<String> studyForm;
    private EntityConnectionCommand teacher;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EntityConnectionCommand getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(EntityConnectionCommand curriculum) {
        this.curriculum = curriculum;
    }

    public void setCurriculums(List<Long> curriculums) {
        this.curriculums = curriculums;
    }

    public void setCurriculumVersion(List<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public List<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public List<Long> getCurriculums() {
        return curriculums;
    }

    public List<String> getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(List<String> studyForm) {
        this.studyForm = studyForm;
    }

    public EntityConnectionCommand getTeacher() {
        return teacher;
    }

    public void setTeacher(EntityConnectionCommand teacher) {
        this.teacher = teacher;
    }
}
