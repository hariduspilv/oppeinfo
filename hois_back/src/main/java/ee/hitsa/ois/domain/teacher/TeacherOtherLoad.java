package ee.hitsa.ois.domain.teacher;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.StudyYear;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class TeacherOtherLoad extends BaseEntityWithId {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(updatable = false, nullable = false)
    private Teacher teacher;
    @Column(nullable = false)
    private String nameEt;
    @Column(nullable = false)
    private BigDecimal hours;
    @Column(nullable = false)
    private BigDecimal percent;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(updatable = false, nullable = false)
    private StudyYear studyYear;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }
}
