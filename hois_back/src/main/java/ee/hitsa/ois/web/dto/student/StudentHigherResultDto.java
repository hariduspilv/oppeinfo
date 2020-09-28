package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.util.List;

public class StudentHigherResultDto {

    private List<StudentHigherModuleResultDto> modules;
    private List<StudentHigherSubjectResultDto> subjectResults;
    private List<StudentHigherStudyPeriodResultDto> studyPeriodResults;
    private List<StudentHigherModuleResultDto> extraCurriculumModuleResults;
    private BigDecimal averageGrade;
    private Boolean isCurriculumFulfilled;
    private BigDecimal creditsSubmitted;
    private BigDecimal creditsSubmittedConsidered;
    private BigDecimal consideredFulfillmentPercentage;
    private BigDecimal fulfillmentPercentage;

    public List<StudentHigherStudyPeriodResultDto> getStudyPeriodResults() {
        return studyPeriodResults;
    }

    public void setStudyPeriodResults(List<StudentHigherStudyPeriodResultDto> studyPeriodResults) {
        this.studyPeriodResults = studyPeriodResults;
    }

    public BigDecimal getCreditsSubmitted() {
        return creditsSubmitted;
    }

    public void setCreditsSubmitted(BigDecimal creditsSubmitted) {
        this.creditsSubmitted = creditsSubmitted;
    }

    public BigDecimal getCreditsSubmittedConsidered() {
        return creditsSubmittedConsidered;
    }

    public void setCreditsSubmittedConsidered(BigDecimal creditsSubmittedConsidered) {
        this.creditsSubmittedConsidered = creditsSubmittedConsidered;
    }

    public BigDecimal getConsideredFulfillmentPercentage() {
        return consideredFulfillmentPercentage;
    }

    public void setConsideredFulfillmentPercentage(BigDecimal consideredFulfillmentPercentage) {
        this.consideredFulfillmentPercentage = consideredFulfillmentPercentage;
    }

    public Boolean getIsCurriculumFulfilled() {
        return isCurriculumFulfilled;
    }

    public void setIsCurriculumFulfilled(Boolean isCurriculumFulfilled) {
        this.isCurriculumFulfilled = isCurriculumFulfilled;
    }

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
    }

    public List<StudentHigherModuleResultDto> getModules() {
        return modules;
    }

    public void setModules(List<StudentHigherModuleResultDto> modules) {
        this.modules = modules;
    }

    public List<StudentHigherSubjectResultDto> getSubjectResults() {
        return subjectResults;
    }

    public void setSubjectResults(List<StudentHigherSubjectResultDto> subjectResults) {
        this.subjectResults = subjectResults;
    }

    public BigDecimal getFulfillmentPercentage() {
        return fulfillmentPercentage;
    }

    public void setFulfillmentPercentage(BigDecimal fulfillmentPercentage) {
        this.fulfillmentPercentage = fulfillmentPercentage;
    }

    public List<StudentHigherModuleResultDto> getExtraCurriculumModuleResults() {
        return extraCurriculumModuleResults;
    }

    public void setExtraCurriculumModuleResults(List<StudentHigherModuleResultDto> extraCurriculumModuleResults) {
        this.extraCurriculumModuleResults = extraCurriculumModuleResults;
    }
}
