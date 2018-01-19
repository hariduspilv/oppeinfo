package ee.hitsa.ois.web.commandobject.finalExamVocationalProtocol;

import java.util.List;

import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;

public class FinalExamVocationalProtocolCreateForm {
    
    private ProtocolVdataForm protocolVdata;
    private List<FinalExamVocationalProtocolStudentCreateForm> protocolStudents;

    public ProtocolVdataForm getProtocolVdata() {
        return protocolVdata;
    }

    public void setProtocolVdata(ProtocolVdataForm protocolVdata) {
        this.protocolVdata = protocolVdata;
    }

    public List<FinalExamVocationalProtocolStudentCreateForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<FinalExamVocationalProtocolStudentCreateForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

}
