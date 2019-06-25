package ee.hitsa.ois.domain.poll;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class PollTarget extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Poll poll;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier target;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_target_id", nullable = false, updatable = false, insertable = false)
    private List<ResponseObject> responseObjects = new ArrayList<>();
    
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
    public List<ResponseObject> getResponseObjects() {
        return responseObjects;
    }
    public void setResponseObjects(List<ResponseObject> responseObjects) {
        this.responseObjects = responseObjects;
    }

}
