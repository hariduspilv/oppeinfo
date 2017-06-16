package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.util.EntityUtil;

public class ProtocolVdataDto {

    private Long id;
    private AutocompleteResult curriculumVersionOccupationModule;
    private BigDecimal moduleCredits = BigDecimal.ZERO;
    private String assessment;
    private AutocompleteResult curriculumVersion;
    private AutocompleteResult studyYear;
    private AutocompleteResult teacher;
    private List<AutocompleteResult> outcomes;

    public static ProtocolVdataDto of(ProtocolVdata protocolVdata) {
        ProtocolVdataDto dto = new ProtocolVdataDto();
        dto.setCurriculumVersionOccupationModule(
                AutocompleteResult.of(protocolVdata.getCurriculumVersionOccupationModule()));
        protocolVdata.getCurriculumVersionOccupationModule().getYearCapacities()
                .forEach(c -> dto.setModuleCredits(dto.getModuleCredits().add(c.getCredits())));
        dto.setAssessment(
                EntityUtil.getNullableCode(protocolVdata.getCurriculumVersionOccupationModule().getAssessment()));
        dto.setCurriculumVersion(AutocompleteResult.of(protocolVdata.getCurriculumVersion()));
        dto.setStudyYear(AutocompleteResult.of(protocolVdata.getStudyYear()));
        dto.setTeacher(AutocompleteResult.of(protocolVdata.getTeacher()));
        dto.setOutcomes(protocolVdata.getCurriculumVersionOccupationModule().getCurriculumModule().getOutcomes()
                .stream().map(AutocompleteResult::of).collect(Collectors.toList()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getCurriculumVersionOccupationModule() {
        return curriculumVersionOccupationModule;
    }

    public void setCurriculumVersionOccupationModule(AutocompleteResult curriculumVersionOccupationModule) {
        this.curriculumVersionOccupationModule = curriculumVersionOccupationModule;
    }

    public BigDecimal getModuleCredits() {
        return moduleCredits;
    }

    public void setModuleCredits(BigDecimal moduleCredits) {
        this.moduleCredits = moduleCredits;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public AutocompleteResult getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(AutocompleteResult studyYear) {
        this.studyYear = studyYear;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public List<AutocompleteResult> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<AutocompleteResult> outcomes) {
        this.outcomes = outcomes;
    }

}
