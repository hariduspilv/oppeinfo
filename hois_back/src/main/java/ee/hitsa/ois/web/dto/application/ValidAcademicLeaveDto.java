package ee.hitsa.ois.web.dto.application;

import java.time.LocalDate;

import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.util.EntityUtil;

public class ValidAcademicLeaveDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isPeriod;
    private Long studyPeriodStart;
    private Long studyPeriodEnd;
    private String reason;
    
    public static ValidAcademicLeaveDto of(DirectiveStudent directiveStudent) {
        ValidAcademicLeaveDto dto = new ValidAcademicLeaveDto();
        dto.setId(EntityUtil.getId(directiveStudent));
        dto.setIsPeriod(directiveStudent.getIsPeriod());
        dto.setStartDate(directiveStudent.getStartDate());
        dto.setEndDate(directiveStudent.getEndDate());
        dto.setStudyPeriodStart(EntityUtil.getNullableId(directiveStudent.getStudyPeriodStart()));
        dto.setStudyPeriodEnd(EntityUtil.getNullableId(directiveStudent.getStudyPeriodEnd()));
        dto.setReason(EntityUtil.getCode(directiveStudent.getReason()));
        if (Boolean.TRUE.equals(directiveStudent.getIsPeriod())) {
            // start and end dates for cancel date validation
            dto.setStartDate(directiveStudent.getStudyPeriodStart().getStartDate());
            dto.setEndDate(directiveStudent.getStudyPeriodEnd().getEndDate());
        }
        return dto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsPeriod() {
        return isPeriod;
    }
    public void setIsPeriod(Boolean isPeriod) {
        this.isPeriod = isPeriod;
    }

    public Long getStudyPeriodStart() {
        return studyPeriodStart;
    }
    public void setStudyPeriodStart(Long studyPeriodStart) {
        this.studyPeriodStart = studyPeriodStart;
    }

    public Long getStudyPeriodEnd() {
        return studyPeriodEnd;
    }
    public void setStudyPeriodEnd(Long studyPeriodEnd) {
        this.studyPeriodEnd = studyPeriodEnd;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
