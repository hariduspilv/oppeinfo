package ee.hitsa.ois.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.TimetableEventService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableSingleEventForm;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByStudentDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;

@RestController
@RequestMapping("/timetableevents")
public class TimetableEventController {

    @Autowired
    private TimetableEventService timetableEventService;

    @GetMapping("/searchFormData")
    public Map<String, ?> searchFormData(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableEventService.searchFormData(user.getSchoolId());
    }

    @GetMapping
    public Page<TimetableEventSearchDto> search(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableEventService.search(criteria, pageable);
    }
    
    @GetMapping("/timetableSearch")
    public List<TimetableEventSearchDto> searchTimetable(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.searchTimetable(criteria);
    }

    @GetMapping("/timetableByGroup")
    public TimetableByGroupDto groupTimetableForWeek(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.groupTimetableForWeek(criteria);
    }
   

    @GetMapping("/timetableByTeacher")
    public TimetableByTeacherDto teacherTimetableForWeek(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.teacherTimetableForWeek(criteria);
    }
    
    @GetMapping("/timetableByStudent")
    public TimetableByStudentDto studentTimetableForWeek(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return timetableEventService.studentTimetableForWeek(criteria);
    }
    
    @GetMapping("/timetableByRoom")
    public TimetableByRoomDto roomTimetableForWeek(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.roomTimetableForWeek(criteria);
    }
    
    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody TimetableSingleEventForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(timetableEventService.createEvent(form));
    }
}
