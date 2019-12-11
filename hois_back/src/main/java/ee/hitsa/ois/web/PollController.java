package ee.hitsa.ois.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import ee.hitsa.ois.domain.poll.Poll;
import ee.hitsa.ois.domain.poll.PollResultStudentCommand;
import ee.hitsa.ois.domain.poll.PollTheme;
import ee.hitsa.ois.domain.poll.PollThemeQuestion;
import ee.hitsa.ois.domain.poll.Question;
import ee.hitsa.ois.domain.poll.Response;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.JobExecutorService;
import ee.hitsa.ois.service.poll.PollAsyncService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.poll.GraphSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.PollCommand;
import ee.hitsa.ois.web.commandobject.poll.PollCommentCommand;
import ee.hitsa.ois.web.commandobject.poll.PollDatesCommand;
import ee.hitsa.ois.web.commandobject.poll.PollForm;
import ee.hitsa.ois.web.commandobject.poll.PollResultStatisticsCommand;
import ee.hitsa.ois.web.commandobject.poll.PollSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemePageableCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeRepedetiveCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import ee.hitsa.ois.web.dto.poll.AnswersDto;
import ee.hitsa.ois.web.dto.poll.GraphDto;
import ee.hitsa.ois.web.dto.poll.PollRelatedObjectsDto;
import ee.hitsa.ois.web.dto.poll.PollResultStatisticsDto;
import ee.hitsa.ois.web.dto.poll.PollResultStudentDto;
import ee.hitsa.ois.web.dto.poll.PollResultStudentOrTeacherDto;
import ee.hitsa.ois.web.dto.poll.PollResultsDto;
import ee.hitsa.ois.web.dto.poll.PollResultsSubjectDto;
import ee.hitsa.ois.web.dto.poll.PollSearchDto;
import ee.hitsa.ois.web.dto.poll.PracticeThemesDto;
import ee.hitsa.ois.web.dto.poll.QuestionDto;
import ee.hitsa.ois.web.dto.poll.QuestionPollSearchDto;
import ee.hitsa.ois.web.dto.poll.QuestionSearchDto;
import ee.hitsa.ois.web.dto.poll.ResponseDto;
import ee.hitsa.ois.web.dto.poll.SubjectAnswerDto;
import ee.hitsa.ois.web.dto.poll.SubjectCommentDto;
import ee.hitsa.ois.web.dto.poll.ThemesDto;

@RestController
@RequestMapping("/poll")
public class PollController {
    
    @Autowired
    private PollAsyncService pollService;
    
    @Autowired
    private JobExecutorService jobExecutorService;
    
    /**
     * Searches
     */

    @GetMapping
    public Page<PollSearchDto> search(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.search(user, command, pageable);
    }
    
    @GetMapping("/answers")
    public Page<AnswersDto> searchAnswers(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        return pollService.searchAnswers(user, command, pageable);
    }
    
    @GetMapping("/answers/subjects")
    public Page<SubjectAnswerDto> searchAnswersPerSubject(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        return pollService.searchAnswersPerSubject(user, command, pageable);
    }
    
    @GetMapping("/answers/{id:\\d+}")
    public PollRelatedObjectsDto searchAnswersPerResponse(HoisUserDetails user, @WithEntity Response response) {
        UserUtil.assertSameSchool(user, response.getPoll().getSchool());
        return pollService.searchAnswersPerResponse(response);
    }
    
    @GetMapping("/results/{id:\\d+}")
    public PollResultsDto pollResults(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResults(poll);
    }
    
    @GetMapping("/results/subjects/{id:\\d+}")
    public Page<PollResultsSubjectDto> pollResultSubjects(HoisUserDetails user, @WithEntity Poll poll, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResultSubjects(user, poll, pageable);
    }
    
    @GetMapping("/results/enterprises/{id:\\d+}")
    public Page<AutocompleteResult> pollResultEnterprises(HoisUserDetails user, @WithEntity Poll poll, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResultEnterprises(poll, pageable);
    }
    
    @GetMapping("/results/enterprises/studentOrTeacher/{id:\\d+}")
    public PollResultStudentOrTeacherDto pollResultStudentOrTeacher(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResultStudentOrTeacher(user, poll);
    }
    
    @GetMapping("/results/student")
    public Page<PollResultStudentDto> pollResultStudent(HoisUserDetails user, PollResultStudentCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResultStudent(command, pageable);
    }
    
    @GetMapping("/results/exportSubject.xls")
    public void subjectIntoExcel(HoisUserDetails user, PollResultStudentCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        HttpUtil.xls(response, "pollsubjectresult.xls", pollService.searchExcel(criteria));
    }
    
    @PutMapping("/graph")
    public GraphDto searchAnswersPerResponse(HoisUserDetails user, @RequestBody GraphSearchCommand command) {
        // TODO: assert
        return pollService.createGraph(user, command, true);
    }
    
    @PutMapping("/graphWithoutStudentAnswer")
    public GraphDto searchAnswersPerResponseWithoutStudent(HoisUserDetails user, @RequestBody GraphSearchCommand command) {
        // TODO: assert
        return pollService.createGraph(user, command, false);
    }
    
    @GetMapping("/questionsList")
    public Page<QuestionSearchDto> searchQuestions(HoisUserDetails user, QuestionSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.searchQuestions(user, command, pageable);
    }
    
    @GetMapping("/questionPolls/{id:\\d+}")
    public Page<QuestionPollSearchDto> questionPolls(HoisUserDetails user, @WithEntity Question question, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, question.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.questionPolls(user, question, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public PollForm get(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.get(poll);
    }
    
    @GetMapping("/themes/{id:\\d+}")
    public ThemesDto themes(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.themes(poll);
    }
    
    @GetMapping("/pollNames")
    public Set<AutocompleteResult> pollNames(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollNames(user);
    }
    
    @GetMapping("/questions")
    public Set<AutocompleteResult> questions(HoisUserDetails user, @RequestParam(name = "type") String pollType) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.questions(user, pollType);
    }
    
    @GetMapping("/question/{id:\\d+}")
    public QuestionDto question(HoisUserDetails user, @WithEntity Question question) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, question.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.question(question);
    }
    
    /**
     * Manipulating poll
     */
    
    @PostMapping
    public PollForm create(HoisUserDetails user,
            @Valid @RequestBody PollCommand practiceEnterpriseForm) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.create(user, practiceEnterpriseForm);
    }
    
    @PutMapping("/{id:\\d+}")
    public PollForm update(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody PollCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.save(user, poll, command);
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void deletePoll(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.deletePoll(user, poll);
    }
    
    /**
     * Changes poll valid from, valid thru or reminder date
     * User for polls that are confirmed and have tables for answering created
     * @param user
     * @param poll
     * @param command
     */
    @PutMapping("/changeDates/{id:\\d+}")
    public void updatePollDates(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody PollDatesCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.updatePollDates(user, poll, command);
    }
    
    @DeleteMapping("/theme/{id:\\d+}")
    public void deleteTheme(HoisUserDetails user, @WithEntity PollTheme pollTheme) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, pollTheme.getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.deleteTheme(user, pollTheme);
        pollService.updateThemeOrderAfterDelete(user, pollTheme.getPoll());
    }
    
    @DeleteMapping("/pollThemeQuestion/{id:\\d+}")
    public void deletePollThemeQuestion(HoisUserDetails user, @WithEntity PollThemeQuestion pollThemeQuestion) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, pollThemeQuestion.getPollTheme().getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.deleteQuestion(user, pollThemeQuestion);
        pollService.updateQuestionOrderAfterDelete(user, pollThemeQuestion.getPollTheme());
    }
    
    @PutMapping("/themes/{id:\\d+}")
    public ThemesDto updateThemeOrder(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody ThemeOrderCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.updateThemeOrder(user, command);
        return pollService.themes(poll);
    }
    
    @PutMapping("/questions/{id:\\d+}")
    public ThemesDto updateQuestionOrder(HoisUserDetails user, @WithEntity PollTheme theme, 
            @Valid @RequestBody QuestionOrderCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, theme.getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.updateQuestionOrder(user, command);
        return pollService.themes(theme.getPoll());
    }
    
    /**
     * Used to display themes in separate pages
     * By default themes are not pageable
     * @param user
     * @param poll
     * @param command
     */
    @PutMapping("/themes/pageable/{id:\\d+}")
    public void setThemesPageable(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody ThemePageableCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.setThemesPageable(user, poll, command);
    }
    
    /**
     * Change the is_repetitive field for theme
     * By default is_repetitive is true
     * Used for journal/subject type polls
     * @param user
     * @param pollTheme
     * @param command
     */
    @PutMapping("/themes/repetitive/{id:\\d+}")
    public void setThemeRepetitive(HoisUserDetails user, @WithEntity PollTheme pollTheme, 
            @Valid @RequestBody ThemeRepedetiveCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, pollTheme.getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.setThemeRepetitive(user, pollTheme, command);
    }
    
    /**
     * Create theme
     * @param user
     * @param poll
     * @param themeCommand
     */
    @PostMapping("/theme/{id:\\d+}")
    public void createTheme(HoisUserDetails user, @WithEntity Poll poll,
            @RequestBody ThemeCommand themeCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.createTheme(user, poll, themeCommand);
    }
    
    /**
     * Change theme
     * @param user
     * @param pollTheme
     * @param themeCommand
     */
    @PutMapping("/theme/{id:\\d+}")
    public void updateTheme(HoisUserDetails user, @WithEntity PollTheme pollTheme,
            @RequestBody ThemeCommand themeCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, pollTheme.getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.updateTheme(user, pollTheme, themeCommand);
    }
    
    /**
     * Create question and link it to theme.
     * @param user
     * @param pollTheme
     * @param questionCommand
     */
    @PostMapping("/pollThemeQuestion/{id:\\d+}")
    public void createPollThemeQuestion(HoisUserDetails user, @WithEntity PollTheme pollTheme,
            @RequestBody QuestionCommand questionCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, pollTheme.getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.createPollThemeQuestion(user, pollTheme, questionCommand);
    }
    
    /**
     * Create question without link to theme.
     * @param user
     * @param questionCommand
     */
    @PostMapping("/question")
    public void createQuestion(HoisUserDetails user, @RequestBody QuestionCommand questionCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.createQuestion(user, questionCommand, new Question());
    }
    
    /**
     * Save teacher comment to poll answers.
     * @param user
     * @param poll
     * @param command
     * @return 
     */
    @PutMapping("/comment/{id:\\d+}")
    public SubjectCommentDto saveComment(HoisUserDetails user, @WithEntity Poll poll,
            @RequestBody PollCommentCommand command) {
        UserUtil.isTeacher(user, poll.getSchool());
        return pollService.saveComment(user, poll, command);
    }
    
    /**
     * Used to change question and its answers
     * @param user
     * @param question
     * @param questionDto
     * @return
     */
    @PutMapping("/pollThemeQuestion/{id:\\d+}")
    public void updatePollThemeQuestion(HoisUserDetails user, @WithEntity PollThemeQuestion pollThemeQuestion,
            @RequestBody QuestionCommand themeCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, pollThemeQuestion.getPollTheme().getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.updatePollThemeQuestion(user, pollThemeQuestion, themeCommand);
    }
    
    /**
     * Used to change question and its answers
     * @param user
     * @param question
     * @param questionDto
     * @return
     */
    @PutMapping("/updateQuestion/{id:\\d+}")
    public QuestionDto updateQuestion(HoisUserDetails user, @WithEntity Question question, @RequestBody QuestionDto questionDto) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, question.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.updateQuestion(user, question, questionDto);
    }
    
    /**
     * Save and then confirm poll data
     * @param user
     * @param poll
     * @param command
     */
    @PutMapping("/confirm/{id:\\d+}")
    public void confirm(HoisUserDetails user, @WithEntity Poll poll, @Valid @RequestBody PollCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.save(user, poll, command);
        pollService.confirm(user, poll);
    }
    
    /**
     * Remove confirmation from poll
     * Can only be done, when tables for responding haven't been created
     * Tabled are created when emails for supervisor are sent out or 
     * someone navigates to front page to see list of polls
     * @param user
     * @param poll
     */
    @PutMapping("/unConfirm/{id:\\d+}")
    public void unConfirm(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.unConfirm(user, poll);
    }
    
    /**
     * Create new Poll with PollThemes, PollThemeQuestions,
     * Questions, QuestionAnswers, PollThemeQuestionFiles from existing poll
     * and replace name with 'copy' at the end
     * @param user
     * @param poll
     * @return Poll id
     */
    @PostMapping("/copy/{id:\\d+}")
    public AutocompleteResult copyPoll(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        Poll newPoll = pollService.createCopyOfPoll(user, poll);
        return new AutocompleteResult(newPoll.getId(), null, null);
    }
    
    /**
     * Used for testing mail service
     */
    @PutMapping("/testEmailService")
    public void testEmailService() {
        pollService.sendPracticeJournalSupervisorEmails();
        pollService.sendEmails();
    }
    
    /**
     * Used for testing poll status switching to finished job
     */
    @PutMapping("/testPollStatusJob")
    public void testPollStatusJob() {
        pollService.checkPollValidThru();
    }
    
    /**
     * Used for testing poll status switching to finished job
     */
    @PutMapping("/testDirectiveJobs")
    public void testDirectiveJobs() {
        jobExecutorService.directiveJob();
        jobExecutorService.ehisJob();
    }
    
    /**
     * EXPERT CONTROLLERS
     */
    
    /**
     * Outsider gets to respond to poll
     * @param uuid random string url
     * @return themes
     */
    @GetMapping("/expert/{uuid}")
    public ThemesDto expertGet(@PathVariable String uuid) {
        Poll poll = pollService.getPollFromUrlExpert(uuid);
        pollService.assertCanView(poll);
        return pollService.themesWithAnswersExpert(poll, uuid);
    }
    
    /**
     * Save expert response
     * @param uuid
     * @param form
     */
    @PutMapping("/expert/{responseId:\\d+}/saveAnswer/{id:\\d+}")
    public void saveExpertResponse(@WithEntity PollThemeQuestion pollThemeQuestion, @WithEntity("responseId") Response response, @RequestBody QuestionDto dto) {
        pollService.assertCanView(response.getPoll());
        pollService.saveOtherResponse(pollThemeQuestion, response, dto);
    }
    
    /**
     * Save expert filled poll and change status to KYSITVASTUSSTAATUS_V (Vastatud)
     * @param uuid
     * @param form
     */
    @PutMapping("/expert/{id:\\d+}/saveAnswer/final")
    public void saveExpertResponseFinal(@WithEntity Response response) {
        pollService.assertCanView(response.getPoll());
        pollService.setResponseFinishedExpert(response);
    }
    
    /**
     * SUPERVISOR CONTROLLERS
     */
    
    /**
     * Contract supervisor from practice journal
     * @param uuid random string url
     * @return themes
     */
    @GetMapping("/supervisor/{uuid}")
    public PracticeThemesDto supervisorGet(@PathVariable String uuid) {
        Poll poll = pollService.getPollFromUrlSupervisor(uuid);
        pollService.assertCanView(poll);
        return pollService.themesWithAnswers(poll, uuid);
    }
    
    /**
     * Save supervisor filled poll
     * @param uuid
     * @param practiceJournalEntriesSupervisorForm
     */
    @PutMapping("/supervisor/{responseId:\\d+}/saveAnswer/{id:\\d+}")
    public void saveSupervisorResponse(@WithEntity("responseId") Response response, @RequestBody QuestionDto form,
            @WithEntity PollThemeQuestion question) {
        pollService.assertCanView(response.getPoll());
        pollService.saveOtherResponse(question, response, form);
    }
    
    /**
     * Change status to KYSITVASTUSSTAATUS_V (Vastatud)
     * @param uuid
     * @param practiceJournalEntriesSupervisorForm
     */
    @PutMapping("/supervisor/{id:\\d+}/saveAnswer/final")
    public void saveSupervisorResponseFinal(@WithEntity Response response) {
        pollService.assertCanView(response.getPoll());
        pollService.setResponseFinishedExpert(response);
    }
    
    /**
     * REGULAR POLL REQUESTS FOR ANSWERING
     */
    
    /**
     * Find polls for everyone except head admin and supervisor
     * Poll response regarding user cant be confirmed
     * @param user
     * @return polls
     */
    @GetMapping("/polls")
    public Set<ResponseDto> getPolls(HoisUserDetails user) {
        return pollService.getPolls(user);
    }
    
    /**
     * Get poll with response and responseobject created
     * Used in dialog window to answer poll
     * @param user
     * @param poll
     * @return poll with answers
     */
    @GetMapping("/withAnswers/{id:\\d+}")
    public PracticeThemesDto getPollWithAnswers(HoisUserDetails user, @WithEntity Response response) {
        UserUtil.assertSameSchool(user, response.getPoll().getSchool());
        return pollService.themesForPractice(response.getPoll(), response, Boolean.FALSE);
    }
    
    /**
     * Get poll with response and responseobject created
     * Used in dialog window to answer poll
     * @param user student
     * @param poll
     * @return poll with answers
     */
    @GetMapping("/withAnswers/journalOrSubject/{id:\\d+}")
    public ThemesDto getPollWithAnswersJournalOrSubject(HoisUserDetails user, @WithEntity Response response) {
        UserUtil.assertSameSchool(user, response.getPoll().getSchool());
        return pollService.themesWithAnswersJournalOrSubject(response.getPoll(), response, Boolean.FALSE);
    }
    
    /**
     * Get poll with response and responseobject created for viewing only
     * @param user
     * @param response
     * @return poll with answers
     */
    @GetMapping("/withAnswersForView/{id:\\d+}")
    public PracticeThemesDto getPollWithAnswersForView(HoisUserDetails user, @WithEntity Response response) {
        UserUtil.assertSameSchool(user, response.getPoll().getSchool());
        return pollService.themesForPractice(response.getPoll(), response, Boolean.TRUE);
    }
    
    /**
     * Get poll with response and responseobject created for viewing only
     * @param user student
     * @param response
     * @return poll with answers
     */
    @GetMapping("/withAnswersForView/journalOrSubject/{id:\\d+}")
    public ThemesDto getPollWithAnswersJournalOrSubjectForView(HoisUserDetails user, @WithEntity Response response) {
        UserUtil.assertSameSchool(user, response.getPoll().getSchool());
        return pollService.themesWithAnswersJournalOrSubject(response.getPoll(), response, Boolean.TRUE);
    }
    
    /**
     * Save poll question answer
     * @param question
     * @param response
     * @param form
     */
    @PutMapping("{responseId:\\d+}/saveAnswer/{id:\\d+}")
    public void saveResponse(HoisUserDetails user, @WithEntity PollThemeQuestion question, @WithEntity("responseId") Response response, @RequestBody QuestionDto form) {
        UserUtil.assertSameSchool(user, question.getPollTheme().getPoll().getSchool());
        pollService.saveOtherResponse(question, response, form);
    }
    
    /**
     * Change poll status to KYSITVASTUSSTAATUS_V (Answered)
     * Saving the whole poll again with confirmation is not necessary
     * @param uuid
     * @param form
     */
    @PutMapping("/{id:\\d+}/saveAnswer/final")
    public void saveResponseFinal(HoisUserDetails user, @WithEntity Response response) {
        UserUtil.assertSameSchool(user, response.getPoll().getSchool());
        pollService.setResponseFinished(user, response);
    }
    
    /**
     * Create excel file from key and byte array contained in service
     * @param user
     * @param key
     * @param response
     * @throws IOException
     */
    @GetMapping("/statistics/pollStatistics")
    public void statisticsIntoExcel(HoisUserDetails user, @RequestParam(required = true) String key, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        HttpUtil.xls(response, "pollstatistics.xls", ((PollResultStatisticsDto)pollService.exportExcelStatus(user, key).getResult()).getFile());
        pollService.deleteKey(user, key);
    }
    
    /**
     * Create hash for async polling or excel statistics and start the async function
     * User keeps polling for status with this hash
     * @param user
     * @param command
     * @return
     */
    @PostMapping("/statistics/excelExport")
    public Map<String, Object> excelExport(HoisUserDetails user, @Valid @RequestBody PollResultStatisticsCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        String requestHash = String.format("%d-%d-%d-%d", Integer.valueOf(user.getUsername().hashCode()),
                command.getPollIds() != null ? Integer.valueOf(command.getPollIds().hashCode()) : Integer.valueOf(0),
                command.getQuestions() != null ? Integer.valueOf(command.getQuestions().hashCode()) : Integer.valueOf(0),
                Integer.valueOf(LocalDateTime.now().hashCode()));
        pollService.exportExcel(user, command, requestHash);
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", requestHash);
        return map;
    }

    /**
     * Used for polling export file status with the key that was provided when function started
     * @param user
     * @param key
     * @return
     */
    @GetMapping("/statistics/excelExportStatus")
    public FutureStatusResponse excelExportStatus(HoisUserDetails user, @RequestParam(required = true) String key) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.exportExcelStatus(user, key);
    }
    
    
    
}
