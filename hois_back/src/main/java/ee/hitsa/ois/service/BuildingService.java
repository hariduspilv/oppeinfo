package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.RoomEquipment;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.BuildingForm;
import ee.hitsa.ois.web.commandobject.RoomForm;
import ee.hitsa.ois.web.commandobject.RoomForm.RoomEquipmentCommand;
import ee.hitsa.ois.web.commandobject.RoomSearchCommand;
import ee.hitsa.ois.web.dto.RoomSearchDto;

@Transactional
@Service
public class BuildingService {
    private static final String LIST_SELECT =
            "b.id b_id, b.name b_name, b.code b_code, b.address b_address, " +
            "r.id r_id, r.name r_name, r.code r_code, r.seats, r.is_study, b.is_dormitory";
    private static final String LIST_FROM =
            "from building b left join room r on r.building_id = b.id";

    @Autowired
    private EntityManager em;

    /**
     * Create new building
     *
     * @param user
     * @param form
     * @return
     */
    public Building create(HoisUserDetails user, BuildingForm form) {
        Building building = new Building();
        building.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(building, form);
    }

    /**
     * Store building
     *
     * @param building
     * @param form
     * @return
     */
    public Building save(Building building, BuildingForm form) {
        EntityUtil.bindToEntity(form, building);

        checkBuildingUniqueness(EntityUtil.getId(building.getSchool()), building.getCode(), building.getId());

        return EntityUtil.save(building, em);
    }

    /**
     * Delete building
     *
     * @param user
     * @param building
     */
    public void delete(HoisUserDetails user, Building building) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(building, em);
    }

    public Page<RoomSearchDto> searchRooms(Long schoolId, RoomSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(LIST_FROM).sort(pageable);

        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", schoolId);

        qb.optionalContains("b.name", "buildingName", criteria.getBuildingName());
        qb.optionalContains("b.code", "buildingCode", criteria.getBuildingCode());
        qb.optionalContains("r.name", "roomName", criteria.getName());
        qb.optionalContains("r.code", "roomCode", criteria.getCode());
        
        Page<Object> result = JpaQueryUtil.pagingResult(qb, LIST_SELECT, em, pageable);
        // load room equipment with single query
        List<Long> roomIds = StreamUtil.toMappedList(r -> resultAsLong(r, 4), 
                result.getContent().stream().filter(r -> resultAsLong(r, 4) != null));
        Map<Long, List<RoomEquipment>> equipment = JpaQueryUtil.loadRelationChilds(
                RoomEquipment.class, roomIds, em, "room", "id").stream()
                .collect(Collectors.groupingBy(re -> EntityUtil.getId(re.getRoom())));
        return result.map(r -> {
            RoomSearchDto dto = new RoomSearchDto();
            dto.setBuilding(resultAsLong(r, 0));
            dto.setBuildingName(resultAsString(r, 1));
            dto.setBuildingCode(resultAsString(r, 2));
            dto.setBuildingAddress(resultAsString(r, 3));
            dto.setId(resultAsLong(r, 4));
            dto.setName(resultAsString(r, 5));
            dto.setCode(resultAsString(r, 6));
            dto.setSeats(resultAsLong(r, 7));
            dto.setIsStudy(resultAsBoolean(r, 8));
            dto.setIsBoardingSchool(resultAsBoolean(r, 9));
            dto.setRoomEquipment(StreamUtil.toMappedList(
                    re -> EntityUtil.bindToDto(re, new RoomEquipmentCommand()), 
                    equipment.get(resultAsLong(r, 4))));
            return dto;
        });
    }
    
    /**
     * Create new room
     *
     * @param user
     * @param form
     * @return
     */
    public Room create(HoisUserDetails user, RoomForm form) {
        return save(user, new Room(), form);
    }

    /**
     * Store room
     *
     * @param user
     * @param room
     * @param form
     * @return
     * @throws AssertionFailedException if there is duplicate equipment in room
     */
    public Room save(HoisUserDetails user, Room room, RoomForm form) {
        EntityUtil.bindToEntity(form, room, "roomEquipment");
        if(!Objects.equals(form.getBuilding(), EntityUtil.getNullableId(room.getBuilding()))) {
            Building building = em.getReference(Building.class, form.getBuilding());
            UserUtil.assertSameSchool(user, building.getSchool());
            room.setBuilding(building);
        }

        checkRoomUniqueness(EntityUtil.getId(room.getBuilding()), room.getCode(), room.getId());

        List<RoomForm.RoomEquipmentCommand> newRoomEquipment = StreamUtil.nullSafeList(form.getRoomEquipment());

        // check for duplicate rows
        AssertionFailedException.throwIf(StreamUtil.toMappedSet(RoomForm.RoomEquipmentCommand::getEquipment, newRoomEquipment).size() != newRoomEquipment.size(), "Duplicate values in equipment list");

        EntityUtil.setUsername(user.getUsername(), em);
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
                storedRoomEquipment.add(re);
            }
            // update count
            re.setEquipmentCount(roomEquipment.getEquipmentCount());
        }
        // remove possible leftovers
        Set<String> newRoomEquipmentCodes = StreamUtil.toMappedSet(RoomForm.RoomEquipmentCommand::getEquipment, newRoomEquipment);
        storedRoomEquipment.removeIf(re -> !newRoomEquipmentCodes.contains(EntityUtil.getCode(re.getEquipment())));
        return EntityUtil.save(room, em);
    }

    /**
     * Delete room
     *
     * @param user
     * @param room
     */
    public void delete(HoisUserDetails user, Room room) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(room, em);
    }

    /**
     * Is building uniquely identifiable?
     *
     * @param schoolId
     * @param code
     * @param buildingId
     */
    public void checkBuildingUniqueness(Long schoolId, String code, Long buildingId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from building b");
        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("upper(b.code) = :code", "code", code.toUpperCase());
        qb.optionalCriteria("b.id != :buildingId", "buildingId", buildingId);
        requireNoRows(qb, "building.alreadyexist");
    }

    /**
     * Is room uniquely identifiable?
     *
     * @param buildingId
     * @param code
     * @param roomId
     */
    public void checkRoomUniqueness(Long buildingId, String code, Long roomId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from room r");
        qb.requiredCriteria("r.building_id = :buildingId", "buildingId", buildingId);
        qb.requiredCriteria("upper(r.code) = :code", "code", code.toUpperCase());
        qb.optionalCriteria("r.id != :roomId", "roomId", roomId);
        requireNoRows(qb, "room.alreadyexist");
    }

    private void requireNoRows(JpaNativeQueryBuilder qb, String message) {
        if(!qb.select("1", em).setMaxResults(1).getResultList().isEmpty()) {
            throw new ValidationFailedException(message);
        }
    }
}
