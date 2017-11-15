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

import ee.hitsa.ois.domain.GeneralMessage;
import ee.hitsa.ois.service.GeneralMessageService;
import ee.hitsa.ois.service.security.HoisUserDetails;
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

    @GetMapping
    public Page<GeneralMessageDto> search(HoisUserDetails user, @Valid GeneralMessageSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return generalMessageService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public GeneralMessageDto get(HoisUserDetails user, @WithEntity("id") GeneralMessage generalMessage) {
        UserUtil.assertIsSchoolAdmin(user, generalMessage.getSchool());
        return GeneralMessageDto.of(generalMessage);
    }

    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody GeneralMessageForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(generalMessageService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public GeneralMessageDto save(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) GeneralMessage generalMessage, @Valid @RequestBody GeneralMessageForm form) {
        UserUtil.assertIsSchoolAdmin(user, generalMessage.getSchool());
        return get(user, generalMessageService.save(generalMessage, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") GeneralMessage generalMessage, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user, generalMessage.getSchool());
        generalMessageService.delete(user, generalMessage);
    }
}
