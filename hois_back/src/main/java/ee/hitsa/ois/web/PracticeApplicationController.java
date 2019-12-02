package ee.hitsa.ois.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.enterprise.PracticeAdmission;
import ee.hitsa.ois.domain.enterprise.PracticeApplication;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.PracticeApplicationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.practice.PracticeApplicationForm;
import ee.hitsa.ois.web.commandobject.practice.PracticeApplicationPeriodsSearchCommand;
import ee.hitsa.ois.web.commandobject.practice.PracticeApplicationRejectForm;
import ee.hitsa.ois.web.commandobject.practice.PracticeApplicationSearchCommand;
import ee.hitsa.ois.web.dto.practice.PracticeAdmissionStudentDto;
import ee.hitsa.ois.web.dto.practice.PracticeApplicationContractDto;
import ee.hitsa.ois.web.dto.practice.PracticeApplicationPeriodSearchDto;
import ee.hitsa.ois.web.dto.practice.PracticeApplicationSearchDto;

@RestController
@RequestMapping("/practiceApplication")
public class PracticeApplicationController {

    @Autowired
    private PracticeApplicationService practiceApplicationService;

    @GetMapping("/canApply")
    public Map<String, Object> canApply(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return practiceApplicationService.canApply(user);
    }

    @GetMapping("/openAdmissions")
    public List<PracticeAdmissionStudentDto> openAdmissions(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return practiceApplicationService.openAdmissions(user);
    }

    @GetMapping("/passedAdmissions")
    public List<PracticeAdmissionStudentDto> passedAdmissions(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return practiceApplicationService.passedAdmissions(user);
    }

    @PostMapping("/apply/{id:\\d+}")
    public void apply(HoisUserDetails user, @WithEntity PracticeAdmission admission,
            @RequestBody PracticeApplicationForm form) {
        UserUtil.assertIsStudent(user);
        practiceApplicationService.apply(user, admission, form);
    }

    @PostMapping("/annul/{id:\\d+}")
    public void annul(HoisUserDetails user, @WithEntity PracticeAdmission admission) {
        UserUtil.assertIsStudent(user);
        practiceApplicationService.annul(user, admission);
    }

    @GetMapping("/applications")
    public Page<PracticeApplicationSearchDto> applications(HoisUserDetails user,
            PracticeApplicationSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_PRAKTIKAAVALDUS);
        return practiceApplicationService.applications(user, command, pageable);
    }

    @GetMapping("/applicationPeriods")
    public Page<PracticeApplicationPeriodSearchDto> applicationPeriods(HoisUserDetails user,
            PracticeApplicationPeriodsSearchCommand cmd, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_PRAKTIKAAVALDUS);
        return practiceApplicationService.applicationPeriods(user, cmd, pageable);
    }

    @PostMapping("/reject/{id:\\d+}")
    public void reject(HoisUserDetails user, @WithEntity PracticeApplication application,
            @Valid @RequestBody PracticeApplicationRejectForm form) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, application.getStudent(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_PRAKTIKAAVALDUS);
        practiceApplicationService.reject(user, application, form);
    }

    @PostMapping("/accept/{id:\\d+}")
    public void accept(HoisUserDetails user, @WithEntity PracticeApplication application) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, application.getStudent(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_PRAKTIKAAVALDUS);
        practiceApplicationService.accept(user, application);
    }

    @GetMapping("/contractData/{id:\\d+}")
    public PracticeApplicationContractDto contractData(HoisUserDetails user,
            @WithEntity PracticeApplication application) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, application.getStudent().getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_PRAKTIKAAVALDUS);
        return practiceApplicationService.contractData(user, application);
    }

}
