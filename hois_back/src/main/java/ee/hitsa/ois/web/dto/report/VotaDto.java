package ee.hitsa.ois.web.dto.report;

import java.math.BigDecimal;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class VotaDto {

    private AutocompleteResult studyYear;
    private AutocompleteResult studyPeriod;
    private AutocompleteResult curriculum;
    private Long applicationCount;
    private BigDecimal totalCredits;
    private BigDecimal acceptedCredits;
    private BigDecimal totalLocalCredits;
    private BigDecimal acceptedLocalCredits;
    private BigDecimal totalAbroadCredits;
    private BigDecimal acceptedAbroadCredits;

    public AutocompleteResult getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(AutocompleteResult studyYear) {
        this.studyYear = studyYear;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public AutocompleteResult getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }

    public Long getApplicationCount() {
        return applicationCount;
    }

    public void setApplicationCount(Long applicationCount) {
        this.applicationCount = applicationCount;
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(BigDecimal totalCredits) {
        this.totalCredits = totalCredits;
    }

    public BigDecimal getAcceptedCredits() {
        return acceptedCredits;
    }

    public void setAcceptedCredits(BigDecimal acceptedCredits) {
        this.acceptedCredits = acceptedCredits;
    }

    public BigDecimal getTotalLocalCredits() {
        return totalLocalCredits;
    }

    public void setTotalLocalCredits(BigDecimal totalLocalCredits) {
        this.totalLocalCredits = totalLocalCredits;
    }

    public BigDecimal getAcceptedLocalCredits() {
        return acceptedLocalCredits;
    }

    public void setAcceptedLocalCredits(BigDecimal acceptedLocalCredits) {
        this.acceptedLocalCredits = acceptedLocalCredits;
    }

    public BigDecimal getTotalAbroadCredits() {
        return totalAbroadCredits;
    }

    public void setTotalAbroadCredits(BigDecimal totalAbroadCredits) {
        this.totalAbroadCredits = totalAbroadCredits;
    }

    public BigDecimal getAcceptedAbroadCredits() {
        return acceptedAbroadCredits;
    }

    public void setAcceptedAbroadCredits(BigDecimal acceptedAbroadCredits) {
        this.acceptedAbroadCredits = acceptedAbroadCredits;
    }
}
