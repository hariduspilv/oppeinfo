package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.service.StudentAbsenceService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.StudentAbsenceUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceLessonsForm;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceSearchCommand;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;

@RestController
@RequestMapping("/absences")
public class StudentAbsenceController {

    @Autowired
    private StudentAbsenceService studentAbsenceService;

    @GetMapping("/{id:\\d+}")
    public StudentAbsenceDto get(HoisUserDetails user, @WithEntity StudentAbsence studentAbsence) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, studentAbsence.getStudent().getSchool());
        StudentAbsenceDto dto = StudentAbsenceDto.of(studentAbsence);
        dto.setCanAccept(Boolean.valueOf(StudentAbsenceUtil.canAccept(user, studentAbsence)));
        dto.setCanReject(Boolean.valueOf(StudentAbsenceUtil.canReject(user, studentAbsence)));
        return dto;
    }

    @GetMapping
    public Page<StudentAbsenceDto> search(HoisUserDetails user, @Valid StudentAbsenceSearchCommand criteria, Pageable pageable) {
        StudentAbsenceUtil.assertCanSearch(user);
        return studentAbsenceService.search(user, criteria, pageable);
    }

    @PutMapping("/accept/{id:\\d+}")
    public StudentAbsenceDto accept(HoisUserDetails user, @WithEntity StudentAbsence studentAbsence) {
        StudentAbsenceUtil.assertCanAccept(user, studentAbsence);
        return get(user, studentAbsenceService.accept(user, studentAbsence));
    }
    
    @PutMapping("/acceptByLessons/{id:\\d+}")
    public StudentAbsenceDto acceptByLessons(HoisUserDetails user, @WithEntity StudentAbsence studentAbsence,
            @RequestBody StudentAbsenceLessonsForm form) {
        StudentAbsenceUtil.assertCanAccept(user, studentAbsence);
        return get(user, studentAbsenceService.acceptByLessons(user, studentAbsence, form));
    }
    
    @PutMapping("/reject/{id:\\d+}")
    public StudentAbsenceDto reject(HoisUserDetails user, @WithEntity StudentAbsence studentAbsence) {
        StudentAbsenceUtil.assertCanReject(user, studentAbsence);
        return get(user, studentAbsenceService.reject(user, studentAbsence));
    }

    @GetMapping("/hasUnaccepted")
    public Map<String, Boolean> hasUnaccepted(HoisUserDetails user) {
        return Collections.singletonMap("hasUnaccepted", Boolean.valueOf(StudentAbsenceUtil.hasPermissionToChangeStatus(user)
                && studentAbsenceService.hasUnaccepted(user)));
    }
}
