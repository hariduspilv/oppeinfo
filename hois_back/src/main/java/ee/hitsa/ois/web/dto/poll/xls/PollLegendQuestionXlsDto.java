package ee.hitsa.ois.web.dto.poll.xls;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollLegendQuestionXlsDto {
    
    private String code;
    private AutocompleteResult name;
    private String type;
    private String answers;
    
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAnswers() {
        return answers;
    }
    public void setAnswers(String answers) {
        this.answers = answers;
    }

}
