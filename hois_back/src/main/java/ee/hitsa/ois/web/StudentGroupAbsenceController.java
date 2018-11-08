package ee.hitsa.ois.web;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentLessonAbsence;
import ee.hitsa.ois.service.StudentGroupAbsenceService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.student.StudentGroupAbsenceCommand;
import ee.hitsa.ois.web.commandobject.student.StudentGroupAbsenceDtoContainer;
import ee.hitsa.ois.web.commandobject.student.StudentGroupAbsenceForm;
import ee.hitsa.ois.web.dto.StudyWeekDto;

@RestController
@RequestMapping("/groupAbsences")
public class StudentGroupAbsenceController {

    @Autowired
    private StudentGroupAbsenceService studentGroupAbsenceService;
    @Autowired
    private EntityManager em; 

    @GetMapping
    public StudentGroupAbsenceDtoContainer get(HoisUserDetails user, @Valid StudentGroupAbsenceCommand criteria) {
        UserUtil.assertIsSchoolAdminOrStudentGroupTeacher(user, em.getReference(StudentGroup.class, criteria.getStudentGroup()));
        return studentGroupAbsenceService.get(criteria);
    }

    @GetMapping("/studyYearWeeks/{id:\\d+}")
    public List<StudyWeekDto> studyYearWeeks(HoisUserDetails user, @PathVariable("id") Long studyYearId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return studentGroupAbsenceService.studyYearWeeks(studyYearId);
    }
    
    @PutMapping("/entry/{id:\\d+}")
    public void updateStudentAbsence(HoisUserDetails user, @WithEntity JournalEntryStudent absence,
            @Valid @RequestBody StudentGroupAbsenceForm form) {
        UserUtil.assertIsSchoolAdminOrStudentGroupTeacher(user, em.getReference(StudentGroup.class, form.getStudentGroup()));
        studentGroupAbsenceService.updateJournalEntryStudentAbsence(absence, form);
    }
    
    @PutMapping("/lesson/{id:\\d+}")
    public void updateStudentAbsenceLesson(HoisUserDetails user, @WithEntity JournalEntryStudentLessonAbsence absence,
            @Valid @RequestBody StudentGroupAbsenceForm form) {
        UserUtil.assertIsSchoolAdminOrStudentGroupTeacher(user, em.getReference(StudentGroup.class, form.getStudentGroup()));
        studentGroupAbsenceService.updateJournalEntryStudentLessonAbsence(absence, form);
    }

}
