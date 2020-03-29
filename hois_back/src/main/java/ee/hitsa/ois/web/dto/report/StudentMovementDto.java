package ee.hitsa.ois.web.dto.report;

import java.math.BigDecimal;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentMovementDto {
    
    private AutocompleteResult object;
    private Long studyingFrom;
    private Long studyingThru;
    private Long startedStudying;
    private Long exmatOwnWish;
    private Long exmatElse;
    private Long completed;
    private BigDecimal exmatPercentage;
    private BigDecimal completionPercentage;
    
    public Long getStudyingFrom() {
        return studyingFrom;
    }
    public void setStudyingFrom(Long studyingFrom) {
        this.studyingFrom = studyingFrom;
    }
    public Long getStudyingThru() {
        return studyingThru;
    }
    public void setStudyingThru(Long studyingThru) {
        this.studyingThru = studyingThru;
    }
    public Long getStartedStudying() {
        return startedStudying;
    }
    public void setStartedStudying(Long startedStudying) {
        this.startedStudying = startedStudying;
    }
    public Long getExmatOwnWish() {
        return exmatOwnWish;
    }
    public void setExmatOwnWish(Long exmatOwnWish) {
        this.exmatOwnWish = exmatOwnWish;
    }
    public Long getExmatElse() {
        return exmatElse;
    }
    public void setExmatElse(Long exmatElse) {
        this.exmatElse = exmatElse;
    }
    public Long getCompleted() {
        return completed;
    }
    public void setCompleted(Long completed) {
        this.completed = completed;
    }
    public AutocompleteResult getObject() {
        return object;
    }
    public void setObject(AutocompleteResult object) {
        this.object = object;
    }
    public BigDecimal getExmatPercentage() {
        return exmatPercentage;
    }
    public void setExmatPercentage(BigDecimal exmatPercentage) {
        this.exmatPercentage = exmatPercentage;
    }
    public BigDecimal getCompletionPercentage() {
        return completionPercentage;
    }
    public void setCompletionPercentage(BigDecimal completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

}
