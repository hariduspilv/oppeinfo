package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import javax.validation.Valid;

import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;

public class HigherProtocolSaveForm extends VersionedCommand {

    @Valid
    private Set<HigherProtocolStudentDto> protocolStudents;

    public Set<HigherProtocolStudentDto> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(Set<HigherProtocolStudentDto> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }
}
