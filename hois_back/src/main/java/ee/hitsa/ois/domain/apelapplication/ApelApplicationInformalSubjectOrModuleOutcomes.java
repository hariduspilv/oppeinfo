package ee.hitsa.ois.domain.apelapplication;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;

@Entity
public class ApelApplicationInformalSubjectOrModuleOutcomes extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumModuleOutcome curriculumModuleOutcomes;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplicationInformalSubjectOrModule apelApplicationInformalSubjectOrModule;

    public CurriculumModuleOutcome getCurriculumModuleOutcomes() {
        return curriculumModuleOutcomes;
    }

    public void setCurriculumModuleOutcomes(CurriculumModuleOutcome curriculumModuleOutcomes) {
        this.curriculumModuleOutcomes = curriculumModuleOutcomes;
    }

    public ApelApplicationInformalSubjectOrModule getApelApplicationInformalSubjectOrModule() {
        return apelApplicationInformalSubjectOrModule;
    }

    public void setApelApplicationInformalSubjectOrModule(
            ApelApplicationInformalSubjectOrModule apelApplicationInformalSubjectOrModule) {
        this.apelApplicationInformalSubjectOrModule = apelApplicationInformalSubjectOrModule;
    }
    
}
