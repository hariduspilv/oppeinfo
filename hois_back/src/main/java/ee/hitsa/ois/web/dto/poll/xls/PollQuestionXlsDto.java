package ee.hitsa.ois.web.dto.poll.xls;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollQuestionXlsDto {
    
    private AutocompleteResult name;
    private String code;
    private String type;
    private List<QuestionAnswerXlsDto> answers = new ArrayList<>();
    private List<String> questionAnswers = new ArrayList<>();
    private Boolean hasAnswers = Boolean.TRUE;
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<QuestionAnswerXlsDto> getAnswers() {
        return answers;
    }
    public void setAnswers(List<QuestionAnswerXlsDto> answers) {
        this.answers = answers;
    }
    public AutocompleteResult getName() {
        return name;
    }
    public void setName(AutocompleteResult name) {
        this.name = name;
    }
    public List<String> getQuestionAnswers() {
        return questionAnswers;
    }
    public void setQuestionAnswers(List<String> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
    public Boolean getHasAnswers() {
        return hasAnswers;
    }
    public void setHasAnswers(Boolean hasAnswers) {
        this.hasAnswers = hasAnswers;
    }

}
