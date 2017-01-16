package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.util.EntityUtil;
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
        return EntityUtil.bindToDto(building, new BuildingDto());
    }
}
