package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.AcademicCalendarService;
import ee.hitsa.ois.web.dto.AcademicCalendarDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;

@RestController
@RequestMapping("/academicCalendar")
public class AcademicCalendarController {

    @Autowired
    private AcademicCalendarService academicCalendarService;
    
    @GetMapping("/{school:\\d+}")
    public AcademicCalendarDto academicCalendar(HoisUserDetails user, @WithEntity("school") School school) {
        UserUtil.isSameSchool(user, school);
        return academicCalendarService.academicCalendar(school.getId());
    }
    
    @GetMapping("/{school:\\d+}/{studyYear:\\d+}")
    public AcademicCalendarDto academicCalendar(HoisUserDetails user, @WithEntity("school") School school, @WithEntity("studyYear") StudyYear studyYear) {
        UserUtil.isSameSchool(user, school);
        return academicCalendarService.academicCalendar(school.getId(), studyYear);
    }
    
    @GetMapping("/studyYears/{school:\\d+}")
    public List<StudyYearSearchDto> timetableStudyYears(@WithEntity("school") School school) {
        return academicCalendarService.studyYears(EntityUtil.getId(school));
    }
}
