package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RoomEquipment extends BaseEntityWithId {

    @ManyToOne(optional = false)
    private Room room;
    @ManyToOne(optional = false)
    @JoinColumn(name = "equipment_code")
    private Classifier equipmentCode;
    private Long equipmentCount;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Classifier getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(Classifier equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public Long getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Long equipmentCount) {
        this.equipmentCount = equipmentCount;
    }
}
