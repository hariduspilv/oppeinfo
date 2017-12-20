package ee.hitsa.ois.domain.student;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class StudentCurriculumCompletion extends BaseEntityWithId {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Student student;
    private Long studyBacklog;
    private Long studyBacklogWithoutGraduate;
    private Long averageMark;
    private Long averageMarkLastStudyPeriod;
    private Long averageMarkBeforeCurrentStudyPeriod;
    private Long credits;
    private Long creditsLastStudyPeriod;
    private Long creditsbeforeCurrentStudyPeriod;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getStudyBacklog() {
        return studyBacklog;
    }

    public void setStudyBacklog(Long studyBacklog) {
        this.studyBacklog = studyBacklog;
    }

    public Long getStudyBacklogWithoutGraduate() {
        return studyBacklogWithoutGraduate;
    }

    public void setStudyBacklogWithoutGraduate(Long studyBacklogWithoutGraduate) {
        this.studyBacklogWithoutGraduate = studyBacklogWithoutGraduate;
    }

    public Long getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(Long averageMark) {
        this.averageMark = averageMark;
    }

    public Long getAverageMarkLastStudyPeriod() {
        return averageMarkLastStudyPeriod;
    }

    public void setAverageMarkLastStudyPeriod(Long averageMarkLastStudyPeriod) {
        this.averageMarkLastStudyPeriod = averageMarkLastStudyPeriod;
    }

    public Long getAverageMarkBeforeCurrentStudyPeriod() {
        return averageMarkBeforeCurrentStudyPeriod;
    }

    public void setAverageMarkBeforeCurrentStudyPeriod(Long averageMarkBeforeCurrentStudyPeriod) {
        this.averageMarkBeforeCurrentStudyPeriod = averageMarkBeforeCurrentStudyPeriod;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public Long getCreditsLastStudyPeriod() {
        return creditsLastStudyPeriod;
    }

    public void setCreditsLastStudyPeriod(Long creditsLastStudyPeriod) {
        this.creditsLastStudyPeriod = creditsLastStudyPeriod;
    }

    public Long getCreditsbeforeCurrentStudyPeriod() {
        return creditsbeforeCurrentStudyPeriod;
    }

    public void setCreditsbeforeCurrentStudyPeriod(Long creditsbeforeCurrentStudyPeriod) {
        this.creditsbeforeCurrentStudyPeriod = creditsbeforeCurrentStudyPeriod;
    }

}
