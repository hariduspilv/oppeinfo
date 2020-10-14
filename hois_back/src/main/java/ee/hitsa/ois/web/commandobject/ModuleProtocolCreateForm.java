package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class ModuleProtocolCreateForm {

    private ProtocolVdataForm protocolVdata;
    private List<ProtocolStudentCreateForm> protocolStudents;

    public ProtocolVdataForm getProtocolVdata() {
        return protocolVdata;
    }

    public void setProtocolVdata(ProtocolVdataForm protocolVdata) {
        this.protocolVdata = protocolVdata;
    }

    public List<ProtocolStudentCreateForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<ProtocolStudentCreateForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

}
