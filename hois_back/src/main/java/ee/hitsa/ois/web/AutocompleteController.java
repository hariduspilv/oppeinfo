package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

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
import ee.hitsa.ois.web.commandobject.AutocompleteCommand;
import ee.hitsa.ois.web.commandobject.PersonLookupCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.PersonDto;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.student.StudentGroupResult;

@RestController
@RequestMapping("/autocomplete")
public class AutocompleteController {

    @Autowired
    private AutocompleteService autocompleteService;

    @GetMapping("/buildings")
    public List<AutocompleteResult> buildings(HoisUserDetails user) {
        return autocompleteService.buildings(user.getSchoolId());
    }

    @GetMapping("/classifiers")
    public List<ClassifierSelection> classifiers(@RequestParam(name = "mainClassCode", required = false) String mainClassCode,
            @RequestParam(name = "mainClassCodes", required = false) List<String> mainClassCodes) {
        if (mainClassCode == null && mainClassCodes == null) {
            throw new IllegalArgumentException("mainClassCode or mainClassCodes must be specified");
        }
        List<String> codes = mainClassCodes != null ? mainClassCodes : Collections.singletonList(mainClassCode);
        return autocompleteService.classifiers(codes);
    }

    @GetMapping("/curriculums")
    public Page<AutocompleteResult> curriculums(HoisUserDetails user, AutocompleteCommand term) {
        return asPage(autocompleteService.curriculums(user.getSchoolId(), term));
    }

    @GetMapping("/curriculumversions")
    public List<CurriculumVersionResult> curriculumVersions(HoisUserDetails user, @RequestParam(name = "valid", required = false) Boolean valid, @RequestParam(name = "sais", required = false) Boolean sais) {
        return autocompleteService.curriculumVersions(user.getSchoolId(), valid, sais);
    }

    @GetMapping("/directivecoordinators")
    public List<AutocompleteResult> directiveCoordinators(HoisUserDetails user) {
        return autocompleteService.directiveCoordinators(user.getSchoolId());
    }

    @GetMapping("/persons")
    public ResponseEntity<PersonDto> person(@Valid PersonLookupCommand lookup) {
        Person person = autocompleteService.person(lookup);
        return person != null ? new ResponseEntity<>(PersonDto.of(person), HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @GetMapping("/schools")
    public List<SchoolWithoutLogo> schools() {
        return autocompleteService.schools();
    }

    @GetMapping("/schooldepartments")
    public List<SchoolDepartmentResult> schoolDepartments(HoisUserDetails user) {
        return autocompleteService.schoolDepartments(user.getSchoolId());
    }

    @GetMapping("/studentgroups")
    public List<StudentGroupResult> studentGroups(HoisUserDetails user) {
        return autocompleteService.studentGroups(user.getSchoolId());
    }

    @GetMapping("/subjects")
    public Page<AutocompleteResult> subjects(HoisUserDetails user, AutocompleteCommand command) {
        return autocompleteService.subjects(user.getSchoolId(), command).map(AutocompleteResult::of);
    }

    @GetMapping("/teachers")
    public Page<AutocompleteResult> teachers(HoisUserDetails user, @Valid AutocompleteCommand lookup) {
        return autocompleteService.teachers(user.getSchoolId(), lookup);
    }

    @GetMapping("/students")
    public Page<AutocompleteResult> students(HoisUserDetails user, @Valid AutocompleteCommand lookup) {
        return asPage(autocompleteService.students(user.getSchoolId(), lookup));
    }

    @GetMapping("/studyPeriods")
    public List<AutocompleteResult> studyPeriods(HoisUserDetails user) {
        return autocompleteService.studyPeriods(user.getSchoolId());
    }

    @GetMapping("/saisAdmissionCodes")
    public List<AutocompleteResult> saisAdmissionCodes(HoisUserDetails user) {
        return autocompleteService.saisAdmissionCodes(user.getSchoolId());
    }

    private static <R> Page<R> asPage(List<R> data) {
        return new PageImpl<>(data);
    }
}
