package ee.hitsa.ois.web;

import java.util.HashMap;
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

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.SubjectService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.SubjectUserRights;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.subject.SubjectForm;
import ee.hitsa.ois.web.commandobject.subject.SubjectSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public SubjectDto create(HoisUserDetails user, @Valid @RequestBody SubjectForm newSubject) {
        SubjectUserRights.assertCanCreate(user);
        return get(user, subjectService.create(user, newSubject));
    }

    @GetMapping("/getPermissions")
    public Map<String, ?> canCreate(HoisUserDetails user) {
        SubjectUserRights.assertCanSearch(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("canCreate", Boolean.valueOf(SubjectUserRights.hasPermissionToEdit(user)));
        response.put("canViewAll", Boolean.valueOf(SubjectUserRights.canViewAllSubjects(user)));
        return response;
    }

    @PutMapping("/{id:\\d+}")
    public SubjectDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Subject subject,
            @Valid @RequestBody SubjectForm newSubject) {
        SubjectUserRights.assertCanEdit(user, subject);
        return get(user, subjectService.save(user, subject, newSubject));
    }

    @PutMapping("saveAndConfirm/{id:\\d+}")
    public SubjectDto saveAndConfirm(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Subject subject, @Valid @RequestBody SubjectForm newSubject) {
        SubjectUserRights.assertCanEdit(user, subject);
        SubjectUserRights.assertCanSetActive(user, subject);
        return get(user, subjectService.saveAndConfirm(user, subject, newSubject));
    }

    @PutMapping("saveAndUnconfirm/{id:\\d+}")
    public SubjectDto saveAndUnconfirm(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Subject subject, @Valid @RequestBody SubjectForm newSubject) {
        SubjectUserRights.assertCanEdit(user, subject);
        SubjectUserRights.assertCanSetPassive(user, subject);
        return get(user, subjectService.saveAndUnconfirm(user, subject, newSubject));
    }

    @GetMapping("/{id:\\d+}")
    public SubjectDto get(HoisUserDetails user, @WithEntity Subject subject) {
        SubjectUserRights.assertCanView(user, subject);
        return subjectService.get(user, subject);
    }

    @GetMapping
    public Page<SubjectSearchDto> search(@Valid SubjectSearchCommand subjectSearchCommand, HoisUserDetails user, Pageable pageable) {
        SubjectUserRights.assertCanSearch(user);
        subjectSearchCommand.setSchoolId(user.getSchoolId());
        return subjectService.search(user, subjectSearchCommand, pageable);
    }

    @GetMapping("/initSearchFormData")
    public Map<String, List<? extends AutocompleteResult>> getSearchForm(HoisUserDetails user) {
        Long schoolId = user.getSchoolId();
        Map<String, List<? extends AutocompleteResult>> result = new HashMap<>();
        result.put("departments", autocompleteService.schoolDepartments(schoolId));
        CurriculumVersionAutocompleteCommand lookup = new CurriculumVersionAutocompleteCommand();
        lookup.setHigher(Boolean.TRUE);
        result.put("curricula", autocompleteService.curriculumVersions(schoolId, lookup));
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
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Subject subject, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        SubjectUserRights.assertCanDelete(user, subject);
        subjectService.delete(user, subject);
    }

    @GetMapping("/unique/code")
    public boolean isCodeUnique(HoisUserDetails user, UniqueCommand command) {
        return subjectService.isCodeUnique(user.getSchoolId(), command);
    }
}
