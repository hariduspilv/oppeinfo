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
import ee.hitsa.ois.web.commandobject.ClassifierAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.DirectiveCoordinatorAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.PersonLookupCommand;
import ee.hitsa.ois.web.commandobject.StudentAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.PersonDto;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;
import ee.hitsa.ois.web.dto.StudyPeriodDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;
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
    public List<ClassifierSelection> classifiers(@Valid ClassifierAutocompleteCommand lookup) {
        List<String> codes = lookup.getMainClassCodes();
        if(codes == null) {
            codes = Collections.singletonList(lookup.getMainClassCode());
        }
        return autocompleteService.classifiers(codes);
    }

    @GetMapping("/classifiers/withparents")
    public List<ClassifierSelection> classifiersWithParents(@Valid ClassifierAutocompleteCommand lookup) {
        List<String> codes = lookup.getMainClassCodes();
        if(codes == null) {
            codes = Collections.singletonList(lookup.getMainClassCode());
        }
        return autocompleteService.classifiersWithParents(codes);
    }

    @GetMapping("/curriculums")
    public Page<AutocompleteResult> curriculums(HoisUserDetails user, AutocompleteCommand term) {
        return asPage(autocompleteService.curriculums(user.getSchoolId(), term));
    }

    @GetMapping("/curriculumversions")
    public List<CurriculumVersionResult> curriculumVersions(HoisUserDetails user, @Valid CurriculumVersionAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersions(user.getSchoolId(), lookup);
    }

    @GetMapping("/curriculumversionomodules")
    public List<AutocompleteResult> curriculumVersionOmodules(@RequestParam("curriculumVersionId") Long curriculumVersionId) {
        return autocompleteService.curriculumVersionOccupationModules(curriculumVersionId);
    }

    @GetMapping("/curriculumversionomodulethemes")
    public List<AutocompleteResult> curriculumVersionOmoduleThemes(@RequestParam("curriculumVersionOmoduleId") Long curriculumVersionOmoduleId) {
        return autocompleteService.curriculumVersionOccupationModuleThemes(curriculumVersionOmoduleId);
    }

    @GetMapping("/directivecoordinators")
    public List<AutocompleteResult> directiveCoordinators(HoisUserDetails user, @Valid DirectiveCoordinatorAutocompleteCommand lookup) {
        return autocompleteService.directiveCoordinators(user.getSchoolId(), lookup);
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
    public List<StudentGroupResult> studentGroups(HoisUserDetails user, @RequestParam(name = "valid", required = false) Boolean valid) {
        return autocompleteService.studentGroups(user.getSchoolId(), valid, null);
    }

    @GetMapping("/subjects")
    public Page<AutocompleteResult> subjects(HoisUserDetails user, AutocompleteCommand command) {
        return autocompleteService.subjects(user.getSchoolId(), command).map(AutocompleteResult::of);
    }

    @GetMapping("/teachers")
    public Page<AutocompleteResult> teachers(HoisUserDetails user, @Valid TeacherAutocompleteCommand lookup) {
        return asPage(autocompleteService.teachers(user.getSchoolId(), lookup));
    }

    @GetMapping("/students")
    public Page<AutocompleteResult> students(HoisUserDetails user, @Valid StudentAutocompleteCommand lookup) {
        if(user.isStudent()) {
            // student is allowed to lookup himself/herself
            lookup.setId(user.getStudentId());
        }
        return asPage(autocompleteService.students(user.getSchoolId(), lookup));
    }

    @GetMapping("/studyPeriods")
    public List<StudyPeriodDto> studyPeriods(HoisUserDetails user) {
        return autocompleteService.studyPeriods(user.getSchoolId());
    }

    @GetMapping("/studyYears")
    public List<StudyYearSearchDto> studyYears(HoisUserDetails user) {
        return autocompleteService.studyYears(user.getSchoolId());
    }

    @GetMapping("/saisAdmissionCodes")
    public List<AutocompleteResult> saisAdmissionCodes(HoisUserDetails user) {
        return autocompleteService.saisAdmissionCodes(user.getSchoolId());
    }

    @GetMapping("/saisClassifiers")
    public List<SaisClassifierSearchDto> saisClassifiers(@RequestParam(name = "parentCode") String parentCode) {
        return autocompleteService.saisClassifiers(parentCode);
    }

    @GetMapping("/vocationalmodules")
    public Page<AutocompleteResult> vocationalModules(HoisUserDetails user, @Valid AutocompleteCommand lookup) {
        return autocompleteService.vocationalModules(user.getSchoolId(), lookup);
    }

    @GetMapping("/journals")
    public List<AutocompleteResult> journals(HoisUserDetails user) {
        return autocompleteService.journals(user);
    }

    private static <R> Page<R> asPage(List<R> data) {
        return new PageImpl<>(data);
    }
}
