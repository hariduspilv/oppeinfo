package ee.hitsa.ois.domain.application;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.OisFile;

@Entity
public class ApplicationFile extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Application application;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, updatable = false, insertable = true)
    private OisFile oisFile;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public OisFile getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFile oisFile) {
        this.oisFile = oisFile;
    }

}
