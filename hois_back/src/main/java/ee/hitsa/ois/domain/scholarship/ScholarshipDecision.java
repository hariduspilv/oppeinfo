package ee.hitsa.ois.domain.scholarship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Committee;

@Entity
public class ScholarshipDecision extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Committee committee;
    
    private String protocolNr;
    private LocalDate decided;
    private String addInfo;

    @OneToMany(mappedBy = "scholarshipDecision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScholarshipDecisionCommitteeMember> members = new ArrayList<>();
    
    public Committee getCommittee() {
        return committee;
    }
    public void setCommittee(Committee committee) {
        this.committee = committee;
    }
    public String getProtocolNr() {
        return protocolNr;
    }
    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }
    public LocalDate getDecided() {
        return decided;
    }
    public void setDecided(LocalDate decided) {
        this.decided = decided;
    }
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    public List<ScholarshipDecisionCommitteeMember> getMembers() {
        return members;
    }
    public void setMembers(List<ScholarshipDecisionCommitteeMember> members) {
        this.members = members;
    }
    
}
