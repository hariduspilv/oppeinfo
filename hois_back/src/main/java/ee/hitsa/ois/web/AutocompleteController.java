package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.ClassifierAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.CommitteeAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.DirectiveCoordinatorAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.JournalAndSubjectAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.JournalAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.PersonLookupCommand;
import ee.hitsa.ois.web.commandobject.PracticeEvaluationAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.RoomAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.SpecialitiesAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.StudentAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.StudentGroupAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionOccupationModuleAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionOccupationModuleThemeAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.studymaterial.StudyMaterialAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.subject.SubjectAutocompleteCommand;
import ee.hitsa.ois.web.curriculum.CurriculumVersionHigherModuleAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.JournalAutocompleteResult;
import ee.hitsa.ois.web.dto.OccupiedAutocompleteResult;
import ee.hitsa.ois.web.dto.PersonDto;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.SchoolWithLogo;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;
import ee.hitsa.ois.web.dto.SpecialityAutocompleteResult;
import ee.hitsa.ois.web.dto.StudyPeriodWithYearDto;
import ee.hitsa.ois.web.dto.StudyPeriodWithYearIdDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.SubjectResult;
import ee.hitsa.ois.web.dto.SupervisorDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelSchoolResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOModulesAndThemesResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseResult;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupResult;

@RestController
@RequestMapping("/autocomplete")
public class AutocompleteController {

    @Autowired
    private AutocompleteService autocompleteService;

    @GetMapping("/basemodules")
    public List<AutocompleteResult> basemodules(HoisUserDetails user, BaseModuleAutocompleteCommand lookup) {
        return autocompleteService.basemodules(user.getSchoolId(), lookup);
    }
    
    @GetMapping("/buildings")
    public List<AutocompleteResult> buildings(HoisUserDetails user) {
        return autocompleteService.buildings(user.getSchoolId());
    }

    @GetMapping("/rooms")
    public Page<OccupiedAutocompleteResult> rooms(HoisUserDetails user, RoomAutocompleteCommand lookup) {
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
    
    @GetMapping("/saisCurriculumClassifiers")
    public List<SaisClassifierSearchDto> saisCurriculumClassifiers(HoisUserDetails user) {
    	return autocompleteService.saisCurriculumClassifiers(user.getSchoolId());
    }

    @GetMapping("/classifiers/withparents")
    public List<ClassifierSelection> classifiersWithParents(@Valid ClassifierAutocompleteCommand lookup) {
        List<String> codes = lookup.getMainClassCodes();
        if(codes == null) {
            codes = Collections.singletonList(lookup.getMainClassCode());
        }
        return autocompleteService.classifiersWithParents(codes);
    }

    @GetMapping("/schoolCapacityTypes")
    public List<ClassifierDto> schoolCapacityTypes(HoisUserDetails user, @Valid SchoolCapacityTypeCommand lookup) {
        return autocompleteService.schoolCapacityTypeDtos(user.getSchoolId(), lookup);
    }

    @GetMapping("/curriculums")
    public Page<CurriculumResult> curriculums(HoisUserDetails user, CurriculumAutocompleteCommand term) {
        return asPage(autocompleteService.curriculums(user.getSchoolId(), term, true));
    }
    
    @GetMapping("/curriculumsauto")
    public List<CurriculumResult> curriculumsAsList(HoisUserDetails user, CurriculumAutocompleteCommand term) {
        return autocompleteService.curriculums(user.getSchoolId(), term, false);
    }

    @GetMapping("/curriculumversions")
    public List<CurriculumVersionResult> curriculumVersions(HoisUserDetails user, CurriculumVersionAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersions(user.getSchoolId(), lookup);
    }
    
    @GetMapping("/curriculumversionhmodules")
    public List<CurriculumVersionHigherModuleResult> curriculumVersionHigherModules(HoisUserDetails user,
            @Valid CurriculumVersionHigherModuleAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersionHigherModules(user, lookup);
    }

    @GetMapping("/curriculumversionomodules")
    public List<CurriculumVersionOccupationModuleResult> curriculumVersionOccupationModules(HoisUserDetails user,
            @Valid CurriculumVersionOccupationModuleAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersionOccupationModules(user, lookup);
    }

    @GetMapping("/curriculumversionomodulethemes")
    public List<CurriculumVersionOccupationModuleThemeResult> curriculumVersionOccupationModuleThemes(
            @Valid CurriculumVersionOccupationModuleThemeAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersionOccupationModuleThemes(lookup);
    }

    @GetMapping("/curriculumversionomodulesandthemes")
    public List<CurriculumVersionOModulesAndThemesResult> curriculumVersionOccupationModulesAndThemes(
            HoisUserDetails user, @Valid CurriculumVersionOccupationModuleAutocompleteCommand lookup) {
        return autocompleteService.curriculumVersionOccupationModulesAndThemes(user, lookup);
    }

    @GetMapping("/directivecoordinators")
    public List<AutocompleteResult> directiveCoordinators(HoisUserDetails user, DirectiveCoordinatorAutocompleteCommand lookup) {
        return autocompleteService.directiveCoordinators(user.getSchoolId(), lookup);
    }

    @GetMapping("/persons")
    public ResponseEntity<PersonDto> person(HoisUserDetails user, @Valid PersonLookupCommand lookup) {
        PersonDto person = autocompleteService.person(user, lookup);
        return person != null ? new ResponseEntity<>(person, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @GetMapping("/schools")
    public List<SchoolWithoutLogo> schools(SearchCommand lookup) {
        return autocompleteService.schools(lookup);
    }
    
    @GetMapping("/schoolsWithLogo")
    public List<SchoolWithLogo> schoolsWithLogo(SearchCommand lookup) {
        return autocompleteService.schoolsWithLogo(lookup);
    }

    @GetMapping("/ldapschools")
    public List<SchoolWithoutLogo> ldapSchools() {
        return autocompleteService.ldapSchools();
    }

    @GetMapping("/apelschools")
    public List<ApelSchoolResult> apelSchools(HoisUserDetails user, SearchCommand lookup) {
        return autocompleteService.apelSchools(user.getSchoolId(), lookup);
    }

    @GetMapping("/schooldepartments")
    public List<SchoolDepartmentResult> schoolDepartments(HoisUserDetails user) {
        return autocompleteService.schoolDepartments(user.getSchoolId());
    }

    @GetMapping("/studentgroups")
    public List<StudentGroupResult> studentGroups(HoisUserDetails user, StudentGroupAutocompleteCommand lookup) {
        return autocompleteService.studentGroups(user.getSchoolId(), lookup);
    }

    @GetMapping("/subjects")
    public Page<SubjectResult> subjects(HoisUserDetails user, SubjectAutocompleteCommand lookup) {
        return asPage(autocompleteService.subjects(user.getSchoolId(), lookup));
    }

    @GetMapping("/subjectsList")
    public List<SubjectResult> subjectsAsList(HoisUserDetails user, SubjectAutocompleteCommand lookup) {
        return autocompleteService.subjects(user.getSchoolId(), lookup);
    }

    @GetMapping("/teachers")
    public Page<OccupiedAutocompleteResult> teachers(HoisUserDetails user, TeacherAutocompleteCommand lookup) {
        return asPage(autocompleteService.teachers(user.getSchoolId(), lookup, true));
    }

    @GetMapping("/teachersList")
    public List<OccupiedAutocompleteResult> teachersAsList(HoisUserDetails user, TeacherAutocompleteCommand lookup) {
        return autocompleteService.teachers(user.getSchoolId(), lookup, false);
    }
    
    @GetMapping("/highspecialities")
    public Set<SpecialityAutocompleteResult> specialities(HoisUserDetails user, SpecialitiesAutocompleteCommand lookup) {
        return autocompleteService.specialities(user, lookup);
    }

    @GetMapping("/students")
    public Page<AutocompleteResult> students(HoisUserDetails user, StudentAutocompleteCommand lookup) {
        if(user.isStudent()) {
            // student is allowed to lookup himself/herself
            lookup.setId(user.getStudentId());
        }
        return asPage(autocompleteService.students(user.getSchoolId(), lookup));
    }

    @GetMapping("/studyMaterials")
    public List<AutocompleteResult> studyMaterials(HoisUserDetails user, StudyMaterialAutocompleteCommand lookup) {
        if (user.isTeacher()) {
            lookup.setTeacher(user.getTeacherId());
        }
        return autocompleteService.studyMaterials(user.getSchoolId(), lookup);
    }

    @GetMapping("/studyPeriods")
    public List<StudyPeriodWithYearIdDto> studyPeriods(HoisUserDetails user) {
        return autocompleteService.studyPeriods(user.getSchoolId());
    }

    @GetMapping("/studyPeriodsWithYear")
    public List<StudyPeriodWithYearDto> studyPeriodsWithYear(HoisUserDetails user) {
        return autocompleteService.studyPeriodsWithYear(user.getSchoolId());
    }

    @GetMapping("/studyYears")
    public List<StudyYearSearchDto> studyYears(HoisUserDetails user) {
        return autocompleteService.studyYears(user.getSchoolId());
    }
    
    @GetMapping("/saisAdmissionCodesArchived")
    public List<AutocompleteResult> saisAdmissionCodesArchived(HoisUserDetails user) {
        return autocompleteService.saisAdmissionCodesArchived(user.getSchoolId());
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
    public Page<AutocompleteResult> vocationalModules(HoisUserDetails user, SearchCommand lookup) {
        return autocompleteService.vocationalModules(user.getSchoolId(), lookup);
    }
    
    @GetMapping("/vocationaloccupationmodules")
    public Page<AutocompleteResult> vocationalOccupationModules(HoisUserDetails user, SearchCommand lookup) {
        return autocompleteService.vocationalOccupationModules(user.getSchoolId(), lookup);
    }

    @GetMapping("/journals")
    public List<JournalAutocompleteResult> journals(HoisUserDetails user, JournalAutocompleteCommand lookup) {
        return autocompleteService.journals(user, lookup);
    }

    @GetMapping("/journalsAndSubjects")
    public Page<AutocompleteResult> journalsAndSubjects(HoisUserDetails user, JournalAndSubjectAutocompleteCommand lookup) {
        return asPage(autocompleteService.journalsAndSubjects(user.getSchoolId(), lookup));
    }
    
    /**
     * Ignores study year, study year will be selected from current study year
     * @param user
     * @param lookup
     * @return journals with student groups as "journal_name(student_groups)"
     */
    @GetMapping("/journalsAndStudentGroups")
    public List<AutocompleteResult> journalsAndStudentGroups(HoisUserDetails user, JournalAndSubjectAutocompleteCommand lookup) {
        return autocompleteService.journalsAndStudentGroups(user.getSchoolId(), lookup);
    }

    @GetMapping("/enterprises")
    public List<EnterpriseResult> enterprises(SearchCommand lookup) {
        return autocompleteService.enterprises(lookup);
    }
    
    @GetMapping("/activeEnterprises")
    public List<EnterpriseResult> activeEnterprises(HoisUserDetails user, SearchCommand lookup) {
        return autocompleteService.activeEnterprises(user, lookup);
    }
    
    @GetMapping("/enterpriseLocations")
    public List<AutocompleteResult> enterpriseLocations(HoisUserDetails user, SearchCommand lookup) {
        return autocompleteService.enterpriseLocations(user, lookup);
    }
    
    @GetMapping("/practiceEvaluation")
    public List<AutocompleteResult> practiceEvaluation(HoisUserDetails user, PracticeEvaluationAutocompleteCommand lookup) {
        return autocompleteService.practiceEvaluation(user, lookup);
    }
    
    @GetMapping("/supervisors")
    public List<SupervisorDto> enterpriseSupervisors(HoisUserDetails user, SearchCommand lookup) {
        return autocompleteService.enterpriseSupervisors(user, lookup);
    }
    
    @GetMapping("/enterpriseContacts")
    public List<SupervisorDto> enterpriseContacts(HoisUserDetails user, SearchCommand lookup) {
        return autocompleteService.enterpriseContacts(user, lookup);
    }

    @GetMapping("/committees")
    public Page<AutocompleteResult> committees(HoisUserDetails user, CommitteeAutocompleteCommand lookup) {
        return asPage(autocompleteService.committees(user.getSchoolId(), lookup));
    }

    @GetMapping("/committeesList")
    public List<AutocompleteResult> committeesList(HoisUserDetails user, CommitteeAutocompleteCommand lookup) {
        return autocompleteService.committees(user.getSchoolId(), lookup);
    }

    @GetMapping("/committeeMembers")
    public List<AutocompleteResult> committeeMembers(HoisUserDetails user, @Valid SearchCommand lookup) {
        UserUtil.assertIsSchoolAdmin(user);
        return autocompleteService.committeeMembers(user.getSchoolId(), lookup);
    }

    private static <R> Page<R> asPage(List<R> data) {
        return new PageImpl<>(data);
    }
}
