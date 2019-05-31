package ee.hitsa.ois.web;

import java.util.Set;

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
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.poll.Poll;
import ee.hitsa.ois.domain.poll.PollTheme;
import ee.hitsa.ois.domain.poll.PollThemeQuestion;
import ee.hitsa.ois.domain.poll.Question;
import ee.hitsa.ois.service.PollService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.poll.PollCommand;
import ee.hitsa.ois.web.commandobject.poll.PollForm;
import ee.hitsa.ois.web.commandobject.poll.PollSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.poll.PollSearchDto;
import ee.hitsa.ois.web.dto.poll.QuestionDto;
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
        return pollService.question(user, question);
    }
}
