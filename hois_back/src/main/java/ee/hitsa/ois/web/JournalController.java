package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.service.JournalService;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.timetable.JournalEndDateCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsSearchCommand;
import ee.hitsa.ois.web.dto.timetable.JournalDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryLessonInfoDto;
import ee.hitsa.ois.web.dto.timetable.JournalSearchDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentDto;

@RestController
@RequestMapping("/journals")
public class JournalController {

    @Autowired
    private JournalService journalService;
    @Autowired
    private StudyYearService studyYearService;

    @GetMapping("")
    public Page<JournalSearchDto> search(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public JournalDto get(HoisUserDetails user, @WithEntity("id") Journal journal) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.get(journal);
    }

    @PostMapping("/{id:\\d+}/saveEndDate")
    public void saveEndDate(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestBody JournalEndDateCommand command) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);

        if (user.isTeacher()) {
            Optional<JournalTeacher> teacher =
                    journal.getJournalTeachers().stream().filter(it -> EntityUtil.getId(it.getTeacher().getPerson()) == user.getPersonId()).findFirst();
            if (!teacher.isPresent() || Boolean.FALSE.equals(teacher.get().getIsConfirmer())) {
                throw new ValidationFailedException("journal.messages.teacherNotAllowedToChangeEndDate");
            }
        }

        journalService.saveEndDate(journal, command);
    }

    @GetMapping("/{id:\\d+}/journalEntry")
    public Page<JournalEntryDto> journalEntries(HoisUserDetails user, @PathVariable("id") Long journalId, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.journalEntries(journalId, pageable);
    }

    @PostMapping("/{id:\\d+}/journalEntry")
    public void saveJournalEntry(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestBody JournalEntryForm journalEntryForm) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        journalService.saveJournalEntry(journal, journalEntryForm);
    }

    @PutMapping("/{id:\\d+}/journalEntry")
    public void updateJournalEntry(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestBody JournalEntryDto journalEntryDto) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        journalService.updateJournalEntry(journal, journalEntryDto);
    }

    @PostMapping("/{id:\\d+}/addStudentsToJournal")
    public void addStudentsToJournal(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestBody JournalStudentsCommand command) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        journalService.addStudentsToJournal(journal, command);
    }

    @PostMapping("/{id:\\d+}/removeStudentsFromJournal")
    public void removeStudentsFromJournal(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestBody JournalStudentsCommand command) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        if (!CollectionUtils.isEmpty(journal.getJournalEntries()) && user.isTeacher()) {
            throw new ValidationFailedException("journal.messages.removingStudentIsNotAllowedJournalHasEntries");
        }
        journalService.removeStudentsFromJournal(journal, command);
    }

    @GetMapping("/{id:\\d+}/otherStudents")
    public Page<JournalStudentDto> otherStudents(HoisUserDetails user, @PathVariable("id") Long journalId, JournalStudentsSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.otherStudents(user, journalId, command, pageable);
    }

    @GetMapping("/{id:\\d+}/suitedStudents")
    public List<JournalStudentDto> suitedStudents(HoisUserDetails user, @PathVariable("id") Long journalId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.suitedStudents(user, journalId);
    }

    @GetMapping("/{id:\\d+}/journalEntry/lessonInfo")
    public JournalEntryLessonInfoDto journalEntryLessonInfo(HoisUserDetails user, @WithEntity("id") Journal journal) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.journalEntryLessonInfo(user, journal);
    }

    @GetMapping("currentStudyYear")
    public Map<String, Long> currentPeriod(HoisUserDetails user) {
        return Collections.singletonMap("currentStudyYear", EntityUtil.getNullableId(studyYearService.getCurrentStudyYear(user.getSchoolId())));
    }

}
