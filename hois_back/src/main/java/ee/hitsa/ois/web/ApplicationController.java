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
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.repository.SchoolRepository;
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
    private SchoolRepository schoolRepository;

    @GetMapping("/{id:\\d+}")
    public ApplicationDto get(@WithEntity("id") Application application) {
        return ApplicationDto.of(application);
    }

    @GetMapping("")
    public Page<ApplicationSearchDto> search(ApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        return applicationService.search(user, command, pageable);
    }

    @PostMapping("")
    public ApplicationDto create(@Valid @RequestBody ApplicationForm applicationForm, HoisUserDetails user) {
        return get(applicationService.create(user, applicationForm));
    }

    @PutMapping("/{id:\\d+}")
    public ApplicationDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Application application,
            @Valid @RequestBody ApplicationForm applicationForm) {
        UserUtil.assertSameSchool(user, application.getStudent().getSchool());
        return get(applicationService.save(application, applicationForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Application application) {
        UserUtil.assertSameSchool(user, application.getStudent().getSchool());
        applicationService.delete(application);
    }

    @GetMapping("/student/{id:\\d+}/validAcademicLeave")
    public ApplicationDto academicLeave(HoisUserDetails user, @WithEntity(value = "id") Student student) {
        return ApplicationDto.of(applicationService.findValidAcademicLeave(EntityUtil.getId(student), EntityUtil.getNullableCode(schoolRepository.getOne(user.getSchoolId()).getEhisSchool())));
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
        if (UserUtil.isStudentRepresentative(user, student)) {
            applicationService.submit(user, application);
        } else if (UserUtil.isSame(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool())) {
            applicationService.submit(user, application);
        } else {
            throw new RuntimeException("User has no rights to submit this application");
        }

        return get(application);
    }

    @PutMapping("/{id:\\d+}/reject")
    public ApplicationDto reject(HoisUserDetails user, @WithEntity(value = "id") Application application,
            @Valid @RequestBody ApplicationRejectForm applicationRejectForm) {
        Student student = application.getStudent();

        if (UserUtil.isStudentRepresentative(user, student)) {
            applicationService.reject(application, applicationRejectForm);
        } else if (UserUtil.isSame(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool())) {
            applicationService.reject(application, applicationRejectForm);
        } else {
            throw new RuntimeException("User has no rights to reject this application");
        }

        return get(application);
    }

}
