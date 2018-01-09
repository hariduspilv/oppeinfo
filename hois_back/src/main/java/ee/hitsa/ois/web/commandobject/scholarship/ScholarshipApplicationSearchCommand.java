package ee.hitsa.ois.web.commandobject.scholarship;

import java.util.List;

import ee.hitsa.ois.validation.Required;

public class ScholarshipApplicationSearchCommand {

    @Required
    private String type;
    private List<Long> curriculum;
    private List<String> courses;
    private String studentName;
    private String nameEt;
    private Long studyPeriod;
    private String status;
    private String studentGroup;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Long> getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(List<Long> curriculum) {
        this.curriculum = curriculum;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
}
