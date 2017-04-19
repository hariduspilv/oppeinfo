package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.service.StudentService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentDirectiveDto;
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

    @GetMapping("/{id:\\d+}")
    public StudentViewDto get(HoisUserDetails user, @WithEntity("id") Student student) {
        assertCanView(user, student);
        StudentViewDto dto = StudentViewDto.of(student);
        // rights for editing student data, adding representative and displaying sensitive fields
        dto.setUserCanEditStudent(Boolean.valueOf(UserUtil.canEditStudent(user, student)));
        dto.setUserCanAddRepresentative(Boolean.valueOf(UserUtil.canAddStudentRepresentative(user, student)));
        dto.setUserIsSchoolAdmin(Boolean.valueOf(UserUtil.isSchoolAdmin(user, student.getSchool())));
        if(!(Boolean.TRUE.equals(dto.getUserIsSchoolAdmin()) || UserUtil.isSame(user, student) || UserUtil.isStudentRepresentative(user, student))) {
            dto.setSpecialNeed(null);
            dto.setIsRepresentativeMandatory(null);
        }
        return dto;
    }

    @PutMapping("/{id:\\d+}")
    public StudentViewDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Student student, @Valid @RequestBody StudentForm form) {
        if(!UserUtil.canEditStudent(user, student)) {
            throw new AssertionFailedException("User cannot edit student data");
        }
        return get(user, studentService.save(user, student, form));
    }

    @GetMapping("/{id:\\d+}/absences")
    public Page<StudentAbsenceDto> absences(HoisUserDetails user, @WithEntity("id") Student student, Pageable pageable) {
        assertCanView(user, student);
        return studentService.absences(EntityUtil.getId(student), pageable);
    }

    @PostMapping("/{studentId:\\d+}/absences")
    public void createAbsence(HoisUserDetails user, @WithEntity(value = "studentId") Student student, @Valid @RequestBody StudentAbsenceForm form) {
        assertCanCreateAbsence(user, student);
        studentService.create(student, form);
    }

    @PutMapping("/{studentId:\\d+}/absences/{id:\\d+}")
    public void updateAbsence(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudentAbsence absence, @Valid @RequestBody StudentAbsenceForm form) {
        assertCanEditAbsence(user, absence);
        studentService.save(absence, form);
    }

    @DeleteMapping("/{studentId:\\d+}/absences/{id:\\d+}")
    public void deleteAbsence(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") StudentAbsence absence, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertCanEditAbsence(user, absence);
        studentService.delete(absence);
    }

    /**
     * Load whole documents tab of student with single request.
     *
     * @param user
     * @param student
     * @return
     */
    @GetMapping("/{id:\\d+}/documents")
    public Map<String, Object> loadDocumentsPage(HoisUserDetails user, @WithEntity("id") Student student) {
        Map<String, Object> result = new HashMap<>();
        int pagesize = 5;
        // TODO correct sorting
        result.put("applications", applications(user, student, new PageRequest(0, pagesize, null, "inserted")));
        result.put("directives", directives(user, student, new PageRequest(0, pagesize, null, "headline")));
        // TODO more data
        result.put("student", Collections.singletonMap("isVocational", Boolean.valueOf(CurriculumUtil.isVocational(student.getCurriculumVersion().getCurriculum().getOrigStudyLevel()))));
        return result;
    }

    @GetMapping("/{id:\\d+}/applications")
    public Page<StudentApplicationDto> applications(HoisUserDetails user, @WithEntity("id") Student student, Pageable pageable) {
        assertCanView(user, student);
        return studentService.applications(EntityUtil.getId(student), pageable);
    }

    @GetMapping("/{id:\\d+}/directives")
    public Page<StudentDirectiveDto> directives(HoisUserDetails user, @WithEntity("id") Student student, Pageable pageable) {
        assertCanView(user, student);
        return studentService.directives(user, student, pageable);
    }

    @GetMapping("/{id:\\d+}/subjects")
    public List<AutocompleteResult> subjects(HoisUserDetails user, @WithEntity("id") Student student) {
        assertCanView(user, student);
        return studentService.subjects(student);
    }

    private static void assertCanView(HoisUserDetails user, Student student) {
        AssertionFailedException.assertTrue(UserUtil.canViewStudent(user, student), "User cannot view student data");
    }

    private static void assertCanCreateAbsence(HoisUserDetails user, Student student) {
        AssertionFailedException.assertTrue(UserUtil.canAddStudentAbsence(user, student), "User cannot add student absence");
    }

    private static void assertCanEditAbsence(HoisUserDetails user, StudentAbsence absence) {
        AssertionFailedException.assertTrue(UserUtil.canEditStudentAbsence(user, absence), "User cannot edit student absence");
    }
}
