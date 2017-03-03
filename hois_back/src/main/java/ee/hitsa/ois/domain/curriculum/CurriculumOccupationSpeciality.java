package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class CurriculumOccupationSpeciality  extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier speciality;

    public CurriculumOccupationSpeciality() {
    }

    public CurriculumOccupationSpeciality(Classifier c) {
        this.setSpeciality(c);
    }

    public Classifier getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Classifier speciality) {
        this.speciality = speciality;
    }

}
