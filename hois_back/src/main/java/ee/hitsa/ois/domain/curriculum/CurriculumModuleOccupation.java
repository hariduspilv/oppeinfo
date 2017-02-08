package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;


@Entity
public class CurriculumModuleOccupation extends BaseEntityWithId {

    @ManyToOne(optional = false)
    private Classifier occupation;

    public CurriculumModuleOccupation() {
    }

    public CurriculumModuleOccupation(Classifier occupation) {
        this.occupation = occupation;
    }

    public Classifier getOccupation() {
        return occupation;
    }

    public void setOccupation(Classifier occupation) {
        this.occupation = occupation;
    }

}
