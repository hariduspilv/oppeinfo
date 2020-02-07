package ee.hitsa.ois.web.dto.poll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PollQuestionResultDto {
    
    private AutocompleteWithOrder question;
    private BigDecimal average;
    private String type;
    private List<PollAnswerResultDto> answers = new ArrayList<>();
    private Long allAnswers = Long.valueOf(0L);
    private Long individualAnswers = Long.valueOf(0);
    
    public AutocompleteWithOrder getQuestion() {
        return question;
    }
    public void setQuestion(AutocompleteWithOrder question) {
        this.question = question;
    }
    public BigDecimal getAverage() {
        return average;
    }
    public void setAverage(BigDecimal average) {
        this.average = average;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<PollAnswerResultDto> getAnswers() {
        return answers;
    }
    public void setAnswers(List<PollAnswerResultDto> answers) {
        this.answers = answers;
    }
    public Long getAllAnswers() {
        return allAnswers;
    }
    public void setAllAnswers(Long allAnswers) {
        this.allAnswers = allAnswers;
    }
    public Long getIndividualAnswers() {
        return individualAnswers;
    }
    public void setIndividualAnswers(Long individualAnswers) {
        this.individualAnswers = individualAnswers;
    }
}
