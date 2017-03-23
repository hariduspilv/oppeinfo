package ee.hitsa.ois.web.commandobject;

import java.util.Set;

public class MessageForm extends VersionedCommand {
    
    private String subject;
    private String content;
    private Long responseTo;
    private Set<Long> receivers;

    public Set<Long> getReceivers() {
        return receivers;
    }
    public void setReceivers(Set<Long> receivers) {
        this.receivers = receivers;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getResponseTo() {
        return responseTo;
    }
    public void setResponseTo(Long responseTo) {
        this.responseTo = responseTo;
    }
}
