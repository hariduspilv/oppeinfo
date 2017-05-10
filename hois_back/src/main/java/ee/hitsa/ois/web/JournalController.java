package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsSearchCommand;
import ee.hitsa.ois.web.dto.timetable.JournalDto;
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
    public Page<JournalSearchDto> search(JournalSearchCommand command, Pageable pageable, HoisUserDetails user) {
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

    @GetMapping("/{id:\\d+}/students")
    public Page<JournalStudentDto> students(HoisUserDetails user, @WithEntity("id") Journal journal, JournalStudentsSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.students(user, journal, command, pageable);
    }

    @GetMapping("currentStudyYear")
    public Map<String, Long> currentPeriod(HoisUserDetails user) {
        return Collections.singletonMap("currentStudyYear", EntityUtil.getNullableId(studyYearService.getCurrentStudyYear(user.getSchoolId())));
    }

}
