package ee.hitsa.ois.domain.apelapplication;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.OisFile;

@Entity
public class ApelApplicationFile extends BaseEntityWithId{

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplication apelApplication;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, updatable = false)
    private OisFile oisFile;

    public ApelApplication getApelApplication() {
        return apelApplication;
    }

    public void setApelApplication(ApelApplication apelApplication) {
        this.apelApplication = apelApplication;
    }

    public OisFile getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFile oisFile) {
        this.oisFile = oisFile;
    }
    
}
