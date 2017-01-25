package ee.hitsa.ois.web;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import ee.hitsa.ois.web.commandobject.SubjectAutocompleteCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@RestController
@RequestMapping("/autocomplete")
public class AutocompleteController {

    @Autowired
    private AutocompleteService autocompleteService;

    @GetMapping("/schooldepartments")
    public Page<AutocompleteResult<Long>> schoolDepartments(HoisUserDetails user, SchoolDepartmentAutocompleteCommand criteria) {
        return asAutocompleteResult(autocompleteService.schoolDepartments(user.getSchool().getId(), criteria), Function.identity());
    }

    @GetMapping("/subjects")
    public Page<AutocompleteResult<Long>> subjects(HoisUserDetails user, SubjectAutocompleteCommand command) {
        return asAutocompleteResult(autocompleteService.subjects(user.getSchool().getId(), command),
                it -> new AutocompleteResult<>(it.getId(), it.getNameEt(), it.getNameEn()));
    }

    private static <V, R> Page<R> asAutocompleteResult(Page<V> data, Function<V, R> mapper) {
        return asAutocompleteResult(data.getContent(), mapper);
    }

    private static <V, R> Page<R> asAutocompleteResult(List<V> data, Function<V, R> mapper) {
        return new PageImpl<>(data.stream().map(mapper).collect(Collectors.toList()));
    }
}
