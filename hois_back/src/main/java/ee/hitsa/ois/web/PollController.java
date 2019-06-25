package ee.hitsa.ois.web;

import java.util.Set;

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
import ee.hitsa.ois.domain.poll.PollTheme;
import ee.hitsa.ois.domain.poll.PollThemeQuestion;
import ee.hitsa.ois.domain.poll.Question;
import ee.hitsa.ois.domain.poll.Response;
import ee.hitsa.ois.service.PollService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.poll.PollCommand;
import ee.hitsa.ois.web.commandobject.poll.PollDatesCommand;
import ee.hitsa.ois.web.commandobject.poll.PollForm;
import ee.hitsa.ois.web.commandobject.poll.PollSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemePageableCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeRepedetiveCommand;
import ee.hitsa.ois.web.commandobject.poll.ResponseForm;
import ee.hitsa.ois.web.commandobject.poll.ThemeOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.poll.PollSearchDto;
import ee.hitsa.ois.web.dto.poll.QuestionDto;
import ee.hitsa.ois.web.dto.poll.ResponseDto;
import ee.hitsa.ois.web.dto.poll.ThemesDto;

@RestController
@RequestMapping("/poll")
public class PollController {
    
    @Autowired
    private PollService pollService;

    @GetMapping
    public Page<PollSearchDto> search(HoisUserDetails user, PollSearchCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.search(user, command, pageable);
    }
    
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
    public void updateQuestionsPageable(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody PollDatesCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updatePollDates(user, poll, command);
    }
    
    @DeleteMapping("/theme/{id:\\d+}")
    public void deleteTheme(HoisUserDetails user, @WithEntity PollTheme pollTheme) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.deleteTheme(user, pollTheme);
        pollService.updateThemeAfterDelete(user, pollTheme.getPoll());
    }
    
    @DeleteMapping("/question/{id:\\d+}")
    public void deleteTheme(HoisUserDetails user, @WithEntity PollThemeQuestion pollThemeQuestion) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.deleteQuestion(user, pollThemeQuestion);
        pollService.updateQuestionAfterDelete(user, pollThemeQuestion.getPollTheme());
    }
    
    @PutMapping("/themes/{id:\\d+}")
    public ThemesDto updateThemes(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody ThemeOrderCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updateThemeOrder(user, command);
        return pollService.themes(poll);
    }
    
    @PutMapping("/questions/{id:\\d+}")
    public ThemesDto updateQuestions(HoisUserDetails user, @WithEntity PollTheme theme, 
            @Valid @RequestBody QuestionOrderCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updateQuestionOrder(user, command);
        return pollService.themes(theme.getPoll());
    }
    
    @PutMapping("/themes/pageable/{id:\\d+}")
    public void updateThemesPageable(HoisUserDetails user, @WithEntity Poll poll, 
            @Valid @RequestBody ThemePageableCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updateThemesPageable(user, poll, command);
    }
    
    @PutMapping("/themes/repetitive/{id:\\d+}")
    public void updateThemePageable(HoisUserDetails user, @WithEntity PollTheme pollTheme, 
            @Valid @RequestBody ThemeRepedetiveCommand command) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updateThemeRepetitive(user, pollTheme, command);
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
    public ThemesDto questions(HoisUserDetails user, @WithEntity Poll poll) {
        UserUtil.assertIsSchoolAdmin(user);
        return pollService.themes(poll);
    }
    
    @PostMapping("/question/{id:\\d+}")
    public void createQuestion(HoisUserDetails user, @WithEntity PollTheme pollTheme,
            @RequestBody QuestionCommand questionCommand) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.createQuestion(user, pollTheme, questionCommand);
    }
    
    @PutMapping("/question/{id:\\d+}")
    public void updateQuestion(HoisUserDetails user, @WithEntity PollThemeQuestion pollThemeQuestion,
            @RequestBody QuestionCommand themeCommand) {
        UserUtil.assertIsSchoolAdmin(user);
        pollService.updateQuestion(user, pollThemeQuestion, themeCommand);
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
    }
    
    /**
     * Contract supervisor from practice journal.
     * @param uuid random string url
     * @return themes
     */
    @GetMapping("/supervisor/{uuid}")
    public ThemesDto supervisorGet(@PathVariable String uuid) {
        Poll poll = pollService.getPollFromUrl(uuid);
        pollService.assertSupervisorView(poll);
        return pollService.themesWithAnswers(poll, uuid);
    }
    
    /**
     * Save supervisor filled poll.
     * @param uuid
     * @param practiceJournalEntriesSupervisorForm
     */
    @PutMapping("/supervisor/{uuid}/saveAnswer")
    public void saveSupervisorResponse(@PathVariable String uuid,
            @RequestBody ResponseForm form) {
        Poll poll = pollService.getPollFromUrl(uuid);
        pollService.assertSupervisorView(poll);
        pollService.saveSupervisorResponse(poll, form, uuid, Boolean.FALSE);
    }
    
    /**
     * Save supervisor filled poll and change status to KYSITVASTUSSTAATUS_V (Vastatud).
     * @param uuid
     * @param practiceJournalEntriesSupervisorForm
     */
    @PutMapping("/supervisor/{uuid}/saveAnswer/final")
    public void saveSupervisorResponseFinal(@PathVariable String uuid,
            @RequestBody ResponseForm form) {
        Poll poll = pollService.getPollFromUrl(uuid);
        pollService.assertSupervisorView(poll);
        pollService.saveSupervisorResponse(poll, form, uuid, Boolean.TRUE);
    }
    
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
    public ThemesDto getPollWithAnswers(HoisUserDetails user, @WithEntity Response response) {
        return pollService.getPollWithAnswers(response);
    }
    
    /**
     * Save filled poll
     * @param user
     * @param poll
     * @param form
     */
    @PutMapping("/{id:\\d+}/saveAnswer")
    public void saveResponse(@WithEntity Response response, @RequestBody ResponseForm form) {
        pollService.saveOtherResponse(response, form, Boolean.FALSE);
    }
    
    /**
     * Save filled poll and change status to KYSITVASTUSSTAATUS_V (Vastatud).
     * @param uuid
     * @param form
     */
    @PutMapping("/{id:\\d+}/saveAnswer/final")
    public void saveResponseFinal(@WithEntity Response response, @RequestBody ResponseForm form) {
        pollService.saveOtherResponse(response, form, Boolean.TRUE);
    }
    
}
