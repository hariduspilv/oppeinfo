package ee.hitsa.ois.domain.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
@Table(name = "curriculum_version_omodule")
public class CurriculumVersionOccupationModule extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumModule curriculumModule;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private CurriculumVersion curriculumVersion;

    @Column(nullable = false)
    private String requirementsEt;

    @Column(nullable = false)
    private String assessmentsEt;
    private String learningMethodsEt;
    private String assessmentMethodsEt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier assessment;

    @Column(nullable = false)
    private String totalGradeDescription;
    private String passDescription;

    @Column(name = "grade3_description")
    private String grade3Description;

    @Column(name = "grade4_description")
    private String grade4Description;

    @Column(name = "grade5_description")
    private String grade5Description;
    private String independentStudyEt;
    private String studyMaterials;

    @Column(nullable = false)
    private String supervisor;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false, updatable = false)
    private Set<CurriculumVersionOccupationModuleCapacity> capacities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false, updatable = false)
    private Set<CurriculumVersionOccupationModuleTheme> themes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false, updatable = false)
    private Set<CurriculumVersionOccupationModuleYearCapacity> yearCapacities = new HashSet<>();

    public String getRequirementsEt() {
        return requirementsEt;
    }

    public void setRequirementsEt(String requirementsEt) {
        this.requirementsEt = requirementsEt;
    }

    public String getAssessmentsEt() {
        return assessmentsEt;
    }

    public void setAssessmentsEt(String assessmentsEt) {
        this.assessmentsEt = assessmentsEt;
    }

    public String getLearningMethodsEt() {
        return learningMethodsEt;
    }

    public void setLearningMethodsEt(String learningMethodsEt) {
        this.learningMethodsEt = learningMethodsEt;
    }

    public String getAssessmentMethodsEt() {
        return assessmentMethodsEt;
    }

    public void setAssessmentMethodsEt(String assessmentMethodsEt) {
        this.assessmentMethodsEt = assessmentMethodsEt;
    }

    public Classifier getAssessment() {
        return assessment;
    }

    public void setAssessment(Classifier assessment) {
        this.assessment = assessment;
    }

    public String getTotalGradeDescription() {
        return totalGradeDescription;
    }

    public void setTotalGradeDescription(String totalGradeDescription) {
        this.totalGradeDescription = totalGradeDescription;
    }

    public String getPassDescription() {
        return passDescription;
    }

    public void setPassDescription(String passDescription) {
        this.passDescription = passDescription;
    }

    public String getGrade3Description() {
        return grade3Description;
    }

    public void setGrade3Description(String grade3Description) {
        this.grade3Description = grade3Description;
    }

    public String getGrade4Description() {
        return grade4Description;
    }

    public void setGrade4Description(String grade4Description) {
        this.grade4Description = grade4Description;
    }

    public String getGrade5Description() {
        return grade5Description;
    }

    public void setGrade5Description(String grade5Description) {
        this.grade5Description = grade5Description;
    }

    public String getIndependentStudyEt() {
        return independentStudyEt;
    }

    public void setIndependentStudyEt(String independentStudyEt) {
        this.independentStudyEt = independentStudyEt;
    }

    public String getStudyMaterials() {
        return studyMaterials;
    }

    public void setStudyMaterials(String studyMaterials) {
        this.studyMaterials = studyMaterials;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public Set<CurriculumVersionOccupationModuleCapacity> getCapacities() {
        return capacities;
    }

    public void setCapacities(Set<CurriculumVersionOccupationModuleCapacity> capacities) {
        getCapacities().clear();
        getCapacities().addAll(capacities);
    }

    public Set<CurriculumVersionOccupationModuleTheme> getThemes() {
        return themes;
    }

    public void setThemes(Set<CurriculumVersionOccupationModuleTheme> themes) {
        getThemes().clear();
        getThemes().addAll(themes);
    }

    public Set<CurriculumVersionOccupationModuleYearCapacity> getYearCapacities() {
        return yearCapacities;
    }

    public void setYearCapacities(Set<CurriculumVersionOccupationModuleYearCapacity> yearCapacities) {
        getYearCapacities().clear();
        getYearCapacities().addAll(yearCapacities);
    }

    public CurriculumModule getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(CurriculumModule curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

}
