package ee.hitsa.ois.domain.apelapplication;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Committee;

@Entity
public class ApelApplicationCommittee extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplication apelApplication;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Committee committee;

    public ApelApplication getApelApplication() {
        return apelApplication;
    }

    public void setApelApplication(ApelApplication apelApplication) {
        this.apelApplication = apelApplication;
    }

    public Committee getCommittee() {
        return committee;
    }

    public void setCommittee(Committee committee) {
        this.committee = committee;
    }

}
