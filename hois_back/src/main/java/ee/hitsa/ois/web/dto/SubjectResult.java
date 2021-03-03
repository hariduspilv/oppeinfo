package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SubjectResult extends AutocompleteResult {

    private String code;

    // name without credits or any other extra
    private String subjectNameEt;
    private String subjectNameEn;

    private String assessment;
    private String gradeCode;
    private LocalDate gradeDate;
    private BigDecimal credits;
    private String teachers;
    private CurriculumVersionHigherModuleDto module;
    private Boolean isOptional;

    public SubjectResult(Long id, String nameEt, String nameEn, String code, BigDecimal credits) {
        super(id, nameEt, nameEn);
        this.subjectNameEt = nameEt;
        this.subjectNameEn = nameEn;
        this.code = code;
        this.credits = credits;
    }

    public SubjectResult(Long id, String nameEt, String nameEn, String subjectNameEt, String subjectNameEn,
            String code, BigDecimal credits) {
        super(id, nameEt, nameEn);
        this.subjectNameEt = subjectNameEt;
        this.subjectNameEn = subjectNameEn;
        this.code = code;
        this.credits = credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubjectNameEt() {
        return subjectNameEt;
    }

    public void setSubjectNameEt(String subjectNameEt) {
        this.subjectNameEt = subjectNameEt;
    }

    public String getSubjectNameEn() {
        return subjectNameEn;
    }

    public void setSubjectNameEn(String subjectNameEn) {
        this.subjectNameEn = subjectNameEn;
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

    public CurriculumVersionHigherModuleDto getModule() {
        return module;
    }

    public void setModule(CurriculumVersionHigherModuleDto module) {
        this.module = module;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }
}
