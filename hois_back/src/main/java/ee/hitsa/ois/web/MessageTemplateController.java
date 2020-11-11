package ee.hitsa.ois.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
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
import ee.hitsa.ois.service.MessageTemplateService;
import ee.hitsa.ois.service.security.HoisUserDetails;
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

    @GetMapping
    public Page<MessageTemplateDto> search(HoisUserDetails user, @Valid MessageTemplateSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return messageTemplateService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public MessageTemplateDto get(HoisUserDetails user, @WithEntity MessageTemplate messageTemplate) {
        UserUtil.assertIsSchoolAdmin(user, messageTemplate.getSchool());
        return MessageTemplateDto.of(messageTemplate);
    }

    @PostMapping
    public MessageTemplateDto create(HoisUserDetails user, @Valid @RequestBody MessageTemplateForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(user, messageTemplateService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public MessageTemplateDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) MessageTemplate messageTemplate, 
            @Valid @RequestBody MessageTemplateForm form) {
        UserUtil.assertIsSchoolAdmin(user, messageTemplate.getSchool());
        return get(user, messageTemplateService.save(messageTemplate, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") MessageTemplate messageTemplate, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user, messageTemplate.getSchool());
        messageTemplateService.delete(user, messageTemplate);
    }

    @GetMapping("/usedTypeCodes")
    public Set<String> getUsedTypeCodes(HoisUserDetails user, String code) {
        return messageTemplateService.getUsedTypeCodes(user.getSchoolId(), code);
    }

    @GetMapping("/usersByPermission")
    public Page<AutocompleteResult> get(HoisUserDetails user, @RequestParam List<String> permissionObjects,
                                        Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return messageTemplateService.getUsersWithGivenUserRightsInSchool(user.getSchoolId(),
                permissionObjects.stream().map(po -> EnumUtil.valueOf(PermissionObject.class, po))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()), pageable);
    }

    @GetMapping("/contentTemplate")
    public Map<String, Object> getContentTemplate(HoisUserDetails user, @RequestParam String templateType) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_AUTOTEADE);
        Map<String, Object> map = new HashMap<>();
        map.put("content", messageTemplateService.getTemplateExample(templateType));
        return map;
    }
}
