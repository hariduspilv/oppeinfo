package ee.hitsa.ois.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class StateCurriculumModule extends BaseEntityWithId {
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "module_code", referencedColumnName = "code")
	private Classifier moduleType;	
	private String nameEt;
	private String nameEn;
	private Integer credits;
	private String objectivesEt;
	private String objectivesEn;
	private String assessmentsEt;
	private String assessmentsEn;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "state_curriculum_module_id", nullable=false, updatable = false, insertable = true)
	private Set<StateCurriculumModuleOccupation> moduleOccupations = new HashSet<>();

	@JsonManagedReference
	@OneToOne(mappedBy = "module", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true)
	private StateCurriculumModuleOutcome outcome;

	public StateCurriculumModule() {
	}

	public Set<StateCurriculumModuleOccupation> getModuleOccupations() {
		return moduleOccupations;
	}

	public void setModuleOccupations(Set<StateCurriculumModuleOccupation> moduleOccupations) {
		this.getModuleOccupations().clear();
		this.getModuleOccupations().addAll(moduleOccupations);
	}
	
	public StateCurriculumModuleOutcome getOutcome() {
		return outcome;
	}

	public void setOutcome(StateCurriculumModuleOutcome outcome) {
		this.outcome = outcome;
		outcome.setModule(this);
	}

	public Classifier getModuleType() {
		return moduleType;
	}

	public void setModuleType(Classifier moduleType) {
		this.moduleType = moduleType;
	}
	
	public void setNameEt(String nameEt) {
		this.nameEt = nameEt;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public String getObjectivesEt() {
		return objectivesEt;
	}

	public void setObjectivesEt(String objectivesEt) {
		this.objectivesEt = objectivesEt;
	}

	public String getObjectivesEn() {
		return objectivesEn;
	}

	public void setObjectivesEn(String objectivesEn) {
		this.objectivesEn = objectivesEn;
	}

	public String getAssessmentsEt() {
		return assessmentsEt;
	}

	public void setAssessmentsEt(String assessmentsEt) {
		this.assessmentsEt = assessmentsEt;
	}

	public String getAssessmentsEn() {
		return assessmentsEn;
	}

	public void setAssessmentsEn(String assessmentsEn) {
		this.assessmentsEn = assessmentsEn;
	}

	public String getNameEt() {
		return nameEt;
	}
}
