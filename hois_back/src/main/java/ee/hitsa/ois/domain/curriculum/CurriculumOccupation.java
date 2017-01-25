package ee.hitsa.ois.domain.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;
import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class CurriculumOccupation extends BaseEntityWithId {

    @ManyToOne(optional = false)
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier occupation;

    @Column(name = "is_occupation_grant", nullable = false)
    private Boolean occupationGrant = Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_occupation_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumOccupationSpeciality> specialities = new HashSet<>();

    public Classifier getOccupation() {
        return occupation;
    }

    public void setOccupation(Classifier occupation) {
        this.occupation = occupation;
    }

    public Boolean getOccupationGrant() {
        return occupationGrant;
    }

    public void setOccupationGrant(Boolean occupationGrant) {
        this.occupationGrant = occupationGrant;
    }

    public Set<CurriculumOccupationSpeciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<CurriculumOccupationSpeciality> specialities) {
        this.specialities = specialities;
    }
}
