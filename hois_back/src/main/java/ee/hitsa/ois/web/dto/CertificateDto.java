package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.util.ClassifierUtil;
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
        CertificateDto dto = EntityUtil.bindToDto(certificate, new CertificateDto(), "student", "school", "content");
        dto.setStudent(EntityUtil.getNullableId(certificate.getStudent()));
        if(ClassifierUtil.equals(CertificateType.TOEND_LIIK_MUU, certificate.getType())) {
            dto.setContent(certificate.getContent());
        }
        return dto;
    }
}
