package ee.hitsa.ois.web.subjectStudyPeriod;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.TeacherService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodCapacitiesService;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodTeacherSearchService;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodTeacherService;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.commandobject.teacher.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodTeacherSearchDto;

@RestController
@RequestMapping("/subjectStudyPeriods/teachers")
public class SubjectStudyPeriodTeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectStudyPeriodCapacitiesService subjectStudyPeriodCapacitiesService;
    @Autowired
    private SubjectStudyPeriodTeacherSearchService subjectStudyPeriodTeacherSearchService;
    @Autowired
    private SubjectStudyPeriodTeacherService subjectStudyPeriodTeacherService;

    @GetMapping
    public Page<SubjectStudyPeriodTeacherSearchDto> searchByTeachers(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodTeacherSearchService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/container")
    public SubjectStudyPeriodDtoContainer getTeachersSspContainer(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container) {
        AssertionFailedException.throwIf(container.getTeacher() == null,
                "Teacher must be specified");
        subjectStudyPeriodTeacherService.setSubjectStudyPeriodsToTeachersContainer(user.getSchoolId(), container);
        subjectStudyPeriodCapacitiesService.setSubjects(container);
        subjectStudyPeriodTeacherService.setSubjectStudyPeriodPlansToTeachersContainer(container);
        return container;
    }

    @PutMapping("/container")
    public SubjectStudyPeriodDtoContainer updateTeachersSspCapacities(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodDtoContainer container) {
        UserUtil.assertIsSchoolAdmin(user);
        AssertionFailedException.throwIf(container.getTeacher() == null,
                "Teacher must be specified");
        subjectStudyPeriodCapacitiesService.updateSspCapacities(user.getSchoolId(), container);
        return getTeachersSspContainer(user, container);
    }

    @GetMapping("/page")
    public Page<AutocompleteResult> getTeachersPage(TeacherSearchCommand command, Pageable pageable, HoisUserDetails user) {
        command.setIsHigher(Boolean.TRUE);
        command.setIsActive(Boolean.TRUE);
        return teacherService.search(user, command, pageable).map(t -> new AutocompleteResult(t.getId(), t.getName(), t.getName()));
    }

    @GetMapping("/list/limited/{studyPeriodId:\\d+}")
    public List<AutocompleteResult> getTeachersFilteredList(HoisUserDetails user, @PathVariable("studyPeriodId") Long studyPeriodId) {
        return subjectStudyPeriodTeacherService.getTeachersList(user.getSchoolId(), studyPeriodId);
    }

}
