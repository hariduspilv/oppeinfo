package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.service.StudentAbsenceService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.StudentAbsenceValidationUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceSearchCommand;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;

@RestController
@RequestMapping("/absences")
public class StudentAbsenceController {
    
    @Autowired
    private StudentAbsenceService studentAbsenceService;
    
    @GetMapping("/{id:\\d+}")
    private StudentAbsenceDto get(HoisUserDetails user, @WithEntity("id") StudentAbsence studentAbsence) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, studentAbsence.getStudent().getSchool());
        return StudentAbsenceDto.of(studentAbsence);
    }
    
    @GetMapping
    private Page<StudentAbsenceDto> search(HoisUserDetails user, StudentAbsenceSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return studentAbsenceService.search(user, criteria, pageable);
    }
    
    @PutMapping("/accept/{id:\\d+}")
    private StudentAbsenceDto accept(HoisUserDetails user, @WithEntity("id") StudentAbsence studentAbsence) {
        StudentAbsenceValidationUtil.assertCanAccept(user, studentAbsence);
        return get(user, studentAbsenceService.accept(studentAbsence));
    }
    
    @GetMapping("/hasUnaccepted")
    private Map<String, Boolean> hasUnaccepted(HoisUserDetails user) {
        return Collections.singletonMap("hasUnaccepted", Boolean.valueOf((user.isSchoolAdmin() || user.isTeacher()) 
                && studentAbsenceService.hasUnaccepted(user)));
    }
}
