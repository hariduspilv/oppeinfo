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
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.StudentRepresentativeService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeApplicationDeclineForm;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeApplicationForm;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeForm;
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
    public StudentRepresentativeDto get(HoisUserDetails user, @WithEntity StudentRepresentative studentRepresentative) {
        assertCanView(user, studentRepresentative.getStudent());
        return StudentRepresentativeDto.of(studentRepresentative, user);
    }

    @PostMapping("/{studentId:\\d+}")
    public void create(HoisUserDetails user, @WithEntity("studentId") Student student, @Valid @RequestBody StudentRepresentativeForm form) {
        if(!UserUtil.canAddStudentRepresentative(user, student)) {
            throw new AssertionFailedException("User cannot add student representative");
        }
        // verify it's not the same person as student
        if(Objects.equals(student.getPerson().getIdcode(), form.getPerson().getIdcode())) {
            throw new ValidationFailedException("student.representative.samewithstudent");
        }
        studentRepresentativeService.create(student, form);
    }

    @PutMapping("/{studentId:\\d+}/{id:\\d+}")
    public StudentRepresentativeDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) StudentRepresentative representative, @Valid @RequestBody StudentRepresentativeForm form) {
        if(!UserUtil.canEditStudentRepresentative(user, representative)) {
            throw new AssertionFailedException("User cannot edit student representative");
        }
        return get(user, studentRepresentativeService.save(representative, form));
    }

    @DeleteMapping("/{studentId:\\d+}/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") StudentRepresentative representative, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        if(!UserUtil.canDeleteStudentRepresentative(user, representative)) {
            throw new AssertionFailedException("User cannot delete student representative");
        }
        studentRepresentativeService.delete(user, representative);
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
    public void acceptApplication(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) StudentRepresentativeApplication application, @SuppressWarnings("unused") @RequestBody VersionedCommand form) {
        assertCanEdit(user, application);
        studentRepresentativeService.acceptApplication(application);
    }

    @PutMapping("/applications/decline/{id:\\d+}")
    public void declineApplication(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) StudentRepresentativeApplication application, @Valid @RequestBody StudentRepresentativeApplicationDeclineForm form) {
        assertCanEdit(user, application);
        studentRepresentativeService.declineApplication(application, form);
    }

    private static void assertCanView(HoisUserDetails user, Student student) {
        if(!UserUtil.canViewStudent(user, student)) {
            throw new AssertionFailedException("User cannot view student data");
        }
    }

    private static void assertCanEdit(HoisUserDetails user, StudentRepresentativeApplication application) {
        UserUtil.assertIsSchoolAdmin(user, application.getStudent().getSchool());
    }
}
