package ee.hitsa.ois.web.dto.poll;

import java.util.ArrayList;
import java.util.List;

public class PollThemeResultDto {
    
    private AutocompleteWithOrder theme;
    private List<AutocompleteSubjectOrJournal> journal = new ArrayList<>();
    private List<AutocompleteSubjectOrJournal> subject = new ArrayList<>();
    private List<PollQuestionResultDto> questions = new ArrayList<>();
    
    public AutocompleteWithOrder getTheme() {
        return theme;
    }
    public void setTheme(AutocompleteWithOrder theme) {
        this.theme = theme;
    }
    public List<PollQuestionResultDto> getQuestions() {
        return questions;
    }
    public void setQuestions(List<PollQuestionResultDto> questions) {
        this.questions = questions;
    }
    public List<AutocompleteSubjectOrJournal> getJournal() {
        return journal;
    }
    public void setJournal(List<AutocompleteSubjectOrJournal> journal) {
        this.journal = journal;
    }
    public List<AutocompleteSubjectOrJournal> getSubject() {
        return subject;
    }
    public void setSubject(List<AutocompleteSubjectOrJournal> subject) {
        this.subject = subject;
    }

}
