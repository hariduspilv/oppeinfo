package ee.hitsa.ois.web;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.poll.Poll;
import ee.hitsa.ois.domain.poll.PollResultStudentCommand;
import ee.hitsa.ois.domain.poll.PollTheme;
import ee.hitsa.ois.domain.poll.PollThemeQuestion;
import ee.hitsa.ois.domain.poll.Question;
import ee.hitsa.ois.domain.poll.Response;
import ee.hitsa.ois.service.PollService;
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
import ee.hitsa.ois.web.dto.poll.AnswersDto;
import ee.hitsa.ois.web.dto.poll.GraphDto;
import ee.hitsa.ois.web.dto.poll.PollRelatedObjectsDto;
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
import ee.hitsa.ois.web.dto.poll.ThemesDto;

@RestController
@RequestMapping("/poll")
public class PollController {
    
    @Autowired
    private PollService pollService;
    
    /**
     * Searches
     */

    @GetMapping
    public Page<PollSearchDto> search(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
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
        // TODO: assert
        return pollService.searchAnswersPerResponse(response);
    }
    
    @GetMapping("/results/{id:\\d+}")
    public PollResultsDto pollResults(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.pollResults(poll);
    }
    
    @GetMapping("/results/subjects/{id:\\d+}")
    public Page<PollResultsSubjectDto> pollResultSubjects(HoisUserDetails user, @WithEntity Poll poll, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.pollResultSubjects(user, poll, pageable);
    }
    
    @GetMapping("/results/enterprises/{id:\\d+}")
    public Page<AutocompleteResult> pollResultEnterprises(HoisUserDetails user, @WithEntity Poll poll, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.pollResultEnterprises(poll, pageable);
    }
    
    @GetMapping("/results/enterprises/studentOrTeacher/{id:\\d+}")
    public PollResultStudentOrTeacherDto pollResultStudentOrTeacher(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.pollResultStudentOrTeacher(user, poll);
    }
    
    @GetMapping("/results/student")
    public Page<PollResultStudentDto> pollResultStudent(HoisUserDetails user, PollResultStudentCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.pollResultStudent(command, pageable);
    }
    
    @GetMapping("/results/exportSubject.xls")
    public void subjectIntoExcel(HoisUserDetails user, PollResultStudentCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        HttpUtil.xls(response, "pollsubjectresult.xls", pollService.searchExcel(criteria));
    }
    
    @GetMapping("/statistics/pollStatistics.xlsx")
    public void subjectIntoExcel(HoisUserDetails user, PollResultStatisticsCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.exportStatistics(user, response, criteria);
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
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.searchQuestions(user, command, pageable);
    }
    
    @GetMapping("/questionPolls/{id:\\d+}")
    public Page<QuestionPollSearchDto> questionPolls(HoisUserDetails user, @WithEntity Question question, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.questionPolls(user, question, pageable);
    }
    
    /**
     * Manipulating poll
     */
    
    @PostMapping
    public PollForm create(HoisUserDetails user,
            @Valid @RequestBody PollCommand practiceEnterpriseForm) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.create(user, practiceEnterpriseForm);
    }
    
    @PutMapping("/{id:\\d+}")
    public PollForm update(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody PollCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.save(user, poll, command);
    }
    
    @GetMapping("/{id:\\d+}")
    public PollForm get(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.get(poll);
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void deletePoll(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.deletePoll(user, poll);
    }
    
    @PutMapping("/changeDates/{id:\\d+}")
    public void updatePollDates(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody PollDatesCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updatePollDates(user, poll, command);
    }
    
    @DeleteMapping("/theme/{id:\\d+}")
    public void deleteTheme(HoisUserDetails user, @WithEntity PollTheme pollTheme) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.deleteTheme(user, pollTheme);
        pollService.updateThemeOrderAfterDelete(user, pollTheme.getPoll());
    }
    
    @DeleteMapping("/question/{id:\\d+}")
    public void deleteQuestion(HoisUserDetails user, @WithEntity PollThemeQuestion pollThemeQuestion) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.deleteQuestion(user, pollThemeQuestion);
        pollService.updateQuestionOrderAfterDelete(user, pollThemeQuestion.getPollTheme());
    }
    
    @PutMapping("/themes/{id:\\d+}")
    public ThemesDto updateThemeOrder(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody ThemeOrderCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updateThemeOrder(user, command);
        return pollService.themes(poll);
    }
    
    @PutMapping("/questions/{id:\\d+}")
    public ThemesDto updateQuestionOrder(HoisUserDetails user, @WithEntity PollTheme theme, 
            @Valid @RequestBody QuestionOrderCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updateQuestionOrder(user, command);
        return pollService.themes(theme.getPoll());
    }
    
    @PutMapping("/themes/pageable/{id:\\d+}")
    public void setThemesPageable(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody ThemePageableCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.setThemesPageable(user, poll, command);
    }
    
    @PutMapping("/themes/repetitive/{id:\\d+}")
    public void setThemeRepetitive(HoisUserDetails user, @WithEntity PollTheme pollTheme, 
            @Valid @RequestBody ThemeRepedetiveCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.setThemeRepetitive(user, pollTheme, command);
    }
    
    @PostMapping("/theme/{id:\\d+}")
    public void createTheme(HoisUserDetails user, @WithEntity Poll poll,
            @RequestBody ThemeCommand themeCommand) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.createTheme(user, poll, themeCommand);
    }
    
    
    @PutMapping("/theme/{id:\\d+}")
    public void updateTheme(HoisUserDetails user, @WithEntity PollTheme pollTheme,
            @RequestBody ThemeCommand themeCommand) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updateTheme(user, pollTheme, themeCommand);
    }
    
    @GetMapping("/themes/{id:\\d+}")
    public ThemesDto themes(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.themes(poll);
    }
    
    @PostMapping("/question/{id:\\d+}")
    public void createQuestion(HoisUserDetails user, @WithEntity PollTheme pollTheme,
            @RequestBody QuestionCommand questionCommand) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.createQuestion(user, pollTheme, questionCommand);
    }
    
    @PutMapping("/comment/{id:\\d+}")
    public void saveComment(HoisUserDetails user, @WithEntity Poll poll,
            @RequestBody PollCommentCommand command) {
        UserUtil.assertIsTeacher(user);
        pollService.saveComment(user, poll, command);
    }
    
    @PutMapping("/question/{id:\\d+}")
    public void updateQuestion(HoisUserDetails user, @WithEntity PollThemeQuestion pollThemeQuestion,
            @RequestBody QuestionCommand themeCommand) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updateQuestion(user, pollThemeQuestion, themeCommand);
    }
    
    @PutMapping("/updateQuestion/{id:\\d+}")
    public QuestionDto updateQuestion(HoisUserDetails user, @WithEntity Question question, @RequestBody QuestionDto questionDto) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.updateQuestion(user, question, questionDto);
    }
    
    @PutMapping("/confirm/{id:\\d+}")
    public void confirm(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.confirm(user, poll);
    }
    
    @PutMapping("/unConfirm/{id:\\d+}")
    public void unConfirm(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.unConfirm(user, poll);
    }
    
    @GetMapping("/pollNames")
    public Set<AutocompleteResult> pollNames(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.pollNames(user);
    }
    
    @GetMapping("/questions")
    public Set<AutocompleteResult> questions(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.questions(user);
    }
    
    @GetMapping("/question/{id:\\d+}")
    public QuestionDto question(HoisUserDetails user, @WithEntity Question question) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.question(question);
    }
    
    /**
     * Create new Poll with PollThemes, PollThemeQuestions,
     * Questions, QuestionAnswers, PollThemeQuestionFiles from existing poll
     * and replace name with 'copy' at the end.
     * @param user
     * @param poll
     * @return Poll id
     */
    @PostMapping("/copy/{id:\\d+}")
    public AutocompleteResult copyPoll(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdmin(user);
        Poll newPoll = pollService.createCopyOfPoll(user, poll);
        return new AutocompleteResult(newPoll.getId(), null, null);
    }
    
    /**
     * Used for testing mail service.
     */
    @PutMapping("/testEmailService")
    public void testEmailService() {
        pollService.sendPracticeJournalSupervisorEmails();
        pollService.sendEmails();
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
     * Save expert filled poll and change status to KYSITVASTUSSTAATUS_V (Vastatud).
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
     * Contract supervisor from practice journal.
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
     * Save supervisor filled poll.
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
     * Change status to KYSITVASTUSSTAATUS_V (Vastatud).
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
    public void saveResponse(@WithEntity PollThemeQuestion question, @WithEntity("responseId") Response response, @RequestBody QuestionDto form) {
        pollService.saveOtherResponse(question, response, form);
    }
    
    /**
     * Change poll status to KYSITVASTUSSTAATUS_V (Vastatud).
     * Saving the whole poll again with confirmation is not necessary.
     * @param uuid
     * @param form
     */
    @PutMapping("/{id:\\d+}/saveAnswer/final")
    public void saveResponseFinal(HoisUserDetails user, @WithEntity Response response) {
        pollService.setResponseFinished(user, response);
    }
    
}
