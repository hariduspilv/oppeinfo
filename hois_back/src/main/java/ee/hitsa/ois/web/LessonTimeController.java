package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

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

import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.LessonTimeService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.timetable.LessonTimeSearchCommand;
import ee.hitsa.ois.web.dto.timetable.LessonTimeGroupsDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeSearchDto;

@RestController
@RequestMapping("/lessontimes")
public class LessonTimeController {

    @Autowired
    private LessonTimeService lessonTimeService;

    @GetMapping
    public Page<LessonTimeSearchDto> search(HoisUserDetails user, @Valid LessonTimeSearchCommand criteria, Pageable pageable) {
        return lessonTimeService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public LessonTimeGroupsDto get(@WithEntity LessonTime lessonTime, HoisUserDetails user) {
        UserUtil.assertSameSchool(user, lessonTime.getSchool());
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUNDAEG);
        return lessonTimeService.getLessonTimeBuildingGroupsDto(lessonTime.getLessonTimeBuildingGroup().getValidFrom(), user.getSchoolId());
    }

    @PostMapping
    public LessonTimeGroupsDto create(@Valid @RequestBody LessonTimeGroupsDto lessonTimeGroupsDto, HoisUserDetails user) {
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUNDAEG);
        LessonTime lessonTime = lessonTimeService.create(user, lessonTimeGroupsDto);
        if (lessonTime != null) {
            return get(lessonTime, user);
        }
        return null;
    }

    @PutMapping
    public LessonTimeGroupsDto save(@Valid @RequestBody LessonTimeGroupsDto lessonTimeGroupsDto, HoisUserDetails user) {
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUNDAEG);
        LessonTime lessonTime = lessonTimeService.save(user, lessonTimeGroupsDto);
        if (lessonTime != null) {
            return get(lessonTime, user);
        }
        return null;
    }

    @GetMapping("currentPeriod")
    public Map<String, LocalDate> currentPeriodStartDate(HoisUserDetails user) {
        return Collections.singletonMap("periodStart", lessonTimeService.currentPeriodStartDate(user.getSchoolId()));
    }

    @GetMapping("minValidFrom")
    public Map<String, LocalDate> minValidFrom(@RequestParam Set<Long> buildings, @RequestParam(required = false) Long lessonTimeId) {
        return Collections.singletonMap("minValidFrom", lessonTimeService.minValidFrom(buildings, lessonTimeId));
    }
}
