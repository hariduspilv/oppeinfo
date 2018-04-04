package ee.hitsa.ois.web;

import java.util.Map;

import javax.validation.Valid;

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
import ee.hitsa.ois.exception.AssertionFailedException;
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
    public Page<LessonPlanSearchDto> search(HoisUserDetails user, @Valid LessonPlanSearchCommand criteria, Pageable pageable) {
        // default search by student group
        UserUtil.assertIsSchoolAdmin(user);
        return lessonPlanService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/byteacher")
    public Page<LessonPlanSearchTeacherDto> search(HoisUserDetails user, @Valid LessonPlanSearchTeacherCommand criteria, Pageable pageable) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin() && !user.isTeacher(), "User has no rights");
        return lessonPlanService.search(user, criteria, pageable);
    }

    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody LessonPlanCreateForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(lessonPlanService.create(user, form));
    }

    @GetMapping("/{id:\\d+}")
    public LessonPlanDto get(HoisUserDetails user, @WithEntity LessonPlan lessonPlan) {
        UserUtil.assertIsSchoolAdmin(user, lessonPlan.getSchool());
        return lessonPlanService.get(lessonPlan);
    }

    @GetMapping("/byteacher/{id:\\d+}/{sy:\\d+}")
    public LessonPlanByTeacherDto get(HoisUserDetails user, @WithEntity Teacher teacher, @WithEntity("sy") StudyYear studyYear) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        UserUtil.assertSameSchool(user, teacher.getSchool());
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        return lessonPlanService.getByTeacher(teacher, studyYear);
    }

    @PutMapping("/{id:\\d+}")
    public LessonPlanDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) LessonPlan lessonPlan, @Valid @RequestBody LessonPlanForm form) {
        UserUtil.assertIsSchoolAdmin(user, lessonPlan.getSchool());
        return get(user, lessonPlanService.save(lessonPlan, form));
    }

    @GetMapping("/searchFormData")
    public Map<String, ?> searchFormData(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return lessonPlanService.searchFormData(user.getSchoolId());
    }

    @GetMapping("/journals/new")
    public LessonPlanJournalDto newJournal(HoisUserDetails user, @RequestParam("lessonPlan") Long lessonPlanId,
            @RequestParam("occupationModule") Long occupationModuleId, 
            @RequestParam(value = "lessonPlanModule", required = false) Long lessonPlanModuleId) {
        return lessonPlanService.newJournal(user, lessonPlanId, occupationModuleId, lessonPlanModuleId);
    }

    @PostMapping("/journals")
    public ResponseEntity<LessonPlanCreatedJournalDto> create(HoisUserDetails user, 
            @Valid @RequestBody LessonPlanJournalForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return new ResponseEntity<>(lessonPlanService.createJournal(user, form), HttpStatus.CREATED);
    }

    @GetMapping("/journals/{id:\\d+}")
    public LessonPlanJournalDto getJournal(HoisUserDetails user, @WithEntity Journal journal, 
            @RequestParam("lessonPlanModule") Long lessonPlanModuleId) {
        UserUtil.assertSameSchool(user, journal.getSchool());
        return lessonPlanService.getJournal(journal, lessonPlanModuleId);
    }

    @PutMapping("/journals/{id:\\d+}")
    public LessonPlanJournalDto saveJournal(HoisUserDetails user, @WithEntity Journal journal, 
            @Valid @RequestBody LessonPlanJournalForm form) {
        UserUtil.assertIsSchoolAdmin(user, journal.getSchool());
        return getJournal(user, lessonPlanService.saveJournal(journal, form, user), form.getLessonPlanModuleId());
    }

    @DeleteMapping("/journals/{id:\\d+}")
    public void deleteJournal(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Journal journal, 
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user, journal.getSchool());
        lessonPlanService.deleteJournal(user, journal);
    }
}
