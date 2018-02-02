package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import java.util.List;

import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;

public class FinalExamVocationalProtocolCreateForm {
    
    private ProtocolVdataForm protocolVdata;
    private List<FinalExamProtocolStudentCreateForm> protocolStudents;

    public ProtocolVdataForm getProtocolVdata() {
        return protocolVdata;
    }

    public void setProtocolVdata(ProtocolVdataForm protocolVdata) {
        this.protocolVdata = protocolVdata;
    }

    public List<FinalExamProtocolStudentCreateForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<FinalExamProtocolStudentCreateForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

}
