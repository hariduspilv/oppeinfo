package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
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

import ee.hitsa.ois.concurrent.AsyncManager;
import ee.hitsa.ois.concurrent.AsyncMemoryManager;
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
import ee.hitsa.ois.service.poll.PollAsyncService.PollStatisticsRequest;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.poll.GraphSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.PollCommand;
import ee.hitsa.ois.web.commandobject.poll.PollCommentCommand;
import ee.hitsa.ois.web.commandobject.poll.PollDatesCommand;
import ee.hitsa.ois.web.commandobject.poll.PollForm;
import ee.hitsa.ois.web.commandobject.poll.PollResultCommand;
import ee.hitsa.ois.web.commandobject.poll.PollResultStatisticsCommand;
import ee.hitsa.ois.web.commandobject.poll.PollSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemePageableCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeRepedetiveCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import ee.hitsa.ois.web.dto.poll.AllPollResultsDto;
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
    private AsyncManager asyncManager;
    
    /**
    @Autowired
    private JobExecutorService jobExecutorService;
    */
    
    
    /**
     * Search for all polls that match the criteria
     * 
     * @param user
     * @param command
     * @param pageable
     * @return Poll related data
     */
    @GetMapping
    public Page<PollSearchDto> search(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.search(user, command, pageable);
    }
    
    /**
     * Search for polls answered by the user
     * 
     * @param user User who's polls are being searched
     * @param command
     * @param pageable
     * @return Poll related data
     */
    @GetMapping("/answers")
    public Page<AnswersDto> searchAnswers(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        return pollService.searchAnswers(user, command, pageable);
    }
    
    /**
     * Search for subject or journal related polls (polls with type KYSITLUS_O)
     * 
     * @param user Poll related teacher
     * @param command
     * @param pageable
     * @return Subjects or journals of which study period teacher or journal teacher is the user
     */
    @GetMapping("/answers/subjects")
    public Page<SubjectAnswerDto> searchAnswersPerSubject(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        UserUtil.assertIsTeacher(user);
        return pollService.searchAnswersPerSubject(user, command, pageable);
    }
    
    /**
     * Used to see which journals, subjects or other questions are answered
     * Later a choice is made from these journals, subjects or questions and its answers are displayed
     * 
     * @param user Student
     * @param response
     * @return Journals, subjects or other themes
     */
    @GetMapping("/answers/{id:\\d+}")
    public PollRelatedObjectsDto searchAnswersPerResponse(HoisUserDetails user, @WithEntity Response response) {
        UserUtil.assertSameSchool(user, response.getPoll().getSchool());
        return pollService.searchAnswersPerResponse(response);
    }
    
    /**
     * Minor statistics about this poll
     * 
     * @param user
     * @param poll
     * @return All responses, partially responded and maximum amount of responses to this poll
     */
    @GetMapping("/results/{id:\\d+}")
    public PollResultsDto pollResults(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResults(poll);
    }
    
    /**
     * Get Poll themes and its related data
     * Percentages are calculated later
     * Not used in graph
     * 
     * @param user
     * @param poll
     * @return How many times an answer was selected overall and per person
     */
    @GetMapping("/results/all/{id:\\d+}")
    public AllPollResultsDto allPollResults(HoisUserDetails user, @WithEntity Poll poll, PollResultCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.allPollResults(poll, command);
    }
    
    /**
     * Get results per journal/subject
     * 
     * @param user
     * @param poll
     * @param pageable
     * @return list of journals/subjects that have been answered to
     */
    @GetMapping("/results/subjects/{id:\\d+}")
    public Page<PollResultsSubjectDto> pollResultSubjects(HoisUserDetails user, @WithEntity Poll poll, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResultSubjects(poll, pageable);
    }
    
    /**
     * Get results per journal/subject
     * 
     * @param user
     * @param poll
     * @param pageable
     * @return list of journals/subjects that have been answered to
     */
    @GetMapping("/results/journals/{id:\\d+}")
    public Page<PollResultsSubjectDto> pollResultJournals(HoisUserDetails user, @WithEntity Poll poll, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResultJournals(poll, pageable);
    }
    
    /**
     * Get enterprises that are used in a poll with 'KYSITLUS_SIHT_E' target code and have been answered to
     * 
     * @param user
     * @param poll
     * @param pageable
     * @return List of enterprises 
     */
    @GetMapping("/results/enterprises/{id:\\d+}")
    public Page<AutocompleteResult> pollResultEnterprises(HoisUserDetails user, @WithEntity Poll poll, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResultEnterprises(user.getSchoolId(), poll, pageable);
    }
    
    /**
     * Get teacher and student target group statistics separately with practice poll
     * 
     * @param user
     * @param poll
     * @return Max responses and amount of people that answered so far
     */
    @GetMapping("/results/enterprises/studentOrTeacher/{id:\\d+}")
    public PollResultStudentOrTeacherDto pollResultStudentOrTeacher(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResultStudentOrTeacher(user, poll);
    }
    
    /**
     * Get all students who answered to subject/journal related poll
     * 
     * @param user
     * @param command
     * @param pageable
     * @return Page of students
     */
    @GetMapping("/results/student")
    public Page<PollResultStudentDto> pollResultStudent(HoisUserDetails user, PollResultStudentCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollResultStudent(command, pageable);
    }
    
    /**
     * Get all answers to poll in excel, depending on poll target given in criteria
     * 
     * @param user
     * @param criteria
     * @param response
     * @throws IOException
     */
    @GetMapping("/results/exportSubject.xls")
    public void subjectIntoExcel(HoisUserDetails user, PollResultStudentCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        HttpUtil.xls(response, "pollsubjectresult.xls", pollService.searchExcel(criteria, user));
    }
    
    /**
     * Get data necessary to create horizontal-bar
     * 
     * @param user
     * @param command
     * @return Average weight over all answers per question, student council counts answers not average weight
     */
    @PutMapping("/graph")
    public GraphDto searchAnswersPerResponse(HoisUserDetails user, @RequestBody GraphSearchCommand command) {
        // TODO: assert
        return pollService.createGraph(user, command, true);
    }
    
    /**
     * Get data necessary to create horizontal-bar with student answers
     * 
     * @param user
     * @param command
     * @return Average weight over all answers per question 
     * Student council counts answers not average weight
     * Student answer is the answered weight
     */
    @PutMapping("/graphWithoutStudentAnswer")
    public GraphDto searchAnswersPerResponseWithoutStudent(HoisUserDetails user, @RequestBody GraphSearchCommand command) {
        // TODO: assert
        return pollService.createGraph(user, command, false);
    }
    
    /**
     * Used in question search form
     * 
     * @param user
     * @param command
     * @param pageable
     * @return Page of questions
     */
    @GetMapping("/questionsList")
    public Page<QuestionSearchDto> searchQuestions(HoisUserDetails user, QuestionSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.searchQuestions(user, command, pageable);
    }
    
    /**
     * Used to show polls where a question is used
     * 
     * @param user
     * @param question
     * @param pageable
     * @return Polls
     */
    @GetMapping("/questionPolls/{id:\\d+}")
    public Page<QuestionPollSearchDto> questionPolls(HoisUserDetails user, @WithEntity Question question, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, question.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.questionPolls(user, question, pageable);
    }
    
    /**
     * Used in view and edit forms
     * 
     * @param user
     * @param poll
     * @return Poll related data
     */
    @GetMapping("/{id:\\d+}")
    public PollForm get(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.get(poll);
    }
    
    /**
     * Used to show all poll related themes, questions and question answer types
     * 
     * @param user
     * @param poll
     * @return List of themes(questions, answer types)
     */
    @GetMapping("/themes/{id:\\d+}")
    public ThemesDto themes(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.themes(poll, user.getStudentId());
    }
    
    /**
     * Get all poll names to check for duplicates
     * 
     * @param user
     * @return All poll names
     */
    @GetMapping("/pollNames")
    public Set<AutocompleteResult> pollNames(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.pollNames(user);
    }
    
    /**
     * Get all questions of given type to reuse in a poll
     * 
     * @param user
     * @param pollType
     * @return Questions of one type
     */
    @GetMapping("/questions")
    public Set<AutocompleteResult> questions(HoisUserDetails user, @RequestParam(name = "type") String pollType) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.questions(user, pollType);
    }
    
    /**
     * Used to view and edit a question
     * 
     * @param user
     * @param question
     * @return Question related data and answer types(names)
     */
    @GetMapping("/question/{id:\\d+}")
    public QuestionDto question(HoisUserDetails user, @WithEntity Question question) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, question.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.question(question);
    }
    
    /**
     * Manipulating poll
     */
    
    /**
     * Create poll
     * @param user
     * @param practiceEnterpriseForm
     * @return Saved poll
     */
    @PostMapping
    public PollForm create(HoisUserDetails user,
            @Valid @RequestBody PollCommand practiceEnterpriseForm) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.create(user, practiceEnterpriseForm);
    }
    
    /**
     * Update poll
     * 
     * @param user
     * @param poll
     * @param command
     * @return Updated poll
     */
    @PutMapping("/{id:\\d+}")
    public PollForm update(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody PollCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return pollService.save(user, poll, command);
    }
    
    /**
     * Delete poll
     * 
     * @param user
     * @param poll
     */
    @DeleteMapping("/{id:\\d+}")
    public void deletePoll(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.deletePoll(user, poll);
    }
    
    /**
     * Changes poll valid from, valid thru or reminder date
     * User for polls that are confirmed and have tables for answering created
     * 
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
    
    /**
     * Delete theme and set theme orders right (so numbers aren't skipped)
     * 
     * @param user
     * @param pollTheme
     */
    @DeleteMapping("/theme/{id:\\d+}")
    public void deleteTheme(HoisUserDetails user, @WithEntity PollTheme pollTheme) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, pollTheme.getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.deleteTheme(user, pollTheme);
        pollService.updateThemeOrderAfterDelete(user, pollTheme.getPoll());
    }
    
    /**
     * Delete question (actually deletes question order, question remains to be reused)
     * 
     * @param user
     * @param pollThemeQuestion
     */
    @DeleteMapping("/pollThemeQuestion/{id:\\d+}")
    public void deletePollThemeQuestion(HoisUserDetails user, @WithEntity PollThemeQuestion pollThemeQuestion) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, pollThemeQuestion.getPollTheme().getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.deleteQuestion(user, pollThemeQuestion);
        pollService.updateQuestionOrderAfterDelete(user, pollThemeQuestion.getPollTheme());
    }
    
    /**
     * Update the order of all themes
     * 
     * @param user
     * @param poll
     * @param command
     * @return Updated themes
     */
    @PutMapping("/themes/{id:\\d+}")
    public ThemesDto updateThemeOrder(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody ThemeOrderCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.updateThemeOrder(user, command);
        return pollService.themes(poll, null);
    }
    
    /**
     * Update the order of all questions
     * 
     * @param user
     * @param theme
     * @param command
     * @return Updated questions
     */
    @PutMapping("/questions/{id:\\d+}")
    public ThemesDto updateQuestionOrder(HoisUserDetails user, @WithEntity PollTheme theme, 
            @Valid @RequestBody QuestionOrderCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, theme.getPoll().getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        pollService.updateQuestionOrder(user, command);
        return pollService.themes(theme.getPoll(), null);
    }
    
    /**
     * Used to display themes in separate pages
     * By default themes are not pageable
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
     * @param user
     * @param poll
     * @return Created poll id
     */
    @PostMapping("/copy/{id:\\d+}")
    public AutocompleteResult copyPoll(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, poll.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KYSITLUS);
        Poll newPoll = pollService.createCopyOfPoll(user, poll);
        return new AutocompleteResult(newPoll.getId(), null, null);
    }
    
    /**
    @PutMapping("/testEmailService")
    public void testEmailService() {
        jobExecutorService.sendPollReminder();
    }
    
    
    @PutMapping("/testPollStatusJob")
    public void testPollStatusJob() {
        pollService.checkPollValidThru();
    }
    
    
    @PutMapping("/testDirectiveJobs")
    public void testDirectiveJobs() {
        jobExecutorService.directiveJob();
        jobExecutorService.ehisJob();
    }
    */
    
    /**
     * EXPERT CONTROLLERS
     */
    
    /**
     * Outer expert gets to respond to poll
     * 
     * @param uuid Random string url
     * @return Poll data necessary to answer to it
     */
    @GetMapping("/expert/{uuid}")
    public ThemesDto expertGet(@PathVariable String uuid) {
        Poll poll = pollService.getPollFromUrlExpert(uuid);
        pollService.assertCanView(poll);
        return pollService.themesWithAnswersExpert(poll, uuid);
    }
    
    /**
     * Save outer expert response
     * 
     * @param pollThemeQuestion
     * @param response
     * @param dto
     */
    @PutMapping("/expert/{responseId:\\d+}/saveAnswer/{id:\\d+}")
    public void saveExpertResponse(@WithEntity PollThemeQuestion pollThemeQuestion, @WithEntity("responseId") Response response, @RequestBody QuestionDto dto) {
        pollService.assertCanView(response.getPoll());
        pollService.saveOtherResponse(pollThemeQuestion, response, dto);
    }
    
    /**
     * Save expert filled poll and change status to KYSITVASTUSSTAATUS_V (Vastatud)
     * 
     * @param response
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
     * For contract supervisor to be able to answer to poll
     * 
     * @param uuid Random string identifier (response_object poll_url)
     * @return themes
     */
    @GetMapping("/supervisor/{uuid}")
    public PracticeThemesDto supervisorGet(@PathVariable String uuid) {
        Poll poll = pollService.getPollFromUrlSupervisor(uuid);
        pollService.assertCanView(poll);
        return pollService.themesWithAnswers(poll, uuid);
    }
    
    /**
     * Save contract supervisor filled poll
     * 
     * @param response
     * @param form
     * @param question
     */
    @PutMapping("/supervisor/{responseId:\\d+}/saveAnswer/{id:\\d+}")
    public void saveSupervisorResponse(@WithEntity("responseId") Response response, @RequestBody QuestionDto form,
            @WithEntity PollThemeQuestion question) {
        pollService.assertCanView(response.getPoll());
        pollService.saveOtherResponse(question, response, form);
    }
    
    /**
     * Contract supervisor confirms poll (status changed to KYSITVASTUSSTAATUS_V (Vastatud))
     * 
     * @param response
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
     * 
     * @param user
     * @return polls
     */
    @GetMapping("/polls")
    public LinkedHashSet<ResponseDto> getPolls(HoisUserDetails user) {
        return pollService.getPolls(user);
    }
    
    /**
     * Get poll with response and responseobject created
     * Used in dialog window to answer poll
     * 
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
     * 
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
     * 
     * @param user
     * @param response
     * @return Poll with answers
     */
    @GetMapping("/withAnswersForView/{id:\\d+}")
    public PracticeThemesDto getPollWithAnswersForView(HoisUserDetails user, @WithEntity Response response) {
        UserUtil.assertSameSchool(user, response.getPoll().getSchool());
        return pollService.themesForPractice(response.getPoll(), response, Boolean.TRUE);
    }
    
    /**
     * Get poll with response and responseobject created for viewing only
     * 
     * @param user student
     * @param response
     * @return Poll with answers
     */
    @GetMapping("/withAnswersForView/journalOrSubject/{id:\\d+}")
    public ThemesDto getPollWithAnswersJournalOrSubjectForView(HoisUserDetails user, @WithEntity Response response) {
        UserUtil.assertSameSchool(user, response.getPoll().getSchool());
        return pollService.themesWithAnswersJournalOrSubject(response.getPoll(), response, Boolean.TRUE);
    }
    
    /**
     * Save poll question answer
     * 
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
     * 
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
     * 
     * @param user
     * @param key
     * @param response
     * @throws IOException
     */
    @GetMapping("/statistics/pollStatistics")
    public void statisticsIntoExcel(HoisUserDetails user, @RequestParam(required = true) String key, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        PollResultStatisticsDto state = (PollResultStatisticsDto) asyncManager.getState(user, AsyncMemoryManager.POLL, key, true).getResult();
        HttpUtil.xls(response, "pollstatistics.xls", state.getFile());
    }
    
    /**
     * Create hash for async polling or excel statistics and start the async function
     * User keeps polling for status with this hash
     * 
     * @param user
     * @param command
     * @return Key where the file locates
     */
    @PostMapping("/statistics/excelExport")
    public Map<String, Object> excelExport(HoisUserDetails user, @Valid @RequestBody PollResultStatisticsCommand command) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        String key = asyncManager.generateKey(user);
        PollStatisticsRequest request = pollService.createRequest(user, command, key);
        asyncManager.createRequest(user, AsyncMemoryManager.POLL, key, request);
        asyncManager.processRequest(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", key);
        return map;
    }

    /**
     * Used for polling export file status with the key that was provided when function started
     * 
     * @param user
     * @param key
     * @return Excel file
     */
    @GetMapping("/statistics/excelExportStatus")
    public FutureStatusResponse excelExportStatus(HoisUserDetails user, @RequestParam(required = true) String key) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KYSITLUS);
        return asyncManager.getState(user, AsyncMemoryManager.POLL, key, false);
    }
    
    
    
}
