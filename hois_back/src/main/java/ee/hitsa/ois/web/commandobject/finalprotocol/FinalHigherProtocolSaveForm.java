package ee.hitsa.ois.web.commandobject.finalprotocol;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class FinalHigherProtocolSaveForm extends VersionedCommand {

    @NotNull
    private LocalDate finalDate;
    private Long committeeId;
    private List<FinalProtocolCommitteeMemberForm> protocolCommitteeMembers;
    private List<FinalHigherProtocolStudentSaveForm> protocolStudents;
    
    public LocalDate getFinalDate() {
        return finalDate;
    }
    
    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }
    
    public Long getCommitteeId() {
        return committeeId;
    }
    
    public void setCommitteeId(Long committeeId) {
        this.committeeId = committeeId;
    }
    
    public List<FinalProtocolCommitteeMemberForm> getProtocolCommitteeMembers() {
        return protocolCommitteeMembers;
    }
    
    public void setProtocolCommitteeMembers(List<FinalProtocolCommitteeMemberForm> protocolCommitteeMembers) {
        this.protocolCommitteeMembers = protocolCommitteeMembers;
    }
    
    public List<FinalHigherProtocolStudentSaveForm> getProtocolStudents() {
        return protocolStudents;
    }
    
    public void setProtocolStudents(List<FinalHigherProtocolStudentSaveForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }
    
}
