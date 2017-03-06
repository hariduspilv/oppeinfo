package ee.hitsa.ois.web;

import java.util.Set;

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

import ee.hitsa.ois.domain.MessageTemplate;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.MessageTemplateService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.MessageTemplateForm;
import ee.hitsa.ois.web.commandobject.MessageTemplateSearchCommand;
import ee.hitsa.ois.web.dto.MessageTemplateDto;

@RestController
@RequestMapping("/messageTemplate")
public class MessageTemplateController {
    
    @Autowired
    private MessageTemplateService messageTemplateService;
    
    @Autowired
    private SchoolRepository schoolRepository;
    
    @GetMapping
    public Page<MessageTemplateDto> search(HoisUserDetails user, @Valid MessageTemplateSearchCommand criteria, Pageable pageable) {
        return messageTemplateService.search(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public MessageTemplateDto get(HoisUserDetails user, @WithEntity("id") MessageTemplate messageTemplate) {
        UserUtil.assertSameSchool(user, messageTemplate.getSchool());
        return MessageTemplateDto.of(messageTemplate);
    }
    
    @PostMapping
    public MessageTemplateDto create(HoisUserDetails user, @Valid @RequestBody MessageTemplateForm form) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return get(user, messageTemplateService.save(messageTemplate, form));
    }

    @PutMapping("/{id:\\d+}")
    public MessageTemplateDto update(HoisUserDetails user, 
            @WithVersionedEntity(value = "id", versionRequestBody = true) MessageTemplate messageTemplate, 
            @Valid @RequestBody MessageTemplateForm form) {
        UserUtil.assertSameSchool(user, messageTemplate.getSchool());
        EntityUtil.bindToEntity(form, messageTemplate);
        return get(user, messageTemplateService.save(messageTemplate, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") MessageTemplate generalMessage, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, generalMessage.getSchool());
        messageTemplateService.delete(generalMessage);
    }
    
    @GetMapping("/usedTypeCodes")
    public Set<String> getUsedTypeCodes(String code) {
        return messageTemplateService.getUsedTypeCodes(code);
    }
}
