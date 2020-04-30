package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;

public class StudentVocationalStudyProgrammeDto {
    // curriculum credits
    private BigDecimal generalStudies;
    private BigDecimal coreStudies;
    private BigDecimal freeChoice;
    private BigDecimal finalExam;

    private BigDecimal earnedGeneralStudies;
    private BigDecimal earnedCoreStudies;
    private BigDecimal earnedFreeChoice;

    public StudentVocationalStudyProgrammeDto(BigDecimal generalStudies, BigDecimal coreStudies,
            BigDecimal finalExam, BigDecimal freeChoice) {
        this.generalStudies = generalStudies;
        this.coreStudies = coreStudies;
        this.finalExam = finalExam;
        this.freeChoice = freeChoice;
    }

    public BigDecimal getGeneralStudies() {
        return generalStudies;
    }

    public void setGeneralStudies(BigDecimal generalStudies) {
        this.generalStudies = generalStudies;
    }

    public BigDecimal getCoreStudies() {
        return coreStudies;
    }

    public void setCoreStudies(BigDecimal coreStudies) {
        this.coreStudies = coreStudies;
    }

    public BigDecimal getFreeChoice() {
        return freeChoice;
    }

    public void setFreeChoice(BigDecimal freeChoice) {
        this.freeChoice = freeChoice;
    }

    public BigDecimal getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(BigDecimal finalExam) {
        this.finalExam = finalExam;
    }

    public BigDecimal getEarnedGeneralStudies() {
        return earnedGeneralStudies != null ? earnedGeneralStudies : BigDecimal.ZERO;
    }

    public void setEarnedGeneralStudies(BigDecimal earnedGeneralStudies) {
        this.earnedGeneralStudies = earnedGeneralStudies;
    }

    public BigDecimal getEarnedCoreStudies() {
        return earnedCoreStudies != null ? earnedCoreStudies : BigDecimal.ZERO;
    }

    public void setEarnedCoreStudies(BigDecimal earnedCoreStudies) {
        this.earnedCoreStudies = earnedCoreStudies;
    }

    public BigDecimal getEarnedFreeChoice() {
        return earnedFreeChoice != null ? earnedFreeChoice : BigDecimal.ZERO;
    }

    public void setEarnedFreeChoice(BigDecimal earnedFreeChoice) {
        this.earnedFreeChoice = earnedFreeChoice;
    }
}
