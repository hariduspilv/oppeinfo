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

import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.service.TimetableEventService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableSingleEventForm;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByStudentDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetableCalendarDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;

@RestController
@RequestMapping("/timetableevents")
public class TimetableEventController {

    @Autowired
    private TimetableEventService timetableEventService;

    @GetMapping("/searchFormData")
    public Map<String, ?> searchFormData(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return timetableEventService.searchFormData(user.getSchoolId());
    }
    
    /*
     * Management of timetable events is handled via timetable event times, because those are the objects that
     * the user is interested in, not the parent objects that hold multiple events 
     */
    @GetMapping("/{id:\\d+}")
    public TimetableSingleEventForm get(HoisUserDetails user, @WithEntity TimetableEventTime eventTime) {
        //UserUtil.assertIsSchoolAdmin(user, eventTime.getTimetableEvent().getTimetableObject().getTimetable().getSchool());
        return timetableEventService.get(eventTime);
    }

    @GetMapping
    public Page<TimetableEventSearchDto> search(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return timetableEventService.search(criteria, pageable, user);
    }
    
    @PostMapping
    public TimetableSingleEventForm create(HoisUserDetails user, @Valid @RequestBody TimetableSingleEventForm form) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return timetableEventService.getTimetableSingleEventForm(timetableEventService.createEvent(form, user.getSchoolId()));
    }
    
    @PutMapping("/{id:\\d+}")
    public TimetableSingleEventForm update(HoisUserDetails user, @Valid @RequestBody TimetableSingleEventForm form) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return timetableEventService.getTimetableSingleEventForm(timetableEventService.updateEvent(form, user.getSchoolId()));
    }
    
    @GetMapping("/timetableSearch")
    public List<TimetableEventSearchDto> searchTimetable(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.searchTimetable(criteria);
    }
    
    @GetMapping("/timetableSearch/calendar")
    public TimetableCalendarDto searchTimetableIcs(@Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        return timetableEventService.getSearchCalendar(criteria, language);
    }

    @GetMapping("/timetableByGroup")
    public TimetableByGroupDto groupTimetableForWeek(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.groupTimetable(criteria);
    }
   
    @GetMapping("/timetableByGroup/calendar")
    public TimetableCalendarDto groupTimetableIcs(@Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        return timetableEventService.getGroupCalendar(criteria, language);
    }

    @GetMapping("/timetableByTeacher")
    public TimetableByTeacherDto teacherTimetableForWeek(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.teacherTimetable(criteria);
    }
    
    @GetMapping("/timetableByTeacher/calendar")
    public TimetableCalendarDto teacherTimetableIcs(@Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        return timetableEventService.getTeacherCalendar(criteria, language);
    }
    
    @GetMapping("/timetableByStudent")
    public TimetableByStudentDto studentTimetableForWeek(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return timetableEventService.studentTimetable(criteria);
    }
    
    @GetMapping("/timetableByStudent/calendar")
    public TimetableCalendarDto studentTimetableIcs(HoisUserDetails user,
            @Valid TimetableEventSearchCommand criteria, @RequestParam("lang") String language) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return timetableEventService.getStudentCalendar(criteria, language);
    }
    
    @GetMapping("/timetableByRoom")
    public TimetableByRoomDto roomTimetableForWeek(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.roomTimetable(criteria);
    }
    
    @GetMapping("/timetableByRoom/calendar")
    public TimetableCalendarDto roomTimetableIcs(@Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        return timetableEventService.getRoomCalendar(criteria, language);
    }
}
