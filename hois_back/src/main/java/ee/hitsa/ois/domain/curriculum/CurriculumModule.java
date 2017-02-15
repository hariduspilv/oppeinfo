package ee.hitsa.ois.domain.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class CurriculumModule extends BaseEntityWithId {

    @ManyToOne(optional = false)
	private Classifier module;

	@Column(nullable = false)
	private String nameEt;
	private String nameEn;

	@Column(nullable = false)
	private Integer credits;

	@Column(nullable = false)
	private String objectivesEt;
	private String objectivesEn;

	@Column(nullable = false, name="is_practice")
	private Boolean practice;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false, insertable = true)
	private Set<CurriculumModuleOccupation> occupations = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumModuleCompetence> competences = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumModuleOutcome> outcomes = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false, insertable = false)
    private Set<CurriculumVersionOccupationModule> curriculumVersionOccupationModules = new HashSet<>();

    public Classifier getModule() {
        return module;
    }

    public void setModule(Classifier module) {
        this.module = module;
    }

    public String getNameEt() {
        return nameEt;
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

}
