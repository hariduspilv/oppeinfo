package ee.hitsa.ois.web;

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

import ee.hitsa.ois.domain.subject.SubjectStudyPeriod;
import ee.hitsa.ois.service.SubjectStudyPeriodService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

@RestController
@RequestMapping("/subjectStudyPeriod")
public class SubjectStudyPeriodController {


    @Autowired
    private SubjectStudyPeriodService subjectStudyPeriodService;

    @GetMapping
    public Page<SubjectStudyPeriodSearchDto> search(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SubjectStudyPeriodDto get(@WithEntity("id") SubjectStudyPeriod subjectStudyPeriod) {
        return SubjectStudyPeriodDto.of(subjectStudyPeriod);
    }

    @PostMapping
    public SubjectStudyPeriodDto create(@Valid @RequestBody SubjectStudyPeriodForm form) {
        return get(subjectStudyPeriodService.create(form));
    }

    @PutMapping("/{id:\\d+}")
    public SubjectStudyPeriodDto update(
            @WithVersionedEntity(value = "id", versionRequestBody = true) SubjectStudyPeriod subjectStudyPeriod, 
            @Valid @RequestBody SubjectStudyPeriodForm form) {
        return get(subjectStudyPeriodService.updateSubjectStudyPeriodTeachers(subjectStudyPeriod, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") SubjectStudyPeriod subjectStudyPeriod, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        subjectStudyPeriodService.delete(subjectStudyPeriod);
    }
}
