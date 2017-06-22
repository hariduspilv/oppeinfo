package ee.hitsa.ois.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.service.ApplicationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.commandobject.ApplicationRejectForm;
import ee.hitsa.ois.web.commandobject.ApplicationSearchCommand;
import ee.hitsa.ois.web.dto.ApplicationApplicableDto;
import ee.hitsa.ois.web.dto.ApplicationDto;
import ee.hitsa.ois.web.dto.ApplicationSearchDto;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/{id:\\d+}")
    public ApplicationDto get(HoisUserDetails user, @WithEntity("id") Application application) {
        if (!UserUtil.canViewStudent(user, application.getStudent())) {
            throw new ValidationFailedException(String.format("user %s is not allowed to view application %d", user.getUsername(), application.getId()));
        }
        return applicationService.get(user, application);
    }

    @GetMapping
    public Page<ApplicationSearchDto> search(ApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return applicationService.search(user, command, pageable);
    }

    @PostMapping
    public ApplicationDto create(@Valid @RequestBody ApplicationForm applicationForm, HoisUserDetails user) {
        return get(user, applicationService.create(user, applicationForm));
    }

    @PutMapping("/{id:\\d+}")
    public ApplicationDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Application application, @Valid @RequestBody ApplicationForm applicationForm) {
        if (!UserUtil.isSchoolAdmin(user, application.getStudent().getSchool()) && !UserUtil.isSame(user, application.getStudent())) {
            throw new ValidationFailedException(String.format("user %s is not allowed to update application %d", user.getUsername(), application.getId()));
        }
        checkUpdateBusinessRules(user, application, applicationForm);
        return get(user, applicationService.save(application, applicationForm));
    }


    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Application application, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        Student student = application.getStudent();
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));
        if(!(UserUtil.isSame(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool())) || !ApplicationStatus.AVALDUS_STAATUS_KOOST.equals(status) ||
                Boolean.TRUE.equals(application.getNeedsRepresentativeConfirm())) {
            throw new ValidationFailedException(String.format("user %s is not allowed to delete application %d with status %s", user.getUsername(), application.getId(), status.name()));
        }
        applicationService.delete(application);
    }

    @GetMapping("/student/{id:\\d+}/validAcademicLeave")
    public ApplicationDto academicLeave(HoisUserDetails user, @WithEntity(value = "id") Student student) {
        if(!(UserUtil.isSame(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool()))) {
            throw new ValidationFailedException(String.format("user %s is not allowed to view validAcademicLeave", user.getUsername()));
        }
        return ApplicationDto.of(applicationService.findLastValidAcademicLeaveWithoutRevocation(EntityUtil.getId(student)));
    }

    @GetMapping("student/{id:\\d+}/applicable")
    public Map<ApplicationType, ApplicationApplicableDto> applicableApplications(HoisUserDetails user, @WithEntity(value = "id") Student student) {
        if(!(UserUtil.isSame(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool()))) {
            throw new ValidationFailedException(String.format("user %s is not allowed to view applicable", user.getUsername()));
        }

        return applicationService.applicableApplicationTypes(student);
    }

    @PutMapping("/{id:\\d+}/submit")
    public ApplicationDto submit(HoisUserDetails user, @WithEntity(value = "id") Application application) {
        if(!UserUtil.canSubmitApplication(user, application)) {
            String status = EntityUtil.getCode(application.getStatus());
            throw new ValidationFailedException(String.format("User %s is not allowed to submit application %d with status %s", user.getUsername(), application.getId(), status));
        }

        Application submitedApplication = applicationService.submit(user, application);
        Student student = application.getStudent();
        if (UserUtil.isSame(user, student) && !UserUtil.isAdultStudent(user, student)) {
            applicationService.sendConfirmNeededNotificationMessage(submitedApplication);
        }

        return get(user, submitedApplication);
    }

    @PutMapping("/{id:\\d+}/reject")
    public ApplicationDto reject(HoisUserDetails user, @WithEntity(value = "id") Application application,
            @Valid @RequestBody ApplicationRejectForm applicationRejectForm) {
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));

        if (!UserUtil.canRejectApplication(user, application)) {
            throw new ValidationFailedException(String.format("user %s is not allowed to reject application %d with status %s", user.getUsername(), application.getId(), status));
        }
        Application rejectedApplication = applicationService.reject(application, applicationRejectForm);
        applicationService.sendRejectionNotificationMessage(rejectedApplication, user);

        return get(user, rejectedApplication);
    }

    @SuppressWarnings("fallthrough")
    private static void checkUpdateBusinessRules(HoisUserDetails user, Application application, ApplicationForm applicationForm) {
        UserUtil.assertSameSchool(user, application.getStudent().getSchool());

        Student student = application.getStudent();
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));

        switch (status) {
        case AVALDUS_STAATUS_KOOST:
            if ((UserUtil.isAdultStudent(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool())) && (ApplicationStatus.AVALDUS_STAATUS_KOOST.name().equals(applicationForm.getStatus())
                    || ApplicationStatus.AVALDUS_STAATUS_ESIT.name().equals(applicationForm.getStatus()))) {
                break;
            } else if (!UserUtil.isAdultStudent(user, student) && ApplicationStatus.AVALDUS_STAATUS_KOOST.name().equals(applicationForm.getStatus())) {
                break;
            } else if (UserUtil.isStudentRepresentative(user, student) && (ApplicationStatus.AVALDUS_STAATUS_ESIT.name().equals(applicationForm.getStatus()) ||
                    ApplicationStatus.AVALDUS_STAATUS_TAGASI.name().equals(applicationForm.getStatus()))) {
                break;
            }
        case AVALDUS_STAATUS_ESIT:
            //fallthrough
        case AVALDUS_STAATUS_YLEVAAT:
            if(UserUtil.isSchoolAdmin(user, student.getSchool())
                    && (ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name().equals(applicationForm.getStatus())) || ApplicationStatus.AVALDUS_STAATUS_TAGASI.name().equals(applicationForm.getStatus())) {
                break;
            }
            //fallthrough
        case AVALDUS_STAATUS_KINNITAM:
            //fallthrough
        case AVALDUS_STAATUS_KINNITATUD:
            //fallthrough
        case AVALDUS_STAATUS_TAGASI:
            throw new ValidationFailedException(String.format("user %s is not allowed to update application %d with status %s", user.getUsername(), application.getId(), status.name()));
        default:
            break;
        }
    }
}
