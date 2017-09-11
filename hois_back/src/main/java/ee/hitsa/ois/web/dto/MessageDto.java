package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.domain.MessageReceiver;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.MessageForm;

public class MessageDto extends MessageForm {

    private Long id;
    private LocalDateTime inserted;
    private Long sendersId;
    private String sendersName;
    private List<String> receiversNames;
    private Boolean isRead;

    public static MessageDto of(Message message) {
        MessageDto dto = EntityUtil.bindToDto(message, new MessageDto(), "sender", "responseTo", "receivers");
        dto.setSendersId(EntityUtil.getId(message.getSender()));
        dto.setSendersName(message.getSender().getFullname());
        dto.setReceiversNames(PersonUtil.sorted(message.getReceivers().stream().map(MessageReceiver::getPerson)));
        dto.setResponseTo(EntityUtil.getNullableId(message.getResponseTo()));
        dto.setReceivers(StreamUtil.toMappedSet(MessageReceiver::getId, message.getReceivers()));
        return dto;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public List<String> getReceiversNames() {
        return receiversNames;
    }

    public void setReceiversNames(List<String> receiversNames) {
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
