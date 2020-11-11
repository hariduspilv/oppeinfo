package ee.hitsa.ois.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;

@Entity
public class MidtermTask extends BaseEntityWithId {

    private String nameEt;
    private String nameEn;
    private String descriptionEt;
    private String descriptionEn;
    private Short percentage;
    private Short thresholdPercentage;
    private BigDecimal maxPoints;
    private Boolean threshold;
    private LocalDate taskDate;
    private Long moodleGradeItemId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private SubjectStudyPeriod subjectStudyPeriod;

    @OneToMany(mappedBy = "midtermTask", fetch = FetchType.LAZY)
    private List<MidtermTaskStudentResult> studentResults;

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDescriptionEt() {
        return descriptionEt;
    }

    public void setDescriptionEt(String descriptionEt) {
        this.descriptionEt = descriptionEt;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public Short getPercentage() {
        return percentage;
    }

    public void setPercentage(Short percentage) {
        this.percentage = percentage;
    }

    public Short getThresholdPercentage() {
        return thresholdPercentage;
    }

    public void setThresholdPercentage(Short thresholdPercentage) {
        this.thresholdPercentage = thresholdPercentage;
    }

    public BigDecimal getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(BigDecimal maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Boolean getThreshold() {
        return threshold;
    }

    public void setThreshold(Boolean threshold) {
        this.threshold = threshold;
    }

    public LocalDate getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }

    public Long getMoodleGradeItemId() {
        return moodleGradeItemId;
    }

    public void setMoodleGradeItemId(Long moodleGradeItemId) {
        this.moodleGradeItemId = moodleGradeItemId;
    }

    public SubjectStudyPeriod getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public List<MidtermTaskStudentResult> getStudentResults() {
        return studentResults != null ? studentResults : (studentResults = new ArrayList<>());
    }

    public void setStudentResults(List<MidtermTaskStudentResult> studentResults) {
        this.studentResults = studentResults;
    }
}
