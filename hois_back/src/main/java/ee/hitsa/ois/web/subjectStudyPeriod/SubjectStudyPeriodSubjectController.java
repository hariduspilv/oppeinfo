package ee.hitsa.ois.web.subjectStudyPeriod;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodCapacitiesService;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodSubjectSearchService;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodSubjectService;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

@RestController
@RequestMapping("/subjectStudyPeriods/subjects")
public class SubjectStudyPeriodSubjectController {

    @Autowired
    private SubjectStudyPeriodCapacitiesService subjectStudyPeriodCapacitiesService;
    @Autowired
    private SubjectStudyPeriodSubjectSearchService subjectStudyPeriodSubjectSearchService;
    @Autowired
    private SubjectStudyPeriodSubjectService subjectStudyPeriodSubjectService;

    @GetMapping
    public Page<SubjectStudyPeriodSearchDto> search(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodSubjectSearchService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/container")
    public SubjectStudyPeriodDtoContainer getSspContainer(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container) {
        AssertionFailedException.throwIf(container.getSubject() == null,
                "Subject must be specified");
        subjectStudyPeriodSubjectService.setSubjectStudyPeriodsToSubjectsContainer(user.getSchoolId(), container);
        return container;
    }

    @PutMapping("/container")
    public SubjectStudyPeriodDtoContainer updateSspCapacities(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodDtoContainer container) {
        UserUtil.assertIsSchoolAdmin(user);
        AssertionFailedException.throwIf(container.getSubject() == null,
                "Subject must be specified");
        subjectStudyPeriodCapacitiesService.updateSspCapacities(user.getSchoolId(), container);
        return getSspContainer(user, container);
    }

    @GetMapping("/list")
    public List<AutocompleteResult> getSubjectsList(HoisUserDetails user) {
        return subjectStudyPeriodSubjectService.getSubjectsList(user.getSchoolId(), null);
    }

    @GetMapping("/list/limited/{studyPeriodId:\\d+}")
    public List<AutocompleteResult> getSubjectsFilteredList(HoisUserDetails user, @PathVariable("studyPeriodId") Long studyPeriodId) {
        return subjectStudyPeriodSubjectService.getSubjectsList(user.getSchoolId(), studyPeriodId);
    }
}
