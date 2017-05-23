package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    private BigDecimal credits;

    @NotNull
    private Integer hours;

    private BigDecimal proportion;
    private String subthemes;
    private Integer studyYearNumber;

    @ClassifierRestriction(MainClassCode.KUTSEHINDAMISVIIS)
    private String assessment;

    private String totalGradeDescription;
    private String passDescription;
    private String grade3Description;
    private String grade4Description;
    private String grade5Description;

    @Valid
    private Set<CurriculumVersionOccupationModuleOutcomeDto> outcomes;

    @Valid
    private Set<CurriculumVersionOccupationModuleThemeCapacityDto> capacities;

    public static CurriculumVersionOccupationModuleThemeDto of(CurriculumVersionOccupationModuleTheme theme) {
        CurriculumVersionOccupationModuleThemeDto dto = EntityUtil.bindToDto(theme, new CurriculumVersionOccupationModuleThemeDto(),
                "capacities", "outcomes");

        dto.setCapacities(StreamUtil.toMappedSet(CurriculumVersionOccupationModuleThemeCapacityDto::of, theme.getCapacities()));
        dto.setOutcomes(StreamUtil.toMappedSet(CurriculumVersionOccupationModuleOutcomeDto::of, theme.getOutcomes()));

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

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
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

    public Integer getStudyYearNumber() {
        return studyYearNumber;
    }

    public void setStudyYearNumber(Integer studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
    }

    public Set<CurriculumVersionOccupationModuleOutcomeDto> getOutcomes() {
        return outcomes != null ? outcomes : (outcomes = new HashSet<>());
    }

    public void setOutcomes(Set<CurriculumVersionOccupationModuleOutcomeDto> outcomes) {
        this.outcomes = outcomes;
    }

    public Set<CurriculumVersionOccupationModuleThemeCapacityDto> getCapacities() {
        return capacities != null ? capacities : (capacities = new HashSet<>());
    }

    public void setCapacities(Set<CurriculumVersionOccupationModuleThemeCapacityDto> capacities) {
        this.capacities = capacities;
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
}
