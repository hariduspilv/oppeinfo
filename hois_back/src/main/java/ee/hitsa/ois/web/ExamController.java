package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.ExamService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.exam.ExamForm;
import ee.hitsa.ois.web.commandobject.exam.ExamSearchForm;
import ee.hitsa.ois.web.commandobject.exam.StudentExamSearchForm;
import ee.hitsa.ois.web.commandobject.exam.StudentSearchCriteria;
import ee.hitsa.ois.web.dto.exam.ExamDto;
import ee.hitsa.ois.web.dto.exam.ExamStudentRegistrationDto;
import ee.hitsa.ois.web.dto.exam.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.exam.ExamSearchDto;

@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping
    public Page<ExamSearchDto> search(HoisUserDetails user, @Valid ExamSearchForm criteria, Pageable pageable) {
        assertCanView(user);
        return examService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ExamDto get(HoisUserDetails user, @PathVariable("id") Long examId) {
        assertCanView(user);
        return examService.get(user, examId);
    }

    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody ExamForm form) {
        assertCanEdit(user);
        return new HttpUtil.CreatedResponse(examService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public ExamDto save(HoisUserDetails user, @PathVariable("id") Long examId, @Valid @RequestBody ExamForm form) {
        assertCanEdit(user);
        return examService.save(user, examId, form);
    }

    @DeleteMapping("/{id:\\d+}")
    public HttpUtil.NoContentResponse delete(HoisUserDetails user, @PathVariable("id") Long examId, @RequestParam("version") Long version) {
        assertCanEdit(user);
        examService.delete(user, examId, version);
        return HttpUtil.NO_CONTENT_RESPONSE;
    }

    @GetMapping("/forregistration")
    public Page<ExamStudentRegistrationDto> examsForRegistration(HoisUserDetails user, @Valid StudentExamSearchForm criteria, Pageable pageable) {
        UserUtil.assertIsStudent(user);
        return examService.examsForRegistration(user, criteria, pageable);
    }

    @PutMapping("/register/{id:\\d+}")
    public HttpUtil.NoContentResponse register(HoisUserDetails user, @PathVariable("id") Long examId) {
        UserUtil.assertIsStudent(user);
        examService.register(user, examId);
        return HttpUtil.NO_CONTENT_RESPONSE;
    }

    @PutMapping("/unregister/{id:\\d+}")
    public HttpUtil.NoContentResponse unregister(HoisUserDetails user, @PathVariable("id") Long examId) {
        UserUtil.assertIsStudent(user);
        examService.unregister(user, examId);
        return HttpUtil.NO_CONTENT_RESPONSE;
    }

    @GetMapping("/studentsforregistration/{id:\\d+}")
    public List<ExamDto.ExamStudent> studentsForRegistration(HoisUserDetails user, @PathVariable("id") Long examId, @Valid StudentSearchCriteria criteria) {
        assertCanEdit(user);
        return examService.studentsForRegistration(user, examId, criteria);
    }

    @GetMapping("/subjectstudyperiods")
    public List<SubjectStudyPeriodDto> subjectStudyPeriods(HoisUserDetails user, @RequestParam("studyPeriod") Long studyPeriodId) {
        assertCanEdit(user);
        return examService.subjectStudyPeriods(user, studyPeriodId);
    }

    private static void assertCanView(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_EKSAM);
    }

    private static void assertCanEdit(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_EKSAM);
    }
}
