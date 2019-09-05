package ee.hitsa.ois.web.dto.poll;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class QuestionSearchDto {

    private AutocompleteResult name;
    private String type;
    private Long polls;
    
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
    public Long getPolls() {
        return polls;
    }
    public void setPolls(Long polls) {
        this.polls = polls;
    }

}
