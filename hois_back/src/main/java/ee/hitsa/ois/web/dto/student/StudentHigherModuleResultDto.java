package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;

public class StudentHigherModuleResultDto extends CurriculumVersionHigherModuleDto {

    private BigDecimal mandatoryCreditsSubmitted;
    private BigDecimal optionalCreditsSubmitted;
    private BigDecimal mandatoryDifference;
    private BigDecimal optionalDifference;
    private BigDecimal totalDifference;
    private Boolean isOk;
    private Long electiveModulesCompleted;
    private Set<StudentHigherElectiveModuleResultDto> electiveModulesResults;

    public static StudentHigherModuleResultDto of(CurriculumVersionHigherModule higherModule) {
        StudentHigherModuleResultDto dto = EntityUtil.bindToDto(higherModule, new StudentHigherModuleResultDto(),
                "electiveModules", "specialities", "subjects");
        dto.setElectiveModulesResults(StreamUtil.toMappedSet(StudentHigherElectiveModuleResultDto::of, higherModule.getElectiveModules()));
        return dto;
    }

    public static StudentHigherModuleResultDto createFreeModule() {
        StudentHigherModuleResultDto dto = new StudentHigherModuleResultDto();
        dto.setId(Long.valueOf(-1));
        dto.setType(HigherModuleType.KORGMOODUL_V.name());
        dto.setCompulsoryStudyCredits(BigDecimal.ZERO);
        dto.setMandatoryCreditsSubmitted(BigDecimal.ZERO);
        dto.setOptionalStudyCredits(BigDecimal.ZERO);
        dto.setElectiveModulesNumber(Short.valueOf((short) 0));
        dto.setMinorSpeciality(Boolean.FALSE);
        dto.setElectiveModulesResults(new HashSet<>());
        return dto;
    }

    public void calculateIsOk() {
        electiveModulesCompleted = Long.valueOf(electiveModulesResults.stream().filter(e -> Boolean.TRUE.equals(e.getIsOk())).count());
        this.isOk = Boolean.valueOf(BigDecimal.ZERO.compareTo(this.totalDifference) <= 0 && electiveModulesCompleted.compareTo(this.getElectiveModulesNumber().longValue()) != -1);
    }

    public Set<StudentHigherElectiveModuleResultDto> getElectiveModulesResults() {
        return electiveModulesResults;
    }
    public void setElectiveModulesResults(Set<StudentHigherElectiveModuleResultDto> electiveModulesResults) {
        this.electiveModulesResults = electiveModulesResults;
    }
    public Long getElectiveModulesCompleted() {
        return electiveModulesCompleted;
    }
    public void setElectiveModulesCompleted(Long electiveModulesCompleted) {
        this.electiveModulesCompleted = electiveModulesCompleted;
    }
    public Boolean getIsOk() {
        return isOk;
    }
    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }
    public BigDecimal getMandatoryDifference() {
        return mandatoryDifference;
    }
    public void setMandatoryDifference(BigDecimal mandatoryDifference) {
        this.mandatoryDifference = mandatoryDifference;
    }
    public BigDecimal getOptionalDifference() {
        return optionalDifference;
    }
    public void setOptionalDifference(BigDecimal optionalDifference) {
        this.optionalDifference = optionalDifference;
    }
    public BigDecimal getMandatoryCreditsSubmitted() {
        return mandatoryCreditsSubmitted;
    }
    public void setMandatoryCreditsSubmitted(BigDecimal mandatoryCreditsSubmitted) {
        this.mandatoryCreditsSubmitted = mandatoryCreditsSubmitted;
    }
    public BigDecimal getOptionalCreditsSubmitted() {
        return optionalCreditsSubmitted;
    }
    public void setOptionalCreditsSubmitted(BigDecimal optionalCreditsSubmitted) {
        this.optionalCreditsSubmitted = optionalCreditsSubmitted;
    }
    public BigDecimal getTotalDifference() {
        return totalDifference;
    }
    public void setTotalDifference(BigDecimal totalDifference) {
        this.totalDifference = totalDifference;
    }
}
