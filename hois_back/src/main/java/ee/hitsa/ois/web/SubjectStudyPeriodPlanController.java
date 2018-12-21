package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.List;
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

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.SubjectStudyPeriodPlanService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodPlanUniqueCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudyPeriodWithYearDto;
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
    public SubjectStudyPeriodPlanDto get(@WithEntity SubjectStudyPeriodPlan plan) {
        return SubjectStudyPeriodPlanDto.of(plan);
    }

    @PostMapping
    public SubjectStudyPeriodPlanDto create(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodPlanDto form) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(),
                "Only school administrator can save subjectStudyPeriodPlan");  
        return get(subjectStudyPeriodPlanService.create(user.getSchoolId(), form));
    }

    @PutMapping("/{id:\\d+}")
    public SubjectStudyPeriodPlanDto save(HoisUserDetails user, 
            @WithVersionedEntity(versionRequestBody = true) SubjectStudyPeriodPlan subjectStudyPeriod, 
            @Valid @RequestBody SubjectStudyPeriodPlanDto form) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(),
                "Only school administrator can update subjectStudyPeriodPlan");  
        return get(subjectStudyPeriodPlanService.save(user.getSchoolId(), subjectStudyPeriod, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") SubjectStudyPeriodPlan plan, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(),
                "Only school administrator can delete subjectStudyPeriodPlan");  
        subjectStudyPeriodPlanService.delete(user, plan);
    }

    @GetMapping("/exists")
    public Map<String, ?> exists(@Valid SubjectStudyPeriodPlanUniqueCommand form) {
        return Collections.singletonMap("exists", subjectStudyPeriodPlanService.exists(form));
    }

    @GetMapping("/curriculums")
    public List<AutocompleteResult> curriculums(HoisUserDetails user, CurriculumSearchCommand criteria) {
        return subjectStudyPeriodPlanService.curriculums(user.getSchoolId(), criteria);
    }

    @GetMapping("/studyPeriod/{id:\\d+}")
    public StudyPeriodWithYearDto studyPeriod(@WithEntity StudyPeriod studyPeriod) {
        return StudyPeriodWithYearDto.of(studyPeriod);
    }

    @GetMapping("/subject/{id:\\d+}")
    public AutocompleteResult subject(@WithEntity Subject subject) {
        return AutocompleteResult.of(subject);
    }

    @GetMapping("/subjects")
    public Page<AutocompleteResult> getSubjectsOptions(SubjectSearchCommand subjectSearchCommand, HoisUserDetails user, Pageable pageable) {
        return subjectStudyPeriodPlanService.getSubjectsOptions(user.getSchoolId(), subjectSearchCommand, pageable);
        
    }
}
