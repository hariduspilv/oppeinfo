package ee.hitsa.ois.domain.statecurriculum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class StateCurriculumModule extends BaseEntityWithId {
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Classifier module;	
	private String nameEt;
	private String nameEn;
	private BigDecimal credits;
	private String objectivesEt;
	private String objectivesEn;
	private String assessmentsEt;
	private String assessmentsEn;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "state_curriculum_module_id", nullable=false, updatable = false)
	private Set<StateCurriculumModuleOccupation> moduleOccupations = new HashSet<>();

	@JsonManagedReference
	@OneToOne(mappedBy = "module", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval=true)
	private StateCurriculumModuleOutcome outcome;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
	private StateCurriculum stateCurriculum;

	public StateCurriculum getStateCurriculum() {
        return stateCurriculum;
    }

    public void setStateCurriculum(StateCurriculum stateCurriculum) {
        this.stateCurriculum = stateCurriculum;
    }

    public StateCurriculumModule() {
	}

	public Set<StateCurriculumModuleOccupation> getModuleOccupations() {
		return moduleOccupations != null ? moduleOccupations : (moduleOccupations = new HashSet<>());
	}

	public void setModuleOccupations(Set<StateCurriculumModuleOccupation> moduleOccupations) {
		this.getModuleOccupations().clear();
		this.getModuleOccupations().addAll(moduleOccupations);
	}
	
	public StateCurriculumModuleOutcome getOutcome() {
		return outcome != null ? outcome : (outcome = new StateCurriculumModuleOutcome());
	}

	public void setOutcome(StateCurriculumModuleOutcome outcome) {
		this.outcome = outcome;
		outcome.setModule(this);
	}

	public Classifier getModule() {
		return module;
	}

	public void setModule(Classifier module) {
		this.module = module;
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

	public BigDecimal getCredits() {
		return credits;
	}

	public void setCredits(BigDecimal credits) {
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
