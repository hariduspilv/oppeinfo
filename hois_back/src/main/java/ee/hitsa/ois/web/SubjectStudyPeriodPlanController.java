package ee.hitsa.ois.web;

import java.util.List;

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

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodPlan;
import ee.hitsa.ois.service.SubjectStudyPeriodPlanService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodPlanSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanSearchDtoContainer;

@RestController
@RequestMapping("/subjectStudyPeriodPlans")
public class SubjectStudyPeriodPlanController {

    @Autowired
    private SubjectStudyPeriodPlanService subjectStudyPeriodPlanService;

    @GetMapping
    public Page<SubjectStudyPeriodPlanSearchDtoContainer> search(HoisUserDetails user, SubjectStudyPeriodPlanSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodPlanService.search(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public SubjectStudyPeriodPlanDto get(@WithEntity("id") SubjectStudyPeriodPlan plan) {
        return SubjectStudyPeriodPlanDto.of(plan);
    }

    @PostMapping
    public SubjectStudyPeriodPlanDto create(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodPlanDto form) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(),
                "Only school administrator can save subjectStudyPeriodPlan");  
        return get(subjectStudyPeriodPlanService.create(user.getSchoolId(), form));
    }

    @PutMapping("/{id:\\d+}")
    public SubjectStudyPeriodPlanDto update(HoisUserDetails user, 
            @WithVersionedEntity(value = "id", versionRequestBody = true) SubjectStudyPeriodPlan subjectStudyPeriod, 
            @Valid @RequestBody SubjectStudyPeriodPlanDto form) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(),
                "Only school administrator can update subjectStudyPeriodPlan");  
        return get(subjectStudyPeriodPlanService.save(user.getSchoolId(), subjectStudyPeriod, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") SubjectStudyPeriodPlan plan, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(),
                "Only school administrator can delete subjectStudyPeriodPlan");  
        subjectStudyPeriodPlanService.delete(plan);
    }
    
    @GetMapping("/studyPeriods")
    public List<StudyPeriodDto> studyPeriods(HoisUserDetails user) {
        return subjectStudyPeriodPlanService.studyPeriods(user.getSchoolId());
    }
    
    @GetMapping("/curriculums")
    public List<AutocompleteResult> curriculums(HoisUserDetails user) {
        return subjectStudyPeriodPlanService.curriculums(user.getSchoolId());
    }
    
    @GetMapping("/studyPeriod/{id:\\d+}")
    public AutocompleteResult studyPeriod(@WithEntity("id") StudyPeriod studyPeriod) {
        return AutocompleteResult.of(studyPeriod);
    }
    
    @GetMapping("/subject/{id:\\d+}")
    public AutocompleteResult subject(@WithEntity("id") Subject subject) {
        return AutocompleteResult.of(subject);
    }
}
