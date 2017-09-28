package ee.hitsa.ois.xml.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionElectiveModule;
import ee.hitsa.ois.util.EntityUtil;

public class CurriculumVersionElectiveModuleXml {
    
    private Long id;
    private Long version;
    private String nameEt;
    private String nameEn;
    
    public static CurriculumVersionElectiveModuleXml of(CurriculumVersionElectiveModule electiveModule) {
        return EntityUtil.bindToDto(electiveModule, new CurriculumVersionElectiveModuleXml());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
