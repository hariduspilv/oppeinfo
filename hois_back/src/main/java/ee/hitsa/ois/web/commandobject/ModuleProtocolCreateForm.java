package ee.hitsa.ois.web.commandobject;

import java.util.List;

import ee.hitsa.ois.web.dto.ProtocolStudentDto;
import ee.hitsa.ois.web.dto.ProtocolVdataDto;

public class ModuleProtocolCreateForm {

    private String protocolNr;
    private ProtocolVdataDto protocolVdata;
    private List<ProtocolStudentDto> protocolStudents;

    public String getProtocolNr() {
        return protocolNr;
    }

    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }

    public ProtocolVdataDto getProtocolVdata() {
        return protocolVdata;
    }

    public void setProtocolVdata(ProtocolVdataDto protocolVdata) {
        this.protocolVdata = protocolVdata;
    }

    public List<ProtocolStudentDto> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<ProtocolStudentDto> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

}
