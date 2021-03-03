package ee.hitsa.ois.web.commandobject.message;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class MessageForm extends VersionedCommand {
    
    @Required
    @Size(max=1000)
    private String subject;
    @Required
    @Size(max=5000)
    private String content;
    private Long responseTo;
    @Required
    @Valid
    private Set<Receiver> receivers;

    public Set<Receiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<Receiver> receivers) {
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
    
    public static class Receiver {

        @Required
        private Long person;
        private String role;

        public Long getPerson() {
            return person;
        }

        public void setPerson(Long person) {
            this.person = person;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
