package ee.hitsa.ois.web.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.RoomEquipment;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.RoomForm;

public class RoomDto extends RoomForm {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static RoomDto of(Room room) {
        return fill(room, room.getRoomEquipment(), new RoomDto());
    }

    protected static <DTO extends RoomDto> DTO fill(Room room, Collection<RoomEquipment> equipment, DTO dto) {
        EntityUtil.bindToDto(room, dto, "roomEquipment");
        dto.setRoomEquipment((equipment != null ? equipment : Collections.emptyList()).stream().map(re -> EntityUtil.bindToDto(re, new RoomEquipmentCommand())).collect(Collectors.toList()));
        return dto;
    }
}
