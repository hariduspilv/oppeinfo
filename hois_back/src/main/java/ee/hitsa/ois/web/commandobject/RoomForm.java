package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.NotEmpty;

public class RoomForm extends VersionedCommand {

    @NotEmpty
    @Size(max = 20)
    private String code;
    @Size(max = 255)
    private String name;
    @Min(0)
    private Long seats;
    private Boolean isStudy;
    private List<RoomEquipmentCommand> roomEquipment;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSeats() {
        return seats;
    }

    public void setSeats(Long seats) {
        this.seats = seats;
    }

    public Boolean getIsStudy() {
        return isStudy;
    }

    public void setIsStudy(Boolean isStudy) {
        this.isStudy = isStudy;
    }

    public List<RoomEquipmentCommand> getRoomEquipment() {
        return roomEquipment;
    }

    public void setRoomEquipment(List<RoomEquipmentCommand> roomEquipment) {
        this.roomEquipment = roomEquipment;
    }

    public static class RoomEquipmentCommand {

        @NotEmpty
        private String equipment;
        @NotNull
        @Min(0)
        private Long equipmentCount;

        public String getEquipment() {
            return equipment;
        }

        public void setEquipment(String equipment) {
            this.equipment = equipment;
        }

        public Long getEquipmentCount() {
            return equipmentCount;
        }

        public void setEquipmentCount(Long equipmentCount) {
            this.equipmentCount = equipmentCount;
        }
    }
}
