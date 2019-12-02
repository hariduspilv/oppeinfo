package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StudentModuleResultDto {

    private Long id;
    private Long curriculumVersionModuleId;
    private String nameEt;
    private String nameEn;
    private String subjectCode;
    private BigDecimal credits;
    private String grade;
    private String gradeValue;
    private LocalDate gradeDate;
    private Boolean isOptional;

    public StudentModuleResultDto(Long id, Long curriculumVersionModuleId, String nameEt, String nameEn,
            String subjectCode, BigDecimal credits, String grade, String gradeValue, LocalDate gradeDate,
            Boolean isOptional) {
        super();
        this.id = id;
        this.curriculumVersionModuleId = curriculumVersionModuleId;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.subjectCode = subjectCode;
        this.credits = credits;
        this.grade = grade;
        this.gradeValue = gradeValue;
        this.gradeDate = gradeDate;
        this.isOptional = isOptional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurriculumVersionModuleId() {
        return curriculumVersionModuleId;
    }

    public void setCurriculumVersionModuleId(Long curriculumVersionModuleId) {
        this.curriculumVersionModuleId = curriculumVersionModuleId;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
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

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

}
