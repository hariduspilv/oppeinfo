package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.service.LessonPlanService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanCreateForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchTeacherCommand;
import ee.hitsa.ois.web.dto.timetable.LessonPlanByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanCreatedJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchTeacherDto;

@RestController
@RequestMapping("/lessonplans")
public class LessonPlanController {

    @Autowired
    private LessonPlanService lessonPlanService;

    @GetMapping
    public Page<LessonPlanSearchDto> search(HoisUserDetails user, @Valid LessonPlanSearchCommand criteria,
            Pageable pageable) {
        // default search by student group
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        return lessonPlanService.search(user, criteria, pageable);
    }

    @GetMapping("/byteacher")
    public Page<LessonPlanSearchTeacherDto> search(HoisUserDetails user, @Valid LessonPlanSearchTeacherCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        return lessonPlanService.search(user, criteria, pageable);
    }

    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody LessonPlanCreateForm form) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        return HttpUtil.created(lessonPlanService.create(user, form));
    }

    @GetMapping("/{id:\\d+}")
    public LessonPlanDto get(HoisUserDetails user, @WithEntity LessonPlan lessonPlan) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, lessonPlan.getStudentGroup());
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        return lessonPlanService.get(lessonPlan);
    }

    @GetMapping("/byteacher/{id:\\d+}/{sy:\\d+}")
    public LessonPlanByTeacherDto get(HoisUserDetails user, @WithEntity Teacher teacher, @WithEntity("sy") StudyYear studyYear) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        UserUtil.assertSameSchool(user, teacher.getSchool());
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        return lessonPlanService.getByTeacher(user, teacher, studyYear);
    }

    @PutMapping("/{id:\\d+}")
    public LessonPlanDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) LessonPlan lessonPlan, @Valid @RequestBody LessonPlanForm form) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, lessonPlan.getStudentGroup());
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        return get(user, lessonPlanService.save(lessonPlan, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") LessonPlan lessonPlan,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, lessonPlan.getStudentGroup());
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        lessonPlanService.delete(user, lessonPlan);
    }

    @GetMapping("/searchFormData")
    public Map<String, ?> searchFormData(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user);
        return lessonPlanService.searchFormData(user);
    }

    @GetMapping("/journals/new")
    public LessonPlanJournalDto newJournal(HoisUserDetails user, @RequestParam("lessonPlan") Long lessonPlanId,
            @RequestParam("occupationModule") Long occupationModuleId,
            @RequestParam(value = "lessonPlanModule", required = false) Long lessonPlanModuleId) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        return lessonPlanService.newJournal(user, lessonPlanId, occupationModuleId, lessonPlanModuleId);
    }

    @PostMapping("/journals")
    public ResponseEntity<LessonPlanCreatedJournalDto> create(HoisUserDetails user,
            @Valid @RequestBody LessonPlanJournalForm form) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        return new ResponseEntity<>(lessonPlanService.createJournal(user, form), HttpStatus.CREATED);
    }

    @GetMapping("/journals/{id:\\d+}")
    public LessonPlanJournalDto getJournal(HoisUserDetails user, @WithEntity Journal journal,
            @RequestParam("lessonPlanModule") Long lessonPlanModuleId) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, journal);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        return lessonPlanService.getJournal(journal, lessonPlanModuleId);
    }

    @PutMapping("/journals/{id:\\d+}")
    public LessonPlanJournalDto saveJournal(HoisUserDetails user, @WithEntity Journal journal,
            @Valid @RequestBody LessonPlanJournalForm form) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, journal);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        return getJournal(user, lessonPlanService.saveJournal(journal, form, user), form.getLessonPlanModuleId());
    }

    @DeleteMapping("/journals/{id:\\d+}")
    public void deleteJournal(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") Journal journal,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, journal.getSchool());
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        lessonPlanService.deleteJournal(user, journal);
    }
    
    @GetMapping("/lessonplansummary.xls")
    public void summaryLessonplanAsExcel(HoisUserDetails user, @Valid LessonPlanSearchCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        HttpUtil.xls(response, "lessonplansummary.xls", lessonPlanService.summaryLessonPlan(criteria, user));
    }

    @GetMapping("/{id:\\d+}/lessonplan.xls")
    public void lessonplanAsExcel(HoisUserDetails user, @WithEntity LessonPlan lessonPlan, HttpServletResponse response)
            throws IOException {
        UserUtil.isSchoolAdminOrLeadingTeacher(user, lessonPlan.getStudentGroup());
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN);
        HttpUtil.xls(response, "lessonplan.xls", lessonPlanService.lessonplanAsExcel(lessonPlan));
    }

    @GetMapping("/byteacher/{id:\\d+}/{sy:\\d+}/lessonplanbyteacher.xls")
    public void lessonplanByTeacherAsExcel(HoisUserDetails user, @WithEntity Teacher teacher,
            @WithEntity("sy") StudyYear studyYear, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        UserUtil.assertSameSchool(user, teacher.getSchool());
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        HttpUtil.xls(response, "lessonplanbyteacher.xls", lessonPlanService.lessonplanByTeacherAsExcel(user, teacher, studyYear));
    }
}
