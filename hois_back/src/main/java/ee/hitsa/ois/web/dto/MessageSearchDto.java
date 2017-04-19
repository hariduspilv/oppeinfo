package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.util.StreamUtil;

public class MessageSearchDto {
    
    private Long id;
    private String subject;
    private String content;
    private LocalDateTime dateSent;
    private String sender;
    private Set<String> receivers;
    private Boolean isRead;

    public MessageSearchDto(Message message) {
        this(message.getId(), message.getSubject(), message.getInserted(), null, null);
    }

    public MessageSearchDto(Long id, String subject, String content, LocalDateTime dateSent, String sender,
            Boolean isRead) {
        this(id, subject, dateSent, sender, isRead);

        this.content = content;
    }

    public MessageSearchDto(Long id, String subject, LocalDateTime dateSent, String sender,
            Boolean isRead) {
        this.id = id;
        this.subject = subject;
        this.dateSent = dateSent;
        this.sender = sender;
        this.isRead = isRead;
    }

    public static MessageSearchDto ofSent(Message message) {
        MessageSearchDto dto = new MessageSearchDto(message);
        dto.setReceivers(StreamUtil.toMappedSet(m -> m.getPerson().getFullname(), message.getReceivers()));
        return dto;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Set<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<String> receivers) {
        this.receivers = receivers;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
