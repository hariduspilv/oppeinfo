package ee.hitsa.ois.web.commandobject.poll;

public class QuestionSearchCommand {
    
    private Boolean pollConnection;
    private String name;
    private String type;
    
    public Boolean getPollConnection() {
        return pollConnection;
    }
    public void setPollConnection(Boolean pollConnection) {
        this.pollConnection = pollConnection;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
