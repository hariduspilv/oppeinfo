package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;

public class SchoolForm extends VersionedCommand {

    private String nameEt;
    @NotNull
    private String nameEn;
    private String email;
    private String code;
    private String address;
    private String phone;
    private OisFileCommand logo;
    private Boolean deleteCurrentLogo;
    @NotNull
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
