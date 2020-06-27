package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.Valid;

import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;

public class HigherProtocolSaveForm extends VersionedCommand {

    @Valid
    private Set<HigherProtocolStudentDto> protocolStudents;
    private LocalDate finalDate;
    private String signerMobileNumber;

    public Set<HigherProtocolStudentDto> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(Set<HigherProtocolStudentDto> protocolStudents) {
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
