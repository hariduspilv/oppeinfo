package ee.hitsa.ois.web.commandobject.apelapplication;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationInformalExperienceForm extends InsertedChangedVersionDto {
    
    private Long id;
    
    @Size(max = 255)
    private String nameEt;
    
    @Size(max = 255)
    private String placeTime;
    
    @Min(0)
    @Max(9999)
    private Short hours;
    
    @Size(max = 255)
    private String documents;
    
    @ClassifierRestriction(MainClassCode.VOTA_INFORMAAL_LIIK)
    private String type;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
