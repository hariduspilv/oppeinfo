package ee.hitsa.ois.web.dto.scholarship;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.util.EntityUtil;

public class ScholarshipTermStudentDto extends ScholarshipTermSearchDto {
    private Long applicationId;
    private BigDecimal averageMark;
    private BigDecimal lastPeriodMark;
    private BigDecimal curriculumCompletion;
    private Long maxAbsences;
    private String addInfo;
    private Boolean isTeacherConfirm;
    private Boolean isFamilyIncomes;
    private String status;
    private LocalDate decisionDate;
    private String rejectComment;

    public ScholarshipTermStudentDto() {

    }

    public ScholarshipTermStudentDto(Long id, String nameEt, String type, LocalDate applicationStart,
            LocalDate applicationEnd, Long places, Boolean isOpen) {
        super(id, nameEt, type, applicationStart, applicationEnd, places, isOpen);
    }

    public static ScholarshipTermStudentDto of(ScholarshipTerm term) {
        ScholarshipTermStudentDto dto = new ScholarshipTermStudentDto();
        EntityUtil.bindToDto(term, dto);
        return dto;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }

    public BigDecimal getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(BigDecimal lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public BigDecimal getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(BigDecimal curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public Long getMaxAbsences() {
        return maxAbsences;
    }

    public void setMaxAbsences(Long maxAbsences) {
        this.maxAbsences = maxAbsences;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Boolean getIsTeacherConfirm() {
        return isTeacherConfirm;
    }

    public void setIsTeacherConfirm(Boolean isTeacherConfirm) {
        this.isTeacherConfirm = isTeacherConfirm;
    }

    public Boolean getIsFamilyIncomes() {
        return isFamilyIncomes;
    }

    public void setIsFamilyIncomes(Boolean isFamilyIncomes) {
        this.isFamilyIncomes = isFamilyIncomes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDate decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

}
