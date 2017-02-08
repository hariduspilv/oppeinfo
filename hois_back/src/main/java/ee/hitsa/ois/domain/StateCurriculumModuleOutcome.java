package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "state_curriculum_module_outcomes")
public class StateCurriculumModuleOutcome extends BaseEntityWithId {
	
	private String outcomesEt;
	private String outcomesEn;
	
	@OneToOne
	@JoinColumn(name = "state_curriculum_module_id", nullable=false, updatable = false, insertable = true)
	@JsonBackReference
	private StateCurriculumModule module;

	public StateCurriculumModuleOutcome() {
	}

	public StateCurriculumModule getModule() {
		return module;
	}

	public void setModule(StateCurriculumModule module) {
		this.module = module;
	}

	public String getOutcomesEt() {
		return outcomesEt;
	}

	public void setOutcomesEt(String outcomesEt) {
		this.outcomesEt = outcomesEt;
	}

	public String getOutcomesEn() {
		return outcomesEn;
	}

	public void setOutcomesEn(String outcomesEn) {
		this.outcomesEn = outcomesEn;
	}
}
