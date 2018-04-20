package ee.hitsa.ois.domain.studentgroupyeartransfer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.student.Student;

@Entity
public class StudentGroupYearTransferLog extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudentGroupYearTransfer studentGroupYearTransfer;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    @Column(nullable = false)
    private Boolean isMatching;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier mismatch;
    
    public StudentGroupYearTransfer getStudentGroupYearTransfer() {
        return studentGroupYearTransfer;
    }
    public void setStudentGroupYearTransfer(StudentGroupYearTransfer studentGroupYearTransfer) {
        this.studentGroupYearTransfer = studentGroupYearTransfer;
    }

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Boolean getIsMatching() {
        return isMatching;
    }
    public void setIsMatching(Boolean isMatching) {
        this.isMatching = isMatching;
    }
    
    public Classifier getMismatch() {
        return mismatch;
    }
    public void setMismatch(Classifier mismatch) {
        this.mismatch = mismatch;
    }
    
}
