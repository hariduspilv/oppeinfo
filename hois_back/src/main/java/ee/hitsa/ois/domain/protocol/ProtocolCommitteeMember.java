package ee.hitsa.ois.domain.protocol;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.CommitteeMember;

@Entity
public class ProtocolCommitteeMember extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Protocol protocol;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = true)
    private CommitteeMember committeeMember;

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public CommitteeMember getCommitteeMember() {
        return committeeMember;
    }

    public void setCommitteeMember(CommitteeMember committeeMember) {
        this.committeeMember = committeeMember;
    }
    
}
