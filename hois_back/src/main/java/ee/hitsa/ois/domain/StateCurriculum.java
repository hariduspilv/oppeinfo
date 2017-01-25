package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/*
 * XXX: when changing list of StateCurriculumModuleOccupations
 * (may be caused by equals() and hashCode() in BaseEntityWithId):
 * Resolved exception caused by Handler execution: org.springframework.orm.ObjectOptimisticLockingFailureException: 
 * Object of class [ee.hitsa.ois.domain.StateCurriculumModule] with identifier [63]: optimistic locking failed; 
 * nested exception is org.hibernate.StaleObjectStateException: Row was updated or deleted by another transaction 
 * (or unsaved-value mapping was incorrect) : [ee.hitsa.ois.domain.StateCurriculumModule#63]
 * 
 * edit: error occurs only occasionally
 */

@Entity
public class StateCurriculum extends BaseEntityWithId {
	
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
	private Integer credits;
	private String practiceDescription;
	private Integer optionalStudyCredits;
	private LocalDate validFrom;
	private LocalDate validThru;

	private String description;
	private String riigiteatajaUrl;
	private String finalExamDescription;

	@Transient
	private String ekrLevel;

	@ManyToOne
	@JoinColumn(name = "status_code", referencedColumnName = "code")
	private Classifier status;

	@ManyToOne
	@JoinColumn(name = "isced_class_code", referencedColumnName = "code")
	private Classifier iscedClass;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "state_curr_class_code", referencedColumnName = "code")
	private Classifier stateCurrClass;
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "state_curriculum_id", nullable=false, updatable = false, insertable = true)
	private Set<StateCurriculumModule> modules = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "state_curriculum_id", nullable=false, updatable = false, insertable = true)
	private Set<StateCurriculumOccupation> occupations = new HashSet<>();

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

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEt(String nameEt) {
		this.nameEt = nameEt;
	}

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

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
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

	public Integer getOptionalStudyCredits() {
		return optionalStudyCredits;
	}

	public void setOptionalStudyCredits(Integer optionalStudyCredits) {
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
		return modules;
	}

	public void setModules(Set<StateCurriculumModule> modules) {
		this.getModules().clear();
		this.getModules().addAll(modules);
	}

	public Set<StateCurriculumOccupation> getOccupations() {
		return occupations;
	}
	
	public void setOccupations(Set<StateCurriculumOccupation> occupations) {
		this.getOccupations().clear();
		this.getOccupations().addAll(occupations);
	}

	@Override
	public String toString() {
		return "StateCurriculum [nameEt=" + nameEt + ", getId()=" + getId() + "]";
	}
}
