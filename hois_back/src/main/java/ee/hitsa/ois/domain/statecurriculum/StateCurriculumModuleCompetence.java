package ee.hitsa.ois.domain.statecurriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class StateCurriculumModuleCompetence extends BaseEntityWithId {
    
    private static final long serialVersionUID = 5445448032122847379L;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_curriculum_module_id", nullable = false, updatable = false)
    private StateCurriculumModule module;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier competence;
    
    private String description;

    public Classifier getCompetence() {
        return competence;
    }

    public void setCompetence(Classifier competence) {
        this.competence = competence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setModule(StateCurriculumModule module) {
        this.module = module;
    }

    public StateCurriculumModule getModule() {
        return module;
    }

}
