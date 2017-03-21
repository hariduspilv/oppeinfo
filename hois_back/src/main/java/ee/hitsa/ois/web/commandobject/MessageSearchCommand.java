package ee.hitsa.ois.web.commandobject;

import java.time.LocalDateTime;

public class MessageSearchCommand {
    
    private String subject;
    private String sender;
    private LocalDateTime sentFrom;
    private LocalDateTime sentThru;
    private Boolean isRead;

    public Boolean getIsRead() {
        return isRead;
    }
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
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
    public LocalDateTime getSentFrom() {
        return sentFrom;
    }
    public void setSentFrom(LocalDateTime sentFrom) {
        this.sentFrom = sentFrom;
    }
    public LocalDateTime getSentThru() {
        return sentThru;
    }
    public void setSentThru(LocalDateTime sentThru) {
        this.sentThru = sentThru;
    }
}
