package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DirectiveCoordinator extends BaseEntityWithId {

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    private String name;
    private String idcode;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
}
