package ee.hitsa.ois.domain.basemodule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class BaseModuleCapacity extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BaseModule baseModule;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier capacityType;
    @Column(nullable = false)
    private Short hours;
    @Column(name = "is_contact", nullable = false)
    private Boolean contact;
    
    public BaseModule getBaseModule() {
        return baseModule;
    }
    public void setBaseModule(BaseModule baseModule) {
        this.baseModule = baseModule;
    }
    public Classifier getCapacityType() {
        return capacityType;
    }
    public void setCapacityType(Classifier capacityType) {
        this.capacityType = capacityType;
    }
    public Short getHours() {
        return hours;
    }
    public void setHours(Short hours) {
        this.hours = hours;
    }
    public Boolean getContact() {
        return contact;
    }
    public void setContact(Boolean contact) {
        this.contact = contact;
    }
}
