package ee.hitsa.ois.message;

public class PollReminderMessage {
    
    public PollReminderMessage() {
        this.pollName = null;
    }

    public PollReminderMessage(String name) {
        this.pollName = name;
    }
    private final String pollName;
    
    public String getKysitlusNimi() {
        return pollName;
    }
}
