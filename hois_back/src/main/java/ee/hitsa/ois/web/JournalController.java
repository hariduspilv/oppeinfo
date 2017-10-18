package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.service.JournalService;
import ee.hitsa.ois.service.JournalUnconfirmedService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.JournalUtil;
import ee.hitsa.ois.util.JournalValidationUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.JournalStudentHasAbsenceCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEndDateCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.StudentNameSearchCommand;
import ee.hitsa.ois.web.dto.timetable.JournalDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryByDateDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryLessonInfoDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryTableDto;
import ee.hitsa.ois.web.dto.timetable.JournalSearchDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalAbsenceDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalResultDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalStudyListDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalTaskListDto;

@RestController
@RequestMapping("/journals")
public class JournalController {

    @Autowired
    private JournalService journalService;
    @Autowired
    private JournalUnconfirmedService journalUnconfirmedService;

    @GetMapping
    public Page<JournalSearchDto> search(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public JournalDto get(HoisUserDetails user, @WithEntity("id") Journal journal) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        JournalDto dto = JournalDto.of(journal);
        dto.setCanBeConfirmed(Boolean.valueOf(JournalUtil.canBeConfirmed(user, journal)));
        dto.setCanBeUnconfirmed(Boolean.valueOf(JournalUtil.canBeUnconfirmed(user, journal)));
        return dto;
    }

    @PutMapping("/confirm/{id:\\d+}")
    public JournalDto confirm(HoisUserDetails user, @WithEntity("id") Journal journal) {
        JournalValidationUtil.asssertCanBeConfirmed(user, journal);
        return get(user, journalService.confirm(journal));
    }
    
    @PutMapping("/unconfirm/{id:\\d+}")
    public JournalDto unconfirm(HoisUserDetails user, @WithEntity("id") Journal journal) {
        JournalValidationUtil.asssertCanBeUnconfirmed(user, journal);
        return get(user, journalService.unconfirm(journal));
    }

    @PostMapping("/{id:\\d+}/saveEndDate")
    public void saveEndDate(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestBody JournalEndDateCommand command) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        JournalValidationUtil.assertIsConfirmer(user, journal);
        journalService.saveEndDate(journal, command);
    }

    @GetMapping("/{id:\\d+}/journalEntry")
    public Page<JournalEntryTableDto> journalTableEntries(HoisUserDetails user, @PathVariable("id") Long journalId, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.journalTableEntries(journalId, pageable);
    }

    @GetMapping("/{id:\\d+}/journalEntry/{journalEntry:\\d+}")
    public JournalEntryDto journalEntry(HoisUserDetails user, @PathVariable("id") Long journalId, @PathVariable("journalEntry") Long journalEntrylId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.journalEntry(journalId, journalEntrylId);
    }

    @PostMapping("/{id:\\d+}/journalEntry")
    public void saveJournalEntry(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestBody JournalEntryForm journalEntryForm) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        journalService.saveJournalEntry(user, journal, journalEntryForm);
    }

    @PutMapping("/{id:\\d+}/journalEntry/{journalEntry:\\d+}")
    public void updateJournalEntry(HoisUserDetails user, @RequestBody JournalEntryForm journalEntryForm, @PathVariable("journalEntry") Long journalEntrylId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        journalService.updateJournalEntry(user, journalEntryForm, journalEntrylId);
    }

    @PostMapping("/{id:\\d+}/addStudentsToJournal")
    public void addStudentsToJournal(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestBody JournalStudentsCommand command) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        JournalValidationUtil.assertAddStudentsToJournal(user, journal);
        journalService.addStudentsToJournal(journal, command);
    }

    @PostMapping("/{id:\\d+}/removeStudentsFromJournal")
    public void removeStudentsFromJournal(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestBody JournalStudentsCommand command) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        JournalValidationUtil.assertRemoveStudentsFromJournal(user, journal);
        journalService.removeStudentsFromJournal(journal, command);
    }

    @GetMapping("/{id:\\d+}/otherStudents")
    public Page<JournalStudentDto> otherStudents(HoisUserDetails user, @PathVariable("id") Long journalId, StudentNameSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.otherStudents(user, journalId, command, pageable);
    }

    @GetMapping("/{id:\\d+}/suitedStudents")
    public List<JournalStudentDto> suitedStudents(HoisUserDetails user, @PathVariable("id") Long journalId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.suitedStudents(user, journalId);
    }

    @GetMapping("/{id:\\d+}/journalStudents")
    public List<JournalStudentDto> journalStudents(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestParam(required = false) Boolean allStudents) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.journalStudents(journal, allStudents);
    }

    @GetMapping("/{id:\\d+}/journalEntriesByDate")
    public List<JournalEntryByDateDto> journalEntriesByDate(HoisUserDetails user, @WithEntity("id") Journal journal, @RequestParam(required = false) Boolean allStudents) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.journalEntriesByDate(journal, allStudents);
    }

    @GetMapping("/{id:\\d+}/journalEntry/lessonInfo")
    public JournalEntryLessonInfoDto journalEntryLessonInfo(HoisUserDetails user, @WithEntity("id") Journal journal) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.journalEntryLessonInfo(journal);
    }

    @GetMapping("/{id:\\d+}/journal.xls")
    public void journalAsExcel(HoisUserDetails user, @WithEntity("id") Journal journal, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        HttpUtil.xls(response, "journal.xls", journalService.journalAsExcel(journal));
    }
    
    @GetMapping("/{id:\\d+}/hasFinalEntry")
    public Map<String, Boolean> hasFinalEntry(HoisUserDetails user, @WithEntity("id") Journal journal)  {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return Collections.singletonMap("hasFinalEntry", Boolean.valueOf(JournalUtil.hasFinalEntry(journal)));
    }
    
    @GetMapping("/{id:\\d+}/studentsWithAcceptedAbsence")
    public Set<Long> journalStudentsWithAcceptedAbsence(HoisUserDetails user, @WithEntity("id") Journal journal, 
            @Valid JournalStudentHasAbsenceCommand command)  {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return journalService.journalStudentsWithAcceptedAbsence(journal, command.getEntryDate());
    }

    @GetMapping("/studentJournals")
    public List<StudentJournalDto> studentJournals(HoisUserDetails user,  @RequestParam("studentId") Long studentId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentJournals(studentId);
    }

    @GetMapping("/{id:\\d+}/studentJournal")
    public StudentJournalDto studentJournal(HoisUserDetails user, @PathVariable("id") Long journalId, @RequestParam("studentId") Long studentId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentJournal(studentId, journalId);
    }

    @GetMapping("/studentJournalTasks")
    public StudentJournalTaskListDto studentJournalTasks(HoisUserDetails user, @RequestParam("studentId") Long studentId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentJournalTasks(user.getSchoolId(), studentId);
    }

    @GetMapping("/studentJournalStudy")
    public StudentJournalStudyListDto studentJournalStudy(HoisUserDetails user, @RequestParam("studentId") Long studentId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentJournalStudy(user.getSchoolId(), studentId);
    }

    /**
     * 
     * Used on home page
     * 
     * @return following information:     <br>
     *  - number of unconfirmed journals which will expire in two weeks     <br>
     *  - are there any unconfirmed journals already ended     <br>
     *  - <b>null</b> when user is not supposed to see presence of unconfirmed journals 
     *    (user is not teacher nor school admin)
     */
    @GetMapping("/unconfirmedJournalsInfo")
    public Map<String, ?> unconfirmedJournalsInfo(HoisUserDetails user) {
        if(!user.isSchoolAdmin() && !user.isTeacher()) {
            return null;
        }
        return journalUnconfirmedService.getInfo(user);
    }

    @GetMapping("/studentJournalAbsences")
    public List<StudentJournalAbsenceDto> studentAbsences(HoisUserDetails user, @RequestParam("studentId") Long studentId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentAbsences(user.getSchoolId(), studentId);
    }
    
    @GetMapping("/studentJournalLastResults")
    public List<StudentJournalResultDto> studentLastResults(HoisUserDetails user, @RequestParam("studentId") Long studentId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentLastResults(user.getSchoolId(), studentId);
    }
    
}
