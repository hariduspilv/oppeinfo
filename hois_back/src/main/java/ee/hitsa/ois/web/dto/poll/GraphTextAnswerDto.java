package ee.hitsa.ois.web.dto.poll;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class GraphTextAnswerDto {
    
    private AutocompleteResult question;
    private List<String> textAnswers = new ArrayList<>();
    
    public AutocompleteResult getQuestion() {
        return question;
    }
    public void setQuestion(AutocompleteResult question) {
        this.question = question;
    }
    public List<String> getTextAnswers() {
        return textAnswers;
    }
    public void setTextAnswers(List<String> textAnswers) {
        this.textAnswers = textAnswers;
    }
}
