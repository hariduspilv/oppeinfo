package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SubjectResult extends AutocompleteResult {

    private String code;
    private String assessment;
    private String gradeCode;
    private LocalDate gradeDate;
    private BigDecimal credits;
    private String teachers;

    public SubjectResult(Long id, String nameEt, String nameEn, String code, BigDecimal credits) {
        super(id, nameEt, nameEn);
        this.code = code;
        this.credits = credits;
    }

    public SubjectResult(Long id, String nameEt, String nameEn, String code, BigDecimal credits, String assessment,
            String gradeCode, LocalDate gradeDate, String teachers) {
        super(id, nameEt, nameEn);
        this.code = code;
        this.credits = credits;
        this.assessment = assessment;
        this.gradeCode = gradeCode;
        this.gradeDate = gradeDate;
        this.teachers = teachers;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

}
