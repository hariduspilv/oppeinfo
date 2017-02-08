package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RoomEquipment extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Room room;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_code", nullable = false, updatable = false)
    private Classifier equipment;
    private Long equipmentCount;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Classifier getEquipment() {
        return equipment;
    }

    public void setEquipment(Classifier equipment) {
        this.equipment = equipment;
    }

    public Long getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Long equipmentCount) {
        this.equipmentCount = equipmentCount;
    }
}
