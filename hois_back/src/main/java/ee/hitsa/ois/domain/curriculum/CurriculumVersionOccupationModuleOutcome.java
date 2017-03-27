package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
@Table(name="curriculum_version_omodule_outcomes")
public class CurriculumVersionOccupationModuleOutcome  extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_module_outcomes_id", nullable = false, updatable = false)
    private CurriculumModuleOutcome outcome;

    public CurriculumModuleOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(CurriculumModuleOutcome outcome) {
        this.outcome = outcome;
    }

}
