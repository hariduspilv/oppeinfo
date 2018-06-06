package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.service.BuildingService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.BuildingForm;
import ee.hitsa.ois.web.commandobject.RoomForm;
import ee.hitsa.ois.web.commandobject.RoomSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.BuildingDto;
import ee.hitsa.ois.web.dto.RoomDto;
import ee.hitsa.ois.web.dto.RoomSearchDto;

@RestController
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    // building: get/save/update/delete
    @GetMapping("/buildings/{id:\\d+}")
    public BuildingDto getBuilding(HoisUserDetails user, @WithEntity Building building) {
        UserUtil.assertIsSchoolAdmin(user, building.getSchool());
        return BuildingDto.of(building);
    }

    @PostMapping("/buildings")
    public HttpUtil.CreatedResponse createBuilding(HoisUserDetails user, @Valid @RequestBody BuildingForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(buildingService.create(user, form));
    }

    @PutMapping("/buildings/{id:\\d+}")
    public BuildingDto saveBuilding(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Building building, @Valid @RequestBody BuildingForm form) {
        UserUtil.assertIsSchoolAdmin(user, building.getSchool());
        return getBuilding(user, buildingService.save(building, form));
    }

    @DeleteMapping("/buildings/{id:\\d+}")
    public void deleteBuilding(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Building building, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user, building.getSchool());
        buildingService.delete(user, building);
    }

    @GetMapping("/buildings/unique")
    public boolean checkBuildingUniqueness(HoisUserDetails user, UniqueCommand criteria) {
        UserUtil.assertIsSchoolAdmin(user);
        try {
            if(StringUtils.hasLength(criteria.getParamValue())) {
                buildingService.checkBuildingUniqueness(user.getSchoolId(), criteria.getParamValue(), criteria.getId());
            }
        } catch(@SuppressWarnings("unused") ValidationFailedException e) {
            return false;
        }
        return true;
    }

    // room: search/get/save/update/delete
    @GetMapping("/rooms")
    public Page<RoomSearchDto> searchRooms(HoisUserDetails user, @Valid RoomSearchCommand searchCommand, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return buildingService.searchRooms(user.getSchoolId(), searchCommand, pageable);
    }

    @GetMapping("/rooms/{id:\\d+}")
    public RoomDto getRoom(HoisUserDetails user, @WithEntity Room room) {
        UserUtil.assertIsSchoolAdmin(user, room.getBuilding().getSchool());
        return RoomDto.of(room);
    }

    @PostMapping("/rooms")
    public HttpUtil.CreatedResponse createRoom(HoisUserDetails user, @Valid @RequestBody RoomForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(buildingService.create(user, form));
    }

    @PutMapping("/rooms/{id:\\d+}")
    public RoomDto saveRoom(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Room room, @Valid @RequestBody RoomForm form) {
        UserUtil.assertIsSchoolAdmin(user, room.getBuilding().getSchool());
        return getRoom(user, buildingService.save(user, room, form));
    }

    @DeleteMapping("/rooms/{id:\\d+}")
    public void deleteRoom(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Room room, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user, room.getBuilding().getSchool());
        buildingService.delete(user, room);
    }

    @GetMapping("/rooms/unique/{buildingId:\\d+}")
    public boolean checkRoomUniqueness(HoisUserDetails user, @PathVariable("buildingId") Long buildingId, UniqueCommand criteria) {
        UserUtil.assertIsSchoolAdmin(user);
        try {
            if(StringUtils.hasLength(criteria.getParamValue()) && !Long.valueOf(0).equals(buildingId)) {
                buildingService.checkRoomUniqueness(buildingId, criteria.getParamValue(), criteria.getId());
            }
        } catch(@SuppressWarnings("unused") ValidationFailedException e) {
            return false;
        }
        return true;
    }
}
