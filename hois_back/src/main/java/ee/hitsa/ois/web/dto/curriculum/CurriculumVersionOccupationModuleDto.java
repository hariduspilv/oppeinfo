package ee.hitsa.ois.web.dto.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionOccupationModuleDto extends VersionedCommand {

    @NotNull
    @Min(1)
    private Long curriculumModule;

    private Long id;

    @NotBlank
    private String requirementsEt;

    @NotBlank
    private String assessmentsEt;
    private String learningMethodsEt;
    private String assessmentMethodsEt;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMISVIIS)
    private String assessment;

    @NotBlank
    private String totalGradeDescription;
    private String passDescription;
    private String grade3Description;
    private String grade4Description;
    private String grade5Description;
    private String independentStudyEt;
    private String studyMaterials;

    @NotBlank
    @Size(max = 100)
    private String supervisor;

    @Valid
    private Set<CurriculumVersionOccupationModuleCapacityDto> capacities;
    @Valid
    private Set<CurriculumVersionOccupationModuleThemeDto> themes;
    @Valid
    private Set<CurriculumVersionOccupationModuleYearCapacityDto> yearCapacities;

    public static CurriculumVersionOccupationModuleDto of(CurriculumVersionOccupationModule module) {
        CurriculumVersionOccupationModuleDto dto = EntityUtil.bindToDto(module, new CurriculumVersionOccupationModuleDto(),
                "curriculumModule", "capacities", "themes", "yearCapacities");

        dto.setCurriculumModule(EntityUtil.getId(module.getCurriculumModule()));
        dto.setCapacities(StreamUtil.toMappedSet(CurriculumVersionOccupationModuleCapacityDto::of, module.getCapacities()));
        dto.setThemes(StreamUtil.toMappedSet(CurriculumVersionOccupationModuleThemeDto::of, module.getThemes()));
        dto.setYearCapacities(StreamUtil.toMappedSet(CurriculumVersionOccupationModuleYearCapacityDto::of, module.getYearCapacities()));
 
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
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

    public Long getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(Long curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public Set<CurriculumVersionOccupationModuleCapacityDto> getCapacities() {
        return capacities != null ? capacities : (capacities = new HashSet<>());
    }

    public void setCapacities(Set<CurriculumVersionOccupationModuleCapacityDto> capacities) {
        this.capacities = capacities;
    }

    public Set<CurriculumVersionOccupationModuleThemeDto> getThemes() {
        return themes != null ? themes : (themes = new HashSet<>());
    }

    public void setThemes(Set<CurriculumVersionOccupationModuleThemeDto> themes) {
        this.themes = themes;
    }

    public Set<CurriculumVersionOccupationModuleYearCapacityDto> getYearCapacities() {
        return yearCapacities != null ? yearCapacities : (yearCapacities = new HashSet<>());
    }

    public void setYearCapacities(Set<CurriculumVersionOccupationModuleYearCapacityDto> yearCapacities) {
        this.yearCapacities = yearCapacities;
    }
}
