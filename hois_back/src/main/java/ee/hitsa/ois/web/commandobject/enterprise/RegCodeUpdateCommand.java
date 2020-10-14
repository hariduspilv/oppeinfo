package ee.hitsa.ois.web.commandobject.enterprise;

import ee.hitsa.ois.web.dto.enterprise.EnterpriseRegCodeCheckDto;

public class RegCodeUpdateCommand extends EnterpriseRegCodeCheckDto {

    private Long enterpriseSchoolId;

    public Long getEnterpriseSchoolId() {
        return enterpriseSchoolId;
    }

    public void setEnterpriseSchoolId(Long enterpriseSchoolId) {
        this.enterpriseSchoolId = enterpriseSchoolId;
    }
}
