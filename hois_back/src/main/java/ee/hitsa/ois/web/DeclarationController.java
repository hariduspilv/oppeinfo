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
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.service.DeclarationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DeclarationUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.DeclarationSearchCommand;
import ee.hitsa.ois.web.commandobject.DeclarationSubjectForm;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.DeclarationDto;
import ee.hitsa.ois.web.dto.DeclarationSubjectDto;

@RestController
@RequestMapping("declarations")
public class DeclarationController {

    @Autowired
    private DeclarationService declarationService;

    @GetMapping("/{id:\\d+}")
    public DeclarationDto get(HoisUserDetails user, @WithEntity("id") Declaration declaration) {
        UserUtil.assertCanViewStudent(user, declaration.getStudent());
        return declarationService.get(user, declaration);
    }

    @GetMapping
    public Page<DeclarationDto> search(HoisUserDetails user, DeclarationSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return declarationService.search(user, criteria, pageable);
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
        Declaration currentDeclaration = declarationService.getCurrent(user.getSchoolId(), user.getStudentId());
        if(currentDeclaration == null) {
            return null;
        }
        return get(user, currentDeclaration);
    }

    @GetMapping("/modules/{id:\\d+}")
    public List<AutocompleteResult> getModules(HoisUserDetails user, @WithEntity("id") Declaration declaration) {
        UserUtil.assertSameSchool(user, declaration.getStudent().getSchool());
        return declarationService.getModules(declaration);
    }

    @GetMapping("/subjects/{id:\\d+}")
    public List<DeclarationSubjectDto> getCurriculumSubjectOptions(HoisUserDetails user, @WithEntity("id") Declaration declaration) {
        UserUtil.assertSameSchool(user, declaration.getStudent().getSchool());
        return declarationService.getCurriculumSubjectOptions(declaration);
    }

    @GetMapping("/subjects/extracurriculum/{id:\\d+}")
    public List<DeclarationSubjectDto> getExtraCurriculumSubjectOptions(HoisUserDetails user, @WithEntity("id") Declaration declaration) {
        UserUtil.assertSameSchool(user, declaration.getStudent().getSchool());
        return declarationService.getExtraCurriculumSubjectsOptions(declaration);
    }

    /**
     * Only students get permission to create new declarations
     * from declaration/current/view page
     */
    @GetMapping("/canCreate")
    public Map<String, ?> canCreate(HoisUserDetails user) {
        return Collections.singletonMap("canCreate", Boolean.valueOf(user.isStudent() ? declarationService.canCreate(user, user.getStudentId()) : false));
    }

    @PostMapping("/create")
    public DeclarationDto createForStudent(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return get(user, declarationService.create(user, user.getStudentId()));
    }

    @PostMapping("/create/{id:\\d+}")
    public DeclarationDto createForSchoolAdmin(HoisUserDetails user, @WithEntity("id") Student student) {
        UserUtil.assertIsSchoolAdmin(user, student.getSchool());
        return get(user, declarationService.create(user, student.getId()));
    }

    @PutMapping("/confirm/{id:\\d+}")
    public DeclarationDto confirm(HoisUserDetails user, @WithEntity("id") Declaration declaration) {
        DeclarationUtil.assertCanConfirm(user, declaration);
        return get(user, declarationService.confirm(user.getUsername(), declaration));
    }

    @PutMapping("/confirm/all")
    public Map<String, ?> confirmAll(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        Integer numberOfNewlyConfirmedDeclarations = declarationService.confirmAll(user);
        Map<String, Object> response = new HashMap<>();
        response.put("numberOfNewlyConfirmedDeclarations", numberOfNewlyConfirmedDeclarations);
        return response;
    }

    @PutMapping("/removeConfirm/{id:\\d+}")
    public DeclarationDto removeConfirmation(HoisUserDetails user, @WithEntity("id") Declaration declaration) {
        DeclarationUtil.assertCanUnconfirmDeclaration(user, declaration);
        return get(user, declarationService.removeConfirmation(declaration));
    }

    @PostMapping("/subject")
    public DeclarationSubjectDto addSubject(HoisUserDetails user, @Valid @RequestBody DeclarationSubjectForm form) {
        return declarationService.addSubject(user, form);
    }

    @DeleteMapping("/subject/{id:\\d+}")
    private void deleteSubject(HoisUserDetails user, @WithEntity("id") DeclarationSubject subject) {
        DeclarationUtil.assertCanChangeDeclaration(user, subject.getDeclaration());
        declarationService.deleteSubject(user, subject);
    }

    @GetMapping("/students")
    public Page<AutocompleteResult> getStudentsWithoutDeclaration(UsersSearchCommand command, 
            Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return declarationService.getStudentsWithoutDeclaration(command, pageable, user.getSchoolId());
    }

    @GetMapping("/currentStudyPeriod")
    public AutocompleteResult getCurrentStudyPeriod(HoisUserDetails user) {
        return declarationService.getCurrentStudyPeriod(user.getSchoolId());
    }
}
