package ee.hitsa.ois.web;

import java.util.Objects;

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

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.student.StudentRepresentativeApplication;
import ee.hitsa.ois.service.StudentRepresentativeService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.StudentRepresentativeApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.StudentRepresentativeApplicationDeclineForm;
import ee.hitsa.ois.web.commandobject.StudentRepresentativeApplicationForm;
import ee.hitsa.ois.web.commandobject.StudentRepresentativeForm;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.student.StudentRepresentativeApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentRepresentativeDto;

@RestController
@RequestMapping("/studentrepresentatives")
public class StudentRepresentativeController {

    @Autowired
    private StudentRepresentativeService studentRepresentativeService;

    @GetMapping("/{studentId:\\d+}")
    public Page<StudentRepresentativeDto> getRepresentatives(HoisUserDetails user, @WithEntity("studentId") Student student, Pageable pageable) {
        assertCanView(user, student);
        return studentRepresentativeService.search(user, EntityUtil.getId(student), pageable);
    }

    @GetMapping("/{studentId:\\d+}/{id:\\d+}")
    public StudentRepresentativeDto get(HoisUserDetails user, @WithEntity("id") StudentRepresentative studentRepresentative) {
        assertCanView(user, studentRepresentative.getStudent());
        return StudentRepresentativeDto.of(studentRepresentative, user);
    }

    @PostMapping("/{studentId:\\d+}")
    public StudentRepresentativeDto create(HoisUserDetails user, @WithEntity("studentId") Student student, @Valid @RequestBody StudentRepresentativeForm form) {
        if(!UserUtil.canAddStudentRepresentative(user, student)) {
            // TODO
            throw new IllegalArgumentException();
        }
        // verify it's not the same person as student
        if(Objects.equals(student.getPerson().getIdcode(), form.getPerson().getIdcode())) {
            throw new ValidationFailedException("person.idcode", "representative-and-student-are-same");
        }
        StudentRepresentative studentRepresentative = new StudentRepresentative();
        studentRepresentative.setStudent(student);
        return get(user, studentRepresentativeService.save(studentRepresentative, form));
    }

    @PutMapping("/{studentId:\\d+}/{id:\\d+}")
    public StudentRepresentativeDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudentRepresentative representative, @Valid @RequestBody StudentRepresentativeForm form) {
        if(!UserUtil.canEditStudentRepresentative(user, representative)) {
            // TODO
            throw new IllegalArgumentException();
        }
        return get(user, studentRepresentativeService.save(representative, form));
    }

    @DeleteMapping("/{studentId:\\d+}/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") StudentRepresentative representative, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        if(!UserUtil.canEditStudentRepresentative(user, representative)) {
            // TODO
            throw new IllegalArgumentException();
        }
        studentRepresentativeService.delete(representative);
    }

    @GetMapping("/applications")
    public Page<StudentRepresentativeApplicationDto> searchApplications(HoisUserDetails user, @Valid StudentRepresentativeApplicationSearchCommand criteria, Pageable pageable) {
        return studentRepresentativeService.searchApplications(user.getSchoolId(), criteria, pageable);
    }

    @PostMapping("/applications")
    public void createApplication(HoisUserDetails user, @Valid @RequestBody StudentRepresentativeApplicationForm form) {
        studentRepresentativeService.createApplication(user, form);
    }

    @PutMapping("/applications/accept/{id:\\d+}")
    public void acceptApplication(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudentRepresentativeApplication application, @SuppressWarnings("unused") @RequestBody VersionedCommand form) {
        assertCanEdit(user, application);
        studentRepresentativeService.acceptApplication(application);
    }

    @PutMapping("/applications/decline/{id:\\d+}")
    public void declineApplication(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudentRepresentativeApplication application, @Valid @RequestBody StudentRepresentativeApplicationDeclineForm form) {
        assertCanEdit(user, application);
        studentRepresentativeService.declineApplication(application, form);
    }

    private static void assertCanView(HoisUserDetails user, Student student) {
        if(!UserUtil.canViewStudent(user, student)) {
            throw new IllegalArgumentException();
        }
    }

    private static void assertCanEdit(HoisUserDetails user, StudentRepresentativeApplication application) {
        if(!UserUtil.isSchoolAdmin(user, application.getStudent().getSchool())) {
            throw new IllegalArgumentException();
        }
    }
}
