package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;


@Entity
public class CurriculumModuleCompetence extends BaseEntityWithId {

    @ManyToOne(optional = false)
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier competence;

    public Classifier getCompetence() {
        return competence;
    }

    public void setCompetence(Classifier competence) {
        this.competence = competence;
    }

}
