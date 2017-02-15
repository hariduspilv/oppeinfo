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
public class BuildingController {

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private SchoolRepository schoolRepository;

    // building: get/save/update/delete
    @GetMapping("/buildings/{id}")
    public BuildingDto getBuilding(HoisUserDetails user, @WithEntity("id") Building building) {
        assertSameSchool(user, building);
        return BuildingDto.of(building);
    }

    @PostMapping("/buildings")
    public BuildingDto createBuilding(HoisUserDetails user, @Valid @RequestBody BuildingForm form) {
        Building building = EntityUtil.bindToEntity(form, new Building());
        building.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return getBuilding(user, buildingService.save(building));
    }

    @PutMapping("/buildings/{id}")
    public BuildingDto updateBuilding(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Building building, @Valid @RequestBody BuildingForm form) {
        assertSameSchool(user, building);
        EntityUtil.bindToEntity(form, building);
        return getBuilding(user, buildingService.save(building));
    }

    @DeleteMapping("/buildings/{id}")
    public void deleteBuilding(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Building building, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertSameSchool(user, building);
        buildingService.delete(building);
    }

    // room: search/get/save/update/delete
    @GetMapping("/rooms")
    public Page<RoomSearchDto> searchRooms(HoisUserDetails user, @Valid RoomSearchCommand searchCommand, Pageable pageable) {
        return buildingService.findAllRooms(user.getSchoolId(), searchCommand, pageable);
    }

    @GetMapping("/rooms/{id}")
    public RoomDto getRoom(HoisUserDetails user, @WithEntity("id") Room room) {
        assertSameSchool(user, room);
        return RoomDto.of(room);
    }

    @PostMapping("/rooms")
    public RoomDto createRoom(HoisUserDetails user, @Valid @RequestBody RoomForm form) {
        return getRoom(user, buildingService.save(new Room(), form));
    }

    @PutMapping("/rooms/{id}")
    public RoomDto updateRoom(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Room room, @Valid @RequestBody RoomForm form) {
        assertSameSchool(user, room);
        return getRoom(user, buildingService.save(room, form));
    }

    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Room room, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertSameSchool(user, room);
        buildingService.delete(room);
    }

    private static void assertSameSchool(HoisUserDetails user, Building building) {
        Long schoolId = user.getSchoolId();
        if(schoolId == null || !schoolId.equals(EntityUtil.getNullableId(building.getSchool()))) {
            throw new IllegalArgumentException();
        }
    }

    private static void assertSameSchool(HoisUserDetails user, Room room) {
        Long schoolId = user.getSchoolId();
        if(schoolId == null || !schoolId.equals(EntityUtil.getNullableId(room.getBuilding().getSchool()))) {
            throw new IllegalArgumentException();
        }
    }
}
