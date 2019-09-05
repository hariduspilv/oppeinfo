package ee.hitsa.ois.web.dto.poll;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class QuestionResponsePairDto {
    private AutocompleteResult answer;
    private Long answers;
    private Long answerNr;
    
    public AutocompleteResult getAnswer() {
        return answer;
    }
    public void setAnswer(AutocompleteResult answer) {
        this.answer = answer;
    }
    public Long getAnswers() {
        return answers;
    }
    public void setAnswers(Long answers) {
        this.answers = answers;
    }
    public Long getAnswerNr() {
        return answerNr;
    }
    public void setAnswerNr(Long answerNr) {
        this.answerNr = answerNr;
    }
}
