package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;

public class StudentVocationalStudyProgramme {
    private BigDecimal generalStudies;
    private BigDecimal coreStudies;
    private BigDecimal freeChoice;
    private BigDecimal finalExam;

    public StudentVocationalStudyProgramme(BigDecimal generalStudies, BigDecimal coreStudies, BigDecimal freeChoice,
            BigDecimal finalExam) {
        this.generalStudies = generalStudies;
        this.coreStudies = coreStudies;
        this.freeChoice = freeChoice;
        this.finalExam = finalExam;
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

}
