package ee.hitsa.ois.web.dto.scholarship;

import java.time.LocalDate;

import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.util.EntityUtil;

public class ScholarshipTermStudentDto extends ScholarshipTermSearchDto {
    private Long averageMark;
    private Long lastPeriodMark;
    private Long curriculumCompletion;
    private Long maxAbsences;
    private Boolean isTeacherConfirm;
    private String status;
    private LocalDate decisionDate;

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

    public Long getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(Long averageMark) {
        this.averageMark = averageMark;
    }

    public Long getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(Long lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public Long getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(Long curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public Long getMaxAbsences() {
        return maxAbsences;
    }

    public void setMaxAbsences(Long maxAbsences) {
        this.maxAbsences = maxAbsences;
    }

    public Boolean getIsTeacherConfirm() {
        return isTeacherConfirm;
    }

    public void setIsTeacherConfirm(Boolean isTeacherConfirm) {
        this.isTeacherConfirm = isTeacherConfirm;
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

}
