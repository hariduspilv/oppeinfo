package ee.hitsa.ois.web.commandobject.finalprotocol;

import java.util.List;

import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;

public class FinalVocationalProtocolCreateForm {
    
    private Boolean isFinalThesis;
    private ProtocolVdataForm protocolVdata;
    private List<FinalProtocolStudentCreateForm> protocolStudents;
    
    public Boolean getIsFinalThesis() {
        return isFinalThesis;
    }

    public void setIsFinalThesis(Boolean isFinalThesis) {
        this.isFinalThesis = isFinalThesis;
    }

    public ProtocolVdataForm getProtocolVdata() {
        return protocolVdata;
    }

    public void setProtocolVdata(ProtocolVdataForm protocolVdata) {
        this.protocolVdata = protocolVdata;
    }

    public List<FinalProtocolStudentCreateForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<FinalProtocolStudentCreateForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

}
