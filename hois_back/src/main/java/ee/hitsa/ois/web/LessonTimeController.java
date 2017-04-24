package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.service.LessonTimeService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.timetable.LessonTimeSearchCommand;
import ee.hitsa.ois.web.dto.timetable.LessonTimeSearchDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimesDto;

@RestController
@RequestMapping("/lessontimes")
public class LessonTimeController {

    @Autowired
    private LessonTimeService lessonTimeService;

    @GetMapping
    public Page<LessonTimeSearchDto> search(HoisUserDetails user, @Valid LessonTimeSearchCommand criteria, Pageable pageable) {
        return lessonTimeService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public LessonTimesDto get(@WithEntity("id") LessonTime lessonTime, HoisUserDetails user) {
        UserUtil.assertSameSchool(user, lessonTime.getSchool());
        return LessonTimesDto.of(lessonTime);
    }

    @GetMapping(value = "currentPeriod")
    public Map<String, LocalDate> currentPeriod() {
        Map<String, LocalDate> result = new HashMap<>();
        LocalDate currentPeriodStartDate = lessonTimeService.currentPeriodStartDate();
        if (currentPeriodStartDate != null) {
            result.put("periodStart", currentPeriodStartDate);
        }
        return result;
    }

}
