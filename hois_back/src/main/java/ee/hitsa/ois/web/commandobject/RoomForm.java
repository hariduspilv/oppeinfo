package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoomForm extends VersionedCommand {

    @NotNull
    @Size(min = 1, max = 20)
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

        @NotNull
        private String equipmentCode;
        @NotNull
        @Min(0)
        private Long equipmentCount;

        public String getEquipmentCode() {
            return equipmentCode;
        }

        public void setEquipmentCode(String equipmentCode) {
            this.equipmentCode = equipmentCode;
        }

        public Long getEquipmentCount() {
            return equipmentCount;
        }

        public void setEquipmentCount(Long equipmentCount) {
            this.equipmentCount = equipmentCount;
        }
    }
}
