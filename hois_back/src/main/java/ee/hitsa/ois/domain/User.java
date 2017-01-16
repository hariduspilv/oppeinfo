package ee.hitsa.ois.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_")
public class User extends BaseEntityWithId {

    @ManyToOne
    private Classifier role;

    @OneToMany(mappedBy = "user")
    public Set<UserRights> userRights;

    @ManyToOne
    private Person person;

    @ManyToOne
    private School school;

    public void setSchool(School school) {
        this.school = school;
    }

    public School getSchool() {
        return school;
    }

    public Classifier getRole() {
        return role;
    }

    public void setRole(Classifier role) {
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
