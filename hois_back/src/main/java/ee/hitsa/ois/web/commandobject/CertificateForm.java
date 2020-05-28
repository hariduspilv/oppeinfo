package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.CertificateValidator.ContentIsEditable;
import ee.hitsa.ois.validation.CertificateValidator.RequiredIfWithoutEkis;
import ee.hitsa.ois.validation.CertificateValidator.StudentIsNotSet;
import ee.hitsa.ois.validation.CertificateValidator.StudentIsSet;
import ee.hitsa.ois.validation.CertificateValidator.ValidateLater;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;

public class CertificateForm extends VersionedCommand {

    @Required
    @Size(max=1000)
    private String headline;
    @Required(groups = {ContentIsEditable.class})
    private String content;
    @Size(max=20)
    @Required(groups = {RequiredIfWithoutEkis.class})
    private String certificateNr;
    @Size(max=1000)
    private String whom;
    @Size(max=100)
    @Required(groups = {ValidateLater.class})
    private String signatoryName;
    @Size(max=20)
    @Required(groups = {ValidateLater.class})
    private String signatoryIdcode;
    @Size(max=100)
    @Required(groups = {StudentIsNotSet.class})
    private String otherName;
    @Size(max=20)
    @Required(groups = {StudentIsNotSet.class})
    private String otherIdcode;
    @Required
    @ClassifierRestriction(MainClassCode.TOEND_LIIK)
    private String type;
    @Required(groups = {StudentIsSet.class})
    private Long student;
    private Boolean showModules;
    private Boolean addOutcomes;
    private Boolean estonian;
    private Boolean showUncompleted;

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

    public String getCertificateNr() {
        return certificateNr;
    }

    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
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

    public Boolean getShowModules() {
        return showModules;
    }

    public void setShowModules(Boolean showModules) {
        this.showModules = showModules;
    }

    public Boolean getAddOutcomes() {
        return addOutcomes;
    }

    public void setAddOutcomes(Boolean addOutcomes) {
        this.addOutcomes = addOutcomes;
    }

    public Boolean getEstonian() {
        return estonian;
    }

    public void setEstonian(Boolean estonian) {
        this.estonian = estonian;
    }

    public Boolean getShowUncompleted() {
        return showUncompleted;
    }

    public void setShowUncompleted(Boolean showUncompleted) {
        this.showUncompleted = showUncompleted;
    }
    
}
