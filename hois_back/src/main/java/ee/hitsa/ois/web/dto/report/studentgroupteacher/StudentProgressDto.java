package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.math.BigDecimal;

public class StudentProgressDto {

    private Boolean isCurriculumFulfilled;
    private Boolean isCumLaude;
    private BigDecimal weightedAverageGrade;

    private Boolean isOccupationExamPassed;
    private BigDecimal missingGeneralStudies;
    private BigDecimal missingCoreStudies;
    private BigDecimal missingFreeChoice;

    public Boolean getIsCurriculumFulfilled() {
        return isCurriculumFulfilled;
    }

    public void setIsCurriculumFulfilled(Boolean isCurriculumFulfilled) {
        this.isCurriculumFulfilled = isCurriculumFulfilled;
    }

    public Boolean getIsCumLaude() {
        return isCumLaude;
    }

    public void setIsCumLaude(Boolean isCumLaude) {
        this.isCumLaude = isCumLaude;
    }

    public BigDecimal getWeightedAverageGrade() {
        return weightedAverageGrade;
    }

    public void setWeightedAverageGrade(BigDecimal weightedAverageGrade) {
        this.weightedAverageGrade = weightedAverageGrade;
    }

    public Boolean getIsOccupationExamPassed() {
        return isOccupationExamPassed;
    }

    public void setIsOccupationExamPassed(Boolean isOccupationExamPassed) {
        this.isOccupationExamPassed = isOccupationExamPassed;
    }

    public BigDecimal getMissingGeneralStudies() {
        return missingGeneralStudies;
    }

    public void setMissingGeneralStudies(BigDecimal missingGeneralStudies) {
        this.missingGeneralStudies = missingGeneralStudies;
    }

    public BigDecimal getMissingCoreStudies() {
        return missingCoreStudies;
    }

    public void setMissingCoreStudies(BigDecimal missingCoreStudies) {
        this.missingCoreStudies = missingCoreStudies;
    }

    public BigDecimal getMissingFreeChoice() {
        return missingFreeChoice;
    }

    public void setMissingFreeChoice(BigDecimal missingFreeChoice) {
        this.missingFreeChoice = missingFreeChoice;
    }

}
