package ee.hitsa.ois.domain.student;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class StudentRemark extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier reason;

    private String remark;
    private LocalDateTime remarkTime;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classifier getReason() {
        return reason;
    }

    public void setReason(Classifier reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(LocalDateTime remarkTime) {
        this.remarkTime = remarkTime;
    }

}
