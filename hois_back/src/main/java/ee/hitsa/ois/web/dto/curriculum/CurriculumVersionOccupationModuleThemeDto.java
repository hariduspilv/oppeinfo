package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleOutcome;
import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionOccupationModuleThemeDto extends VersionedCommand {

    private Long id;

    @NotBlank
    private String nameEt;
    @NotNull
    private Long module;

    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal credits;

    @NotNull
    @Min(0)
    @Max(10000)
    private Short hours;

    @Min(0)
    @Max(999)
    private BigDecimal proportion;
    @Size(max=10000)
    private String subthemes;
    @Min(0)
    @Max(10000)
    private Short studyYearNumber;

    @ClassifierRestriction(MainClassCode.KUTSEHINDAMISVIIS)
    private String assessment;
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
    private Boolean moduleOutcomes;

    private Set<Long> outcomes;
    @Valid
    private Set<CurriculumVersionOccupationModuleThemeCapacityDto> capacities;
    
    // used for curriculum fulfillment
    private Boolean otherCurriculumVersionModuleTheme;
    private Set<CurriculumModuleOutcomeDto> curriculumModuleOutcomes;

    public static CurriculumVersionOccupationModuleThemeDto of(CurriculumVersionOccupationModuleTheme theme) {
        CurriculumVersionOccupationModuleThemeDto dto = EntityUtil.bindToDto(theme, new CurriculumVersionOccupationModuleThemeDto(),
                "capacities", "outcomes");

        dto.setCapacities(StreamUtil.toMappedSet(CurriculumVersionOccupationModuleThemeCapacityDto::of, theme.getCapacities()));
        dto.setOutcomes(StreamUtil.toMappedSet(o -> EntityUtil.getId(o.getOutcome()), theme.getOutcomes()));
        dto.setModule(EntityUtil.getId(theme.getModule()));
        return dto;
    }

    public static CurriculumVersionOccupationModuleThemeDto forApelApplicationForm(CurriculumVersionOccupationModuleTheme theme) {
        CurriculumVersionOccupationModuleThemeDto dto = new CurriculumVersionOccupationModuleThemeDto();
        dto.setId(theme.getId());
        dto.setNameEt(theme.getNameEt());
        dto.setCredits(theme.getCredits());
        dto.setHours(theme.getHours());
        dto.setOutcomes(StreamUtil.toMappedSet(o -> EntityUtil.getId(o.getOutcome()), theme.getOutcomes()));
        dto.setModule(EntityUtil.getId(theme.getModule()));
        return dto;
    }

    public static CurriculumVersionOccupationModuleThemeDto forCurriculumFulfillment(CurriculumVersionOccupationModuleTheme theme) {
        CurriculumVersionOccupationModuleThemeDto dto = new CurriculumVersionOccupationModuleThemeDto();
        dto.setId(theme.getId());
        dto.setNameEt(theme.getNameEt());
        dto.setCredits(theme.getCredits());
        dto.setModule(EntityUtil.getId(theme.getModule()));
        dto.setModuleOutcomes(theme.getModuleOutcomes());
        dto.setCurriculumModuleOutcomes(StreamUtil.toMappedSet(o -> CurriculumModuleOutcomeDto.of(o.getOutcome()),
                theme.getOutcomes()));
        dto.setAssessment(EntityUtil.getNullableCode(theme.getAssessment()));
        dto.setStudyYearNumber(theme.getStudyYearNumber());
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

    public Long getModule() {
        return module;
    }

    public void setModule(Long module) {
        this.module = module;
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

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public String getSubthemes() {
        return subthemes;
    }

    public void setSubthemes(String subthemes) {
        this.subthemes = subthemes;
    }

    public Short getStudyYearNumber() {
        return studyYearNumber;
    }

    public void setStudyYearNumber(Short studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
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

    public Boolean getModuleOutcomes() {
        return moduleOutcomes;
    }

    public void setModuleOutcomes(Boolean moduleOutcomes) {
        this.moduleOutcomes = moduleOutcomes;
    }

    public Set<Long> getOutcomes() {
        return outcomes != null ? outcomes : (new HashSet<>());
    }

    public void setOutcomes(Set<Long> outcomes) {
        this.outcomes = outcomes;
    }

    public Set<CurriculumVersionOccupationModuleThemeCapacityDto> getCapacities() {
        return capacities != null ? capacities : (capacities = new HashSet<>());
    }

    public void setCapacities(Set<CurriculumVersionOccupationModuleThemeCapacityDto> capacities) {
        this.capacities = capacities;
    }

    public Boolean getOtherCurriculumVersionModuleTheme() {
        return otherCurriculumVersionModuleTheme;
    }

    public void setOtherCurriculumVersionModuleTheme(Boolean otherCurriculumVersionModuleTheme) {
        this.otherCurriculumVersionModuleTheme = otherCurriculumVersionModuleTheme;
    }

    public Set<CurriculumModuleOutcomeDto> getCurriculumModuleOutcomes() {
        return curriculumModuleOutcomes != null ? curriculumModuleOutcomes : (curriculumModuleOutcomes = new HashSet<>());
    }

    public void setCurriculumModuleOutcomes(Set<CurriculumModuleOutcomeDto> curriculumModuleOutcomes) {
        this.curriculumModuleOutcomes = curriculumModuleOutcomes;
    }
}
