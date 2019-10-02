package ee.hitsa.ois.web.dto.poll;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollResultsDto {
    
    private String type;
    private AutocompleteResult poll;
    private Long allResponses;
    private Long partialResponses;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getPartialResponses() {
        return partialResponses;
    }
    public void setPartialResponses(Long partialResponses) {
        this.partialResponses = partialResponses;
    }
    public Long getAllResponses() {
        return allResponses;
    }
    public void setAllResponses(Long allResponses) {
        this.allResponses = allResponses;
    }
    public AutocompleteResult getPoll() {
        return poll;
    }
    public void setPoll(AutocompleteResult poll) {
        this.poll = poll;
    }

}
