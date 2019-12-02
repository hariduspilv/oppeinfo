package ee.hitsa.ois.web.commandobject.poll;

import java.util.List;

public class PollResultStatisticsCommand {
    
    private String key;
    private List<Long> pollIds;
    private List<Long> questions;
    private String message;
    
    public List<Long> getPollIds() {
        return pollIds;
    }
    public void setPollIds(List<Long> pollIds) {
        this.pollIds = pollIds;
    }
    public List<Long> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Long> questions) {
        this.questions = questions;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
