package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.service.TeacherService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.TeacherForm;
import ee.hitsa.ois.web.commandobject.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/{id:\\d+}")
    public TeacherDto get(@WithEntity("id") Teacher teacher) {
        return TeacherDto.of(teacher);
    }

    @GetMapping("")
    public Page<TeacherDto> search(TeacherSearchCommand command, Pageable pageable, HoisUserDetails user) {
        if (!user.isExternalExpert()) {
            command.setSchool(user.getSchoolId());
        }
        return teacherService.search(command, pageable);
    }

    @PostMapping("")
    public TeacherDto create(@Valid @RequestBody TeacherForm teacherForm, HoisUserDetails user) {
        return teacherService.create(user, teacherForm);
    }

    @PutMapping("/{id:\\d+}")
    public TeacherDto save(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Teacher teacher, @Valid @RequestBody TeacherForm teacherForm) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        return teacherService.save(user, teacher, teacherForm);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Teacher teacher,  @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        teacherService.delete(teacher);
    }
}
