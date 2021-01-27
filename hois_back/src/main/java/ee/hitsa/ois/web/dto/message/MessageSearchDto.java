package ee.hitsa.ois.web.dto.message;

import java.time.LocalDateTime;
import java.util.List;

public class MessageSearchDto {
    
    private final Long id;
    private final String subject;
    private final String content;
    private final LocalDateTime dateSent;
    private final String sender;
    private List<String> receivers;
    private final Boolean isRead;
    private final Long sendersId;

    public MessageSearchDto(Long id, String subject, String content, LocalDateTime dateSent, String sender,
            Boolean isRead, Long sendersId) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.dateSent = dateSent;
        this.sender = sender;
        this.isRead = isRead;
        this.sendersId = sendersId;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public String getSender() {
        return sender;
    }

    public Long getSendersId() {
        return sendersId;
    }
}
