package ee.hitsa.ois.web;

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
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableNewHigherTimeOccupiedCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableNewVocationalTimeOccupiedCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableSingleEventForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableTimeOccupiedCommand;
import ee.hitsa.ois.web.dto.timetable.TimetableByDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByStudentDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetableCalendarDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableTimeOccupiedDto;

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
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        UserUtil.assertSameSchool(user, eventTime.getTimetableEvent().getSchool());
        return timetableEventService.get(user, eventTime);
    }

    @GetMapping
    public Page<TimetableEventSearchDto> search(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return timetableEventService.search(criteria, pageable, user.getSchoolId());
    }
    
    @PostMapping
    public void create(HoisUserDetails user, @Valid @RequestBody TimetableSingleEventForm form) {
        timetableEventService.isAdminOrIsTeachersEvent(user, user.getSchoolId(),
                StreamUtil.toMappedList(t -> t.getTeacher().getId(), form.getTeachers()));
        timetableEventService.createEvent(user, form);
    }
    
    @PutMapping("/{id:\\d+}")
    public TimetableSingleEventForm update(HoisUserDetails user, @Valid @RequestBody TimetableSingleEventForm form) {
        timetableEventService.isAdminOrIsTeachersEvent(user, user.getSchoolId(),
                StreamUtil.toMappedList(t -> t.getTeacher().getId(), form.getTeachers()));
        timetableEventService.updateEvent(form);
        return timetableEventService.getTimetableSingleEventForm(form.getId());
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void deleteEvent(HoisUserDetails user, @WithEntity TimetableEventTime timetableEventTime) {
        timetableEventService.isAdminOrIsTeachersEvent(user, timetableEventTime);
        timetableEventService.delete(user, timetableEventTime);
    }

    @GetMapping("/timetableSearch/{school:\\d+}")
    public TimetableByDto searchTimetable(@PathVariable("school") Long school, @Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.searchTimetable(criteria, school);
    }

    @GetMapping("/timetableSearch/calendar/{school:\\d+}")
    public TimetableCalendarDto searchTimetableIcs(@PathVariable("school") Long school, @Valid TimetableEventSearchCommand criteria,
            @RequestParam("lang") String language) {
        return timetableEventService.getSearchCalendar(criteria, language, school);
    }

    @GetMapping("/timetableSearch/searchFormData/{school:\\d+}/{studyYear:\\d+}")
    public Map<String, ?> searchTimetableFormData(@PathVariable("school") Long school,
            @PathVariable("studyYear") Long studyYearId) {
        return timetableEventService.searchTimetableFormData(school, studyYearId);
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

    @GetMapping("/timetableByStudent/{school:\\d+}")
    public TimetableByStudentDto studentTimetableForWeek(HoisUserDetails user, @PathVariable("school") Long school,
            @Valid TimetableEventSearchCommand criteria) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return timetableEventService.studentTimetable(criteria, school);
    }

    @GetMapping("/timetableByStudent/calendar/{school:\\d+}")
    public TimetableCalendarDto studentTimetableIcs(HoisUserDetails user, @PathVariable("school") Long school,
            @Valid TimetableEventSearchCommand criteria, @RequestParam("lang") String language) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return timetableEventService.getStudentCalendar(criteria, language, school);
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

    @GetMapping("/timetableTimeOccupied")
    public TimetableTimeOccupiedDto timetableTimeOccupied(TimetableTimeOccupiedCommand command) {
        return timetableEventService.timetableTimeOccupied(command);
    }

    @GetMapping("/timetableNewVocationalTimeOccupied")
    public TimetableTimeOccupiedDto timetableTimeOccupied(HoisUserDetails user, TimetableNewVocationalTimeOccupiedCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableEventService.timetableTimeOccupied(command);
    }

    @GetMapping("/timetableNewHigherTimeOccupied")
    public TimetableTimeOccupiedDto timetableTimeOccupied(HoisUserDetails user, TimetableNewHigherTimeOccupiedCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableEventService.timetableTimeOccupied(command);
    }
}
