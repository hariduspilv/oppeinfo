package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.util.PersonUtil;

import java.util.List;

public class HigherProtocolModuleDto {

    private AutocompleteResult module;
    private String teacher;
    private List<HigherProtocolModuleSubjectDto> subjects;

    public static HigherProtocolModuleDto of(ProtocolHdata protocolHdata) {
        HigherProtocolModuleDto dto = new HigherProtocolModuleDto();
        dto.setModule(AutocompleteResult.of(protocolHdata.getCurriculumVersionHmodule()));
        dto.setTeacher(PersonUtil.fullname(protocolHdata.getTeacher().getPerson()));
        return dto;
    }

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<HigherProtocolModuleSubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<HigherProtocolModuleSubjectDto> subjects) {
        this.subjects = subjects;
    }

}
