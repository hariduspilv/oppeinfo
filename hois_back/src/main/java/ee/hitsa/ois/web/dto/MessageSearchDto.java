package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Message;

public class MessageSearchDto {
    
    private Long id;
    private String subject;
    private String content;
    private LocalDate dateSent;
    private String sender;
    private Set<String> receivers;
    private Boolean isRead;
    
    public MessageSearchDto(Message message) {
        this.subject = message.getSubject();
        this.dateSent = message.getInserted().toLocalDate();
        this.id = message.getId();
    }
    
    public MessageSearchDto(Long id, String subject, String content, LocalDate dateSent, String sender,
            Boolean isRead) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.dateSent = dateSent;
        this.sender = sender;
        this.isRead = isRead;
    }

    public static MessageSearchDto ofReceived(Message message, Long personId) {
        MessageSearchDto dto = new MessageSearchDto(message);
        dto.setSender(message.getSender().getFullname());
        dto.setIsRead(message.isReadBy(personId));
        return dto;
    }
    
    public static MessageSearchDto ofReceivedWithContent(Message message, Long personId) {
        MessageSearchDto dto = ofReceived(message, personId);
        dto.setContent(message.getContent());
        return dto;
    }
    
    public static MessageSearchDto ofSent(Message message) {
        MessageSearchDto dto = new MessageSearchDto(message);
        dto.setReceivers(message.getReceivers().stream().map(m -> m.getPerson().getFullname()).collect(Collectors.toSet()));
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

    public LocalDate getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDate dateSent) {
        this.dateSent = dateSent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
