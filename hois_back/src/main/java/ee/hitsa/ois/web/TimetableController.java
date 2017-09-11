package ee.hitsa.ois.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.service.TimetableService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.TimetableRoomAndTimeForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableManagementSearchCommand;
import ee.hitsa.ois.web.dto.timetable.TimetableDatesDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableManagementSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetablePlanDto;

@RestController
@RequestMapping("/timetables")
public class TimetableController {

    @Autowired
    TimetableService timetableService;

    @GetMapping("/{id:\\d+}")
    public TimetableDto edit(HoisUserDetails user, @WithEntity("id") Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.get(user, timetable);
    }

    @GetMapping("/{id:\\d+}/view")
    public TimetableDto get(HoisUserDetails user, @WithEntity("id") Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.getForView(timetable);
    }

    @GetMapping("/{id:\\d+}/createPlan")
    public TimetablePlanDto createPlan(HoisUserDetails user, @WithEntity("id") Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.getPlan(timetable);
    }

    @GetMapping("/managementSearchFormData")
    public Map<String, ?> timetableManagement(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.managementSearchFormData(user.getSchoolId());
    }

    @GetMapping("/searchTimetableForManagement")
    public Page<TimetableManagementSearchDto> timetableForManagement(HoisUserDetails user,
            @Valid TimetableManagementSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.searchTimetableForManagement(criteria, pageable, user);
    }

    @GetMapping("/blockedDatesForPeriod")
    public List<TimetableDatesDto> blockedDatesForPeriod(HoisUserDetails user,
            @RequestParam("studyPeriod") Long studyPeriod, @RequestParam("code") String code,
            @RequestParam(name = "currentTimetable", required = false) Long currentTimetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.getBlockedDatesForPeriod(user, studyPeriod, code, currentTimetable);
    }

    @PostMapping()
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody TimetableEditForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(timetableService.createTimetable(user, form));
    }

    @PostMapping("/saveEvent")
    public TimetablePlanDto saveEvent(HoisUserDetails user, @Valid @RequestBody TimetableEventForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        Timetable timetable = timetableService.saveEvent(form);
        //TODO: create a different smaller query for getting updated subjects after saving a subject
        return createPlan(user, timetable);
    }
    
    @PostMapping("/deleteEvent")
    public TimetablePlanDto deleteEvent(HoisUserDetails user, @Valid @RequestBody TimetableRoomAndTimeForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return createPlan(user, timetableService.deleteEvent(form));
    }
    
    @PostMapping("/saveEventRoomsAndTimes")
    public TimetablePlanDto saveEventRoomsAndTimes(HoisUserDetails user, @Valid @RequestBody TimetableRoomAndTimeForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return createPlan(user, timetableService.saveEventRoomsAndTimes(form));
    }

    @PutMapping("/{id:\\d+}")
    public TimetableDto update(HoisUserDetails user, @Valid @RequestBody TimetableEditForm form,
            @WithEntity("id") Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(user, timetableService.save(user, form, timetable));
    }
}
