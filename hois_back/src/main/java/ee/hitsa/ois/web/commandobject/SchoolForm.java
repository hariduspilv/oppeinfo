package ee.hitsa.ois.web.commandobject;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;

public class SchoolForm extends VersionedCommand {

    private String nameEt;
    @NotEmpty
    @Size(max = 255)
    private String nameEn;
    @NotEmpty
    @Size(max = 255)
    private String email;
    @Size(max = 10)
    private String code;
    @Size(max = 1000)
    private String address;
    @Size(max = 100)
    private String phone;
    @Valid
    private OisFileCommand logo;
    private Boolean deleteCurrentLogo;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.EHIS_KOOL)
    private String ehisSchool;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OisFileCommand getLogo() {
        return logo;
    }

    public void setLogo(OisFileCommand logo) {
        this.logo = logo;
    }

    public Boolean getDeleteCurrentLogo() {
        return deleteCurrentLogo;
    }

    public void setDeleteCurrentLogo(Boolean deleteCurrentLogo) {
        this.deleteCurrentLogo = deleteCurrentLogo;
    }

    public String getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(String ehisSchool) {
        this.ehisSchool = ehisSchool;
    }
}
