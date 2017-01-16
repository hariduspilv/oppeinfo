package ee.hitsa.ois.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;

@Entity
public class CurriculumModule extends BaseEntityWithId {

    @ManyToOne(optional = false)
	@JsonDeserialize(using = ClassifierJsonDeserializer.class)
	private Classifier module;

	@Column(nullable = false)
	private String nameEt;
	private String nameEn;

	@Column(nullable = false)
	private Integer credits;

	@Column(nullable = false)
	private String objectivesEt;
	private String objectivesEn;

	@Column(nullable = false)
	private String outcomesEt;
	private String outcomesEn;

	@Column(nullable = false, name="is_practice")
	private boolean practice;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false, insertable = true)
	private Set<CurriculumModuleOccupation> occupations = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_module_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumModuleCompetence> competences = new HashSet<>();

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

    public boolean isPractice() {
        return practice;
    }

    public void setPractice(boolean practice) {
        this.practice = practice;
    }

    public Set<CurriculumModuleOccupation> getOccupations() {
        return occupations;
    }

    public void setOccupations(Set<CurriculumModuleOccupation> occupations) {
        this.occupations = occupations;
    }

    public Set<CurriculumModuleCompetence> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<CurriculumModuleCompetence> competences) {
        this.competences = competences;
    }

}
