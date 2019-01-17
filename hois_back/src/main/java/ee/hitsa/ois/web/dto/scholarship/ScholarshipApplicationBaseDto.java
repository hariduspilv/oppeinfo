package ee.hitsa.ois.web.dto.scholarship;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ScholarshipApplicationBaseDto {
    private Long id;
    private BigDecimal averageMark;
    private BigDecimal lastPeriodMark;
    private BigDecimal saisPoints;
    private BigDecimal curriculumCompletion;
    private Long absences;
    private Boolean isStudyBacklog;
    private LocalDateTime inserted;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getSaisPoints() {
        return saisPoints;
    }

    public void setSaisPoints(BigDecimal saisPoints) {
        this.saisPoints = saisPoints;
    }

    public BigDecimal getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(BigDecimal curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public Long getAbsences() {
        return absences;
    }

    public void setAbsences(Long absences) {
        this.absences = absences;
    }

    public Boolean getIsStudyBacklog() {
        return isStudyBacklog;
    }

    public void setIsStudyBacklog(Boolean isStudyBacklog) {
        this.isStudyBacklog = isStudyBacklog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

}
