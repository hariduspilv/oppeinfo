package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.StudentGroup;
import ee.hitsa.ois.service.StudentGroupService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.StudentGroupForm;
import ee.hitsa.ois.web.commandobject.StudentGroupSearchCommand;
import ee.hitsa.ois.web.dto.StudentGroupDto;
import ee.hitsa.ois.web.dto.StudentGroupSearchDto;

@RestController
@RequestMapping("/studentgroups")
public class StudentGroupController {

    @Autowired
    private StudentGroupService studentGroupService;

    @GetMapping
    public Page<StudentGroupSearchDto> search(HoisUserDetails user, @Valid StudentGroupSearchCommand criteria, Pageable pageable) {
        return studentGroupService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id}")
    public StudentGroupDto get(HoisUserDetails user, @WithEntity("id") StudentGroup studentGroup) {
        return StudentGroupDto.of(studentGroup);
    }

    @PostMapping
    public StudentGroupDto create(HoisUserDetails user, @Valid StudentGroupForm form) {
        return get(user, studentGroupService.save(new StudentGroup(), form));
    }

    @PutMapping("/{id}")
    public StudentGroupDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudentGroup studentGroup, @Valid StudentGroupForm form) {
        return get(user, studentGroupService.save(studentGroup, form));
    }

}
