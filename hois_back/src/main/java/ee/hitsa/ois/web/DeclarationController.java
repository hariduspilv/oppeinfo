package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.DeclarationRepository;
import ee.hitsa.ois.service.DeclarationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.StudentUtil;
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
    @Autowired
    private DeclarationRepository declarationRepository;

    @GetMapping("/{id:\\d+}")
    public DeclarationDto get(HoisUserDetails user, @WithEntity("id") Declaration declaration) {
        DeclarationDto dto = DeclarationDto.of(declaration);
        declarationService.setAreSubjectsDeclaredRepeatedy(dto.getSubjects(), declaration.getId());
        dto.setCanBeChanged(Boolean.valueOf(canChangeDeclaration(user, declaration)));
        dto.setCanBeSetUnconfirmed(Boolean.valueOf(canUnconfirmDeclaration(user, declaration)));
        dto.setCanBeSetConfirmed(Boolean.valueOf(canConfirmDeclaration(user, declaration)));
        return dto;
    }

    @GetMapping
    public Page<DeclarationDto> search(DeclarationSearchCommand criteria, Pageable pageable) {
        return declarationService.search(criteria, pageable);
    }

    @GetMapping("/hasPrevious")
    public Map<String, ?> checkIfStudentHasPreviousDeclarations(HoisUserDetails user) {
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
    public List<AutocompleteResult> getModules(@WithEntity("id") Declaration declaration) {
        return declarationService.getModules(declaration);
    }

    @GetMapping("/subjects/{id:\\d+}")
    public List<DeclarationSubjectDto> getCurriculumSubjectOptions(@WithEntity("id") Declaration declaration) {
        return declarationService.getCurriculumSubjectOptions(declaration);
    }

    @GetMapping("/subjects/extracurriculum/{id:\\d+}")
    public List<DeclarationSubjectDto> getExtraCurriculumSubjectOptions(@WithEntity("id") Declaration declaration) {
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
        UserUtil.assertSameSchool(user, declaration.getStudent().getSchool());
        AssertionFailedException.throwIf(!canConfirmDeclaration(user, declaration),
                "You cannot confirm declaration!");
        return get(user, declarationService.confirm(user.getUsername(), declaration));
    }

    @PutMapping("/confirm/all")
    public Map<String, ?> confirmAll(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(),
                "Only school admin can confirm all declarations!");
        
        Integer numberOfNewlyConfirmedDeclarations = declarationService.confirmAll(user);
        Map<String, Object> response = new HashMap<>();
        response.put("numberOfNewlyConfirmedDeclarations", numberOfNewlyConfirmedDeclarations);
        return response;
    }

    @PutMapping("/removeConfirm/{id:\\d+}")
    public DeclarationDto removeConfirmation(HoisUserDetails user, @WithEntity("id") Declaration declaration) {
        UserUtil.assertSameSchool(user, declaration.getStudent().getSchool());
        AssertionFailedException.throwIf(!canUnconfirmDeclaration(user, declaration),
                "You cannot set declaration unconfirmed!");
        return get(user, declarationService.removeConfirmation(declaration));
    }

    @PostMapping("/subject")
    public DeclarationSubjectDto addSubject(HoisUserDetails user, @Valid @RequestBody DeclarationSubjectForm form) {

        Declaration d = declarationRepository.getOne(form.getDeclaration());
        UserUtil.assertSameSchool(user, d.getStudent().getSchool());
        AssertionFailedException.throwIf(!canChangeDeclaration(user, d),
                "You cannot add subjects to declaration!");

        DeclarationSubjectDto dto = DeclarationSubjectDto.of(declarationService.addSubject(user, form));
        declarationService.setAreSubjectsDeclaredRepeatedy(new HashSet<>(Arrays.asList(dto)), dto.getDeclaration());

        return dto;
    }

    @DeleteMapping("/subject/{id:\\d+}")
    private void deleteSubject(HoisUserDetails user, @WithEntity("id") DeclarationSubject subject) {
        UserUtil.assertSameSchool(user, subject.getDeclaration().getStudent().getSchool());
        AssertionFailedException.throwIf(!canChangeDeclaration(user, subject.getDeclaration()),
                "You cannot change declaration!");
        declarationService.deleteSubject(subject);
    }

    @GetMapping("/students")
    public Page<AutocompleteResult> getStudentsWithoutDeclaration(UsersSearchCommand command, 
            Pageable pageable, HoisUserDetails user) {
        return declarationService.getStudentsWithoutDeclaration(command, pageable, user.getSchoolId());
    }

    @GetMapping("/currentStudyPeriod")
    public AutocompleteResult getCurrentStudyPeriod(HoisUserDetails user) {
        return declarationService.getCurrentStudyPeriod(user.getSchoolId());
    }

    public static boolean canConfirmDeclaration(HoisUserDetails user, Declaration declaration) {
        return ClassifierUtil.equals(DeclarationStatus.OPINGUKAVA_STAATUS_S, declaration.getStatus()) &&
                (user.isSchoolAdmin() || (UserUtil.isStudent(user, declaration.getStudent())
                && StudentUtil.isStudying(declaration.getStudent())));
    }

    /**
     * For now even confirmed declarations can be changed by school admin
     */
    public static boolean canChangeDeclaration(HoisUserDetails user, Declaration declaration) {
        return user.isSchoolAdmin() || (UserUtil.isStudent(user, declaration.getStudent())
                && StudentUtil.isStudying(declaration.getStudent())
                && ClassifierUtil.equals(DeclarationStatus.OPINGUKAVA_STAATUS_S, declaration.getStatus()));
    }

    public static boolean canUnconfirmDeclaration(HoisUserDetails user, Declaration declaration) {
        if(user.isStudent() && !UserUtil.isStudent(user, declaration.getStudent())) {
            return false;
        }
        return ClassifierUtil.equals(DeclarationStatus.OPINGUKAVA_STAATUS_K, declaration.getStatus()) &&
                LocalDate.now().isBefore(declaration.getStudyPeriod().getEndDate());
    }
}
