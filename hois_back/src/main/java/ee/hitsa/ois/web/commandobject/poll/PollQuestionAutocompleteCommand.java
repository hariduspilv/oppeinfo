package ee.hitsa.ois.web.commandobject.poll;

import java.util.List;

public class PollQuestionAutocompleteCommand {
    
    private List<Long> polls;

    public List<Long> getPolls() {
        return polls;
    }

    public void setPolls(List<Long> polls) {
        this.polls = polls;
    }

}
