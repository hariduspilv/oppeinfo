package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.util.List;

public class StudentHigherResultDto {

    private List<StudentHigherModuleResultDto> modules;
    private List<StudentHigherSubjectResultDto> subjectResults;
    private List<StudentHigherStudyPeriodResultDto> studyPeriodResults;
    private BigDecimal averageGrade;
    private Boolean isCurriculumFulfilled;
    private BigDecimal creditsSubmitted;
    private BigDecimal creditsSubmittedConsidered;

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
}
