package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "user_")
public class User extends BaseEntityWithId {

    @ManyToOne(optional = false)
    private Classifier role;

    @OneToMany(mappedBy = "user")
    private Set<UserRights> userRights;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Person person;

    @ManyToOne
    private School school;

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
}
