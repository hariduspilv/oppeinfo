package ee.hitsa.ois.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.StudentGroupService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.student.StudentGroupForm;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchStudentsCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentGroupDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupStudentDto;

@RestController
@RequestMapping("/studentgroups")
public class StudentGroupController {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudentGroupService studentGroupService;

    @GetMapping
    public Page<StudentGroupSearchDto> search(HoisUserDetails user, @Valid StudentGroupSearchCommand criteria, Pageable pageable) {
        return studentGroupService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public StudentGroupDto get(HoisUserDetails user, @WithEntity("id") StudentGroup studentGroup) {
        UserUtil.assertSameSchool(user, studentGroup.getSchool());
        return StudentGroupDto.of(studentGroup);
    }

    @PostMapping
    public StudentGroupDto create(HoisUserDetails user, @Valid @RequestBody StudentGroupForm form) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return get(user, studentGroupService.save(user, studentGroup, form));
    }

    @PutMapping("/{id:\\d+}")
    public StudentGroupDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudentGroup studentGroup, @Valid @RequestBody StudentGroupForm form) {
        UserUtil.assertSameSchool(user, studentGroup.getSchool());
        return get(user, studentGroupService.save(user, studentGroup, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") StudentGroup studentGroup, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, studentGroup.getSchool());
        studentGroupService.delete(studentGroup);
    }

    @GetMapping("/curriculumdata/{id:\\d+}")
    public Map<String, Object> curriculumRelatedData(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        Map<String, Object> data = new HashMap<>();
        data.put("curriculumVersions", curriculum.getVersions().stream().map(AutocompleteResult::of).collect(Collectors.toList()));
        data.put("languages", curriculum.getStudyLanguages().stream().map(r -> EntityUtil.getCode(r.getStudyLang())).collect(Collectors.toList()));
        data.put("studyForms", curriculum.getStudyForms().stream().map(r -> EntityUtil.getCode(r.getStudyForm())).collect(Collectors.toList()));
        data.put("origStudyLevel", EntityUtil.getCode(curriculum.getOrigStudyLevel()));
        data.put("specialities", studentGroupService.findSpecialities(curriculum));
        data.put("isVocational", Boolean.valueOf(CurriculumUtil.isVocational(curriculum.getOrigStudyLevel())));
        return data;
    }

    @GetMapping("/findstudents")
    public List<StudentGroupStudentDto> searchStudents(HoisUserDetails user, @Valid StudentGroupSearchStudentsCommand criteria) {
        return studentGroupService.searchStudents(user.getSchoolId(), criteria);
    }
}
