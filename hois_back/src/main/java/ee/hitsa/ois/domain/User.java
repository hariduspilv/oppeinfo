package ee.hitsa.ois.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;

@Entity
@Table(name = "user_")
public class User extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier role;

    @OneToMany(mappedBy = "user")
    private Set<UserRights> userRights;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    public Classifier getRole() {
        return role;
    }

    public void setRole(Classifier role) {
        this.role = role;
    }

    public Set<UserRights> getUserRights() {
        return userRights;
    }

    public void setUserRights(Set<UserRights> userRights) {
        this.userRights = userRights;
    }

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
