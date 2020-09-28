package ee.hitsa.ois.domain.schoolcapacity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;

@Entity
public class SchoolCapacityType extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier capacityType;
    @Column(nullable = false)
    private Boolean isHigher;
    @Column(nullable = false)
    private Boolean isUsable;
    @Column(nullable = false)
    private Boolean isTimetable;
    @Column(nullable = false)
    private Boolean isContact;
    @OneToMany(mappedBy = "schoolCapacityType", fetch = FetchType.LAZY)
    private List<SchoolCapacityTypeLoad> typeLoads = new ArrayList<>();
    
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    
    public Classifier getCapacityType() {
        return capacityType;
    }
    public void setCapacityType(Classifier capacityType) {
        this.capacityType = capacityType;
    }
    
    public Boolean getIsHigher() {
        return isHigher;
    }
    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
    
    public Boolean getIsUsable() {
        return isUsable;
    }
    public void setIsUsable(Boolean isUsable) {
        this.isUsable = isUsable;
    }
    
    public Boolean getIsTimetable() {
        return isTimetable;
    }
    public void setIsTimetable(Boolean isTimetable) {
        this.isTimetable = isTimetable;
    }

    public Boolean getIsContact() {
        return isContact;
    }
    public void setIsContact(Boolean isContact) {
        this.isContact = isContact;
    }

    public List<SchoolCapacityTypeLoad> getTypeLoads() {
        return typeLoads;
    }
    public void setTypeLoads(List<SchoolCapacityTypeLoad> typeLoads) {
        this.typeLoads = typeLoads;
    }
    
}
