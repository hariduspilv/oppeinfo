package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;

public class HigherProtocolModuleSubjectDto {
    private Long id;
    private String code;
    private String nameEt;
    private String nameEn;
    private BigDecimal credits;
    private Long studentHigherResultId;

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
        return nameEn != null ? nameEn : nameEt;
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

    public Long getStudentHigherResultId() {
        return studentHigherResultId;
    }

    public void setStudentHigherResultId(Long studentHigherResultId) {
        this.studentHigherResultId = studentHigherResultId;
    }
}
