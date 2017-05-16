package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.service.SubjectService;
import ee.hitsa.ois.service.SubjectStudyPeriodService;
import ee.hitsa.ois.service.TeacherService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.commandobject.teacher.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;
import ee.hitsa.ois.web.dto.TeacherSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

@RestController
@RequestMapping("/subjectStudyPeriods")
public class SubjectStudyPeriodController {

    @Autowired
    private SubjectStudyPeriodService subjectStudyPeriodService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public Page<SubjectStudyPeriodSearchDto> search(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SubjectStudyPeriodDto get(@WithEntity("id") SubjectStudyPeriod subjectStudyPeriod) {
        return SubjectStudyPeriodDto.of(subjectStudyPeriod);
    }

    @PostMapping
    public SubjectStudyPeriodDto create(@Valid @RequestBody SubjectStudyPeriodForm form, HoisUserDetails user) {
        checkIfSchoolAdmin(user);
        return get(subjectStudyPeriodService.create(form));
    }

    @PutMapping("/{id:\\d+}")
    public SubjectStudyPeriodDto update(
            @WithVersionedEntity(value = "id", versionRequestBody = true) SubjectStudyPeriod subjectStudyPeriod, 
            @Valid @RequestBody SubjectStudyPeriodForm form, HoisUserDetails user) {
        checkIfSchoolAdmin(user);
        UserUtil.assertSameSchool(user, subjectStudyPeriod.getSubject().getSchool());
        return get(subjectStudyPeriodService.update(subjectStudyPeriod, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") 
    SubjectStudyPeriod subjectStudyPeriod, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        checkIfSchoolAdmin(user);
        UserUtil.assertSameSchool(user, subjectStudyPeriod.getSubject().getSchool());
        subjectStudyPeriodService.delete(subjectStudyPeriod);
    }
    
    @GetMapping("/teachers/page")
    public Page<AutocompleteResult> getTeacheroptions(TeacherSearchCommand command, Pageable pageable, HoisUserDetails user) {
        command.setSchool(user.getSchoolId());
        command.setIsHigher(Boolean.TRUE);
        command.setIsActive(Boolean.TRUE);
        return teacherService.search(command, pageable).map(t -> {
            return new AutocompleteResult(t.getId(), t.getName(), t.getName());
        });
    }
    
    /**
     * Where is it used?
     */
//    @GetMapping("/list")
//    public List<SubjectStudyPeriodDto> searchList(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria) {
//        return subjectStudyPeriodService.searchList(user.getSchoolId(), criteria);
//    }
    
    @GetMapping("/studentGroups")
    public Page<StudentGroupSearchDto> searchByStudentGroup(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodService.searchByStudentGroup(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/studentGroups/container")
    public SubjectStudyPeriodDtoContainer getStudentGroupsSspContainer(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container) {
        checkIfSchoolAdmin(user);
        AssertionFailedException.throwIf(container.getStudentGroup() == null,
                "StudentGroup must be specified");
        container.setSubjectStudyPeriodDtos(subjectStudyPeriodService.getSubjectStudyPeriodsList(user.getSchoolId(), container));
        subjectStudyPeriodService.setSubjects(container);
        subjectStudyPeriodService.setSubjectStudyPeriodPlansForStudentGroupContainer(container);
        return container;
    }
    
    @PutMapping("/studentGroups/container")
    public SubjectStudyPeriodDtoContainer updateStudentGroupsSspCapacities(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodDtoContainer container) {
        AssertionFailedException.throwIf(container.getStudentGroup() == null,
                "StudentGroup must be specified");
        subjectStudyPeriodService.updateSspCapacities(user.getSchoolId(), container);
        return getStudentGroupsSspContainer(user, container);
    }
    
    @GetMapping("/studentGroups/list")
    public List<StudentGroupSearchDto> getStudentGroupsForSearchForm(HoisUserDetails user) {
        return subjectStudyPeriodService.getStudentGroupsList(user.getSchoolId(), null);
    }
    
    @GetMapping("/studentGroups/list/new/{studyPeriodId:\\d+}")
    public List<StudentGroupSearchDto> getStudentGroupsForEditForm(HoisUserDetails user, @PathVariable("studyPeriodId") Long studyPeriodId) {
        return subjectStudyPeriodService.getStudentGroupsList(user.getSchoolId(), studyPeriodId);
    }
    
    @GetMapping("/curricula")
    public List<CurriculumSearchDto> curricula(HoisUserDetails user) {
        return subjectStudyPeriodService.curricula(user.getSchoolId());
    }
    
    @GetMapping("/curriculum/{id:\\d+}")
    public CurriculumDto curriculum(@WithEntity("id") Curriculum curriculum) {
        CurriculumDto dto = new CurriculumDto();
        dto.setId(EntityUtil.getId(curriculum));
        dto.setNameEt(curriculum.getNameEt());
        dto.setNameEn(curriculum.getNameEn());
        dto.setStudyPeriod(curriculum.getStudyPeriod());
        return dto;
    }
    
    /**
     * Later subjects' options will be more limited
     */
    @GetMapping("/subjects")
    public List<AutocompleteResult> subjects(HoisUserDetails user) {
        SubjectSearchCommand subjectSearchCommand = new SubjectSearchCommand();
        subjectSearchCommand.setStatus(Collections.singletonList(SubjectStatus.AINESTAATUS_K.name()));
        return subjectService.search(user.getSchoolId(), subjectSearchCommand, new PageRequest(0, Integer.MAX_VALUE))
                .map(s -> new AutocompleteResult(s.getId(), 
                        SubjectUtil.subjectName(s.getCode(), s.getNameEt(), s.getCredits()), 
                        SubjectUtil.subjectName(s.getCode(), s.getNameEn(), s.getCredits())))
                .getContent();
    }
    
    @GetMapping("/teachers")
    public Page<TeacherSearchDto> searchByTeachers(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodService.searchByTeachers(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/teachers/container")
    public SubjectStudyPeriodDtoContainer getTeachersSspContainer(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container) {
        AssertionFailedException.throwIf(container.getTeacher() == null,
                "Teacher must be specified");
        subjectStudyPeriodService.setSubjectStudyPeriodsForTeachersContainer(user.getSchoolId(), container);
        subjectStudyPeriodService.setSubjects(container);
        subjectStudyPeriodService.setSubjectStudyPeriodPlansForTeachersContainer(container);
        return container;
    }
    
    @PutMapping("/teachers/container")
    public SubjectStudyPeriodDtoContainer updateTeachersSspCapacities(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodDtoContainer container) {
        checkIfSchoolAdmin(user);
        AssertionFailedException.throwIf(container.getTeacher() == null,
                "Teacher must be specified");
        subjectStudyPeriodService.updateSspCapacities(user.getSchoolId(), container);
        return getTeachersSspContainer(user, container);
    }
    
    @GetMapping("/teachers/list/new/{studyPeriodId:\\d+}")
    public List<AutocompleteResult> getTeacherOptionsForEditForm(HoisUserDetails user, @PathVariable("studyPeriodId") Long studyPeriodId) {
        return subjectStudyPeriodService.getTeacherOptionsForEditForm(user.getSchoolId(), studyPeriodId);
    }
    
    @GetMapping("/teacher/{id:\\d+}")
    public AutocompleteResult teacher(@WithEntity("id") Teacher teacher) {
        return AutocompleteResult.of(teacher); 
    }
    
    public void checkIfSchoolAdmin(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(), "Only school admins have rights for this action!");
    }
}
