package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.Subject;
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

    @PostMapping(value = "")
    public SubjectDto create(HoisUserDetails user, @Valid @RequestBody SubjectForm newSubject) {
        Subject subject = EntityUtil.bindToEntity(newSubject, new Subject());
        subject.setSchool(user.getSchool());
        return get(user, subjectService.save(subject, newSubject));
    }

    @PutMapping(value = "/{id}")
    public SubjectDto save(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Subject subject, @Valid @RequestBody SubjectForm newSubject) {
        Long schoolId = user.getSchool().getId();
        if(schoolId == null || subject.getSchool() == null || !schoolId.equals(subject.getSchool().getId())) {
            throw new IllegalArgumentException();
        }
        return get(user, subjectService.save(EntityUtil.bindToEntity(newSubject, subject), newSubject));
    }

    @GetMapping(value = "/{id}")
    public SubjectDto get(HoisUserDetails user, @WithEntity("id") Subject subject) {
        Long schoolId = user.getSchool().getId();
        if(schoolId == null || subject.getSchool() == null || !schoolId.equals(subject.getSchool().getId())) {
            throw new IllegalArgumentException();
        }
        return SubjectDto.of(subject);
    }

    @GetMapping(value = "")
    public Page<SubjectDto> search(SubjectSearchCommand subjectSearchCommand, HoisUserDetails user,Pageable pageable) {
        return subjectService.search(user.getSchool().getId(), subjectSearchCommand, pageable).map(SubjectDto::of);
    }

    @GetMapping(value = "/initSearchFormData")
    public SubjectSearchFormData getSearchForm(HoisUserDetails user) {
        Long schoolId = user.getSchool().getId();
        SubjectSearchFormData searchFormData = new SubjectSearchFormData();
        searchFormData.departments = autocompleteService.schoolDepartments(schoolId, new SchoolDepartmentAutocompleteCommand());
        searchFormData.curricula = autocompleteService.curriculums(schoolId);
        return searchFormData;
    }

    @GetMapping(value = "/initEditFormData")
    public Map<String, List<AutocompleteResult<Long>>> getEditForm(HoisUserDetails user) {
        Map<String, List<AutocompleteResult<Long>>> result = new HashMap<>();
        Long schoolId = user.getSchool().getId();
        result.put("departments", autocompleteService.schoolDepartments(schoolId, new SchoolDepartmentAutocompleteCommand()));
        return result;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Subject subject, @RequestParam("version") Long version) {
        Long schoolId = user.getSchool().getId();
        if(schoolId == null || subject.getSchool() == null || !schoolId.equals(subject.getSchool().getId())) {
            throw new IllegalArgumentException();
        }
        subjectService.delete(subject);
    }
}

class SubjectSearchFormData {
    public List<AutocompleteResult<Long>> departments;
    public List<AutocompleteResult<Long>> curricula;
}
