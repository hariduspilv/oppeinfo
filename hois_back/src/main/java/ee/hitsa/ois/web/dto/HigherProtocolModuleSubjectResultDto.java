package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;

public class HigherProtocolModuleSubjectResultDto {

    private Long studentHigherResultId;
    private Long subjectId;
    private BigDecimal credits;
    private String grade;
    private Boolean isApelTransfer;

    public HigherProtocolModuleSubjectResultDto() {

    }

    public HigherProtocolModuleSubjectResultDto(Long studentHigherResultId, Long subjectId, BigDecimal credits,
            String grade, Long apelApplicationRecordId) {
        this.studentHigherResultId = studentHigherResultId;
        this.subjectId = subjectId;
        this.credits = credits;
        this.grade = grade;
        this.isApelTransfer = Boolean.valueOf(apelApplicationRecordId != null);
    }

    public Long getStudentHigherResultId() {
        return studentHigherResultId;
    }

    public void setStudentHigherResultId(Long studentHigherResultId) {
        this.studentHigherResultId = studentHigherResultId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Boolean getIsApelTransfer() {
        return isApelTransfer;
    }

    public void setIsApelTransfer(Boolean isApelTransfer) {
        this.isApelTransfer = isApelTransfer;
    }
}
