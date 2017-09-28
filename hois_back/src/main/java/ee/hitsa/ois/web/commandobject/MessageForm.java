package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.NotEmpty;

public class MessageForm extends VersionedCommand {
    
    @NotNull
    @Size(max=1000)
    private String subject;
    @NotNull
    @Size(max=2000)
    private String content;
    private Long responseTo;
    @NotEmpty
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
