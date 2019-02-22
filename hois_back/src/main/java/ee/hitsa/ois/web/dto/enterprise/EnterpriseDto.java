package ee.hitsa.ois.web.dto.enterprise;

import ee.hitsa.ois.domain.enterprise.Enterprise;
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
