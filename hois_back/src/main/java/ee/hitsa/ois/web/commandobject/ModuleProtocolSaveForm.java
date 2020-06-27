package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class ModuleProtocolSaveForm extends VersionedCommand {

    private List<ModuleProtocolStudentSaveForm> protocolStudents;
    private String signerMobileNumber;

    public List<ModuleProtocolStudentSaveForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<ModuleProtocolStudentSaveForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

    public String getSignerMobileNumber() {
        return signerMobileNumber;
    }

    public void setSignerMobileNumber(String signerMobileNumber) {
        this.signerMobileNumber = signerMobileNumber;
    }
}
