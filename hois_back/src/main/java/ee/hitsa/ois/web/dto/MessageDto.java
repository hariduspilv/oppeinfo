package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.MessageForm;

public class MessageDto extends MessageForm {
    private Long id;
    private LocalDateTime inserted;
    private Long sendersId;
    private String sendersName;
    private Set<String> receiversNames;
    private Boolean isRead;
    
    public static MessageDto of(Message message) {
        MessageDto dto = EntityUtil.bindToDto(message, new MessageDto(), "sender", "responseTo", "receivers");
        dto.setSendersId(message.getSender().getId());
        dto.setSendersName(message.getSender().getFullname());
        dto.setReceiversNames(message.getReceivers().stream().map(m -> m.getPerson().getFullname()).collect(Collectors.toSet()));
        if(message.getResponseTo() != null) {
            dto.setResponseTo(message.getResponseTo().getId());
        }
        dto.setReceivers(message.getReceivers().stream().map(r -> r.getId()).collect(Collectors.toSet()));
        return dto;
    }
    
    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Set<String> getReceiversNames() {
        return receiversNames;
    }

    public void setReceiversNames(Set<String> receiversNames) {
        this.receiversNames = receiversNames;
    }

    public Long getSendersId() {
        return sendersId;
    }

    public void setSendersId(Long sendersId) {
        this.sendersId = sendersId;
    }

    public String getSendersName() {
        return sendersName;
    }

    public void setSendersName(String sendersName) {
        this.sendersName = sendersName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
}
