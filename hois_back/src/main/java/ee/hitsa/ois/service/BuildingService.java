package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import ee.hitsa.ois.repository.RoomRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.BuildingForm;
import ee.hitsa.ois.web.commandobject.RoomForm;
import ee.hitsa.ois.web.commandobject.RoomSearchCommand;
import ee.hitsa.ois.web.dto.RoomSearchDto;

@Transactional
@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    public Building create(HoisUserDetails user, BuildingForm form) {
        Building building = new Building();
        building.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return save(building, form);
    }

    public Building save(Building building, BuildingForm form) {
        EntityUtil.bindToEntity(form, building);
        return buildingRepository.save(building);
    }

    public void delete(Building building) {
        EntityUtil.deleteEntity(buildingRepository, building);
    }

    @SuppressWarnings("unchecked")
    public Page<RoomSearchDto> findAllRooms(Long schoolId, RoomSearchCommand criteria, Pageable pageable) {
        Page<Object[]> data = JpaQueryUtil.query(Object[].class, Building.class, (root, query, cb) -> {
            Join<Object, Object> rooms = root.join("rooms", JoinType.LEFT);
            ((CriteriaQuery<Object[]>)query).select(cb.array(root, rooms));

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            propertyContains(() -> rooms.get("name"), cb, criteria.getName(), filters::add);
            propertyContains(() -> rooms.get("code"), cb, criteria.getCode(), filters::add);
            propertyContains(() -> root.get("name"), cb, criteria.getBuildingName(), filters::add);
            propertyContains(() -> root.get("code"), cb, criteria.getBuildingCode(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable, em);

        // load room equipment with single query
        List<Long> roomIds = data.getContent().stream().filter(r -> r[1] != null).map(r -> ((Room)r[1]).getId()).collect(Collectors.toList());
        Map<Long, List<RoomEquipment>> equipment = JpaQueryUtil.loadRelationChilds(RoomEquipment.class, roomIds, em, "room", "id").stream().collect(Collectors.groupingBy(re -> EntityUtil.getId(re.getRoom())));

        return data.map(r -> RoomSearchDto.of((Building)r[0], (Room)r[1], equipment.get(EntityUtil.getNullableId((Room)r[1]))));
    }

    public Room create(HoisUserDetails user, RoomForm form) {
        return save(user, new Room(), form);
    }

    public Room save(HoisUserDetails user, Room room, RoomForm form) {
        EntityUtil.bindToEntity(form, room, "roomEquipment");
        if(!Objects.equals(form.getBuilding(), EntityUtil.getNullableId(room.getBuilding()))) {
            Building building = buildingRepository.getOne(form.getBuilding());
            UserUtil.assertSameSchool(user, building.getSchool());
            room.setBuilding(building);
        }

        List<RoomForm.RoomEquipmentCommand> newRoomEquipment = form.getRoomEquipment();
        if(newRoomEquipment != null) {
            // check for duplicate rows
            if(newRoomEquipment.stream().map(RoomForm.RoomEquipmentCommand::getEquipment).collect(Collectors.toSet()).size() != newRoomEquipment.size()) {
                throw new AssertionFailedException("Duplicate values in equipment list");
            }

            List<RoomEquipment> storedRoomEquipment = room.getRoomEquipment();
            if(storedRoomEquipment == null) {
                room.setRoomEquipment(storedRoomEquipment = new ArrayList<>());
            }
            Map<String, RoomEquipment> roomEquipmentCodes = StreamUtil.toMap(re -> EntityUtil.getCode(re.getEquipment()), storedRoomEquipment);

            for(RoomForm.RoomEquipmentCommand roomEquipment : newRoomEquipment) {
                String roomEquipmentCode = roomEquipment.getEquipment();
                RoomEquipment re = roomEquipmentCodes.remove(roomEquipmentCode);
                if(re == null) {
                    // add new equipment to room
                    re = new RoomEquipment();
                    re.setRoom(room);
                    re.setEquipment(EntityUtil.validateClassifier(em.getReference(Classifier.class, roomEquipmentCode), MainClassCode.SEADMED));
                    re.setEquipmentCount(roomEquipment.getEquipmentCount());
                    storedRoomEquipment.add(re);
                } else if(!re.getEquipmentCount().equals(roomEquipment.getEquipmentCount())) {
                    // update count for existing
                    re.setEquipmentCount(roomEquipment.getEquipmentCount());
                }
            }

            // remove possible letfovers
            Set<String> newRoomEquipmentCodes = newRoomEquipment.stream().map(RoomForm.RoomEquipmentCommand::getEquipment).collect(Collectors.toSet());
            storedRoomEquipment.removeIf(re -> !newRoomEquipmentCodes.contains(EntityUtil.getCode(re.getEquipment())));
        }

        return roomRepository.save(room);
    }

    public void delete(Room room) {
        roomRepository.delete(room);
    }
}
