package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.enums.CertificateStatus;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.CertificateForm;

public class CertificateDto extends CertificateForm {
    
    private Long id;
    private String wdUrl;
    private Long wdId;
    private String certificateNr;
    private boolean canBeChanged;
    private boolean canViewFromEkis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWdUrl() {
        return wdUrl;
    }

    public void setWdUrl(String wdUrl) {
        this.wdUrl = wdUrl;
    }

    public Long getWdId() {
        return wdId;
    }

    public void setWdId(Long wdId) {
        this.wdId = wdId;
    }

    public String getCertificateNr() {
        return certificateNr;
    }

    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }

    public boolean isCanBeChanged() {
        return canBeChanged;
    }

    public void setCanBeChanged(boolean canBeChanged) {
        this.canBeChanged = canBeChanged;
    }

    public boolean isCanViewFromEkis() {
        return canViewFromEkis;
    }

    public void setCanViewFromEkis(boolean canViewFromEkis) {
        this.canViewFromEkis = canViewFromEkis;
    }

    public static CertificateDto of(Certificate certificate) {
        CertificateDto dto = EntityUtil.bindToDto(certificate, new CertificateDto(), "student", "school");
        dto.setStudent(EntityUtil.getNullableId(certificate.getStudent()));
        dto.setCanViewFromEkis(ClassifierUtil.equals(CertificateStatus.TOEND_STAATUS_V, certificate.getStatus()) && certificate.getWdId() != null);
        return dto;
    }
}
