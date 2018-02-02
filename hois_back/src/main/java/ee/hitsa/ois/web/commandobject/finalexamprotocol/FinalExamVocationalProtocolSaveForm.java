package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import java.util.List;

import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class FinalExamVocationalProtocolSaveForm extends VersionedCommand {

    private Long committeeId;
    private List<FinalExamProtocolCommitteeMemberForm> protocolCommitteeMembers;
    private List<FinalExamVocationalProtocolStudentSaveForm> protocolStudents;
    
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

    public List<FinalExamVocationalProtocolStudentSaveForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<FinalExamVocationalProtocolStudentSaveForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }
}
