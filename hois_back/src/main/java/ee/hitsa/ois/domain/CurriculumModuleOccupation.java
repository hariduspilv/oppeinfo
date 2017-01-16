package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;


@Entity
public class CurriculumModuleOccupation extends BaseEntityWithId {

    @ManyToOne(optional = false)
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier occupation;

    public Classifier getOccupation() {
        return occupation;
    }

    public void setOccupation(Classifier occupation) {
        this.occupation = occupation;
    }

}
