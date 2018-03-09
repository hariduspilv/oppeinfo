package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.ExamService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.exam.ExamSearchForm;
import ee.hitsa.ois.web.dto.exam.ExamRegistrationDto;
import ee.hitsa.ois.web.dto.exam.ExamSearchDto;

@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping
    public Page<ExamSearchDto> search(HoisUserDetails user, @Valid ExamSearchForm criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_EKSAM);
        return examService.search(user, criteria, pageable);
    }

    @GetMapping("/forregistration")
    public Page<ExamRegistrationDto> examsForRegistration(HoisUserDetails user, Pageable pageable) {
        UserUtil.assertIsStudent(user);
        return examService.examsForRegistration(user, pageable);
    }
}
