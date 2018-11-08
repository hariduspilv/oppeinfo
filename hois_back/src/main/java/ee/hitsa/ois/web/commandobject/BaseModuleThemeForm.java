package ee.hitsa.ois.web.commandobject;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleOutcomesDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleThemeCapacityDto;

public class BaseModuleThemeForm extends VersionedCommand {
    
    @NotNull
    private BaseModuleDto baseModule;
    
    @NotBlank
    @Size(max=255)
    private String nameEt;
    
    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal credits;
    
    @NotNull
    @Min(0)
    @Max(10000)
    private Short hours;
    
    @Size(max=10000)
    private String subthemes;
    @Size(max=10000)
    private String totalGradeDescription;
    @Size(max=10000)
    private String passDescription;
    @Size(max=10000)
    private String grade3Description;
    @Size(max=10000)
    private String grade4Description;
    @Size(max=10000)
    private String grade5Description;
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMISVIIS)
    private String assessment;
    
    private Set<BaseModuleThemeCapacityDto> capacities;
    private Set<BaseModuleOutcomesDto> outcomes;
    
    public BaseModuleDto getBaseModule() {
        return baseModule;
    }
    public void setBaseModule(BaseModuleDto baseModule) {
        this.baseModule = baseModule;
    }
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
    public Short getHours() {
        return hours;
    }
    public void setHours(Short hours) {
        this.hours = hours;
    }
    public String getSubthemes() {
        return subthemes;
    }
    public void setSubthemes(String subthemes) {
        this.subthemes = subthemes;
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
    public String getAssessment() {
        return assessment;
    }
    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
    public Set<BaseModuleThemeCapacityDto> getCapacities() {
        return capacities;
    }
    public void setCapacities(Set<BaseModuleThemeCapacityDto> capacities) {
        this.capacities = capacities;
    }
    public Set<BaseModuleOutcomesDto> getOutcomes() {
        return outcomes;
    }
    public void setOutcomes(Set<BaseModuleOutcomesDto> outcomes) {
        this.outcomes = outcomes;
    }
}
