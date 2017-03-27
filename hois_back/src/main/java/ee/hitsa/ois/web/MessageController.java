package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.service.MessageService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.MessageForm;
import ee.hitsa.ois.web.commandobject.MessageSearchCommand;
import ee.hitsa.ois.web.dto.MessageDto;
import ee.hitsa.ois.web.dto.MessageSearchDto;

@RestController
@RequestMapping("/message")
public class MessageController {
    
    @Autowired
    private MessageService messageService;
    
    @GetMapping("/sent")
    public Page<MessageSearchDto> searchSent(HoisUserDetails user, @Valid MessageSearchCommand criteria, Pageable pageable) {
        return messageService.searchSent(user, criteria, pageable);
    }
    
    @GetMapping("/received")
    public Page<MessageSearchDto> searchReceived(HoisUserDetails user, @Valid MessageSearchCommand criteria, Pageable pageable) {
        return messageService.searchReceived(user, criteria, pageable);
    }
    
    @GetMapping("/received/mainPage")
    public Page<MessageSearchDto> searchReceivedForMainPage(HoisUserDetails user, Pageable pageable) {
        return messageService.show(user, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public MessageDto get(HoisUserDetails user, @WithEntity("id") Message message) {
        MessageDto dto = MessageDto.of(message);
        dto.setIsRead(message.isReadBy(user.getPersonId()));
        return dto;
    }
    
    @PostMapping
    public MessageDto create(HoisUserDetails user, @Valid @RequestBody MessageForm form) {
        return get(user, messageService.create(user, form));
    }
    
    @PutMapping("/{id:\\d+}")
    public void setRead(HoisUserDetails user, @WithEntity("id") Message message) {
        messageService.setRead(user.getPersonId(), message);
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Message message, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        messageService.delete(message);
    }
}
