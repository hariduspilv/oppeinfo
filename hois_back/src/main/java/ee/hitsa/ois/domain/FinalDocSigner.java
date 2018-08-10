package ee.hitsa.ois.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.school.School;

@Entity
public class FinalDocSigner extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String position;
    private String positionEn;
    @Column(nullable = false)
    private Boolean isFirst;
    @Column(nullable = false)
    private Boolean isValid;
    
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
    
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getPositionEn() {
        return positionEn;
    }
    public void setPositionEn(String positionEn) {
        this.positionEn = positionEn;
    }
    
    public Boolean getIsFirst() {
        return isFirst;
    }
    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }
    
    public Boolean getIsValid() {
        return isValid;
    }
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
    
}
