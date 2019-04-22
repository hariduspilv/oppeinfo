package ee.hitsa.ois.web;

import java.util.List;

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
import ee.hitsa.ois.domain.student.StudentRemark;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.service.StudentRemarkService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.student.StudentRemarkForm;
import ee.hitsa.ois.web.commandobject.student.StudentRemarkSearchCommand;
import ee.hitsa.ois.web.dto.student.StudentRemarkDto;

@RestController
@RequestMapping("/remarks")
public class StudentRemarkController {

    @Autowired
    private StudentRemarkService studentRemarkService;

    @GetMapping
    public Page<StudentRemarkDto> search(HoisUserDetails user, @Valid StudentRemarkSearchCommand criteria,
            Pageable pageable) {
        studentRemarkService.assertCanSearch(user);
        return studentRemarkService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public StudentRemarkDto get(HoisUserDetails user, @WithEntity StudentRemark studentRemark) {
        studentRemarkService.assertCanView(user, EntityUtil.getId(studentRemark.getStudent()));
        return StudentRemarkDto.of(studentRemark);
    }

    @GetMapping("/journal/{id:\\d+}")
    public StudentRemarkDto getJournalRemark(HoisUserDetails user, @WithEntity JournalEntryStudent entry) {
        studentRemarkService.assertCanView(user, EntityUtil.getId(entry.getJournalStudent().getStudent()));
        return StudentRemarkDto.of(entry);
    }

    @PostMapping
    public StudentRemarkDto create(HoisUserDetails user, @Valid @RequestBody StudentRemarkForm studentRemarkForm) {
        studentRemarkService.assertCanView(user, studentRemarkForm.getStudent().getId());
        return get(user, studentRemarkService.create(studentRemarkForm));
    }

    @PutMapping("/{id:\\d+}")
    public StudentRemarkDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) StudentRemark studentRemark,
            @Valid @RequestBody StudentRemarkForm studentRemarkForm) {
        studentRemarkService.assertCanEdit(user, EntityUtil.getId(studentRemark.getStudent()));
        return get(user, studentRemarkService.save(studentRemark, studentRemarkForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") StudentRemark studentRemark,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        studentRemarkService.assertCanEdit(user, EntityUtil.getId(studentRemark.getStudent()));
        studentRemarkService.delete(user, studentRemark);
    }

    @GetMapping("/student/{id:\\d+}")
    public Page<StudentRemarkDto> studentRemarks(HoisUserDetails user, @WithEntity Student student, Pageable pageable) {
        UserUtil.assertCanViewStudentSpecificData(user, student);
        return studentRemarkService.studentRemarks(EntityUtil.getId(student), pageable);
    }

    @GetMapping("/student/recent/{id:\\d+}")
    public List<StudentRemarkDto> studentRecentRemarks(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return studentRemarkService.studentRecentRemarks(user.getSchoolId(), EntityUtil.getId(student));
    }

    @GetMapping("/studentGroupRemarks")
    public List<StudentRemarkDto> studentRecentRemarks(HoisUserDetails user) {
        UserUtil.assertIsTeacher(user);
        return studentRemarkService.studentGroupTodaysRemarks(user);
    }
}
