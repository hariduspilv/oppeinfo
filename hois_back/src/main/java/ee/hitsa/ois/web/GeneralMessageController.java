package ee.hitsa.ois.web;

import java.util.List;

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

import ee.hitsa.ois.domain.GeneralMessage;
import ee.hitsa.ois.service.GeneralMessageService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.GeneralMessageUserRights;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.GeneralMessageForm;
import ee.hitsa.ois.web.commandobject.GeneralMessageSearchCommand;
import ee.hitsa.ois.web.dto.GeneralMessageDto;

@RestController
@RequestMapping("/generalmessages")
public class GeneralMessageController {

    @Autowired
    private GeneralMessageService generalMessageService;

    @GetMapping("/show")
    public Page<GeneralMessageDto> show(HoisUserDetails user, Pageable pageable) {
        return generalMessageService.show(user, pageable);
    }

    @GetMapping("/showsitemessages")
    public List<GeneralMessageDto> show() {
        return generalMessageService.showSiteMessages();
    }

    @GetMapping
    public Page<GeneralMessageDto> search(HoisUserDetails user, @Valid GeneralMessageSearchCommand criteria, Pageable pageable) {
        GeneralMessageUserRights.assertCanSearch(user);
        return generalMessageService.search(user, criteria, pageable);
    }

    @GetMapping("/site")
    public Page<GeneralMessageDto> searchSiteMessages(HoisUserDetails user, @Valid GeneralMessageSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsMainAdmin(user);
        return generalMessageService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public GeneralMessageDto get(HoisUserDetails user, @WithEntity GeneralMessage generalMessage) {
        GeneralMessageUserRights.assertCanView(user, generalMessage);
        return GeneralMessageDto.of(generalMessage);
    }

    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody GeneralMessageForm form) {
        GeneralMessageUserRights.assertCanCreate(user, form);
        return HttpUtil.created(generalMessageService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public GeneralMessageDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) GeneralMessage generalMessage,
            @Valid @RequestBody GeneralMessageForm form) {
        GeneralMessageUserRights.assertCanEdit(user, generalMessage, form);
        return get(user, generalMessageService.save(user, generalMessage, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") GeneralMessage generalMessage,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        GeneralMessageUserRights.assertCanDelete(user, generalMessage);
        generalMessageService.delete(user, generalMessage);
    }
}
