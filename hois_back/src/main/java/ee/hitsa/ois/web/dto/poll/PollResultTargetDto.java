package ee.hitsa.ois.web.dto.poll;

public class PollResultTargetDto {
    
    private Integer currentResponses;
    private Integer maxResponses;
    
    public Integer getCurrentResponses() {
        return currentResponses;
    }
    public void setCurrentResponses(Integer currentResponses) {
        this.currentResponses = currentResponses;
    }
    public Integer getMaxResponses() {
        return maxResponses;
    }
    public void setMaxResponses(Integer maxResponses) {
        this.maxResponses = maxResponses;
    }

}
