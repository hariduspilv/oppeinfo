package ee.hitsa.ois.web.commandobject.apelapplication;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class ApelSchoolForm extends VersionedCommand {
    
    @NotBlank
    @Size(max = 100)
    private String nameEt;
    
    @Size(max = 100)
    private String nameEn;
    
    @ClassifierRestriction(MainClassCode.EHIS_KOOL)
    private String ehisSchool;
    
    @NotNull
    @ClassifierRestriction(MainClassCode.RIIK)
    private String country;
    

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(String ehisSchool) {
        this.ehisSchool = ehisSchool;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
}
