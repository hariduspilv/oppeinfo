package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.SubjectService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SubjectForm;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;

    @PostMapping("")
    public SubjectDto create(HoisUserDetails user, @Valid @RequestBody SubjectForm newSubject) {
        return get(user, subjectService.create(user, newSubject));
    }

    @PutMapping("/{id:\\d+}")
    public SubjectDto save(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Subject subject, @Valid @RequestBody SubjectForm newSubject) {
        UserUtil.assertSameSchool(user, subject.getSchool());
        return get(user, subjectService.save(user, subject, newSubject));
    }

    @GetMapping("/{id:\\d+}")
    public SubjectDto get(HoisUserDetails user, @WithEntity("id") Subject subject) {
        UserUtil.assertSameSchool(user, subject.getSchool());
        return SubjectDto.of(subject, curriculumVersionRepository.findAllDistinctByModules_Subjects_Subject_id(subject.getId()));
    }

    @GetMapping("")
    public Page<SubjectSearchDto> search(@Valid SubjectSearchCommand subjectSearchCommand, HoisUserDetails user, Pageable pageable) {
        return subjectService.search(user.getSchoolId(), subjectSearchCommand, pageable);
    }

    @GetMapping("/initSearchFormData")
    public Map<String, List<? extends AutocompleteResult>> getSearchForm(HoisUserDetails user) {
        Long schoolId = user.getSchoolId();
        Map<String, List<? extends AutocompleteResult>> result = new HashMap<>();
        result.put("departments", autocompleteService.schoolDepartments(schoolId));
        result.put("curricula", autocompleteService.curriculumVersions(schoolId, null, null));
        return result;
    }

    @GetMapping("/initEditFormData")
    public Map<String, List<? extends AutocompleteResult>> getEditForm(HoisUserDetails user) {
        Long schoolId = user.getSchoolId();
        Map<String, List<? extends AutocompleteResult>> result = new HashMap<>();
        result.put("departments", autocompleteService.schoolDepartments(schoolId));
        return result;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Subject subject, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, subject.getSchool());
        subjectService.delete(subject);
    }
}
