package ee.hitsa.ois.web.commandobject;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.CertificateValidator.*;

public class CertificateForm extends VersionedCommand {
    @NotBlank
    @Size(max=1000)
    private String headline;
    @NotNull(groups = {OtherType.class})
    private String content;
    @Size(max=1000)
    private String whom;
    @Size(max=20)
    private String certificateNr;
    @NotBlank
    @Size(max=100)
    private String signatoryName;
    @NotBlank
    @Size(max=20)
    private String signatoryIdcode;
    @Size(max=100)
    @NotNull(groups = {StudentIsNotSet.class})
    private String otherName;
    @Size(max=20)
    @NotNull(groups = {StudentIsNotSet.class})
    private String otherIdcode;
    private String wdUrl;
    private BigInteger wdId;
    @NotNull
    @ClassifierRestriction(MainClassCode.TOEND_LIIK)
    private String type;
    @NotNull(groups = {StudentIsSet.class})
    private Long student;
    
    public String getOtherName() {
        return otherName;
    }
    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }
    public String getOtherIdcode() {
        return otherIdcode;
    }
    public void setOtherIdcode(String otherIdcode) {
        this.otherIdcode = otherIdcode;
    }
    public String getHeadline() {
        return headline;
    }
    public void setHeadline(String headline) {
        this.headline = headline;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getWhom() {
        return whom;
    }
    public void setWhom(String whom) {
        this.whom = whom;
    }
    public String getCertificateNr() {
        return certificateNr;
    }
    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }
    public String getSignatoryName() {
        return signatoryName;
    }
    public void setSignatoryName(String signatoryName) {
        this.signatoryName = signatoryName;
    }
    public String getSignatoryIdcode() {
        return signatoryIdcode;
    }
    public void setSignatoryIdcode(String signatoryIdcode) {
        this.signatoryIdcode = signatoryIdcode;
    }
    public String getWdUrl() {
        return wdUrl;
    }
    public void setWdUrl(String wdUrl) {
        this.wdUrl = wdUrl;
    }
    public BigInteger getWdId() {
        return wdId;
    }
    public void setWdId(BigInteger wdId) {
        this.wdId = wdId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getStudent() {
        return student;
    }
    public void setStudent(Long student) {
        this.student = student;
    }
}
