package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;

public class PrerequisiteSubjectDto {
    
    private Long id;
    private String code;
    private String nameEt;
    private String nameEn;
    private BigDecimal credits;
    private String assessment;
    private String grade;
    
    public PrerequisiteSubjectDto(Long id, String code, String nameEt, String nameEn, BigDecimal credits,
            String assessment, String grade) {
        this.id = id;
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.credits = credits;
        this.assessment = assessment;
        this.grade = grade;
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

    public BigDecimal getCredits() {
        return credits;
    }
    
    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }
    
    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
}
