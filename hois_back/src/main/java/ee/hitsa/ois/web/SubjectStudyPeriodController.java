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

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.service.SubjectStudyPeriodService;
import ee.hitsa.ois.service.TeacherService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodStudentGroupSearchCommand;
import ee.hitsa.ois.web.commandobject.teacher.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;
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

    @GetMapping
    public Page<SubjectStudyPeriodSearchDto> search(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SubjectStudyPeriodDto get(@WithEntity("id") SubjectStudyPeriod subjectStudyPeriod) {
        return SubjectStudyPeriodDto.of(subjectStudyPeriod);
    }

    @PostMapping
    public SubjectStudyPeriodDto create(@Valid @RequestBody SubjectStudyPeriodForm form) {
        return get(subjectStudyPeriodService.create(form));
    }

    @PutMapping("/{id:\\d+}")
    public SubjectStudyPeriodDto update(
            @WithVersionedEntity(value = "id", versionRequestBody = true) SubjectStudyPeriod subjectStudyPeriod, 
            @Valid @RequestBody SubjectStudyPeriodForm form) {
        return get(subjectStudyPeriodService.update(subjectStudyPeriod, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") SubjectStudyPeriod subjectStudyPeriod, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        subjectStudyPeriodService.delete(subjectStudyPeriod);
    }
    
    
    @GetMapping("/list")
    public List<SubjectStudyPeriodDto> searchList(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria) {
        return subjectStudyPeriodService.searchList(user.getSchoolId(), criteria);
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
    
    @GetMapping("/studentGroups")
    public Page<StudentGroupSearchDto> searchStudentGroups(HoisUserDetails user, SubjectStudyPeriodStudentGroupSearchCommand criteria, Pageable pageable) {
        return subjectStudyPeriodService.studentGroups(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/studentGroups/list")
    public List<StudentGroupSearchDto> getStudentGroupsList(HoisUserDetails user) {
        return subjectStudyPeriodService.getStudentGroupsList(user.getSchoolId(), null);
    }
    
    @GetMapping("/studentGroups/editform/{studyPeriodId:\\d+}")
    public List<StudentGroupSearchDto> getStudentGroupsForEditForm(HoisUserDetails user, @PathVariable("studyPeriodId") Long studyPeriodId) {
        return subjectStudyPeriodService.getStudentGroupsList(user.getSchoolId(), studyPeriodId);
    }
    
    @GetMapping("/container")
    public SubjectStudyPeriodDtoContainer getSspContainer(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container) {
        container.setSubjectStudyPeriodDtos(subjectStudyPeriodService.getSubjectStudyPeriodsList(user.getSchoolId(), container));
        subjectStudyPeriodService.setSubjects(container);
        subjectStudyPeriodService.setSubjectStudyPeriodPlans(container);
        return container;
    }
    
    @PutMapping("/container")
    public SubjectStudyPeriodDtoContainer updateSspCapacities(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodDtoContainer container) {
        subjectStudyPeriodService.updateSspCapacities(user.getSchoolId(), container);
        return getSspContainer(user, container);
    }
    
    @GetMapping("/teachers")
    public Page<AutocompleteResult> searchTeachers(TeacherSearchCommand command, Pageable pageable, HoisUserDetails user) {
        command.setSchool(user.getSchoolId());
        command.setIsHigher(Boolean.TRUE);
        return teacherService.search(command, pageable).map(t -> {
            return new AutocompleteResult(t.getId(), t.getName(), t.getName());
        });
    }
}
