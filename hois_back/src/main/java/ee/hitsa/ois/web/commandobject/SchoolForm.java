package ee.hitsa.ois.web.commandobject;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;

public class SchoolForm extends VersionedCommand {

    private String nameEt;
    @Required
    @Size(max = 255)
    private String nameEn;
    @Required
    @Size(max = 255)
    private String email;
    @Size(max = 10)
    private String code;
    @Size(max = 1000)
    private String address;
    @Size(max = 100)
    private String phone;
    @Size(max = 255)
    private String emailDomain;
    private Boolean generateUserEmail;
    @Size(max = 10)
    private String rtipSchoolCode;
    @Valid
    private OisFileCommand logo;
    private Boolean deleteCurrentLogo;
    @Required
    @ClassifierRestriction(MainClassCode.EHIS_KOOL)
    private String ehisSchool;
    @Size(max = 255)
    private String adUrl;
    @Min(0)
    @Max(65535)
    private Long adPort;
    @Size(max = 255)
    private String adDomain;
    @Size(max = 255)
    private String adBase;
    @Size(max = 50)
    private String adIdcodeField;

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

    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public Boolean getGenerateUserEmail() {
        return generateUserEmail;
    }

    public void setGenerateUserEmail(Boolean generateUserEmail) {
        this.generateUserEmail = generateUserEmail;
    }

    public String getRtipSchoolCode() {
        return rtipSchoolCode;
    }

    public void setRtipSchoolCode(String rtipSchoolCode) {
        this.rtipSchoolCode = rtipSchoolCode;
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

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public Long getAdPort() {
        return adPort;
    }

    public void setAdPort(Long adPort) {
        this.adPort = adPort;
    }

    public String getAdDomain() {
        return adDomain;
    }

    public void setAdDomain(String adDomain) {
        this.adDomain = adDomain;
    }

    public String getAdBase() {
        return adBase;
    }

    public void setAdBase(String adBase) {
        this.adBase = adBase;
    }

    public String getAdIdcodeField() {
        return adIdcodeField;
    }

    public void setAdIdcodeField(String adIdcodeField) {
        this.adIdcodeField = adIdcodeField;
    }
    
}
