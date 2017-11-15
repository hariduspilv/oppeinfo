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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.service.TimetableEventService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
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

    @GetMapping
    public Page<TimetableEventSearchDto> search(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return timetableEventService.search(criteria, pageable);
    }
    
    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody TimetableSingleEventForm form) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return HttpUtil.created(timetableEventService.createEvent(form));
    }
    
    @GetMapping("/timetableSearch")
    public List<TimetableEventSearchDto> searchTimetable(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.searchTimetable(criteria);
    }
    
    @GetMapping("/timetableSearch/calendar")
    public TimetableCalendarDto searchTimetableIcs(@Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        Language lang = language.equals("et") ? Language.ET : Language.EN;
        return timetableEventService.getSearchCalendar(criteria, lang);
    }

    @GetMapping("/timetableByGroup")
    public TimetableByGroupDto groupTimetableForWeek(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.groupTimetable(criteria);
    }
   
    @GetMapping("/timetableByGroup/calendar")
    public TimetableCalendarDto groupTimetableIcs(@Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        Language lang = language.equals("et") ? Language.ET : Language.EN;
        return timetableEventService.getGroupCalendar(criteria, lang);
    }

    @GetMapping("/timetableByTeacher")
    public TimetableByTeacherDto teacherTimetableForWeek(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.teacherTimetable(criteria);
    }
    
    @GetMapping("/timetableByTeacher/calendar")
    public TimetableCalendarDto teacherTimetableIcs(@Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        Language lang = language.equals("et") ? Language.ET : Language.EN;
        return timetableEventService.getTeacherCalendar(criteria, lang);
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
        Language lang = language.equals("et") ? Language.ET : Language.EN; 
        return timetableEventService.getStudentCalendar(criteria, lang);
    }
    
    @GetMapping("/timetableByRoom")
    public TimetableByRoomDto roomTimetableForWeek(@Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.roomTimetable(criteria);
    }
    
    @GetMapping("/timetableByRoom/calendar")
    public TimetableCalendarDto roomTimetableIcs(@Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        Language lang = language.equals("et") ? Language.ET : Language.EN;
        return timetableEventService.getRoomCalendar(criteria, lang);
    }
}
