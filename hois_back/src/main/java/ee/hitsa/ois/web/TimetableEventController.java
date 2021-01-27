package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.HttpUtil;
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
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.service.TimetableEventService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.TimetableUserRights;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventRoomsCommand;
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
import ee.hitsa.ois.web.dto.timetable.TimetableEventRoomSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableTimeOccupiedDto;

@RestController
@RequestMapping("/timetableevents")
public class TimetableEventController {

    @Autowired
    private TimetableEventService timetableEventService;

    /*
     * Management of timetable events is handled via timetable event times, because those are the objects that
     * the user is interested in, not the parent objects that hold multiple events 
     */
    @GetMapping("/{id:\\d+}")
    public TimetableSingleEventForm get(HoisUserDetails user, @WithEntity TimetableEventTime eventTime) {
        TimetableUserRights.assertCanViewEvent(user, eventTime);
        return timetableEventService.get(user, eventTime);
    }

    @GetMapping
    public Page<TimetableEventSearchDto> search(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria,
            Pageable pageable) {
        TimetableUserRights.assertCanSearchEvents(user);
        return timetableEventService.search(user, criteria, pageable);
    }

    @GetMapping("/search.pdf")
    public void excel(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria,
            HttpServletResponse response) throws IOException {
        TimetableUserRights.assertCanSearchEvents(user);
        HttpUtil.pdf(response, "event_search.pdf", timetableEventService.searchPrint(user, criteria));
    }

    @GetMapping("/rooms")
    public Page<TimetableEventRoomSearchDto> searchRooms(HoisUserDetails user, @Valid TimetableEventRoomsCommand cmd, Pageable pageable) {
        // Same user rights as for event searching.
        TimetableUserRights.assertCanSearchEvents(user);
        return timetableEventService.searchRooms(user.getSchoolId(), cmd, pageable);
    }

    @PostMapping
    public void create(HoisUserDetails user, @Valid @RequestBody TimetableSingleEventForm form) {
        TimetableUserRights.assertCanCreateEvent(user, form);
        timetableEventService.createEvent(user, form);
    }

    @PutMapping("/{id:\\d+}")
    public TimetableSingleEventForm update(HoisUserDetails user, @WithEntity TimetableEventTime eventTime,
            @Valid @RequestBody TimetableSingleEventForm form) {
        TimetableUserRights.assertCanEditOrDeleteEvent(user, eventTime);
        timetableEventService.updateEvent(form);
        return timetableEventService.get(user, eventTime);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteEvent(HoisUserDetails user, @WithEntity TimetableEventTime eventTime) {
        TimetableUserRights.assertCanEditOrDeleteEvent(user, eventTime);
        timetableEventService.delete(user, eventTime);
    }

    @GetMapping("/timetableSearch/{school:\\d+}")
    public Page<TimetableEventSearchDto> searchTimetable(@WithEntity("school") School school,
            @Valid TimetableEventSearchCommand criteria, Pageable pageable) {
        return timetableEventService.searchTimetable(criteria, school, pageable);
    }

    @GetMapping("/timetableSearch/{school:\\d+}/searchFormData")
    public Map<String, ?> searchTimetableFormData(@WithEntity("school") School school) {
        return timetableEventService.searchTimetableFormData(school);
    }

    @GetMapping("/timetableByGroup/{school:\\d+}")
    public TimetableByGroupDto groupTimetableForWeek(@WithEntity("school") School school, @Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.groupTimetable(school, criteria, true);
    }

    @GetMapping("/timetableByGroup/{school:\\d+}/calendar")
    public TimetableCalendarDto groupTimetableIcs(@WithEntity("school") School school, @Valid TimetableEventSearchCommand criteria,
            Language lang) {
        return timetableEventService.getGroupCalendar(school, criteria, lang);
    }

    @GetMapping("/timetableByTeacher/{school:\\d+}")
    public TimetableByTeacherDto teacherTimetableForWeek(@WithEntity("school") School school,
            @Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.teacherTimetable(school, criteria);
    }

    @GetMapping("/timetableByTeacher/{school:\\d+}/calendar")
    public TimetableCalendarDto teacherTimetableIcs(@WithEntity("school") School school, @Valid TimetableEventSearchCommand criteria,
            Language lang) {
        return timetableEventService.getTeacherCalendar(school, criteria, lang);
    }

    @GetMapping("/timetableByRoom/{school:\\d+}")
    public TimetableByRoomDto roomTimetableForWeek(@WithEntity("school") School school, @Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.roomTimetable(school, criteria, true);
    }

    @GetMapping("/timetableByRoom/{school:\\d+}/calendar")
    public TimetableCalendarDto roomTimetableIcs(@WithEntity("school") School school, @Valid TimetableEventSearchCommand criteria,
            Language lang) {
        return timetableEventService.getRoomCalendar(school, criteria, lang);
    }

    @GetMapping("/timetableByStudent/{school:\\d+}")
    public TimetableByStudentDto studentTimetableForWeek(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrStudentOrRepresentative(user);
        return timetableEventService.studentTimetable(user, criteria);
    }

    @GetMapping("/timetableByStudent/{school:\\d+}/calendar")
    public TimetableCalendarDto studentTimetableIcs(HoisUserDetails user, @Valid TimetableEventSearchCommand criteria,
            Language lang) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrStudentOrRepresentative(user);
        return timetableEventService.getStudentCalendar(user, criteria, lang);
    }

    @GetMapping("/timetableByPerson/{encodedPerson}")
    public TimetableByDto personalTimetableForWeek(@PathVariable("encodedPerson") String encodedPerson,
            @Valid TimetableEventSearchCommand criteria) {
        return timetableEventService.personalTimetable(criteria, encodedPerson);
    }

    @GetMapping("/timetableByPerson/{encodedPerson}/calendar")
    public TimetableCalendarDto personalTimetableIcs(@PathVariable("encodedPerson") String encodedPerson,
            @Valid TimetableEventSearchCommand criteria, Language lang) {
        return timetableEventService.getPersonalCalendar(criteria, encodedPerson, lang);
    }

    @GetMapping("/timetableByPerson/{encodedPerson}/calendar.ics")
    public void personalTimetableIcsLink(@PathVariable("encodedPerson") String encodedPerson, Language lang,
            HttpServletResponse response) {
        timetableEventService.getPersonalCalendarLink(response, encodedPerson, lang);
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
