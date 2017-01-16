package ee.hitsa.ois.web.dto;

import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Room;
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
        return fill(room, new RoomDto());
    }

    protected static <DTO extends RoomDto> DTO fill(Room room, DTO dto) {
        EntityUtil.bindToDto(room, dto, "roomEquipment");
        dto.setRoomEquipment(room.getRoomEquipment().stream().map(re -> {
            RoomEquipmentCommand e = new RoomEquipmentCommand();
            e.setEquipmentCode(re.getEquipmentCode().getCode());
            e.setEquipmentCount(re.getEquipmentCount());
            return e;
        }).collect(Collectors.toList()));
        return dto;
    }
}
