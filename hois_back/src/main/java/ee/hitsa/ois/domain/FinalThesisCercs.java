package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class FinalThesisCercs extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FinalThesis finalThesis;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier cercs;

    public FinalThesis getFinalThesis() {
        return finalThesis;
    }

    public void setFinalThesis(FinalThesis finalThesis) {
        this.finalThesis = finalThesis;
    }

    public Classifier getCercs() {
        return cercs;
    }

    public void setCercs(Classifier cercs) {
        this.cercs = cercs;
    }

}
