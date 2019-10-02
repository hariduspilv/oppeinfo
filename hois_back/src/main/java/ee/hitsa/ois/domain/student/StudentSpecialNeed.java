package ee.hitsa.ois.domain.student;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class StudentSpecialNeed extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Student student;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier specialNeed;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classifier getSpecialNeed() {
        return specialNeed;
    }

    public void setSpecialNeed(Classifier specialNeed) {
        this.specialNeed = specialNeed;
    }
}
