package ee.hitsa.ois.web;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.SubjectAutocompleteCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.commandobject.PersonLookupCommand;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.PersonDto;

@RestController
@RequestMapping("/autocomplete")
public class AutocompleteController {

    @Autowired
    private AutocompleteService autocompleteService;

    @GetMapping("/buildings")
    public List<AutocompleteResult<Long>> buildings(HoisUserDetails user) {
        return autocompleteService.buildings(user.getSchoolId());
    }

    @GetMapping("/classifiers")
    public List<ClassifierSelection> classifiers(@NotNull @RequestParam("mainClassCode") String mainClassCode) {
        return autocompleteService.classifiers(mainClassCode);
    }

    @GetMapping("/curriculumversions")
    public Page<AutocompleteResult<Long>> curriculumVersions(HoisUserDetails user) {
        return asAutocompleteResult(autocompleteService.curriculumVersions(user.getSchoolId()), r -> r);
    }

    @GetMapping("/persons")
    public ResponseEntity<PersonDto> person(@Valid PersonLookupCommand lookup) {
        Person person = autocompleteService.person(lookup);
        return person != null ? new ResponseEntity<>(PersonDto.of(person), HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @GetMapping("/schooldepartments")
    public Page<AutocompleteResult<Long>> schoolDepartments(HoisUserDetails user, SchoolDepartmentAutocompleteCommand criteria) {
        return asAutocompleteResult(autocompleteService.schoolDepartments(user.getSchoolId(), criteria), Function.identity());
    }

    @GetMapping("/subjects")
    public Page<AutocompleteResult<Long>> subjects(HoisUserDetails user, SubjectAutocompleteCommand command) {
        return asAutocompleteResult(autocompleteService.subjects(user.getSchoolId(), command), AutocompleteResult::of);
    }

    private static <V, R> Page<R> asAutocompleteResult(Page<V> data, Function<V, R> mapper) {
        return asAutocompleteResult(data.getContent(), mapper);
    }

    private static <V, R> Page<R> asAutocompleteResult(List<V> data, Function<V, R> mapper) {
        return new PageImpl<>(data.stream().map(mapper).collect(Collectors.toList()));
    }
}
