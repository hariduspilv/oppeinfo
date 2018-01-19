package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.util.List;

import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

public class ProtocolVdataDto {

    private Long id;
    private AutocompleteResult curriculumVersionOccupationModule;
    private BigDecimal moduleCredits;
    private String assessment;
    private AutocompleteResult curriculumVersion;
    private AutocompleteResult studyYear;
    private AutocompleteResult teacher;
    private List<AutocompleteResult> outcomes;
    private Long curriculum;
    private Long curriculumModule;

    public static ProtocolVdataDto of(ProtocolVdata protocolVdata) {
        ProtocolVdataDto dto = new ProtocolVdataDto();
        dto.setCurriculumVersionOccupationModule(
                AutocompleteResult.of(protocolVdata.getCurriculumVersionOccupationModule()));
        dto.setModuleCredits(protocolVdata.getCurriculumVersionOccupationModule().getCurriculumModule().getCredits());
        dto.setAssessment(
                EntityUtil.getNullableCode(protocolVdata.getCurriculumVersionOccupationModule().getAssessment()));
        dto.setCurriculumVersion(AutocompleteResult.of(protocolVdata.getCurriculumVersion()));
        dto.setStudyYear(AutocompleteResult.of(protocolVdata.getStudyYear()));
        dto.setTeacher(AutocompleteResult.of(protocolVdata.getTeacher()));
        dto.setOutcomes(StreamUtil.toMappedList(AutocompleteResult::of, protocolVdata.getCurriculumVersionOccupationModule().getCurriculumModule().getOutcomes()));

        dto.setCurriculum(EntityUtil.getId(protocolVdata.getCurriculumVersionOccupationModule().getCurriculumModule().getCurriculum()));
        dto.setCurriculumModule(EntityUtil.getId(protocolVdata.getCurriculumVersionOccupationModule().getCurriculumModule()));
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

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public Long getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(Long curriculumModule) {
        this.curriculumModule = curriculumModule;
    }
}
