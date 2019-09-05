package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentHigherSubjectResultGradeDto {

    private Long id;
    private String grade;
    private String gradeValue;
    private LocalDate gradeDate;
    private List<String> teachers = new ArrayList<>();
    private Long studyPeriod;
    private Short gradeMark;
    private String gradeNameEt;
    private String assessedBy;
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGradeNameEt() {
        return gradeNameEt;
    }

    public void setGradeNameEt(String gradeNameEt) {
        this.gradeNameEt = gradeNameEt;
    }

    public String getAssessedBy() {
        return assessedBy;
    }

    public void setAssessedBy(String assessedBy) {
        this.assessedBy = assessedBy;
    }

    public Short getGradeMark() {
        return gradeMark;
    }

    public void setGradeMark(Short gradeMark) {
        this.gradeMark = gradeMark;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
