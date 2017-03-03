package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;


@Entity
public class CurriculumModuleCompetence extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier competence;

    public CurriculumModuleCompetence() {
    }


    public CurriculumModuleCompetence(Classifier c) {
        this.competence = c;
    }

    public Classifier getCompetence() {
        return competence;
    }

    public void setCompetence(Classifier competence) {
        this.competence = competence;
    }

}
