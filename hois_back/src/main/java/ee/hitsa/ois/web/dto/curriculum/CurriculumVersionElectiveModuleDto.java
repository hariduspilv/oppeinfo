package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionElectiveModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionElectiveModuleDto extends VersionedCommand {

    private Long id;
    private Long referenceNumber;
    @NotBlank
    @Size(max=255)
    private String nameEt;
    @NotBlank
    @Size(max=255)
    private String nameEn;

    public static CurriculumVersionElectiveModuleDto of(CurriculumVersionElectiveModule electiveModule) {
        CurriculumVersionElectiveModuleDto dto = EntityUtil.bindToDto(electiveModule, new CurriculumVersionElectiveModuleDto(),
                "subjects", "referenceNumber");
        return dto;
    }
    @XmlTransient
    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        setReferenceNumber(id);
    }

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
}
