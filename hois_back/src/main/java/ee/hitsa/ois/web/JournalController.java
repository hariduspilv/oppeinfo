package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.service.JournalAsyncService;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntriesCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalOutcomeForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalSuitableStudentsCommand;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;
import ee.hitsa.ois.web.dto.timetable.JournalOutcomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.service.JournalService;
import ee.hitsa.ois.service.JournalUnconfirmedService;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.service.moodle.MoodleService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.JournalUtil;
import ee.hitsa.ois.util.MoodleUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.JournalStudentHasAbsenceCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEndDateCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryQuickUpdateForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalReviewForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.OtherStudentsSearchCommand;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.moodle.EnrollResult;
import ee.hitsa.ois.web.dto.studymaterial.JournalLessonHoursDto;
import ee.hitsa.ois.web.dto.timetable.JournalDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryByDateDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryLessonInfoDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentAcceptedAbsenceDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentResultDto;
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
    private JournalAsyncService journalAsyncService;
    @Autowired
    private JournalUnconfirmedService journalUnconfirmedService;
    @Autowired
    private EntityManager em;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private MoodleService moodleService;

    @GetMapping
    public Page<JournalSearchDto> search(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        JournalUtil.assertCanView(user);
        return journalService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public JournalDto get(HoisUserDetails user, @WithEntity Journal journal) {
        JournalUtil.assertCanView(user, journal);
        return journalService.get(user,  journal);
    }

    @PutMapping("/confirm/{id:\\d+}")
    public JournalDto confirm(HoisUserDetails user, @WithEntity Journal journal) {
        JournalUtil.asssertCanConfirm(user, journal);
        return journalService.get(user, journalService.confirm(journal));
    }
    
    @PutMapping("/unconfirm/{id:\\d+}")
    public JournalDto unconfirm(HoisUserDetails user, @WithEntity Journal journal) {
        JournalUtil.asssertCanUnconfirm(user, journal);
        return journalService.get(user, journalService.unconfirm(journal));
    }

    @PostMapping("/{id:\\d+}/saveEndDate")
    public void saveEndDate(HoisUserDetails user, @WithEntity Journal journal, @RequestBody JournalEndDateCommand command) {
        JournalUtil.asssertCanConfirm(user, journal);
        journalService.saveEndDate(journal, command);
    }

    @PostMapping("/{id:\\d+}/journalReview")
    public JournalDto saveJournalReview(HoisUserDetails user, @WithEntity Journal journal, @RequestBody JournalReviewForm journalReviewForm) {
        JournalUtil.assertCanReview(user, journal);
        return journalService.get(user, journalService.saveJournalReview(journal, journalReviewForm));
    }

    @GetMapping("/{id:\\d+}/journalEntry")
    public Page<JournalEntryTableDto> journalTableEntries(HoisUserDetails user, @WithEntity Journal journal,
            JournalEntriesCommand command, Pageable pageable) {
        JournalUtil.assertCanView(user, journal);
        return journalService.journalTableEntries(journal, command, pageable);
    }

    @GetMapping("/{id:\\d+}/journalEntry/{journalEntry:\\d+}")
    public JournalEntryDto journalEntry(HoisUserDetails user, @PathVariable("id") Long journalId, @PathVariable("journalEntry") Long journalEntrylId) {
        JournalUtil.assertCanView(user, em.find(Journal.class, journalId));
        return journalService.journalEntry(journalId, journalEntrylId);
    }

    @PostMapping("/{id:\\d+}/journalEntry")
    public void saveJournalEntry(HoisUserDetails user, @WithEntity Journal journal, @RequestBody JournalEntryForm journalEntryForm) {
        JournalUtil.asssertCanChange(user, journal);
        journalService.saveJournalEntry(user, journal, journalEntryForm);
    }

    @PutMapping("/{id:\\d+}/journalEntry/{journalEntry:\\d+}")
    public void updateJournalEntry(HoisUserDetails user, @WithEntity Journal journal, @RequestBody JournalEntryForm journalEntryForm, @PathVariable("journalEntry") Long journalEntrylId) {
        JournalUtil.asssertCanChange(user, journal);
        journalService.updateJournalEntry(user, journal, journalEntryForm, journalEntrylId);
    }

    @DeleteMapping("/{journalId:\\d+}/journalEntry/{id:\\d+}")
    public void deleteJournalEntry(HoisUserDetails user, @WithEntity("journalId") Journal journal, @WithVersionedEntity(versionRequestParam = "version") JournalEntry entry, 
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        JournalUtil.asssertCanChange(user, journal);
        journalService.deleteJournalEntry(user, entry);
    }

    @PostMapping("/{id:\\d+}/journalEntry/quickUpdate")
    public Map<Long, List<JournalEntryStudentResultDto>> quickUpdateJournalEntry(HoisUserDetails user,
            @WithEntity Journal journal, @RequestBody JournalEntryQuickUpdateForm journalEntryForm) {
        JournalUtil.asssertCanChange(user, journal);
        return journalService.quickUpdateJournalEntry(user, journalEntryForm);
    }

    @GetMapping("/{id:\\d+}/journalOutcome")
    public Page<CurriculumModuleOutcomeResult> journalTableOutcomes(HoisUserDetails user,
            @PathVariable("id") Long journalId, Pageable pageable) {
        JournalUtil.assertCanView(user, em.find(Journal.class, journalId));
        return journalService.journalTableOutcomes(journalId, pageable);
    }

    @GetMapping("/{id:\\d+}/journalOutcome/{outcome:\\d+}")
    public JournalOutcomeDto journalOutcome(HoisUserDetails user, @WithEntity Journal journal,
            @WithEntity("outcome") CurriculumModuleOutcome outcome) {
        JournalUtil.assertCanView(user, journal);
        return journalService.journalOutcome(user, journal, outcome);
    }

    @PostMapping("/{id:\\d+}/journalOutcome/{outcome:\\d+}")
    public void saveJournalOutcome(HoisUserDetails user, @WithEntity Journal journal,
            @WithEntity("outcome") CurriculumModuleOutcome outcome,
            @Valid @RequestBody JournalOutcomeForm journalOutcomeForm) {
        JournalUtil.asssertCanChange(user, journal);
        journalService.saveOutcomeResults(user, outcome, journalOutcomeForm);
    }

    @PostMapping("/{id:\\d+}/addStudentsToJournal")
    public void addStudentsToJournal(HoisUserDetails user, @WithEntity Journal journal, @RequestBody JournalStudentsCommand command) {
        JournalUtil.assertCanAddStudent(user, journal);
        journalService.addStudentsToJournal(user, journal, command);
    }

    @PostMapping("/{id:\\d+}/removeStudentsFromJournal")
    public void removeStudentsFromJournal(HoisUserDetails user, @WithEntity Journal journal, @RequestBody JournalStudentsCommand command) {
        JournalUtil.assertCanRemoveStudent(user, journal);
        journalService.removeStudentsFromJournal(user, journal, command);
    }

    @GetMapping("/{id:\\d+}/otherStudents")
    public Page<JournalStudentDto> otherStudents(HoisUserDetails user, @WithEntity Journal journal,
            OtherStudentsSearchCommand command, Pageable pageable) {
        JournalUtil.asssertCanChange(user, journal);
        return journalService.otherStudents(user, journal.getId(), command, pageable);
    }

    @GetMapping("/{id:\\d+}/suitedStudents")
    public List<JournalStudentDto> suitedStudents(HoisUserDetails user, @WithEntity Journal journal) {
        JournalUtil.asssertCanChange(user, journal);
        return journalService.suitedStudents(user, journal.getId());
    }

    @GetMapping("/{id:\\d+}/journalStudents")
    public List<JournalStudentDto> journalStudents(HoisUserDetails user, @WithEntity Journal journal, @RequestParam(required = false) Boolean allStudents) {
        JournalUtil.assertCanView(user, journal);
        return journalService.journalStudents(user, journal, allStudents);
    }

    @GetMapping("/{id:\\d+}/journalEntriesByDate")
    public List<JournalEntryByDateDto> journalEntriesByDate(HoisUserDetails user, @WithEntity Journal journal,
            @RequestParam(required = false) Boolean allStudents) {
        JournalUtil.assertCanView(user, journal);
        return journalService.journalEntriesByDate(journal, allStudents);
    }

    @GetMapping("/{id:\\d+}/journalEntry/lessonInfo")
    public JournalEntryLessonInfoDto journalEntryLessonInfo(HoisUserDetails user, @WithEntity Journal journal) {
        JournalUtil.assertCanView(user, journal);
        return journalService.journalEntryLessonInfo(journal);
    }

    @GetMapping("/{id:\\d+}/theme/{theme:\\d+}")
    public CurriculumVersionOccupationModuleThemeDto journalTheme(HoisUserDetails user,
            @WithEntity Journal journal, @WithEntity("theme") CurriculumVersionOccupationModuleTheme theme) {
        JournalUtil.assertCanView(user, journal);
        return journalService.journalTheme(theme);
    }


    @GetMapping("/{id:\\d+}/journal.xls")
    public void journalAsExcel(HoisUserDetails user, @WithEntity Journal journal, HttpServletResponse response) throws IOException {
        JournalUtil.assertCanView(user, journal);
        HttpUtil.xls(response, "journal.xls", journalService.journalAsExcel(journal));
    }

    @GetMapping("/{id:\\d+}/canAddFinalEntry")
    public Map<String, Boolean> canAddFinalEntry(HoisUserDetails user, @WithEntity Journal journal)  {
        JournalUtil.assertCanView(user, journal);
        return Collections.singletonMap("canAddFinalEntry", Boolean.valueOf(journalService.canAddFinalEntry(journal)));
    }

    @GetMapping("/{id:\\d+}/usedHours")
    public JournalLessonHoursDto usedHours(HoisUserDetails user, @WithEntity Journal journal) {
        JournalUtil.assertCanView(user, journal);
        return journalService.usedHours(journal);
    }

    @GetMapping("/{id:\\d+}/studentsWithAcceptedAbsences")
    public List<JournalEntryStudentAcceptedAbsenceDto> journalStudentsWithAcceptedAbsences(HoisUserDetails user,
            @WithEntity Journal journal, @Valid JournalStudentHasAbsenceCommand command) {
        JournalUtil.assertCanView(user, journal);
        return journalService.journalStudentsWithAcceptedAbsences(journal, command.getEntryDate());
    }
    
    @GetMapping("/studentJournalStudyYears")
    public List<StudyYearSearchDto> studentJournalStudyYears(HoisUserDetails user, @RequestParam("studentId") Long studentId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentJournalStudyYears(studentId);
    }

    @GetMapping("/studentJournals")
    public List<StudentJournalDto> studentJournals(HoisUserDetails user, @RequestParam("studentId") Long studentId, @RequestParam("studyYearId") Long studyYearId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentJournals(studentId, studyYearId);
    }

    @GetMapping("/studentJournal")
    public StudentJournalDto studentJournal(HoisUserDetails user, @RequestParam("studentId") Long studentId,
            @RequestParam("journalId") Long journalId) {
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

    @GetMapping("/{id:\\d+}/withoutFinalResult")
    public List<JournalStudentDto> withoutFinalResult(HoisUserDetails user, @WithEntity Journal journal) {
        JournalUtil.assertCanView(user);
        return JournalUtil.withoutFinalResult(journal);
    }

    @GetMapping("/canConfirmAll")
    public Map<String, Boolean> canConfirmAll(HoisUserDetails user) {
        JournalUtil.assertCanView(user);
        StudyYear studyYear = studyYearService.getCurrentStudyYear(user.getSchoolId());
        return Collections.singletonMap("canConfirmAll", Boolean.valueOf(JournalUtil.canConfirmAll(user, studyYear)));
    }

    @PutMapping("/confirmAll")
    public Map<String, Integer> confirmAll(HoisUserDetails user) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(user.getSchoolId());
        JournalUtil.assertCanConfirmAll(user, studyYear);
        return Collections.singletonMap("numberOfConfirmedJournals", journalService.confirmAll(user));
    }

    @PostMapping("/addAllSuitableStudentsRequest")
    public Map<String, Object> addAllSuitableStudents(HoisUserDetails user,
            @RequestBody @Valid JournalSuitableStudentsCommand criteria) {
        JournalUtil.asserCanAddAllSuitableStudents(user);
        String requestHash = journalAsyncService.addAllSuitableStudentsRequest(user, criteria.getStudyYearId());
        return Collections.singletonMap("key", requestHash);
    }

    @GetMapping("/addAllSuitableStudentsStatus")
    public FutureStatusResponse studentsWithoutPhotoRequestStatus(HoisUserDetails user, @RequestParam String key) {
        return journalAsyncService.addAllSuitableStudentsStatus(user, key);
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
    public List<StudentJournalAbsenceDto> studentAbsences(HoisUserDetails user,
            @RequestParam("studentId") Long studentId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentAbsences(user.getSchoolId(), studentId);
    }

    @GetMapping("/studentJournalLastResults")
    public List<StudentJournalResultDto> studentLastResults(HoisUserDetails user,
            @RequestParam("studentId") Long studentId) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return journalService.studentLastResults(user.getSchoolId(), studentId);
    }

    @PutMapping("/{id:\\d+}/moodle/courseLink")
    public void moodleCourseLink(HoisUserDetails user, HttpServletRequest request, @WithEntity Journal journal, @RequestBody Long courseId) {
        JournalUtil.asssertCanChange(user, journal);
        moodleService.saveMoodleCourseLink(MoodleUtil.createContext(user, request, journal), journal, courseId);
    }

    @PostMapping("/{id:\\d+}/moodle/enrollStudents")
    public EnrollResult moodleEnrollStudents(HoisUserDetails user, HttpServletRequest request, @WithEntity Journal journal) {
        JournalUtil.asssertCanChange(user, journal);
        return moodleService.moodleEnrollStudents(MoodleUtil.createContext(user, request, journal), journal);
    }

    @PostMapping("/{id:\\d+}/moodle/importGradeItems")
    public void moodleImportGradeItems(HoisUserDetails user, HttpServletRequest request, @WithEntity Journal journal) {
        JournalUtil.asssertCanChange(user, journal);
        moodleService.moodleImportGradeItems(MoodleUtil.createContext(user, request, journal), journal);
    }

    @PostMapping("/{id:\\d+}/moodle/importAllGrades")
    public void moodleImportAllGrades(HoisUserDetails user, HttpServletRequest request, @WithEntity Journal journal) {
        JournalUtil.asssertCanChange(user, journal);
        moodleService.moodleImportAllGrades(MoodleUtil.createContext(user, request, journal), journal);
    }

    @PostMapping("/{id:\\d+}/moodle/importMissingGrades")
    public void moodleImportMissingGrades(HoisUserDetails user, HttpServletRequest request, @WithEntity Journal journal) {
        JournalUtil.asssertCanChange(user, journal);
        moodleService.moodleImportMissingGrades(MoodleUtil.createContext(user, request, journal), journal);
    }

}
