package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.SubjectService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentAutocompleteCommand;
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
        newSubject.setStatus(SubjectStatus.AINESTAATUS_S.name());
        return get(user, subjectService.save(user, new Subject(), newSubject));
    }

    @PutMapping("/{id:\\d+}")
    public SubjectDto save(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Subject subject, @Valid @RequestBody SubjectForm newSubject) {
        assertSameSchool(user, subject);
        return get(user, subjectService.save(user, subject, newSubject));
    }

    @GetMapping("/{id:\\d+}")
    public SubjectDto get(HoisUserDetails user, @WithEntity("id") Subject subject) {
        assertSameSchool(user, subject);
        return SubjectDto.of(subject, curriculumVersionRepository.findAllDistinctByModules_Subjects_Subject_id(subject.getId()));
    }

    @GetMapping("")
    public Page<SubjectSearchDto> search(@Valid SubjectSearchCommand subjectSearchCommand, HoisUserDetails user, Pageable pageable) {
        return subjectService.search(user.getSchoolId(), subjectSearchCommand, pageable);
    }

    @GetMapping("/initSearchFormData")
    public SubjectSearchFormData getSearchForm(HoisUserDetails user) {
        Long schoolId = user.getSchoolId();
        SubjectSearchFormData searchFormData = new SubjectSearchFormData();
        searchFormData.departments = autocompleteService.schoolDepartments(schoolId, new SchoolDepartmentAutocompleteCommand());
        searchFormData.curricula = autocompleteService.curriculums(schoolId);
        return searchFormData;
    }

    @GetMapping("/initEditFormData")
    public Map<String, List<AutocompleteResult<Long>>> getEditForm(HoisUserDetails user) {
        Map<String, List<AutocompleteResult<Long>>> result = new HashMap<>();
        Long schoolId = user.getSchoolId();
        result.put("departments", autocompleteService.schoolDepartments(schoolId, new SchoolDepartmentAutocompleteCommand()));
        return result;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Subject subject, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertSameSchool(user, subject);
        subjectService.delete(subject);
    }

    private static void assertSameSchool(HoisUserDetails user, Subject subject) {
        Long schoolId = user.getSchoolId();
        if (schoolId == null || !schoolId.equals(EntityUtil.getNullableId(subject.getSchool()))) {
            throw new IllegalArgumentException();
        }
    }
}

class SubjectSearchFormData {
    public List<AutocompleteResult<Long>> departments;
    public List<AutocompleteResult<Long>> curricula;
}
