package ee.hitsa.ois.web.dto.message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.MessageTemplate;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.message.MessageTemplateForm;

public class MessageTemplateDto extends MessageTemplateForm {
    private Long id;
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;

    private List<String> userRights;
    
    public static MessageTemplateDto of(MessageTemplate messageTemplate) {
        MessageTemplateDto dto = EntityUtil.bindToDto(messageTemplate, new MessageTemplateDto());
        MessageType type = EnumUtil.valueOf(MessageType.class, messageTemplate.getType());
        if (type != null) {
            dto.setUserRights(StreamUtil.nullSafeSet(type.getPermissions())
                    .stream()
                    .map(Enum::name)
                    .collect(Collectors.toList()));
        }
        return dto;
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

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public List<String> getUserRights() {
        return userRights;
    }

    public void setUserRights(List<String> userRights) {
        this.userRights = userRights;
    }
}
