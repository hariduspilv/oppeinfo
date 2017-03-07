package ee.hitsa.ois.web.commandobject;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class CertificateForm extends VersionedCommand {
    @NotBlank
    @Size(max=1000)
    private String headline;
    @NotBlank
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
    private String wdUrl;
    private BigInteger wdId;
    @NotNull
    @ClassifierRestriction(MainClassCode.TOEND_LIIK)
    private String type;
    @NotNull
    @ClassifierRestriction(MainClassCode.TOEND_STAATUS)
    private String status;
    private Long student;

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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getStudent() {
        return student;
    }
    public void setStudent(Long student) {
        this.student = student;
    }
}
