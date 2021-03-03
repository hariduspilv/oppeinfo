package ee.hitsa.ois.domain.statecurriculum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.util.Translatable;

@Entity
public class StateCurriculum extends BaseEntityWithId implements Translatable {
	
	private static final long serialVersionUID = 1457162479961880680L;
	
	private String nameEt;
	private String nameEn;
	private String objectivesEt;
	private String objectivesEn;
	private String outcomesEt;
	private String outcomesEn;
	private String admissionRequirementsEt;
	private String admissionRequirementsEn;
	private String graduationRequirementsEt;
	private String graduationRequirementsEn;
	private BigDecimal credits;
	private String practiceDescription;
	private BigDecimal optionalStudyCredits;
	private LocalDate validFrom;
	private LocalDate validThru;

	private String description;
	private String riigiteatajaUrl;
	private String finalExamDescription;
	private Long courses;
	private Boolean isVocational;

	@Transient
	private String ekrLevel;

	@ManyToOne(fetch = FetchType.LAZY)
	private Classifier status;
	
	// in secondary school used to determine EKR level
	@ManyToOne(fetch = FetchType.LAZY)
	private Classifier iscedClass;
	
	// in secondary school used to determine if its basic or highschool curriculum
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Classifier stateCurrClass;
	
	@OneToMany(mappedBy = "stateCurriculum", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<StateCurriculumModule> modules = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "state_curriculum_id", nullable=false, updatable = false)
	private Set<StateCurriculumOccupation> occupations = new HashSet<>();
	
    @OneToMany(mappedBy = "stateCurriculum")
    private Set<Curriculum> curricula;

    public Set<Curriculum> getCurricula() {
        return curricula != null ? curricula : (curricula = new HashSet<>());
    }

    public void setCurricula(Set<Curriculum> curricula) {
        this.curricula = curricula;
    }

    public String getEkrLevel() {
		return ekrLevel;
	}

	public void setEkrLevel(String ekrLevel) {
		this.ekrLevel = ekrLevel;
	}

	public Classifier getIscedClass() {
		return iscedClass;
	}

	public void setIscedClass(Classifier iscedClass) {
		this.iscedClass = iscedClass;
	}

	public Classifier getStatus() {
		return status;
	}

	public void setStatus(Classifier status) {
		this.status = status;
	}

	public Classifier getStateCurrClass() {
		return stateCurrClass;
	}

	public void setStateCurrClass(Classifier stateCurrClass) {
		this.stateCurrClass = stateCurrClass;
	}

	@Override
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEt(String nameEt) {
		this.nameEt = nameEt;
	}

	@Override
	public String getNameEt() {
		return nameEt;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
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

	public String getAdmissionRequirementsEt() {
		return admissionRequirementsEt;
	}

	public void setAdmissionRequirementsEt(String admissionRequirementsEt) {
		this.admissionRequirementsEt = admissionRequirementsEt;
	}

	public String getAdmissionRequirementsEn() {
		return admissionRequirementsEn;
	}

	public void setAdmissionRequirementsEn(String admissionRequirementsEn) {
		this.admissionRequirementsEn = admissionRequirementsEn;
	}

	public String getGraduationRequirementsEt() {
		return graduationRequirementsEt;
	}

	public void setGraduationRequirementsEt(String graduationRequirementsEt) {
		this.graduationRequirementsEt = graduationRequirementsEt;
	}

	public String getGraduationRequirementsEn() {
		return graduationRequirementsEn;
	}

	public void setGraduationRequirementsEn(String graduationRequirementsEn) {
		this.graduationRequirementsEn = graduationRequirementsEn;
	}

	public BigDecimal getCredits() {
		return credits;
	}

	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}

	public String getPracticeDescription() {
		return practiceDescription;
	}

	public void setPracticeDescription(String practiceDescription) {
		this.practiceDescription = practiceDescription;
	}

	public String getFinalExamDescription() {
		return finalExamDescription;
	}

	public void setFinalExamDescription(String finalExamDescription) {
		this.finalExamDescription = finalExamDescription;
	}

	public BigDecimal getOptionalStudyCredits() {
		return optionalStudyCredits;
	}

	public void setOptionalStudyCredits(BigDecimal optionalStudyCredits) {
		this.optionalStudyCredits = optionalStudyCredits;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidThru() {
		return validThru;
	}

	public void setValidThru(LocalDate validThru) {
		this.validThru = validThru;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRiigiteatajaUrl() {
		return riigiteatajaUrl;
	}

	public void setRiigiteatajaUrl(String riigiteatajaUrl) {
		this.riigiteatajaUrl = riigiteatajaUrl;
	}

	public Set<StateCurriculumModule> getModules() {
		return modules != null ? modules : (modules = new HashSet<>());
	}

	public void setModules(Set<StateCurriculumModule> modules) {
		this.getModules().clear();
		this.getModules().addAll(modules);
	}

	public Set<StateCurriculumOccupation> getOccupations() {
		return occupations != null ? occupations : (occupations = new HashSet<>());
	}
	
	public void setOccupations(Set<StateCurriculumOccupation> occupations) {
		this.getOccupations().clear();
		this.getOccupations().addAll(occupations);
	}

    public Long getCourses() {
        return courses;
    }

    public void setCourses(Long courses) {
        this.courses = courses;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
