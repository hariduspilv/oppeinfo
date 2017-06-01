package ee.hitsa.ois.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.service.LessonPlanService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanCreateForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchTeacherCommand;
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
    public LessonPlanDto get(HoisUserDetails user, @WithEntity("id") LessonPlan lessonPlan) {
        UserUtil.assertIsSchoolAdmin(user, lessonPlan.getSchool());
        return LessonPlanDto.of(lessonPlan);
    }

    @PutMapping("/{id:\\d+}")
    public LessonPlanDto update(HoisUserDetails user, @WithEntity("id") @WithVersionedEntity(value = "id", versionRequestBody = true) LessonPlan lessonPlan, @Valid @RequestBody LessonPlanForm form) {
        UserUtil.assertIsSchoolAdmin(user, lessonPlan.getSchool());
        return get(user, lessonPlanService.save(lessonPlan, form));
    }

    @GetMapping("/searchFormData")
    public Map<String, ?> studentgroupsForLessonPlan(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return lessonPlanService.searchFormData(user.getSchoolId());
    }

    @PostMapping("/journals")
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody LessonPlanJournalForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(lessonPlanService.createJournal(user, form));
    }

    @GetMapping("/journals/new")
    public LessonPlanJournalDto newJournal(HoisUserDetails user, @RequestParam("lessonPlanModule") Long lessonPlanModuleId) {
        return lessonPlanService.newJournal(user, lessonPlanModuleId);
    }

    @GetMapping("/journals/{id:\\d+}")
    public LessonPlanJournalDto get(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestParam("lessonPlanModule") Long lessonPlanModuleId) {
        UserUtil.assertSameSchool(user, journal.getSchool());
        return lessonPlanService.getJournal(journal, lessonPlanModuleId);
    }

    @PutMapping("/journals/{id:\\d+}")
    public LessonPlanJournalDto update(HoisUserDetails user, @WithEntity("id") Journal journal, @Valid @RequestBody LessonPlanJournalForm form) {
        UserUtil.assertIsSchoolAdmin(user, journal.getSchool());
        return get(user, lessonPlanService.saveJournal(journal, form), form.getLessonPlanModuleId());
    }

    @DeleteMapping("/journals/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Journal journal, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user, journal.getSchool());
        lessonPlanService.deleteJournal(journal);
    }
}
