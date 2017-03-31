package ee.hitsa.ois.domain.student;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StudentHistory extends StudentBase {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    private LocalDateTime validFrom;
    private LocalDateTime validThru;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private StudentHistory prevStudentHistory;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDateTime validThru) {
        this.validThru = validThru;
    }

    public StudentHistory getPrevStudentHistory() {
        return prevStudentHistory;
    }

    public void setPrevStudentHistory(StudentHistory prevStudentHistory) {
        this.prevStudentHistory = prevStudentHistory;
    }
}
