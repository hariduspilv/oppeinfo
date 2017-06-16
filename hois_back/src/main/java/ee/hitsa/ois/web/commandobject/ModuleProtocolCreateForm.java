package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class ModuleProtocolCreateForm {

    private ProtocolVdataForm protocolVdata;
    private List<ModuleProtocolStudentCreateForm> protocolStudents;

    public ProtocolVdataForm getProtocolVdata() {
        return protocolVdata;
    }

    public void setProtocolVdata(ProtocolVdataForm protocolVdata) {
        this.protocolVdata = protocolVdata;
    }

    public List<ModuleProtocolStudentCreateForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<ModuleProtocolStudentCreateForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

}
