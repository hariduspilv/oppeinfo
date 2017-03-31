package ee.hitsa.ois.web;

import java.util.Map;

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

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.exceptions.HoisBusinessRuleException;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.ApplicationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.commandobject.ApplicationRejectForm;
import ee.hitsa.ois.web.commandobject.ApplicationSearchCommand;
import ee.hitsa.ois.web.dto.ApplicationDto;
import ee.hitsa.ois.web.dto.ApplicationSearchDto;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/{id:\\d+}")
    public ApplicationDto get(HoisUserDetails user, @WithEntity("id") Application application) {
        UserUtil.assertSameSchool(user, application.getStudent().getSchool());
        return applicationService.get(user, application);
    }

    @GetMapping("")
    public Page<ApplicationSearchDto> search(ApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        return applicationService.search(user, command, pageable);
    }

    @PostMapping("")
    public ApplicationDto create(@Valid @RequestBody ApplicationForm applicationForm, HoisUserDetails user) {
        Student student = studentRepository.getOne(applicationForm.getStudent().getId());
        if(!(UserUtil.isSame(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool()))) {
            throw new HoisBusinessRuleException(String.format("user %s is not allowed to create application", user.getUsername()));
        }

        return get(user, applicationService.create( applicationForm));
    }

    @PutMapping("/{id:\\d+}")
    public ApplicationDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Application application, @Valid @RequestBody ApplicationForm applicationForm) {
        checkUpdateBusinessRules(user, application, applicationForm);
        return get(user, applicationService.save(application, applicationForm));
    }


    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Application application, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        Student student = application.getStudent();
        School school = application.getStudent().getSchool();
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));
        if(((UserUtil.isSame(user, student) || UserUtil.isSchoolAdmin(user, school)) && ApplicationStatus.AVALDUS_STAATUS_KOOST.equals(status))) {
            applicationService.delete(application);
        } else {
            throw new HoisBusinessRuleException(String.format("user %s is not allowed to delete application %d with status %s", user.getUsername(), application.getId(), status.name()));
        }
    }

    @GetMapping("/student/{id:\\d+}/validAcademicLeave")
    public ApplicationDto academicLeave(HoisUserDetails user, @WithEntity(value = "id") Student student) {
        return ApplicationDto.of(applicationService.findValidAcademicLeave(EntityUtil.getId(student)));
    }

    @GetMapping("student/{id:\\d+}/applicable")
    public Map<ApplicationType, Boolean> applicableApplications(HoisUserDetails user, @WithEntity(value = "id") Student student) {
        return applicationService.applicableApplications(EntityUtil.getId(student), user.getSchoolId());
    }

    @GetMapping("/{id:\\d+}/validAcademicLeaveRevocation")
    public ApplicationDto academicLeaveRevocation(@PathVariable("id") Long applicationId) {
        return ApplicationDto.of(applicationService.findValidAcademicLeaveRevocation(applicationId));
    }

    @PutMapping("/{id:\\d+}/submit")
    public ApplicationDto submit(HoisUserDetails user, @WithEntity(value = "id") Application application) {
        Student student = application.getStudent();
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));

        if (UserUtil.isStudentRepresentative(user, student) && ApplicationStatus.AVALDUS_STAATUS_KOOST.equals(status)) {
            applicationService.submit(user, application);
        } else if (UserUtil.isSame(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool()) && ApplicationStatus.AVALDUS_STAATUS_KOOST.equals(status)) {
            applicationService.submit(user, application);
        } else {
            throw new HoisBusinessRuleException(String.format("User %s is not allowed to submit application %d with status %s", user.getUsername(), application.getId(), status));
        }

        return get(user, application);
    }

    @PutMapping("/{id:\\d+}/reject")
    public ApplicationDto reject(HoisUserDetails user, @WithEntity(value = "id") Application application,
            @Valid @RequestBody ApplicationRejectForm applicationRejectForm) {
        Student student = application.getStudent();
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));

        if (UserUtil.isStudentRepresentative(user, student) && ApplicationStatus.AVALDUS_STAATUS_KOOST.equals(status)
                && Boolean.TRUE.equals(application.getNeedsRepresentativeConfirm())) {
            applicationService.reject(application, applicationRejectForm);
        } else if (UserUtil.isSchoolAdmin(user, student.getSchool()) && ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.equals(status)) {
            applicationService.reject(application, applicationRejectForm);
        } else {
            throw new HoisBusinessRuleException(String.format("user %s is not allowed to reject application %d with status %s", user.getUsername(), application.getId(), status));
        }

        return get(user, application);
    }

    private static void checkUpdateBusinessRules(HoisUserDetails user, Application application, ApplicationForm applicationForm) {
        UserUtil.assertSameSchool(user, application.getStudent().getSchool());

        Student student = application.getStudent();
        School school = application.getStudent().getSchool();
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));


        switch (status) {
        case AVALDUS_STAATUS_KOOST:
            if ((UserUtil.isAdultStudent(user, student) || UserUtil.isSchoolAdmin(user, school)) && (applicationForm.getStatus().equals(ApplicationStatus.AVALDUS_STAATUS_KOOST.name())
                    || applicationForm.getStatus().equals(ApplicationStatus.AVALDUS_STAATUS_ESIT.name()))) {
                break;
            } else if (!UserUtil.isAdultStudent(user, student) && (applicationForm.getStatus().equals(ApplicationStatus.AVALDUS_STAATUS_KOOST.name()))) {
                break;
            } else if (UserUtil.isStudentRepresentative(user, student) && (applicationForm.getStatus().equals(ApplicationStatus.AVALDUS_STAATUS_ESIT.name()) ||
                    applicationForm.getStatus().equals(ApplicationStatus.AVALDUS_STAATUS_TAGASI.name()))) {
                break;
            }
        case AVALDUS_STAATUS_ESIT:
            //fallthrough
        case AVALDUS_STAATUS_YLEVAAT:
            if(UserUtil.isSchoolAdmin(user, school)
                    && (applicationForm.getStatus().equals(ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name())) || applicationForm.getStatus().equals(ApplicationStatus.AVALDUS_STAATUS_TAGASI.name())) {
                break;
            }
            //fallthrough
        case AVALDUS_STAATUS_KINNITAM:
            //fallthrough
        case AVALDUS_STAATUS_KINNITATUD:
            //fallthrough
        case AVALDUS_STAATUS_TAGASI:
            throw new HoisBusinessRuleException(String.format("user %s is not allowed to update application %d with status %s", user.getUsername(), application.getId(), status.name()));
        default:
            break;
        }
    }


}
