package ee.hitsa.ois.domain.poll;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class PollTarget extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Poll poll;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier target;
    
    public Poll getPoll() {
        return poll;
    }
    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    public Classifier getTarget() {
        return target;
    }
    public void setTarget(Classifier target) {
        this.target = target;
    }

}
