package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.util.Arrays;
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
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.DeclarationRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.DeclarationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
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
    private StudentRepository studentRepository;
    @Autowired
    private DeclarationRepository declarationRepository;

    @GetMapping("/{id:\\d+}")
    public DeclarationDto get(HoisUserDetails user, @WithEntity("id") Declaration declaration) {
        DeclarationDto dto = DeclarationDto.of(declaration);
        declarationService.setAreSubjectsDeclaredRepeatedy(dto.getSubjects(), declaration.getId());
        dto.setCanBeChanged(canChangeDeclaration(user, declaration));
        dto.setCanBeSetUnconfirmed(canUnconfirmDeclaration(user, declaration));
        dto.setCanBeSetConfirmed(canConfirmDeclaration(user, declaration));
        return dto;
    }

    @GetMapping
    public Page<DeclarationDto> search(DeclarationSearchCommand criteria, Pageable pageable) {
        return declarationService.search(criteria, pageable);
    }

    @GetMapping("/hasPrevious")
    public Map<String, ?> studyLevels(HoisUserDetails user) {
        Map<String, Object> response = new HashMap<>();
        response.put("hasPrevious", declarationService.studentHasPreviousDeclarations(user.getStudentId()));
        return response;
    }

    @GetMapping("/previous")
    public Page<DeclarationDto> searchStudentsPreviousDeclarations(HoisUserDetails user, Pageable pageable) {
        return declarationService.searchStudentsPreviousDeclarations(user.getStudentId(), pageable);
    }

    @GetMapping("/current")
    public DeclarationDto getStudentsCurrentDeclaration(HoisUserDetails user) {
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
        return declarationService.getExtraCurriculumSubjects(declaration);
    }

    /**
     * Only students get permission to create new declarations
     * from declaration/current/view page
     */
    @GetMapping("/canCreate")
    public Map<String, ?> canCreate(HoisUserDetails user) {

        Boolean answer = Boolean.FALSE;
        if(user.isStudent()) {
            Student student = studentRepository.getOne(user.getStudentId());
            answer = student.getStatus().getCode().equals(StudentStatus.OPPURSTAATUS_O.name());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("canCreate", answer);
        return response;
    }

    @PostMapping("create")
    public DeclarationDto createForStudent(HoisUserDetails user) {
        Student student = studentRepository.getOne(user.getStudentId()); 
        AssertionFailedException.throwIf(!canCreateDeclaration(user, student),
                "You cannot create declaration!");
        return get(user, declarationService.create(user.getSchoolId(), student));
    }
    @PostMapping("create/{id:\\d+}")
    public DeclarationDto createForSchoolAdmin(HoisUserDetails user, @WithEntity("id") Student student) {
        UserUtil.assertSameSchool(user, student.getSchool());
        AssertionFailedException.throwIf(!canCreateDeclaration(user, student),
                "You cannot create declaration!");
        return get(user, declarationService.create(user.getSchoolId(), student));
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
    
    private boolean canConfirmDeclaration(HoisUserDetails user, Declaration declaration) {
        return (user.isSchoolAdmin() || user.isStudent() 
                && declaration.getStudent().getStatus().getCode().equals(StudentStatus.OPPURSTAATUS_O.name())
                && declaration.getStudent().getId().equals(user.getStudentId()))
                && declaration.getStatus().getCode().equals(DeclarationStatus.OPINGUKAVA_STAATUS_S.name());
    }

    private boolean canChangeDeclaration(HoisUserDetails user, Declaration declaration) {
        return user.isSchoolAdmin() || user.isStudent() 
                && declaration.getStudent().getStatus().getCode().equals(StudentStatus.OPPURSTAATUS_O.name())
                && declaration.getStatus().getCode().equals(DeclarationStatus.OPINGUKAVA_STAATUS_S.name())
                && declaration.getStudent().getId().equals(user.getStudentId());
    }

    private boolean canUnconfirmDeclaration(HoisUserDetails user, Declaration declaration) {
        if(user.isStudent() && !declaration.getStudent().getId().equals(user.getStudentId())) {
            return false;
        }
        return declaration.getStatus().getCode().equals(DeclarationStatus.OPINGUKAVA_STAATUS_K.name()) && 
                LocalDate.now().isBefore(declaration.getStudyPeriod().getEndDate());
    }

    private boolean canCreateDeclaration(HoisUserDetails user, Student student) {
        if(user.isSchoolAdmin()) {
            return true;
        } else if(user.isStudent() && student != null) {
            return student.getStatus().getCode().equals(StudentStatus.OPPURSTAATUS_O.name());
        }
        return false;
    }
}
