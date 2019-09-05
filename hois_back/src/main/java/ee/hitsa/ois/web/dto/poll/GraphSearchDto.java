package ee.hitsa.ois.web.dto.poll;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class GraphSearchDto {
    
    private AutocompleteResult theme;
    private AutocompleteResult question;
    private AutocompleteResult questionAnswer;
    private List<Long> responseIds;
    private Long answerNr;
    private Long answers;
    private Long minNr;
    private Long maxNr;
    
    public AutocompleteResult getTheme() {
        return theme;
    }
    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }
    public AutocompleteResult getQuestion() {
        return question;
    }
    public void setQuestion(AutocompleteResult question) {
        this.question = question;
    }
    public Long getMinNr() {
        return minNr;
    }
    public void setMinNr(Long minNr) {
        this.minNr = minNr;
    }
    public Long getMaxNr() {
        return maxNr;
    }
    public void setMaxNr(Long maxNr) {
        this.maxNr = maxNr;
    }
    public AutocompleteResult getQuestionAnswer() {
        return questionAnswer;
    }
    public void setQuestionAnswer(AutocompleteResult questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
    public Long getAnswerNr() {
        return answerNr;
    }
    public void setAnswerNr(Long answerNr) {
        this.answerNr = answerNr;
    }
    public Long getAnswers() {
        return answers;
    }
    public void setAnswers(Long answers) {
        this.answers = answers;
    }
    public List<Long> getResponseIds() {
        return responseIds;
    }
    public void setResponseIds(List<Long> responseIds) {
        this.responseIds = responseIds;
    }
}
