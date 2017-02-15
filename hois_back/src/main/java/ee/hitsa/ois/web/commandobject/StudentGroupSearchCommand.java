package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class StudentGroupSearchCommand {

    private String code;
    private List<Long> curriculum;
    private List<String> studyForm;
    private Long teacher;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Long> getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(List<Long> curriculum) {
        this.curriculum = curriculum;
    }

    public List<String> getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(List<String> studyForm) {
        this.studyForm = studyForm;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
}
