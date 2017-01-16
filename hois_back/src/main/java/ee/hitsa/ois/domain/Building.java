package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Building extends BaseEntityWithId {

    private String code;
    private String name;
    private String address;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public School getSchool() {
       return school;
    }

    public void setSchool(School school) {
       this.school = school;
    }
}
