package ee.hitsa.ois.web.dto.poll.xls;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollResponseXlsDto {
    private List<AutocompleteResult> basicData = new ArrayList<>();
    private List<Object> questionAnswer = new ArrayList<>();
    
    public List<Object> getQuestionAnswer() {
        return questionAnswer;
    }
    public void setQuestionAnswer(List<Object> questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
    public List<AutocompleteResult> getBasicData() {
        return basicData;
    }
    public void setBasicData(List<AutocompleteResult> basicData) {
        this.basicData = basicData;
    }
    
}
