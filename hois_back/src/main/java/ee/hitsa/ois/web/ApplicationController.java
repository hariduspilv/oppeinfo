package ee.hitsa.ois.web;

import java.util.List;
import java.util.stream.Collectors;

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
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.ApplicationService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.commandobject.ApplicationSearchCommand;
import ee.hitsa.ois.web.dto.ApplicationDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;

    @GetMapping("/{id:\\d+}")
    public ApplicationDto get(@WithEntity("id") Application application) {
        return ApplicationDto.of(application);
    }

    @GetMapping("")
    public Page<ApplicationDto> search(ApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        return applicationService.search(user, command, pageable);
    }

    @PostMapping("")
    public ApplicationDto create(@Valid @RequestBody ApplicationForm applicationForm, HoisUserDetails user) {
        return applicationService.create(user, applicationForm);
    }

    @PutMapping("/{id:\\d+}")
    public ApplicationDto update(HoisUserDetails user, @WithEntity("id") Application application,
            @Valid @RequestBody ApplicationForm applicationForm) {
        assertSameSchool(user, application);
        return applicationService.save(user, application, applicationForm);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity("id") Application application) {
        assertSameSchool(user, application);
        applicationService.delete(application);
    }


    @GetMapping("/academicLeave")
    public ApplicationDto academicLeave(HoisUserDetails user, @RequestParam Long student) {
        return ApplicationDto.of(applicationService.findValidAcademicLeave(student, EntityUtil.getNullableCode(schoolRepository.getOne(user.getSchoolId()).getEhisSchool())));
    }

    @GetMapping("/{id:\\d+}/academicLeaveRevocation")
    public ApplicationDto academicLeaveRevocation(HoisUserDetails user, @PathVariable("id") Long applicationId) {
        return ApplicationDto.of(applicationService.findValidAcademicLeaveRevocation(applicationId));
    }

    @GetMapping("/curriculumVersions")
    public List<AutocompleteResult> curriculumVersions(HoisUserDetails user) {
        return curriculumVersionRepository.findAllByCurriculumSchoolIdAndStatusCode(user.getSchoolId(), "OPPEKAVA_VERSIOON_STAATUS_K")
                .stream().map(AutocompleteResult::of).collect(Collectors.toList());
    }

    private void assertSameSchool(HoisUserDetails user, Application application) {
        Long schoolId = user.getSchoolId();
        if (schoolId == null) {
            throw new IllegalArgumentException();
        }
        School school = schoolService.getOne(schoolId);
        String code = EntityUtil.getNullableCode(application.getEhisSchool());
        if (school == null || school.getEhisSchool() == null || !school.getEhisSchool().getCode().equals(code)) {
            throw new IllegalArgumentException();
        }
    }

}
