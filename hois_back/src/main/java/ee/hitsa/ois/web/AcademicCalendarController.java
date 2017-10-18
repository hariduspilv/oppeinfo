package ee.hitsa.ois.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.AcademicCalendarService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.AcademicCalendarDto;

@RestController
@RequestMapping("/academicCalendar")
public class AcademicCalendarController {

    @Autowired
    private AcademicCalendarService academicCalendarService;

    @GetMapping
    public AcademicCalendarDto academicCalendar(HoisUserDetails user) {
        return academicCalendarService.academicCalendar(user.getSchoolId());
    }
}
