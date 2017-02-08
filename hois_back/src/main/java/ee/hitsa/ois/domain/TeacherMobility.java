package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class TeacherMobility extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Teacher teacher;
    private LocalDate start;
    private LocalDate end;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier target;
    private String school;
    @ManyToOne(optional = false)
    private Classifier state;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Classifier getTarget() {
        return target;
    }

    public void setTarget(Classifier target) {
        this.target = target;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Classifier getState() {
        return state;
    }

    public void setState(Classifier state) {
        this.state = state;
    }
}
