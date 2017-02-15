package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.curriculum.Curriculum;

public class StudentGroupSearchDto {

    private Long id;
    private String code;
    private AutocompleteResult<Long> curriculum;
    private String studyForm;
    private Integer course;
    private Long studentCount;

    public StudentGroupSearchDto() {
    }

    public StudentGroupSearchDto(Long id, String code, Curriculum curriculum, String studyForm, Integer course, Long studentCount) {
        this.id = id;
        this.code = code;
        this.curriculum = AutocompleteResult.of(curriculum);
        this.studyForm = studyForm;
        this.course = course;
        this.studentCount = studentCount;
    }

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

    public AutocompleteResult<Long> getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult<Long> curriculum) {
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
}
