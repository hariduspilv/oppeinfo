package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.protocol.ProtocolVdata;

public class ProtocolVdataDto {

    private Long id;
    private AutocompleteResult curriculumVersionOccupationModule;
    private AutocompleteResult curriculumVersion;
    private AutocompleteResult studyYear;
    private AutocompleteResult teacher;

    public static ProtocolVdataDto of(ProtocolVdata protocolVdata) {
        ProtocolVdataDto dto = new ProtocolVdataDto();
        dto.setCurriculumVersionOccupationModule(AutocompleteResult.of(protocolVdata.getCurriculumVersionOccupationModule()));
        dto.setCurriculumVersion(AutocompleteResult.of(protocolVdata.getCurriculumVersion()));
        dto.setStudyYear(AutocompleteResult.of(protocolVdata.getStudyYear()));
        dto.setTeacher(AutocompleteResult.of(protocolVdata.getTeacher()));
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

}
