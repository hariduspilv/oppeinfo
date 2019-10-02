package ee.hitsa.ois.web.dto.poll;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollResultsQuestionDto {
    
    private List<Long> polls;
    private AutocompleteResult question;
    
    public List<Long> getPolls() {
        return polls;
    }
    public void setPolls(List<Long> polls) {
        this.polls = polls;
    }
    public AutocompleteResult getQuestion() {
        return question;
    }
    public void setQuestion(AutocompleteResult question) {
        this.question = question;
    }

}
