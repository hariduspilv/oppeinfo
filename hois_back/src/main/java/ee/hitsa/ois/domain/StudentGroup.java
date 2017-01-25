package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StudentGroup extends BaseEntityWithId {

    private String code;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
