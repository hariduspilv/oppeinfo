package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.Valid;

import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;

public class HigherProtocolSaveForm extends VersionedCommand {

    @Valid
    private Set<ProtocolStudentSaveForm> protocolStudents;
    private LocalDate finalDate;
    private String signerMobileNumber;

    public Set<ProtocolStudentSaveForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(Set<ProtocolStudentSaveForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public String getSignerMobileNumber() {
        return signerMobileNumber;
    }

    public void setSignerMobileNumber(String signerMobileNumber) {
        this.signerMobileNumber = signerMobileNumber;
    }
}
