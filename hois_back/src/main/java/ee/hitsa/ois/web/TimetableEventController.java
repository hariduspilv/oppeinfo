package ee.hitsa.ois.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        timetableEventService.isAdminOrIsTeachersEvent(user, eventTime);
        return timetableEventService.get(eventTime);
    }

    @GetMapping
    public Page<TimetableEventSearchDto> search(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return timetableEventService.search(criteria, pageable, user.getSchoolId());
    }
    
    @PostMapping
    public TimetableSingleEventForm create(HoisUserDetails user, @Valid @RequestBody TimetableSingleEventForm form) {
        timetableEventService.isAdminOrIsTeachersEvent(user, user.getSchoolId(), form.getTeachers());
        return timetableEventService.getTimetableSingleEventForm(timetableEventService.createEvent(form, user.getSchoolId()));
    }
    
    @PutMapping("/{id:\\d+}")
    public TimetableSingleEventForm update(HoisUserDetails user, @Valid @RequestBody TimetableSingleEventForm form) {
        timetableEventService.isAdminOrIsTeachersEvent(user, user.getSchoolId(), form.getTeachers());
        return timetableEventService.getTimetableSingleEventForm(timetableEventService.updateEvent(form));
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void deleteEvent(HoisUserDetails user, @WithEntity TimetableEventTime timetableEventTime) {
        timetableEventService.isAdminOrIsTeachersEvent(user, timetableEventTime);
        timetableEventService.delete(user, timetableEventTime);
    }

    @GetMapping("/timetableSearch")
    public List<TimetableEventSearchDto> searchTimetable(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.searchTimetable(criteria, user.getSchoolId());
    }
    
    @GetMapping("/timetableSearch/calendar")
    public TimetableCalendarDto searchTimetableIcs(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        return timetableEventService.getSearchCalendar(criteria, language, user.getSchoolId());
    }
    
    @GetMapping("/timetableByGroup/{school:\\d+}")
    public TimetableByGroupDto groupTimetableForWeek(@PathVariable("school") Long school, @Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.groupTimetable(criteria, school);
    }
   
    @GetMapping("/timetableByGroup/calendar/{school:\\d+}")
    public TimetableCalendarDto groupTimetableIcs(@PathVariable("school") Long school, @Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        return timetableEventService.getGroupCalendar(criteria, language, school);
    }

    @GetMapping("/timetableByTeacher/{school:\\d+}")
    public TimetableByTeacherDto teacherTimetableForWeek(@PathVariable("school") Long school, @Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.teacherTimetable(criteria, school);
    }
    
    @GetMapping("/timetableByTeacher/calendar/{school:\\d+}")
    public TimetableCalendarDto teacherTimetableIcs(@PathVariable("school") Long school, @Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        return timetableEventService.getTeacherCalendar(criteria, language, school);
    }
    
    @GetMapping("/timetableByStudent")
    public TimetableByStudentDto studentTimetableForWeek(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return timetableEventService.studentTimetable(criteria, user.getSchoolId());
    }
    
    @GetMapping("/timetableByStudent/calendar")
    public TimetableCalendarDto studentTimetableIcs(HoisUserDetails user,
            @Valid TimetableEventSearchCommand criteria, @RequestParam("lang") String language) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return timetableEventService.getStudentCalendar(criteria, language, user.getSchoolId());
    }
    
    @GetMapping("/timetableByRoom/{school:\\d+}")
    public TimetableByRoomDto roomTimetableForWeek(@PathVariable("school") Long school, @Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.roomTimetable(criteria, school);
    }
    
    @GetMapping("/timetableByRoom/calendar/{school:\\d+}")
    public TimetableCalendarDto roomTimetableIcs(@PathVariable("school") Long school, @Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        return timetableEventService.getRoomCalendar(criteria, language, school);
    }
}
