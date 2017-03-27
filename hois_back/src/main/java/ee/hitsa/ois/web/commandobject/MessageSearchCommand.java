package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

public class MessageSearchCommand {
    
    private String subject;
    private String sender;
    private LocalDate sentFrom;
    private LocalDate sentThru;

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public LocalDate getSentFrom() {
        return sentFrom;
    }
    public void setSentFrom(LocalDate sentFrom) {
        this.sentFrom = sentFrom;
    }
    public LocalDate getSentThru() {
        return sentThru;
    }
    public void setSentThru(LocalDate sentThru) {
        this.sentThru = sentThru;
    }
}
