package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.EstonianIdCode;

public class StudentSearchCommand {

    @Size(max = 255)
    private String name;
    @EstonianIdCode
    private String idcode;
    private List<Long> curriculumVersion;
    @Size(max = 100)
    private String studentGroup;
    private List<String> studyForm;
    private List<String> status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public List<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(List<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<String> getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(List<String> studyForm) {
        this.studyForm = studyForm;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}
