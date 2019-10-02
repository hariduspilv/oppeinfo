package ee.hitsa.ois.web.dto.poll.xls;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollResponseLegendXlsDto {
    
    private String code;
    private AutocompleteResult name;
    private List<PollQuestionXlsDto> questions = new ArrayList<>();
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public AutocompleteResult getName() {
        return name;
    }
    public void setName(AutocompleteResult name) {
        this.name = name;
    }
    public List<PollQuestionXlsDto> getQuestions() {
        return questions;
    }
    public void setQuestions(List<PollQuestionXlsDto> questions) {
        this.questions = questions;
    }
    

}
