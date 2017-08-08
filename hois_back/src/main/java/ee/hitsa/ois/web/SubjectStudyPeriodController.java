package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.SubjectStudyPeriodService;
import ee.hitsa.ois.service.TeacherService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
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
    
    // SubjectStudyPeriods

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
        UserUtil.assertIsSchoolAdmin(user);
        return get(subjectStudyPeriodService.create(form));
    }

    @PutMapping("/{id:\\d+}")
    public SubjectStudyPeriodDto update(
            @WithVersionedEntity(value = "id", versionRequestBody = true) SubjectStudyPeriod subjectStudyPeriod, 
            @Valid @RequestBody SubjectStudyPeriodForm form, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, subjectStudyPeriod.getSubject().getSchool());
        return get(subjectStudyPeriodService.update(subjectStudyPeriod, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") 
    SubjectStudyPeriod subjectStudyPeriod, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, subjectStudyPeriod.getSubject().getSchool());
        subjectStudyPeriodService.delete(subjectStudyPeriod);
    }
    
    // StudentGroups
    
    @GetMapping("/studentGroups")
    public Page<StudentGroupSearchDto> searchByStudentGroup(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodService.searchByStudentGroup(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/studentGroups/container")
    public SubjectStudyPeriodDtoContainer getStudentGroupsSspContainer(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container) {
        AssertionFailedException.throwIf(container.getStudentGroup() == null,
                "StudentGroup must be specified");
        subjectStudyPeriodService.setSubjectStudyPeriodsToStudentGroupsContainer(user.getSchoolId(), container);
        subjectStudyPeriodService.setSubjects(container);
        subjectStudyPeriodService.setSubjectStudyPeriodPlansToStudentGroupContainer(container);
        return container;
    }
    
    @PutMapping("/studentGroups/container")
    public SubjectStudyPeriodDtoContainer updateStudentGroupsSspCapacities(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodDtoContainer container) {
        UserUtil.assertIsSchoolAdmin(user);
        AssertionFailedException.throwIf(container.getStudentGroup() == null,
                "StudentGroup must be specified");
        subjectStudyPeriodService.updateSspCapacities(user.getSchoolId(), container);
        return getStudentGroupsSspContainer(user, container);
    }
    
    @GetMapping("/studentGroups/list")
    public List<StudentGroupSearchDto> getStudentGroupsList(HoisUserDetails user) {
        return subjectStudyPeriodService.getStudentGroupsList(user.getSchoolId(), null);
    }
    
    @GetMapping("/studentGroups/list/limited/{studyPeriodId:\\d+}")
    public List<StudentGroupSearchDto> getStudentGroupsFilteredList(HoisUserDetails user, @PathVariable("studyPeriodId") Long studyPeriodId) {
        return subjectStudyPeriodService.getStudentGroupsList(user.getSchoolId(), studyPeriodId);
    }
    
    // Curricula for StudentGroups
    
    @GetMapping("/curriculum/{id:\\d+}")
    public CurriculumDto getCurriculum(@WithEntity("id") Curriculum curriculum) {
        CurriculumDto dto = new CurriculumDto();
        dto.setId(EntityUtil.getId(curriculum));
        dto.setNameEt(curriculum.getNameEt());
        dto.setNameEn(curriculum.getNameEn());
        dto.setStudyPeriod(curriculum.getStudyPeriod());
        return dto;
    }
    
    @GetMapping("/curricula")
    public List<CurriculumSearchDto> getCurricula(HoisUserDetails user) {
        return subjectStudyPeriodService.getCurricula(user.getSchoolId());
    }
    
    // Teachers
    
    @GetMapping("/teachers")
    public Page<TeacherSearchDto> searchByTeachers(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodService.searchByTeachers(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/teachers/container")
    public SubjectStudyPeriodDtoContainer getTeachersSspContainer(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container) {
        AssertionFailedException.throwIf(container.getTeacher() == null,
                "Teacher must be specified");
        subjectStudyPeriodService.setSubjectStudyPeriodsToTeachersContainer(user.getSchoolId(), container);
        subjectStudyPeriodService.setSubjects(container);
        subjectStudyPeriodService.setSubjectStudyPeriodPlansToTeachersContainer(container);
        return container;
    }
    
    @PutMapping("/teachers/container")
    public SubjectStudyPeriodDtoContainer updateTeachersSspCapacities(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodDtoContainer container) {
        UserUtil.assertIsSchoolAdmin(user);
        AssertionFailedException.throwIf(container.getTeacher() == null,
                "Teacher must be specified");
        subjectStudyPeriodService.updateSspCapacities(user.getSchoolId(), container);
        return getTeachersSspContainer(user, container);
    }
    
    @GetMapping("/teacher/{id:\\d+}")
    public AutocompleteResult getTeacher(@WithEntity("id") Teacher teacher) {
        return AutocompleteResult.of(teacher); 
    }
    
    @GetMapping("/teachers/page")
    public Page<AutocompleteResult> getTeachersPage(TeacherSearchCommand command, Pageable pageable, HoisUserDetails user) {
        command.setSchool(user.getSchoolId());
        command.setIsHigher(Boolean.TRUE);
        command.setIsActive(Boolean.TRUE);
        return teacherService.search(command, pageable).map(t -> {
            return new AutocompleteResult(t.getId(), t.getName(), t.getName());
        });
    }
    
    @GetMapping("/teachers/list/limited/{studyPeriodId:\\d+}")
    public List<AutocompleteResult> getTeachersFilteredList(HoisUserDetails user, @PathVariable("studyPeriodId") Long studyPeriodId) {
        return subjectStudyPeriodService.getTeachersList(user.getSchoolId(), studyPeriodId);
    }
    
    // subjects
    
    @GetMapping("/subjects")
    public Page<SubjectStudyPeriodSearchDto> searchBySubjects(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodService.searchBySubjects(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/subjects/container")
    public SubjectStudyPeriodDtoContainer getSubjectsSspContainer(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container) {
        AssertionFailedException.throwIf(container.getSubject() == null,
                "Subject must be specified");
        subjectStudyPeriodService.setSubjectStudyPeriodsToSubjectsContainer(user.getSchoolId(), container);
        return container;
    }
    
    @PutMapping("/subjects/container")
    public SubjectStudyPeriodDtoContainer updateSubjectsSspCapacities(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodDtoContainer container) {
        UserUtil.assertIsSchoolAdmin(user);
        AssertionFailedException.throwIf(container.getSubject() == null,
                "Subject must be specified");
        subjectStudyPeriodService.updateSspCapacities(user.getSchoolId(), container);
        return getSubjectsSspContainer(user, container);
    }
    
    @GetMapping("/subject/{id:\\d+}")
    public AutocompleteResult getSubject(@WithEntity("id") Subject subject) {
        return AutocompleteResult.of(subject); 
    }
    
    @GetMapping("/subjects/list")
    public List<AutocompleteResult> getSubjectsList(HoisUserDetails user) {
        return subjectStudyPeriodService.getSubjectsList(user.getSchoolId(), null);
    }
    
    @GetMapping("/subjects/list/limited/{studyPeriodId:\\d+}")
    public List<AutocompleteResult> getSubjectsFilteredList(HoisUserDetails user, @PathVariable("studyPeriodId") Long studyPeriodId) {
        return subjectStudyPeriodService.getSubjectsList(user.getSchoolId(), studyPeriodId);
    }
    
    @GetMapping("/studyPeriod/{id:\\d+}")
    public AutocompleteResult getStudyPeriod(@WithEntity("id") StudyPeriod studyPeriod) {
        return AutocompleteResult.of(studyPeriod); 
    }
}
