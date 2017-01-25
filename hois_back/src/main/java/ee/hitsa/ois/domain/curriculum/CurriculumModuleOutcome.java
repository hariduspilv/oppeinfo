package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
@Table(name = "curriculum_module_outcomes")
public class CurriculumModuleOutcome extends BaseEntityWithId {

    @Column(nullable = false)
	private String outcomeEt;

	private String outcomeEn;

    public String getOutcomeEt() {
        return outcomeEt;
    }

    public void setOutcomeEt(String outcomeEt) {
        this.outcomeEt = outcomeEt;
    }

    public String getOutcomeEn() {
        return outcomeEn;
    }

    public void setOutcomeEn(String outcomeEn) {
        this.outcomeEn = outcomeEn;
    }


}
