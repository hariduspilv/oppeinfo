package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Enterprise;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.EnterpriseForm;

public class EnterpriseDto extends EnterpriseForm {

    private Long id;

    public static EnterpriseDto of(Enterprise enterprise) {
        return EntityUtil.bindToDto(enterprise, new EnterpriseDto());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
