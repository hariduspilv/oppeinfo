package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.Teacher;
import ee.hitsa.ois.service.TeacherService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
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

    @GetMapping("/{id}")
    public TeacherDto get(@WithEntity("id") Teacher teacher) {
        return TeacherDto.of(teacher);
    }

    @GetMapping("")
    public Page<TeacherDto> search(TeacherSearchCommand command, Pageable pageable) {
        return teacherService.search(command, pageable);
    }

    @PostMapping("")
    public TeacherDto create(@Valid @RequestBody TeacherForm teacherForm, HoisUserDetails user) {
        return teacherService.save(user , new Teacher(), teacherForm);
        //throw new RuntimeException();
        //return null;
    }

    @PutMapping("/{id}")
    public TeacherDto save(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Teacher teacher, @Valid @RequestBody TeacherForm teacherForm) {
        assertSameSchool(user, teacher);
        return teacherService.save(user, teacher, teacherForm);
    }

    private static void assertSameSchool(HoisUserDetails user, Teacher teacher) {
        Long schoolId = user.getSchoolId();
        if (schoolId == null || !schoolId.equals(EntityUtil.getNullableId(teacher.getSchool()))) {
            throw new IllegalArgumentException();
        }
    }
}
