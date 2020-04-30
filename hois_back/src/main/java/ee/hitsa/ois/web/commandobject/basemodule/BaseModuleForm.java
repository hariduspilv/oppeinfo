package ee.hitsa.ois.web.commandobject.basemodule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleCapacityDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleOutcomesDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleThemeDto;

@DateRange
public class BaseModuleForm extends VersionedCommand {

    @NotBlank
    @Size(max = 255)
    private String nameEt;
    @Min(0)
    @Max(999)
    private BigDecimal credits;
    @Size(max = 255)
    private String nameEn;
    @NotBlank
    @Size(max = 10000)
    private String objectivesEt;
    @Size(max = 10000)
    private String objectivesEn;
    @NotBlank
    @Size(max = 20000)
    private String assessmentsEt;
    @Size(max = 20000)
    private String assessmentsEn;
    @NotBlank
    @Size(max = 20000)
    private String cvRequirementsEt;
    @NotBlank
    @Size(max = 20000)
    private String cvAssessmentsEt;
    @Size(max = 20000)
    private String cvLearningMethodsEt;
    @Size(max = 20000)
    private String cvAssessmentMethodsEt;
    @Size(max = 20000)
    private String cvIndependentStudyEt;
    @Size(max = 20000)
    private String cvStudyMaterials;
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMISVIIS)
    private String cvAssessment;
    @NotBlank
    @Size(max = 20000)
    private String cvTotalGradeDescription;
    @Size(max = 20000)
    private String cvPassDescription;
    @Size(max = 20000)
    private String cvGrade3Description;
    @Size(max = 20000)
    private String cvGrade4Description;
    @Size(max = 20000)
    private String cvGrade5Description;
    @NotNull
    private Teacher teacher;
    @NotNull
    private LocalDate validFrom;
    private LocalDate validThru;
    @Size(max = 255)
    private String addNameEt;

    @NotEmpty
    private Set<BaseModuleOutcomesDto> outcomes;
    private Set<BaseModuleCapacityDto> capacities;
    private Set<BaseModuleThemeDto> themes;

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getNameEn() {
        return nameEn;
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

    public String getCvAssessment() {
        return cvAssessment;
    }

    public void setCvAssessment(String cvAssessment) {
        this.cvAssessment = cvAssessment;
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

    public Set<BaseModuleOutcomesDto> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(Set<BaseModuleOutcomesDto> outcomes) {
        this.outcomes = outcomes;
    }

    public Set<BaseModuleCapacityDto> getCapacities() {
        return capacities;
    }

    public void setCapacities(Set<BaseModuleCapacityDto> capacities) {
        this.capacities = capacities;
    }

    public Set<BaseModuleThemeDto> getThemes() {
        return themes;
    }

    public void setThemes(Set<BaseModuleThemeDto> themes) {
        this.themes = themes;
    }

    public String getAddNameEt() {
        return addNameEt;
    }

    public void setAddNameEt(String addNameEt) {
        this.addNameEt = addNameEt;
    }

    public static class Teacher {

        @NotNull
        private Long id;
        private String nameEt;
        private String nameEn;
        private String nameRu;

        public static Teacher of(ee.hitsa.ois.domain.teacher.Teacher teacher) {
            Teacher dto = new Teacher();
            dto.setId(teacher.getId());
            dto.setNameEt(teacher.getPerson().getFullname());
            dto.setNameEn(teacher.getPerson().getFullname());
            dto.setNameRu(teacher.getPerson().getFullname());
            return dto;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getNameRu() {
            return nameRu;
        }

        public void setNameRu(String nameRu) {
            this.nameRu = nameRu;
        }
    }
}
