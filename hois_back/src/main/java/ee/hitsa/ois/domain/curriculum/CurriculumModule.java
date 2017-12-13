package ee.hitsa.ois.domain.curriculum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.util.Translatable;

@Entity
public class CurriculumModule extends BaseEntityWithId implements Translatable {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Curriculum curriculum;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Classifier module;

	@Column(nullable = false)
	private String nameEt;
	private String nameEn;

	@Column(nullable = false)
	private BigDecimal credits;

	@Column(nullable = false)
	private String objectivesEt;
	private String objectivesEn;
	
	private String assessmentsEt;
	private String assessmentsEn;

	@Column(nullable = false, name="is_practice")
	private Boolean practice;
	
	private Boolean isAdditional;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false)
	private Set<CurriculumModuleOccupation> occupations = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false)
    private Set<CurriculumModuleCompetence> competences = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false)
    private Set<CurriculumModuleOutcome> outcomes = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false, insertable = false)
    private Set<CurriculumVersionOccupationModule> curriculumVersionOccupationModules = new HashSet<>();


    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public Classifier getModule() {
        return module;
    }

    public void setModule(Classifier module) {
        this.module = module;
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    @Override
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

    public Boolean getPractice() {
        return practice;
    }

    public void setPractice(Boolean practice) {
        this.practice = practice;
    }

    public Set<CurriculumModuleOccupation> getOccupations() {
        return occupations != null ? occupations : (occupations = new HashSet<>());
    }

    public void setOccupations(Set<CurriculumModuleOccupation> occupations) {
        getOccupations().clear();
        getOccupations().addAll(occupations);
    }

    public Set<CurriculumModuleCompetence> getCompetences() {
        return competences != null ? competences : (competences = new HashSet<>());
    }

    public void setCompetences(Set<CurriculumModuleCompetence> competences) {
        getCompetences().clear();
        getCompetences().addAll(competences);
    }

    public Set<CurriculumModuleOutcome> getOutcomes() {
        return outcomes != null ? outcomes : (outcomes = new HashSet<>());
    }

    public void setOutcomes(Set<CurriculumModuleOutcome> outcomes) {
        getOutcomes().clear();
        getOutcomes().addAll(outcomes);
    }

    public Set<CurriculumVersionOccupationModule> getCurriculumVersionOccupationModules() {
        return curriculumVersionOccupationModules;
    }

    public void setCurriculumVersionOccupationModules(
            Set<CurriculumVersionOccupationModule> curriculumVersionOccupationModules) {
        this.curriculumVersionOccupationModules = curriculumVersionOccupationModules;
    }

    public Boolean getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(Boolean isAdditional) {
        this.isAdditional = isAdditional;
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
}
