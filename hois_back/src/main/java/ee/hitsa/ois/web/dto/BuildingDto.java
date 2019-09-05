package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.web.commandobject.BuildingForm;

public class BuildingDto extends BuildingForm {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static BuildingDto of(Building building) {
        BuildingDto dto = new BuildingDto();
        dto.setId(building.getId());
        dto.setCode(building.getCode());
        dto.setName(building.getName());
        dto.setAddress(building.getAddress());
        dto.setAddressAds(building.getAddressAds());
        dto.setAddressAdsOid(building.getAddressAdsOid());
        dto.setIsDormitory(building.getIsDormitory());
        dto.setVersion(building.getVersion());
        return dto;
    }
}
