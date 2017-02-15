package ee.hitsa.ois.web.dto;

import java.util.List;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.RoomEquipment;

public class RoomSearchDto extends RoomDto {

    private String buildingName;
    private String buildingCode;
    private String buildingAddress;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public static RoomSearchDto of(Building building, Room room, List<RoomEquipment> equipment) {
        RoomSearchDto dto = new RoomSearchDto();
        if(room != null) {
            fill(room, equipment, dto);
        }
        dto.setBuilding(building.getId());
        dto.setBuildingName(building.getName());
        dto.setBuildingCode(building.getCode());
        dto.setBuildingAddress(building.getAddress());
        return dto;
    }
}
