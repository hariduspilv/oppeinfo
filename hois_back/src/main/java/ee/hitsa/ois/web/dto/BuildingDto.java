package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.web.commandobject.BuildingForm;

public class BuildingDto extends BuildingForm {
    private Long id;

    public BuildingDto() {
    }

    public BuildingDto(Long id, String code, String name, String address, Long version) {
        this.id = id;
        setCode(code);
        setName(name);
        setAddress(address);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static BuildingDto of(Building building) {
        return new BuildingDto(building.getId(), building.getCode(), building.getName(), building.getAddress(), building.getVersion());
    }
}
