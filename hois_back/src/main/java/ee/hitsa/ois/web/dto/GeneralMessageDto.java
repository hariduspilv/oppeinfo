package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.GeneralMessage;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.GeneralMessageForm;

public class GeneralMessageDto extends GeneralMessageForm {
    private Long id;
    private LocalDateTime inserted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public GeneralMessageDto() {
    }

    public GeneralMessageDto(Long id, String title, LocalDateTime inserted) {
        this.id = id;
        setTitle(title);
        this.inserted = inserted;
    }

    public static GeneralMessageDto of(GeneralMessage generalMessage) {
        GeneralMessageDto dto = EntityUtil.bindToDto(generalMessage, new GeneralMessageDto(), "targets");
        dto.setTargets(generalMessage.getTargets().stream().map(t -> t.getRole().getCode()).collect(Collectors.toList()));
        return dto;
    }
}
