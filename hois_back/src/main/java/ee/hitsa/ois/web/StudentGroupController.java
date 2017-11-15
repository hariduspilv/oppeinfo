package ee.hitsa.ois.web;

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

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.StudentGroupService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.student.StudentGroupForm;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchStudentsCommand;
import ee.hitsa.ois.web.dto.student.StudentGroupDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupStudentDto;

@RestController
@RequestMapping("/studentgroups")
public class StudentGroupController {

    @Autowired
    private StudentGroupService studentGroupService;

    @GetMapping
    public Page<StudentGroupSearchDto> search(HoisUserDetails user, @Valid StudentGroupSearchCommand criteria, Pageable pageable) {
        // school admin or teacher
        if(user.isTeacher()) {
            // TODO change frontend, make message compose form to use endpoint /autocomplete/studentgroups
            // message receivers
            criteria.setTeacher(new EntityConnectionCommand(user.getTeacherId()));
        } else if(!user.isSchoolAdmin()) {
            throw new AssertionFailedException("User cannot search student groups");
        }
        return studentGroupService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public StudentGroupDto get(HoisUserDetails user, @WithEntity("id") StudentGroup studentGroup) {
        UserUtil.assertSameSchool(user, studentGroup.getSchool());
        return StudentGroupDto.of(studentGroup);
    }

    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody StudentGroupForm form) {
        return HttpUtil.created(studentGroupService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public StudentGroupDto save(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudentGroup studentGroup, @Valid @RequestBody StudentGroupForm form) {
        UserUtil.assertSameSchool(user, studentGroup.getSchool());
        return get(user, studentGroupService.save(user, studentGroup, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") StudentGroup studentGroup, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, studentGroup.getSchool());
        studentGroupService.delete(user, studentGroup);
    }

    @GetMapping("/curriculumdata/{id:\\d+}")
    public Map<String, ?> curriculumRelatedData(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        return studentGroupService.curriculumData(curriculum);
    }

    @GetMapping("/findstudents")
    public List<StudentGroupStudentDto> searchStudents(HoisUserDetails user, @Valid StudentGroupSearchStudentsCommand criteria) {
        return studentGroupService.searchStudents(user.getSchoolId(), criteria);
    }
}
