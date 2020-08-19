package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.util.EntityUtil;

public class SchoolWithLogo extends SchoolWithoutLogo {
    
    private Long oisFileId;
    private byte[] logo;
    
    public SchoolWithLogo(Long id, String code, String nameEt, String nameEn, String email, Long oisFileId) {
        super(id, code, nameEt, nameEn, email);
        this.oisFileId = oisFileId;
    }

    public SchoolWithLogo(School school) {
        super(school);
        this.oisFileId = EntityUtil.getNullableId(school.getLogo());
    }

    public Long getOisFileId() {
        return oisFileId;
    }

    public void setOisFileId(Long oisFileId) {
        this.oisFileId = oisFileId;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
    
}
