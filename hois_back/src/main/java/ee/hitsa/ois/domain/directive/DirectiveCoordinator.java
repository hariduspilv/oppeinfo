package ee.hitsa.ois.domain.directive;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.school.School;

@Entity
public class DirectiveCoordinator extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    private String name;
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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
