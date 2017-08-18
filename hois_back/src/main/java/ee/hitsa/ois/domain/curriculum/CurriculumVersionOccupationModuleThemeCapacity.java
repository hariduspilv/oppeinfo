package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
@Table(name = "curriculum_version_omodule_theme_capacity")
public class CurriculumVersionOccupationModuleThemeCapacity extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_theme_id", nullable = false, updatable = false, insertable = false)
    private CurriculumVersionOccupationModuleTheme theme;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier capacityType;

    @Column(nullable = false)
    private Short hours;

    @Column(name = "is_contact", nullable = false)
    private Boolean contact;

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

    public CurriculumVersionOccupationModuleTheme getTheme() {
        return theme;
    }

    public void setTheme(CurriculumVersionOccupationModuleTheme theme) {
        this.theme = theme;
    }

}
