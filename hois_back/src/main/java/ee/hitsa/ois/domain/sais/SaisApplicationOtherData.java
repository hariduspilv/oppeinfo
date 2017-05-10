package ee.hitsa.ois.domain.sais;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.validation.NotEmpty;

@Entity
public class SaisApplicationOtherData extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    private SaisApplication saisApplication;

    @Size(max = 255)
    @NotEmpty
    private String otherDataName;

    @Size(max = 4000)
    @NotEmpty
    private String otherDataValue;

    public SaisApplication getSaisApplication() {
        return saisApplication;
    }

    public void setSaisApplication(SaisApplication saisApplication) {
        this.saisApplication = saisApplication;
    }

    public String getOtherDataName() {
        return otherDataName;
    }

    public void setOtherDataName(String otherDataName) {
        this.otherDataName = otherDataName;
    }

    public String getOtherDataValue() {
        return otherDataValue;
    }

    public void setOtherDataValue(String otherDataValue) {
        this.otherDataValue = otherDataValue;
    }

}
