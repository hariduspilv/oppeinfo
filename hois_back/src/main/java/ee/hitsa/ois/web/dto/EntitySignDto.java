package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.bdoc.UnsignedBdocContainer;
import ee.hitsa.ois.domain.BaseEntityWithId;

public class EntitySignDto {
    private Long id;
    private Long version;
    private String digestToSign;

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

    public String getDigestToSign() {
        return digestToSign;
    }

    public void setDigestToSign(String digestToSign) {
        this.digestToSign = digestToSign;
    }

    public static EntitySignDto of(BaseEntityWithId entity, UnsignedBdocContainer unsignedBdocContainer) {
        EntitySignDto dto = new EntitySignDto();
        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setDigestToSign(unsignedBdocContainer.getDigestToSign());
        return dto;
    }

}
