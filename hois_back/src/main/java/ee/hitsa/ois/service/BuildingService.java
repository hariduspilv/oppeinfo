package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.RoomEquipment;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.BuildingRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.RoomRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.RoomForm;
import ee.hitsa.ois.web.commandobject.RoomSearchCommand;
import ee.hitsa.ois.web.dto.BuildingDto;

@Transactional
@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    ClassifierRepository classifierRepository;
    @Autowired
    RoomRepository roomRepository;

    public Page<BuildingDto> findAllBuildings(Long schoolId, Pageable pageable) {
        return buildingRepository.findAllBySchool_id(schoolId, pageable);
    }

    public Building save(Building building) {
        return buildingRepository.save(building);
    }

    public void delete(Building building) {
        EntityUtil.deleteEntity(buildingRepository, building);
    }

    public Page<Room> getAllRooms(Long buildingId, Pageable pageable) {
        return roomRepository.findAllByBuilding_id(buildingId, pageable);
    }

    public Page<Room> findAllRooms(Long schoolId, RoomSearchCommand criteria, Pageable pageable) {
        return roomRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("building").get("school").get("id"), schoolId));

            propertyContains(() -> root.get("name"), cb, criteria.getName(), filters::add);
            propertyContains(() -> root.get("code"), cb, criteria.getCode(), filters::add);
            propertyContains(() -> root.get("building").get("name"), cb, criteria.getBuildingName(), filters::add);
            propertyContains(() -> root.get("building").get("code"), cb, criteria.getBuildingCode(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable);
    }

    public Room save(Room room, RoomForm form) {
        List<RoomForm.RoomEquipmentCommand> newRoomEquipment = form.getRoomEquipment();

        if(newRoomEquipment != null) {
            // check for duplicate rows
            if(newRoomEquipment.stream().map(RoomForm.RoomEquipmentCommand::getEquipmentCode).collect(Collectors.toSet()).size() != newRoomEquipment.size()) {
                throw new IllegalArgumentException("Duplicate values in equipment list");
            }

            List<RoomEquipment> storedRoomEquipment = room.getRoomEquipment();
            if(storedRoomEquipment == null) {
                room.setRoomEquipment(storedRoomEquipment = new ArrayList<>());
            }
            Map<String, RoomEquipment> roomEquipmentCodes = storedRoomEquipment.stream().collect(Collectors.toMap(re -> re.getEquipmentCode().getCode(), re -> re));

            for(RoomForm.RoomEquipmentCommand roomEquipment : newRoomEquipment) {
                String roomEquipmentCode = roomEquipment.getEquipmentCode();
                RoomEquipment re = roomEquipmentCodes.remove(roomEquipmentCode);
                if(re == null) {
                    // add new equipment to room
                    Classifier c = classifierRepository.getOne(roomEquipmentCode);
                    // verify that domain code is from SEADMED and raise IllegalArgumentException if wrong
                    if(!MainClassCode.SEADMED.name().equals(c.getMainClassCode())) {
                        throw new IllegalArgumentException("Wrong classifier code: "+c.getMainClassCode());
                    }
                    re = new RoomEquipment();
                    re.setRoom(room);
                    re.setEquipmentCode(c);
                    re.setEquipmentCount(roomEquipment.getEquipmentCount());
                    storedRoomEquipment.add(re);
                } else if(!re.getEquipmentCount().equals(roomEquipment.getEquipmentCount())) {
                    // update count for existing
                    re.setEquipmentCount(roomEquipment.getEquipmentCount());
                }
            }

            // remove possible letfovers
            Set<String> newRoomEquipmentCodes = newRoomEquipment.stream().map(RoomForm.RoomEquipmentCommand::getEquipmentCode).collect(Collectors.toSet());
            storedRoomEquipment.removeIf(re -> !newRoomEquipmentCodes.contains(re.getEquipmentCode().getCode()));
        }

        return roomRepository.save(room);
    }

    public void delete(Room room) {
        roomRepository.delete(room);
    }
}
