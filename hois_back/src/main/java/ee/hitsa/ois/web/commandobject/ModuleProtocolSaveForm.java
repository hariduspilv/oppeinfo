package ee.hitsa.ois.web.commandobject;

import java.util.List;

import ee.hitsa.ois.web.dto.ProtocolStudentDto;

public class ModuleProtocolSaveForm extends VersionedCommand {

    private List<ProtocolStudentDto> protocolStudents;

    public List<ProtocolStudentDto> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<ProtocolStudentDto> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }
    
    


}
