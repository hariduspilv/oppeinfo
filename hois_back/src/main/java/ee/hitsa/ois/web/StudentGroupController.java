package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.StudentGroupService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.StudentGroupForm;
import ee.hitsa.ois.web.commandobject.StudentGroupSearchCommand;
import ee.hitsa.ois.web.dto.StudentGroupDto;
import ee.hitsa.ois.web.dto.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.StudentGroupStudentDto;

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
        assertSameSchool(user, studentGroup);
        return StudentGroupDto.of(studentGroup);
    }

    @PostMapping
    public StudentGroupDto create(HoisUserDetails user, @Valid StudentGroupForm form) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return get(user, studentGroupService.save(new StudentGroup(), form));
    }

    @PutMapping("/{id:\\d+}")
    public StudentGroupDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudentGroup studentGroup, @Valid StudentGroupForm form) {
        assertSameSchool(user, studentGroup);
        return get(user, studentGroupService.save(studentGroup, form));
    }

    @GetMapping("/findstudents")
    public List<StudentGroupStudentDto> searchStudents(HoisUserDetails user) {
        return studentGroupService.searchStudents(user.getSchoolId());
    }

    private static void assertSameSchool(HoisUserDetails user, StudentGroup studentGroup) {
        Long schoolId = user.getSchoolId();
        if(schoolId == null || !schoolId.equals(EntityUtil.getId(studentGroup))) {
            throw new IllegalArgumentException();
        }
    }
}
