package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.SchoolBoardService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SchoolWithLogo;
import ee.hitsa.ois.web.dto.timetable.GroupTimetableDto;
import ee.hitsa.ois.web.dto.timetable.RoomTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TeacherTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventRoomSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudyYearWeekDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schoolBoard")
public class SchoolBoardController {

    @Autowired
    private SchoolBoardService schoolBoardService;

    @GetMapping("/{id:\\d+}")
    public Map<String, Object> schoolBoardUrl(HoisUserDetails user, @WithEntity School school) {
        UserUtil.assertIsSchoolAdmin(user, school);
        return Collections.singletonMap("url", schoolBoardService.schoolBoardUrl(school));
    }

    @GetMapping("/{schoolIdentifier}/school")
    public SchoolWithLogo school(@PathVariable String schoolIdentifier) {
        return schoolBoardService.school(schoolIdentifier);
    }

    @GetMapping("/{schoolIdentifier}/group/timetables")
    public List<GroupTimetableDto> groupTimetables(@PathVariable String schoolIdentifier) {
        return schoolBoardService.groupTimetables(schoolIdentifier);
    }

    @GetMapping("/{schoolIdentifier}/timetableByGroup")
    public TimetableByGroupDto groupTimetableForWeek(@PathVariable String schoolIdentifier,
            @Valid TimetableEventSearchCommand criteria) {
        return schoolBoardService.groupTimetableForWeek(schoolIdentifier, criteria);
    }

    @GetMapping("/{schoolIdentifier}/teacher/timetables")
    public List<TeacherTimetableDto> teacherTimetables(@PathVariable String schoolIdentifier) {
        return schoolBoardService.teacherTimetables(schoolIdentifier);
    }

    @GetMapping("/{schoolIdentifier}/timetableByTeacher")
    public TimetableByTeacherDto teacherTimetableForWeek(@PathVariable String schoolIdentifier,
            @Valid TimetableEventSearchCommand criteria) {
        return schoolBoardService.teacherTimetableForWeek(schoolIdentifier, criteria);
    }

    @GetMapping("/{schoolIdentifier}/room/timetables")
    public List<RoomTimetableDto> roomTimetables(@PathVariable String schoolIdentifier) {
        return schoolBoardService.roomTimetables(schoolIdentifier);
    }

    @GetMapping("/{schoolIdentifier}/timetableByRoom")
    public TimetableByRoomDto roomTimetableForWeek(@PathVariable String schoolIdentifier,
            @Valid TimetableEventSearchCommand criteria) {
        return schoolBoardService.roomTimetableForWeek(schoolIdentifier, criteria);
    }

    @GetMapping("/{schoolIdentifier}/timetableStudyYearWeeks")
    public List<TimetableStudyYearWeekDto> timetableStudyYearWeeks(@PathVariable String schoolIdentifier) {
        return schoolBoardService.timetableStudyYearWeeks(schoolIdentifier);
    }

    @GetMapping("/{schoolIdentifier}/currentEvents")
    public List<TimetableEventSearchDto> currentEvents(@PathVariable String schoolIdentifier) {
        return schoolBoardService.currentEvents(schoolIdentifier);
    }

    @GetMapping("/{schoolIdentifier}/roomsWithCurrentEvents")
    public List<RoomTimetableDto> roomsWithCurrentEvents(@PathVariable String schoolIdentifier) {
        return schoolBoardService.roomsWithCurrentEvents(schoolIdentifier);
    }

    @GetMapping("/{schoolIdentifier}/room/{id:\\d+}")
    public AutocompleteResult room(@PathVariable String schoolIdentifier, @WithEntity Room room) {
        return schoolBoardService.room(schoolIdentifier, room);
    }

    @GetMapping("/{schoolIdentifier}/currentEvents/{roomId}")
    public List<TimetableEventSearchDto> roomCurrentEvents(@PathVariable String schoolIdentifier,
            @PathVariable Long roomId) {
        return schoolBoardService.roomCurrentEvents(schoolIdentifier, roomId);
    }

    @GetMapping("/{schoolIdentifier}/freeRooms/current")
    public Page<TimetableEventRoomSearchDto> currentlyFreeRooms(@PathVariable String schoolIdentifier,
           Pageable pageable) {
        return schoolBoardService.currentlyFreeRooms(schoolIdentifier, pageable);
    }

    @GetMapping("/{schoolIdentifier}/freeRooms/wholeDay")
    public Page<TimetableEventRoomSearchDto> wholeDayFreeRooms(@PathVariable String schoolIdentifier,
            Pageable pageable) {
        return schoolBoardService.wholeDayFreeRooms(schoolIdentifier, pageable);
    }
}
