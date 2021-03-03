package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumAddress;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumAddressForm;

public class CurriculumAddressDto extends CurriculumAddressForm {

    private Boolean canDelete;

    public static CurriculumAddressDto of(CurriculumAddress address) {
        CurriculumAddressDto dto = EntityUtil.bindToDto(address, new CurriculumAddressDto());
        dto.setCanDelete(address.getStudentGroups().isEmpty());
        return dto;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }
}
