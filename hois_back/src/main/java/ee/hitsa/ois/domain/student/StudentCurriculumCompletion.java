package ee.hitsa.ois.domain.student;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import ee.hitsa.ois.domain.BaseTask;

@Entity
public class StudentCurriculumCompletion extends BaseTask {
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    private BigDecimal studyBacklog;
    private BigDecimal studyBacklogWithoutGraduate;
    private BigDecimal averageMark;
    private BigDecimal averageMarkLastStudyPeriod;
    private BigDecimal averageMarkBeforeCurrentStudyPeriod;
    private BigDecimal credits;
    private BigDecimal creditsLastStudyPeriod;
    private BigDecimal creditsBeforeCurrentStudyPeriod;
    private BigDecimal studyOptionalBacklog;
    private Boolean isModulesOk;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public BigDecimal getStudyBacklog() {
        return studyBacklog;
    }

    public void setStudyBacklog(BigDecimal studyBacklog) {
        this.studyBacklog = studyBacklog;
    }

    public BigDecimal getStudyBacklogWithoutGraduate() {
        return studyBacklogWithoutGraduate;
    }

    public void setStudyBacklogWithoutGraduate(BigDecimal studyBacklogWithoutGraduate) {
        this.studyBacklogWithoutGraduate = studyBacklogWithoutGraduate;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }

    public BigDecimal getAverageMarkLastStudyPeriod() {
        return averageMarkLastStudyPeriod;
    }

    public void setAverageMarkLastStudyPeriod(BigDecimal averageMarkLastStudyPeriod) {
        this.averageMarkLastStudyPeriod = averageMarkLastStudyPeriod;
    }

    public BigDecimal getAverageMarkBeforeCurrentStudyPeriod() {
        return averageMarkBeforeCurrentStudyPeriod;
    }

    public void setAverageMarkBeforeCurrentStudyPeriod(BigDecimal averageMarkBeforeCurrentStudyPeriod) {
        this.averageMarkBeforeCurrentStudyPeriod = averageMarkBeforeCurrentStudyPeriod;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public BigDecimal getCreditsLastStudyPeriod() {
        return creditsLastStudyPeriod;
    }

    public void setCreditsLastStudyPeriod(BigDecimal creditsLastStudyPeriod) {
        this.creditsLastStudyPeriod = creditsLastStudyPeriod;
    }

    public BigDecimal getCreditsBeforeCurrentStudyPeriod() {
        return creditsBeforeCurrentStudyPeriod;
    }

    public void setCreditsBeforeCurrentStudyPeriod(BigDecimal creditsBeforeCurrentStudyPeriod) {
        this.creditsBeforeCurrentStudyPeriod = creditsBeforeCurrentStudyPeriod;
    }

    public BigDecimal getStudyOptionalBacklog() {
        return studyOptionalBacklog;
    }

    public void setStudyOptionalBacklog(BigDecimal studyOptionalBacklog) {
        this.studyOptionalBacklog = studyOptionalBacklog;
    }

    public Boolean getIsModulesOk() {
        return isModulesOk;
    }

    public void setIsModulesOk(Boolean isModulesOk) {
        this.isModulesOk = isModulesOk;
    }

    public Boolean isCurriculumFulfilled() {
        return Boolean.valueOf(BigDecimal.ZERO.compareTo(studyBacklog) == 0 && Boolean.TRUE.equals(isModulesOk));
    }
}
