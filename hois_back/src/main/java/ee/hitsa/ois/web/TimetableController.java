package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.service.TimetableGenerationService;
import ee.hitsa.ois.service.TimetableService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.TimetableCopyForm;
import ee.hitsa.ois.web.commandobject.TimetableRoomAndTimeForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventHigherForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventVocationalForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableManagementSearchCommand;
import ee.hitsa.ois.web.dto.timetable.GroupTimetableDto;
import ee.hitsa.ois.web.dto.timetable.HigherTimetablePlanDto;
import ee.hitsa.ois.web.dto.timetable.RoomTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TeacherTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDatesDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableManagementSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetablePlanDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentStudyYearWeekDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudyYearWeekDto;
import ee.hitsa.ois.web.dto.timetable.VocationalTimetablePlanDto;

@RestController
@RequestMapping("/timetables")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;
    @Autowired
    private TimetableGenerationService timetableGenerationService;

    @GetMapping("/{id:\\d+}")
    public TimetableDto edit(HoisUserDetails user, @WithEntity Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.get(user, timetable);
    }

    @GetMapping("/{id:\\d+}/view")
    public TimetableDto get(@WithEntity Timetable timetable) {
        //UserUtil.assertIsSchoolAdmin(user);
        /* IKE TODO */
        return timetableService.getForView(timetable);
    }
 
    @GetMapping("/{id:\\d+}/createVocationalPlan")
    public VocationalTimetablePlanDto createVocationalPlan(HoisUserDetails user, @WithEntity Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.getVocationalPlan(timetable);
    }
    
    @GetMapping("/{id:\\d+}/createHigherPlan")
    public HigherTimetablePlanDto createHigherPlan(HoisUserDetails user, @WithEntity Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.getHigherPlan(timetable);
    }

    @GetMapping("/managementSearchFormData")
    public Map<String, ?> managementSearchFormData(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.managementSearchFormData(user.getSchoolId());
    }

    @GetMapping("/searchTimetableForManagement")
    public Page<TimetableManagementSearchDto> searchTimetableForManagement(HoisUserDetails user,
            @Valid TimetableManagementSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.searchTimetableForManagement(criteria, pageable, user);
    }

    @GetMapping("/blockedDatesForPeriod")
    public List<TimetableDatesDto> blockedDatesForPeriod(HoisUserDetails user,
            @RequestParam("studyPeriod") Long studyPeriod, @RequestParam("code") String code,
            @RequestParam(name = "currentTimetable", required = false) Long currentTimetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.blockedDatesForPeriod(user, studyPeriod, code, currentTimetable);
    }

    @PostMapping
    public TimetableDto create(HoisUserDetails user, @Valid @RequestBody TimetableEditForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(timetableService.createTimetable(user, form));
    }

    @PostMapping("/saveVocationalEvent")
    public TimetablePlanDto saveVocationalEvent(HoisUserDetails user,
            @Valid @RequestBody TimetableEventVocationalForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        Timetable timetable = timetableService.saveVocationalEvent(form);
        // TODO: create a different smaller query for getting updated subjects after
        // saving a subject
        return createVocationalPlan(user, timetable);
    }

    @PostMapping("/saveHigherEvent")
    public TimetablePlanDto saveHigherEvent(HoisUserDetails user, @Valid @RequestBody TimetableEventHigherForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        Timetable timetable = timetableService.saveHigherEvent(form);
        // TODO: create a different smaller query for getting updated subjects after
        // saving a subject
        return createHigherPlan(user, timetable);
    }

    @PostMapping("/deleteVocationalEvent")
    public TimetablePlanDto deleteVocationalEvent(HoisUserDetails user, @Valid @RequestBody TimetableRoomAndTimeForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return createVocationalPlan(user, timetableService.deleteEvent(user, form));
    }

    @PostMapping("/deleteHigherEvent")
    public TimetablePlanDto deleteHigherEvent(HoisUserDetails user, @Valid @RequestBody TimetableRoomAndTimeForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return createHigherPlan(user, timetableService.deleteEvent(user, form));
    }

    @GetMapping("/getPossibleTargetsForCopy")
    public List<TimetableManagementSearchDto> getPossibleTargetsForCopy(HoisUserDetails user, @RequestParam("id") Long timetableId) {
        UserUtil.assertIsSchoolAdmin(user);
        return timetableService.getPossibleTargetsForCopy(user, timetableId);
    }

    @GetMapping("/copyTimetable")
    public TimetableDto cloneTimetable(HoisUserDetails user, @Valid TimetableCopyForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(timetableService.cloneTimetable(user, form));
    }

    @PostMapping("/saveVocationalEventRoomsAndTimes")
    public TimetablePlanDto saveVocationalEventRoomsAndTimes(HoisUserDetails user,
            @Valid @RequestBody TimetableRoomAndTimeForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return createVocationalPlan(user, timetableService.saveEventRoomsAndTimes(user, form));
    }

    @PostMapping("/saveHigherEventRoomsAndTimes")
    public TimetablePlanDto saveHigherEventRoomsAndTimes(HoisUserDetails user,
            @Valid @RequestBody TimetableRoomAndTimeForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return createHigherPlan(user, timetableService.saveEventRoomsAndTimes(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public TimetableDto save(HoisUserDetails user, @Valid @RequestBody TimetableEditForm form,
            @WithEntity Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(timetableService.save(user, form, timetable));
    }
    
    @PutMapping("/{id:\\d+}/confirm")
    public TimetableDto confirm(HoisUserDetails user, @WithEntity Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(timetableService.confirm(timetable));
    }
    
    @PutMapping("/{id:\\d+}/publicize")
    public TimetableDto publicize(HoisUserDetails user, @WithEntity Timetable timetable) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(timetableService.publicize(timetable));
    }
    
    @GetMapping("/timetableStudyYearWeeks/{school:\\d+}")
    public List<TimetableStudyYearWeekDto> timetableStudyYearWeeks(@PathVariable("school") Long schoolId) {
        return timetableService.timetableStudyYearWeeks(schoolId);
    }
    
    @GetMapping("/timetableStudyYearWeeks/student/{id:\\d+}")
    public List<TimetableStudentStudyYearWeekDto> timetableStudyYearWeeksStudent(@WithEntity Student student) {
        return timetableService.timetableStudyYearWeeksStudent(student);
    }
    
    @GetMapping("/group/{school:\\d+}")
    public List<GroupTimetableDto> groupTimetables(@PathVariable("school") Long schoolId) {
        return timetableService.groupTimetables(schoolId);
    }

    @GetMapping("/teacher/{school:\\d+}")
    public List<TeacherTimetableDto> teacherTimetables(@PathVariable("school") Long schoolId) {
        return timetableService.teacherTimetables(schoolId);
    }
    
    @GetMapping("/room/{school:\\d+}")
    public List<RoomTimetableDto> roomTimetables(@PathVariable("school") Long schoolId) {
        return timetableService.roomTimetables(schoolId);
    }
    
    @GetMapping("/timetableDifference.xls")
    public void timetableDifferenceExcel(HoisUserDetails user, @RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        HttpUtil.xls(response, "timetableDifference.xls", timetableGenerationService.timetableDifferenceExcel(id));
    }
    
    @GetMapping("/timetablePlan.xlsx")
    public void timetablePlan(HoisUserDetails user, @RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        HttpUtil.xls(response, "timetablePlan.xlsx", timetableGenerationService.timetablePlanExcel(id));
    }
}
