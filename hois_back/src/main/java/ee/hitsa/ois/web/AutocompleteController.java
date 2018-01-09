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

import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.commandobject.AutocompleteCommand;
import ee.hitsa.ois.web.commandobject.ClassifierAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.DirectiveCoordinatorAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.JournalAndSubjectAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.JournalAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.PersonLookupCommand;
import ee.hitsa.ois.web.commandobject.RoomsAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.StudentAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.StudentGroupAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.SubjectAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionModuleAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionOccupationModuleThemeAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.EnterpriseResult;
import ee.hitsa.ois.web.dto.PersonDto;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;
import ee.hitsa.ois.web.dto.StudyPeriodWithYearDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.SubjectResult;
import ee.hitsa.ois.web.dto.apelapplication.ApelSchoolResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOModulesAndThemesResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeResult;
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

    @GetMapping("/rooms")
    public Page<AutocompleteResult> rooms(HoisUserDetails user, @Valid RoomsAutocompleteCommand lookup) {
        return asPage(autocompleteService.rooms(user.getSchoolId(), lookup));
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
    
    @GetMapping("/curriculumversionhmodules")
    public List<AutocompleteResult> curriculumVersionHigherModules(@Valid CurriculumVersionModuleAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersionHigherModules(lookup);
    }

    @GetMapping("/curriculumversionomodules")
    public List<CurriculumVersionOccupationModuleResult> curriculumVersionOccupationModules(@Valid CurriculumVersionModuleAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersionOccupationModules(lookup);
    }

    @GetMapping("/curriculumversionomodulethemes")
    public List<CurriculumVersionOccupationModuleThemeResult> curriculumVersionOccupationModuleThemes(@Valid CurriculumVersionOccupationModuleThemeAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersionOccupationModuleThemes(lookup);
    }
    
    @GetMapping("/curriculumversionomodulesandthemes")
    public List<CurriculumVersionOModulesAndThemesResult> curriculumVersionOccupationModulesAndThemes(@Valid CurriculumVersionModuleAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersionOccupationModulesAndThemes(lookup);
    }

    @GetMapping("/directivecoordinators")
    public List<AutocompleteResult> directiveCoordinators(HoisUserDetails user, @Valid DirectiveCoordinatorAutocompleteCommand lookup) {
        return autocompleteService.directiveCoordinators(user.getSchoolId(), lookup);
    }

    @GetMapping("/persons")
    public ResponseEntity<PersonDto> person(HoisUserDetails user, @Valid PersonLookupCommand lookup) {
        PersonDto person = autocompleteService.person(user, lookup);
        return person != null ? new ResponseEntity<>(person, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @GetMapping("/schools")
    public List<SchoolWithoutLogo> schools(AutocompleteCommand lookup) {
        return autocompleteService.schools(lookup);
    }

    @GetMapping("/ldapschools")
    public List<SchoolWithoutLogo> ldapSchools() {
        return autocompleteService.ldapSchools();
    }

    @GetMapping("/apelschools")
    public List<ApelSchoolResult> apelSchools(HoisUserDetails user) {
        return autocompleteService.apelSchools(user.getSchoolId());
    }

    @GetMapping("/schooldepartments")
    public List<SchoolDepartmentResult> schoolDepartments(HoisUserDetails user) {
        return autocompleteService.schoolDepartments(user.getSchoolId());
    }

    @GetMapping("/studentgroups")
    public List<StudentGroupResult> studentGroups(HoisUserDetails user, @Valid StudentGroupAutocompleteCommand lookup) {
        return autocompleteService.studentGroups(user.getSchoolId(), lookup);
    }

    @GetMapping("/subjects")
    public Page<SubjectResult> subjects(HoisUserDetails user, @Valid SubjectAutocompleteCommand lookup) {
        return asPage(autocompleteService.subjects(user.getSchoolId(), lookup));
    }

    @GetMapping("/subjectsList")
    public List<SubjectResult> subjectsAsList(HoisUserDetails user, @Valid SubjectAutocompleteCommand lookup) {
        return autocompleteService.subjects(user.getSchoolId(), lookup);
    }

    @GetMapping("/teachers")
    public Page<AutocompleteResult> teachers(HoisUserDetails user, @Valid TeacherAutocompleteCommand lookup) {
        return asPage(autocompleteService.teachers(user.getSchoolId(), lookup));
    }

    @GetMapping("/teachersList")
    public List<AutocompleteResult> teachersAsList(HoisUserDetails user, @Valid TeacherAutocompleteCommand lookup) {
        return autocompleteService.teachers(user.getSchoolId(), lookup);
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
    public List<StudyPeriodWithYearDto> studyPeriods(HoisUserDetails user) {
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
    public List<AutocompleteResult> journals(HoisUserDetails user, @Valid JournalAutocompleteCommand lookup) {
        return autocompleteService.journals(user, lookup);
    }

    @GetMapping("/journalsAndSubjects")
    public Page<AutocompleteResult> journalsAndSubjects(HoisUserDetails user, @Valid JournalAndSubjectAutocompleteCommand lookup) {
        return asPage(autocompleteService.journalsAndSubjects(user, lookup));
    }
    
    @GetMapping("/enterprises")
    public List<EnterpriseResult> enterprises() {
        return autocompleteService.enterprises();
    }

    private static <R> Page<R> asPage(List<R> data) {
        return new PageImpl<>(data);
    }
}
