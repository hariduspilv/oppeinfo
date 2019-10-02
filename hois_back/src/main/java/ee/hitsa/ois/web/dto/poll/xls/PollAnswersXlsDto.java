package ee.hitsa.ois.web.dto.poll.xls;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollAnswersXlsDto {
    
    private List<AutocompleteResult> basicData = new ArrayList<>();
    private List<PollAnswerXlsDto> answers = new ArrayList<>();
    
    public List<PollAnswerXlsDto> getAnswers() {
        return answers;
    }
    public void setAnswers(List<PollAnswerXlsDto> answers) {
        this.answers = answers;
    }
    public List<AutocompleteResult> getBasicData() {
        return basicData;
    }
    public void setBasicData(List<AutocompleteResult> basicData) {
        this.basicData = basicData;
    }

}
