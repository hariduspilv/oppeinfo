package ee.hitsa.ois.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.TimetableEventService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByStudentDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByTeacherDto;
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

    @GetMapping("/timetableByGroup")
    public TimetableByGroupDto groupTimetableForWeek(HoisUserDetails user,
            @RequestParam("studentGroupId") Long studentGroupId, @RequestParam("timetableId") Long timetableId) {
        return timetableEventService.groupTimetableForWeek(studentGroupId, timetableId);
    }

    @GetMapping("/timetableByTeacher")
    public TimetableByTeacherDto teacherTimetableForWeek(HoisUserDetails user, @RequestParam("teacherId") Long teacherId,
            @RequestParam("timetableId") Long timetableId) {
        return timetableEventService.teacherTimetableForWeek(teacherId, timetableId);
    }
    
    @GetMapping("/timetableByStudent")
    public TimetableByStudentDto studentTimetableForWeek(HoisUserDetails user, @RequestParam("studentId") Long studentId,
            @RequestParam("timetableId") Long timetableId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return timetableEventService.studentTimetableForWeek(studentId, timetableId);
    }
    
    @GetMapping("/timetableByRoom")
    public TimetableByRoomDto roomTimetableForWeek(HoisUserDetails user, @RequestParam("roomId") Long roomId,
            @RequestParam("timetableId") Long timetableId) {
        return timetableEventService.roomTimetableForWeek(roomId, timetableId);
    }
    
}
