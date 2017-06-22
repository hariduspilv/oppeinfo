package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.ContractForm;

public class ContractDto extends ContractForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static ContractDto of(Contract contract) {
        ContractDto dto = EntityUtil.bindToDto(contract, new ContractDto());
        return dto;
    }

}
