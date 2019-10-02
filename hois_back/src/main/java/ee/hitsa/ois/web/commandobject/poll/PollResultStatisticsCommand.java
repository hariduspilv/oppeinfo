package ee.hitsa.ois.web.commandobject.poll;

import java.util.List;

public class PollResultStatisticsCommand {
    
    private List<Long> pollIds;
    private List<Long> questions;
    
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

}
