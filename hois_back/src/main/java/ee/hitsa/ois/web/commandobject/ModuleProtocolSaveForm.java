package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class ModuleProtocolSaveForm extends VersionedCommand {

    private List<ProtocolStudentSaveForm> protocolStudents;
    private String signerMobileNumber;

    public List<ProtocolStudentSaveForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<ProtocolStudentSaveForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

    public String getSignerMobileNumber() {
        return signerMobileNumber;
    }

    public void setSignerMobileNumber(String signerMobileNumber) {
        this.signerMobileNumber = signerMobileNumber;
    }
}
