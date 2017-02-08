package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.BuildingService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.BuildingForm;
import ee.hitsa.ois.web.commandobject.RoomForm;
import ee.hitsa.ois.web.commandobject.RoomSearchCommand;
import ee.hitsa.ois.web.dto.BuildingDto;
import ee.hitsa.ois.web.dto.RoomDto;
import ee.hitsa.ois.web.dto.RoomSearchDto;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private SchoolRepository schoolRepository;

    // building: search/get/save/update/delete
    @GetMapping(value = "")
    public Page<BuildingDto> searchBuildings(HoisUserDetails user, Pageable pageable) {
        Long schoolId = user.getSchoolId();
        return buildingService.findAllBuildings(schoolId, pageable);
    }

    @GetMapping(value = "/{id}")
    public BuildingDto getBuilding(HoisUserDetails user, @WithEntity("id") Building building) {
        assertSameSchool(user, building);
        return BuildingDto.of(building);
    }

    @PostMapping(value = "")
    public BuildingDto createBuilding(HoisUserDetails user, @Valid @RequestBody BuildingForm form) {
        Building building = EntityUtil.bindToEntity(form, new Building());
        building.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return getBuilding(user, buildingService.save(building));
    }

    @PutMapping(value = "/{id}")
    public BuildingDto updateBuilding(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Building building, @Valid @RequestBody BuildingForm form) {
        assertSameSchool(user, building);
        EntityUtil.bindToEntity(form, building);
        return getBuilding(user, buildingService.save(building));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteBuilding(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Building building, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertSameSchool(user, building);
        buildingService.delete(building);
    }

    // room: search/get/save/update/delete
    @GetMapping(value = "/{id}/rooms")
    public Page<RoomDto> getBuildingRooms(HoisUserDetails user, @WithEntity("id") Building building, Pageable pageable) {
        assertSameSchool(user, building);
        return buildingService.getAllRooms(EntityUtil.getId(building), pageable);
    }

    @GetMapping(value = "/searchrooms")
    public Page<RoomSearchDto> searchRooms(HoisUserDetails user, @Valid RoomSearchCommand searchCommand, Pageable pageable) {
        return buildingService.findAllRooms(user.getSchoolId(), searchCommand, pageable);
    }

    @GetMapping(value = "/{buildingId}/rooms/{id}")
    public RoomDto getRoom(HoisUserDetails user, @WithEntity("buildingId") Building building, @WithEntity("id") Room room) {
        assertSameSchool(user, building);
        assertSameBuilding(building, room);
        return RoomDto.of(room);
    }

    @PostMapping(value = "/{id}/rooms")
    public RoomDto createRoom(HoisUserDetails user, @WithEntity("id") Building building, @Valid @RequestBody RoomForm form) {
        assertSameSchool(user, building);
        Room room = EntityUtil.bindToEntity(form, new Room(), "roomEquipment");
        room.setBuilding(building);
        return getRoom(user, building, buildingService.save(room, form));
    }

    @PutMapping(value = "/{buildingId}/rooms/{id}")
    public RoomDto updateRoom(HoisUserDetails user,  @WithEntity("buildingId") Building building, @WithVersionedEntity(value = "id", versionRequestBody = true) Room room, @Valid @RequestBody RoomForm form) {
        assertSameSchool(user, building);
        assertSameBuilding(building, room);
        EntityUtil.bindToEntity(form, room, "roomEquipment");
        return getRoom(user, building, buildingService.save(room, form));
    }

    @DeleteMapping(value = "/{buildingId}/rooms/{id}")
    public void deleteRoom(HoisUserDetails user,  @WithEntity("buildingId") Building building, @WithVersionedEntity(value = "id", versionRequestParam = "version") Room room, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertSameSchool(user, building);
        assertSameBuilding(building, room);
        buildingService.delete(room);
    }

    private static void assertSameBuilding(Building building, Room room) {
        Long buildingId = EntityUtil.getId(building);
        if(buildingId == null || !buildingId.equals(EntityUtil.getNullableId(room.getBuilding()))) {
            throw new IllegalArgumentException();
        }
    }

    private static void assertSameSchool(HoisUserDetails user, Building building) {
        Long schoolId = user.getSchoolId();
        if(schoolId == null || !schoolId.equals(EntityUtil.getNullableId(building.getSchool()))) {
            throw new IllegalArgumentException();
        }
    }
}
