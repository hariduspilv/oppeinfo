package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class MessageForm extends VersionedCommand {
    
    private String subject;
    private String content;
    @ClassifierRestriction(MainClassCode.ROLL)
    private String sendersRole;
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
    public String getSendersRole() {
        return sendersRole;
    }
    public void setSendersRole(String sendersRole) {
        this.sendersRole = sendersRole;
    }
    public Long getResponseTo() {
        return responseTo;
    }
    public void setResponseTo(Long responseTo) {
        this.responseTo = responseTo;
    }
}
