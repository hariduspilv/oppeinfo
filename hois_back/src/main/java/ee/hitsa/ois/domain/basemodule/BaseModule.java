package ee.hitsa.ois.domain.basemodule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.util.Translatable;

@Entity
public class BaseModule extends BaseEntityWithId implements Translatable {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;
    @Column(nullable = false)
    private String nameEt;
    private String nameEn;
    @Column(nullable = false)
    private BigDecimal credits;
    @Column(nullable = false)
    private String objectivesEt;
    private String objectivesEn;
    @Column(nullable = false)
    private String assessmentsEt;
    private String assessmentsEn;
    @Column(nullable = false)
    private String cvRequirementsEt;
    @Column(nullable = false)
    private String cvAssessmentsEt;
    private String cvLearningMethodsEt;
    private String cvAssessmentMethodsEt;
    private String cvIndependentStudyEt;
    private String cvStudyMaterials;
    @Column(nullable = false)
    private String cvTotalGradeDescription;
    private String cvPassDescription;
    @Column(name = "cv_grade3_description")
    private String cvGrade3Description;
    @Column(name = "cv_grade4_description")
    private String cvGrade4Description;
    @Column(name = "cv_grade5_description")
    private String cvGrade5Description;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier cvAssessment;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Teacher teacher;
    @Column(nullable = false)
    private LocalDate validFrom;
    private LocalDate validThru;
    private String addNameEt;

    @OneToMany(mappedBy = "baseModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BaseModuleCapacity> capacities = new HashSet<>();
    @OneToMany(mappedBy = "baseModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BaseModuleTheme> themes = new HashSet<>();
    @OneToMany(mappedBy = "baseModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BaseModuleOutcomes> outcomes = new HashSet<>();
    @OneToMany(mappedBy = "baseModule")
    private Set<CurriculumVersionOccupationModule> curriculumVersionOModules = new HashSet<>();
    @OneToMany(mappedBy = "baseModule")
    private Set<CurriculumModule> curriculumModules = new HashSet<>();

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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

    public String getCvRequirementsEt() {
        return cvRequirementsEt;
    }

    public void setCvRequirementsEt(String cvRequirementsEt) {
        this.cvRequirementsEt = cvRequirementsEt;
    }

    public String getCvAssessmentsEt() {
        return cvAssessmentsEt;
    }

    public void setCvAssessmentsEt(String cvAssessmentsEt) {
        this.cvAssessmentsEt = cvAssessmentsEt;
    }

    public String getCvLearningMethodsEt() {
        return cvLearningMethodsEt;
    }

    public void setCvLearningMethodsEt(String cvLearningMethodsEt) {
        this.cvLearningMethodsEt = cvLearningMethodsEt;
    }

    public String getCvAssessmentMethodsEt() {
        return cvAssessmentMethodsEt;
    }

    public void setCvAssessmentMethodsEt(String cvAssessmentMethodsEt) {
        this.cvAssessmentMethodsEt = cvAssessmentMethodsEt;
    }

    public String getCvIndependentStudyEt() {
        return cvIndependentStudyEt;
    }

    public void setCvIndependentStudyEt(String cvIndependentStudyEt) {
        this.cvIndependentStudyEt = cvIndependentStudyEt;
    }

    public String getCvStudyMaterials() {
        return cvStudyMaterials;
    }

    public void setCvStudyMaterials(String cvStudyMaterials) {
        this.cvStudyMaterials = cvStudyMaterials;
    }

    public String getCvTotalGradeDescription() {
        return cvTotalGradeDescription;
    }

    public void setCvTotalGradeDescription(String cvTotalGradeDescription) {
        this.cvTotalGradeDescription = cvTotalGradeDescription;
    }

    public String getCvPassDescription() {
        return cvPassDescription;
    }

    public void setCvPassDescription(String cvPassDescription) {
        this.cvPassDescription = cvPassDescription;
    }

    public String getCvGrade3Description() {
        return cvGrade3Description;
    }

    public void setCvGrade3Description(String cvGrade3Description) {
        this.cvGrade3Description = cvGrade3Description;
    }

    public String getCvGrade4Description() {
        return cvGrade4Description;
    }

    public void setCvGrade4Description(String cvGrade4Description) {
        this.cvGrade4Description = cvGrade4Description;
    }

    public String getCvGrade5Description() {
        return cvGrade5Description;
    }

    public void setCvGrade5Description(String cvGrade5Description) {
        this.cvGrade5Description = cvGrade5Description;
    }

    public Classifier getCvAssessment() {
        return cvAssessment;
    }

    public void setCvAssessment(Classifier cvAssessment) {
        this.cvAssessment = cvAssessment;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    public String getAddNameEt() {
        return addNameEt;
    }

    public void setAddNameEt(String addNameEt) {
        this.addNameEt = addNameEt;
    }

    public Set<BaseModuleCapacity> getCapacities() {
        return capacities != null ? capacities : (capacities = new HashSet<>());
    }

    public void setCapacities(Set<BaseModuleCapacity> capacities) {
        getCapacities().clear();
        getCapacities().addAll(capacities);
    }

    public Set<BaseModuleTheme> getThemes() {
        return themes != null ? themes : (themes = new HashSet<>());
    }

    public void setThemes(Set<BaseModuleTheme> themes) {
        getThemes().clear();
        getThemes().addAll(themes);
    }

    public Set<BaseModuleOutcomes> getOutcomes() {
        return outcomes != null ? outcomes : (outcomes = new HashSet<>());
    }

    public void setOutcomes(Set<BaseModuleOutcomes> outcomes) {
        getOutcomes().clear();
        getOutcomes().addAll(outcomes);
    }

    public Set<CurriculumVersionOccupationModule> getCurriculumVersionOModules() {
        return curriculumVersionOModules != null ? curriculumVersionOModules
                : (curriculumVersionOModules = new HashSet<>());
    }

    public void setCurriculumVersionOModules(Set<CurriculumVersionOccupationModule> curriculumVersionOModules) {
        getCurriculumVersionOModules().clear();
        getCurriculumVersionOModules().addAll(curriculumVersionOModules);
    }

    public Set<CurriculumModule> getCurriculumModules() {
        return curriculumModules != null ? curriculumModules : (curriculumModules = new HashSet<>());
    }

    public void setCurriculumModules(Set<CurriculumModule> curriculumModules) {
        getCurriculumModules().clear();
        getCurriculumModules().addAll(curriculumModules);
    }
}
