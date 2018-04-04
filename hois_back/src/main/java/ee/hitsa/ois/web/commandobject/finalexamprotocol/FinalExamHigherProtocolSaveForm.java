package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class FinalExamHigherProtocolSaveForm extends VersionedCommand {

    @NotNull
    private LocalDate finalDate;
    private Long committeeId;
    private List<FinalExamProtocolCommitteeMemberForm> protocolCommitteeMembers;
    private List<FinalExamHigherProtocolStudentSaveForm> protocolStudents;
    
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
    
    public List<FinalExamProtocolCommitteeMemberForm> getProtocolCommitteeMembers() {
        return protocolCommitteeMembers;
    }
    
    public void setProtocolCommitteeMembers(List<FinalExamProtocolCommitteeMemberForm> protocolCommitteeMembers) {
        this.protocolCommitteeMembers = protocolCommitteeMembers;
    }
    
    public List<FinalExamHigherProtocolStudentSaveForm> getProtocolStudents() {
        return protocolStudents;
    }
    
    public void setProtocolStudents(List<FinalExamHigherProtocolStudentSaveForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }
    
}
