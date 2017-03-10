package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.CertificateForm;

public class CertificateDto extends CertificateForm {
    
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static CertificateDto of(Certificate certificate) {
        CertificateDto dto = EntityUtil.bindToDto(certificate, new CertificateDto(), "student", "school");
        dto.setStudent(EntityUtil.getNullableId(certificate.getStudent()));
        return dto;
    }
}
