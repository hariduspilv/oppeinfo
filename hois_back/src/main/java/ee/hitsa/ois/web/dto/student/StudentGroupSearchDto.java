package ee.hitsa.ois.web.dto.student;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentGroupSearchDto {

    private Long id;
    private String code;
    private AutocompleteResult curriculum;
    private String studyForm;
    private Integer course;
    private Long studentCount;
    private List<Long> schoolDepartments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AutocompleteResult getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Long getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Long studentCount) {
        this.studentCount = studentCount;
    }

    public List<Long> getSchoolDepartments() {
        return schoolDepartments;
    }

    public void setSchoolDepartments(List<Long> schoolDepartments) {
        this.schoolDepartments = schoolDepartments;
    }
}
