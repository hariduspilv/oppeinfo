package ee.hitsa.ois.domain.apelapplication;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class ApelApplicationInformalExperience extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplicationRecord apelApplicationRecord;
    
    private String nameEt;
    private String placeTime;
    private Short hours;
    private String documents;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;

    public ApelApplicationRecord getApelApplicationRecord() {
        return apelApplicationRecord;
    }

    public void setApelApplicationRecord(ApelApplicationRecord apelApplicationRecord) {
        this.apelApplicationRecord = apelApplicationRecord;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getPlaceTime() {
        return placeTime;
    }

    public void setPlaceTime(String placeTime) {
        this.placeTime = placeTime;
    }

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }
    
}
