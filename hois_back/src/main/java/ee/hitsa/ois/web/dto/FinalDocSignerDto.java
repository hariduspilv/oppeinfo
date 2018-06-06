package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.FinalDocSigner;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.FinalDocSignerForm;

public class FinalDocSignerDto extends FinalDocSignerForm {
    
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public static FinalDocSignerDto of(FinalDocSigner signer) {
        FinalDocSignerDto dto = new FinalDocSignerDto();
        dto.setId(EntityUtil.getId(signer));
        dto.setVersion(signer.getVersion());
        dto.setName(signer.getName());
        dto.setPosition(signer.getPosition());
        dto.setIsFirst(signer.getIsFirst());
        dto.setIsValid(signer.getIsValid());
        return dto;
    }

}
