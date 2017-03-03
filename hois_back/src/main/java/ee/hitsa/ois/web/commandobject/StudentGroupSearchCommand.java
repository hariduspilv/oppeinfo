package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class StudentGroupSearchCommand {

    private String code;
    private List<Long> curriculumVersion;
    private List<String> studyForm;
    private EntityConnectionCommand<Long> teacher;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(List<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public List<String> getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(List<String> studyForm) {
        this.studyForm = studyForm;
    }

    public EntityConnectionCommand<Long> getTeacher() {
        return teacher;
    }

    public void setTeacher(EntityConnectionCommand<Long> teacher) {
        this.teacher = teacher;
    }
}
