package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GeneralMessageTarget extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private GeneralMessage generalMessage;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_code", nullable = false, updatable = false)
    private Classifier role;

    public GeneralMessage getGeneralMessage() {
        return generalMessage;
    }

    public void setGeneralMessage(GeneralMessage generalMessage) {
        this.generalMessage = generalMessage;
    }

    public Classifier getRole() {
        return role;
    }

    public void setRole(Classifier role) {
        this.role = role;
    }
}
