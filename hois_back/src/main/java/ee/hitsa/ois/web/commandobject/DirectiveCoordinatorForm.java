package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.EstonianIdCode;
import ee.hitsa.ois.validation.NotEmpty;

public class DirectiveCoordinatorForm extends VersionedCommand {

    @NotEmpty
    @Size(max = 100)
    private String name;
    @NotEmpty
    @EstonianIdCode
    private String idcode;
    private Boolean isDirective;
    private Boolean isCertificate;
    private Boolean isCertificateDefault;
    
    public Boolean getIsDirective() {
        return isDirective;
    }

    public void setIsDirective(Boolean isDirective) {
        this.isDirective = isDirective;
    }

    public Boolean getIsCertificate() {
        return isCertificate;
    }

    public void setIsCertificate(Boolean isCertificate) {
        this.isCertificate = isCertificate;
    }

    public Boolean getIsCertificateDefault() {
        return isCertificateDefault;
    }

    public void setIsCertificateDefault(Boolean isCertificateDefault) {
        this.isCertificateDefault = isCertificateDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
}
