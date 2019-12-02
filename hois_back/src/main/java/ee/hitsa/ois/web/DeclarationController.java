package ee.hitsa.ois.web;

import java.util.Collections;
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

import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.DeclarationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DeclarationUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.DeclarationSearchCommand;
import ee.hitsa.ois.web.commandobject.DeclarationSubjectForm;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.DeclarationDto;
import ee.hitsa.ois.web.dto.DeclarationSubjectDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@RestController
@RequestMapping("declarations")
public class DeclarationController {

    @Autowired
    private DeclarationService declarationService;

    @GetMapping("/{id:\\d+}")
    public DeclarationDto get(HoisUserDetails user, @WithEntity Declaration declaration) {
        UserUtil.assertCanViewStudent(user, declaration.getStudent());
        return declarationService.get(user, declaration);
    }

    @GetMapping
    public Page<DeclarationDto> search(HoisUserDetails user, @Valid DeclarationSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return declarationService.search(user, criteria, pageable);
    }
    
    @GetMapping("/search-students")
    public List<AutocompleteResult> searchStudents(HoisUserDetails user, SearchCommand lookup) {
        return declarationService.autocompleteStudents(user, lookup);
    }

    @GetMapping("/hasPrevious")
    public Map<String, ?> studentHasPreviousDeclarations(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        Map<String, Object> response = new HashMap<>();
        response.put("hasPrevious", Boolean.valueOf(declarationService.studentHasPreviousDeclarations(user.getStudentId())));
        return response;
    }

    @GetMapping("/previous")
    public Page<DeclarationDto> searchStudentsPreviousDeclarations(HoisUserDetails user, Pageable pageable) {
        UserUtil.assertIsStudent(user);
        return declarationService.searchStudentsPreviousDeclarations(user.getStudentId(), pageable);
    }

    @GetMapping("/current")
    public DeclarationDto getStudentsCurrentDeclaration(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        Declaration declaration = declarationService.getCurrent(user.getSchoolId(), user.getStudentId());
        if(declaration == null) {
            return null;
        }
        return get(user, declaration);
    }

    @GetMapping("/next")
    public DeclarationDto getStudentsNextDeclaration(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        Declaration declaration = declarationService.getNext(user.getSchoolId(), user.getStudentId());
        if(declaration == null) {
            return null;
        }
        return get(user, declaration);
    }

    @GetMapping("/subjects/{id:\\d+}")
    public Page<DeclarationSubjectDto> getCurriculumSubjectOptions(HoisUserDetails user, @WithEntity Declaration declaration, Pageable pageable) {
        UserUtil.assertSameSchool(user, declaration.getStudent().getSchool());
        return declarationService.getCurriculumSubjectOptions(declaration, pageable);
    }

    @GetMapping("/subjects/extracurriculum/{id:\\d+}")
    public List<DeclarationSubjectDto> getExtraCurriculumSubjectOptions(HoisUserDetails user, @WithEntity Declaration declaration) {
        UserUtil.assertSameSchool(user, declaration.getStudent().getSchool());
        return declarationService.getExtraCurriculumSubjectsOptions(declaration);
    }

    @GetMapping("/isDeclarationPeriod")
    public Map<String, ?> isDeclarationPeriod(HoisUserDetails user, @RequestParam(name="next", defaultValue="false") boolean isNextDeclaration) {
        return isNextDeclaration ? declarationService.isNextDeclarationPeriod(user) : declarationService.isDeclarationPeriod(user);
    }
    
    @GetMapping("/declarationPeriod/{id:\\d+}")
    public Map<String, ?> declarationPeriod(HoisUserDetails user, @WithEntity Declaration declaration) {
        UserUtil.assertIsSchoolAdminOrStudent(user, declaration.getStudent().getSchool());
        return declarationService.declarationPeriod(declaration);
    }

    /**
     * Only students get permission to create new declarations
     * from declaration/current/view page
     */
    @GetMapping("/canCreate")
    public Map<String, ?> canCreate(HoisUserDetails user, @RequestParam(name="next", defaultValue="false") boolean isNextDeclaration) {
        return Collections.singletonMap("canCreate", Boolean.valueOf(user.isStudent()
                ? (isNextDeclaration ? declarationService.canCreateNext(user, user.getStudentId()) : declarationService.canCreateCurrent(user, user.getStudentId()))
                : false));
    }

    @PostMapping("/create")
    public DeclarationDto createForStudent(HoisUserDetails user, @RequestParam(name="next", defaultValue="false") boolean isNextDeclaration) {
        UserUtil.assertIsStudent(user);
        return get(user, declarationService.create(user, user.getStudentId(), isNextDeclaration));
    }

    @PostMapping("/create/{id:\\d+}")
    public DeclarationDto createForSchoolAdmin(HoisUserDetails user, @WithEntity Student student, @RequestParam(name="next", defaultValue="false") boolean isNextDeclaration) {
        UserUtil.assertIsSchoolAdmin(user, student.getSchool());
        return get(user, declarationService.create(user, student.getId(), isNextDeclaration));
    }

    @PutMapping("/confirm/{id:\\d+}")
    public DeclarationDto confirm(HoisUserDetails user, @WithEntity Declaration declaration) {
        DeclarationUtil.assertCanConfirm(user, declaration);
        return get(user, declarationService.confirm(user.getUsername(), declaration));
    }

    @PutMapping("/confirm/all")
    public Map<String, ?> confirmAll(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_OPINGUKAVA);
        Integer numberOfNewlyConfirmedDeclarations = declarationService.confirmAll(user);
        Map<String, Object> response = new HashMap<>();
        response.put("numberOfNewlyConfirmedDeclarations", numberOfNewlyConfirmedDeclarations);
        return response;
    }

    @PutMapping("/removeConfirm/{id:\\d+}")
    public DeclarationDto removeConfirmation(HoisUserDetails user, @WithEntity Declaration declaration) {
        DeclarationUtil.assertCanUnconfirmDeclaration(user, declaration);
        return get(user, declarationService.removeConfirmation(declaration));
    }

    @PostMapping("/subject")
    public DeclarationSubjectDto addSubject(HoisUserDetails user, @Valid @RequestBody DeclarationSubjectForm form) {
        return declarationService.addSubject(user, form);
    }

    @DeleteMapping("/subject/{id:\\d+}")
    private void deleteSubject(HoisUserDetails user, @WithEntity DeclarationSubject subject) {
        DeclarationUtil.assertCanChangeDeclaration(user, subject.getDeclaration());
        declarationService.deleteSubject(user, subject);
    }

    @GetMapping("/students")
    public Page<AutocompleteResult> getStudentsWithoutDeclaration(UsersSearchCommand command, 
            Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return declarationService.getStudentsWithoutDeclaration(command, pageable, user.getSchoolId());
    }
    
    /**
     * @return list of active students who have no declaration in current study period
     */
    @GetMapping("/withoutDeclaration")
    public Page<StudentSearchDto> searchStudentsWithoutDeclaration(HoisUserDetails user, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return declarationService.searchStudentsWithoutDeclaration(new UsersSearchCommand(), user.getSchoolId(), pageable);
    }

    @GetMapping("/currentStudyPeriod")
    public AutocompleteResult getCurrentStudyPeriod(HoisUserDetails user) {
        return declarationService.getCurrentStudyPeriod(user.getSchoolId());
    }
    
    /**
     * @param user
     * @return next study period in case if declaration period is open.
     */
    @GetMapping("/nextStudyPeriod")
    public AutocompleteResult getNextStudyPeriodIfOpenDeclarationPeriod(HoisUserDetails user) {
        return declarationService.getNextStudyPeriodIfOpenDeclarationPeriod(user.getSchoolId());
    }
}
