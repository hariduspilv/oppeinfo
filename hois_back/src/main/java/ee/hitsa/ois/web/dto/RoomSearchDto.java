package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Room;

public class RoomSearchDto extends RoomDto {

    private Long buildingId;
    private String buildingName;
    private String buildingCode;
    private String buildingAddress;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

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

    public static RoomSearchDto of(Room room) {
        RoomSearchDto dto = fill(room, new RoomSearchDto());
        Building building = room.getBuilding();
        dto.setBuildingId(building.getId());
        dto.setBuildingName(building.getName());
        dto.setBuildingCode(building.getCode());
        dto.setBuildingAddress(building.getAddress());
        return dto;
    }
}
