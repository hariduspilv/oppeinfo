package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.LessonPlanService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchTeacherCommand;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchDto;

@RestController
@RequestMapping("/lessonplans")
public class LessonPlanController {

    @Autowired
    private LessonPlanService lessonPlanService;

    @GetMapping
    public Page<LessonPlanSearchDto> search(HoisUserDetails user, @Valid LessonPlanSearchCommand criteria, Pageable pageable) {
        return lessonPlanService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/byteacher")
    public Page<LessonPlanSearchDto> search(HoisUserDetails user, @Valid LessonPlanSearchTeacherCommand criteria, Pageable pageable) {
        return lessonPlanService.search(user.getSchoolId(), criteria, pageable);
    }
}
