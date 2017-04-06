package ee.hitsa.ois.domain.student;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;

@Entity
public class Student extends StudentBase {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Person person;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private StudentHistory studentHistory;
    @OneToMany(mappedBy = "student")
    private List<StudentRepresentative> representatives;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public StudentHistory getStudentHistory() {
        return studentHistory;
    }

    public void setStudentHistory(StudentHistory studentHistory) {
        this.studentHistory = studentHistory;
    }

    public List<StudentRepresentative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<StudentRepresentative> representatives) {
        this.representatives = representatives;
    }

}
