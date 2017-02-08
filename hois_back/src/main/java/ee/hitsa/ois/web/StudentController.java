package ee.hitsa.ois.web;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Student;
import ee.hitsa.ois.domain.StudentAbsence;
import ee.hitsa.ois.service.StudentService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.StudentAbsenceForm;
import ee.hitsa.ois.web.commandobject.StudentForm;
import ee.hitsa.ois.web.commandobject.StudentSearchCommand;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;
import ee.hitsa.ois.web.dto.student.StudentViewDto;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Page<StudentSearchDto> search(HoisUserDetails user, @Valid StudentSearchCommand criteria, Pageable pageable) {
        return studentService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id}")
    public StudentViewDto get(HoisUserDetails user, @WithEntity("id") Student student) {
        assertCanView(user, student);
        StudentViewDto dto = StudentViewDto.of(student);
        // rights for editing student data, adding representative and displaying sensitive fields
        dto.setUserCanEditStudent(Boolean.valueOf(UserUtil.canEditStudent(user, student)));
        dto.setUserCanAddRepresentative(Boolean.valueOf(UserUtil.canAddStudentRepresentative(user, student)));
        dto.setUserIsSchoolAdmin(Boolean.valueOf(UserUtil.isSchoolAdmin(user, student.getSchool())));
        if(!(Boolean.TRUE.equals(dto.getUserIsSchoolAdmin()) || UserUtil.isStudent(user, student) || UserUtil.isStudentRepresentative(user, student))) {
            dto.setSpecialNeed(null);
            dto.setIsRepresentativeMandatory(null);
        }
        return dto;
    }

    @PutMapping(value = "/{id}")
    public StudentViewDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Student student, @Valid @RequestBody StudentForm form) {
        if(!UserUtil.canEditStudent(user, student)) {
            throw new IllegalArgumentException();
        }
        return get(user, studentService.save(user, student, form));
    }

    @GetMapping("/{id}/absences")
    public Page<StudentAbsenceDto> getAbsences(HoisUserDetails user, @WithEntity("id") Student student, Pageable pageable) {
        assertCanView(user, student);
        return studentService.absences(EntityUtil.getId(student), pageable);
    }

    @PostMapping("/{studentId}/absences")
    public void createAbsence(HoisUserDetails user, @WithEntity(value = "studentId") Student student, @Valid @RequestBody StudentAbsenceForm form) {
        assertCanCreateAbsence(user, student);
        StudentAbsence absence = EntityUtil.bindToEntity(form, new StudentAbsence());
        absence.setStudent(student);
        absence.setIsAccepted(Boolean.FALSE);
        studentService.save(absence);
    }

    @PutMapping("/{studentId}/absences/{id}")
    public void updateAbsence(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudentAbsence absence, @Valid @RequestBody StudentAbsenceForm form) {
        assertCanEditAbsence(user, absence);
        EntityUtil.bindToEntity(form, absence);
        studentService.save(absence);
    }

    @DeleteMapping("/{studentId}/absences/{id}")
    public void deleteAbsence(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") StudentAbsence absence, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertCanEditAbsence(user, absence);
        studentService.delete(absence);
    }

    @GetMapping("/{id}/directives")
    public Page<StudentViewDto> getDirectives(HoisUserDetails user, @WithEntity("id") Student student, Pageable pageable) {
        assertCanView(user, student);
        // TODO implement and change returned dto type
        // return studentService.searchDirectives(student.getId(), pageable).map(StudentDirectiveDto::of);
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    private static void assertCanView(HoisUserDetails user, Student student) {
        if(!UserUtil.canViewStudent(user, student)) {
            throw new IllegalArgumentException();
        }
    }

    private static void assertCanCreateAbsence(HoisUserDetails user, Student student) {
        if(!UserUtil.canAddStudentAbsence(user, student)) {
            throw new IllegalArgumentException();
        }
    }

    private static void assertCanEditAbsence(HoisUserDetails user, StudentAbsence absence) {
        if(!UserUtil.canEditStudentAbsence(user, absence)) {
            throw new IllegalArgumentException();
        }
    }
}
