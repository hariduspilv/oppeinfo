package ee.hitsa.ois.domain.scholarship;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.CommitteeMember;

@Entity
public class ScholarshipDecisionCommitteeMember extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ScholarshipDecision scholarshipDecision;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CommitteeMember committeeMember;

    public ScholarshipDecision getScholarshipDecision() {
        return scholarshipDecision;
    }

    public void setScholarshipDecision(ScholarshipDecision scholarshipDecision) {
        this.scholarshipDecision = scholarshipDecision;
    }

    public CommitteeMember getCommitteeMember() {
        return committeeMember;
    }

    public void setCommitteeMember(CommitteeMember committeeMember) {
        this.committeeMember = committeeMember;
    }
    
}
