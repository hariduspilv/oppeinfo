package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
@Table(name = "curriculum_module_outcomes")
public class CurriculumModuleOutcome extends BaseEntityWithId {

	private String outcomeEt;
	private String outcomeEn;
	private Long orderNr;
	
    public Long getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
    }

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
