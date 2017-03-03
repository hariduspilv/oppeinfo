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
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.GeneralMessageService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
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
    @Autowired
    private SchoolRepository schoolRepository;

    @GetMapping("/show")
    public Page<GeneralMessageDto> show(HoisUserDetails user, Pageable pageable) {
        return generalMessageService.show(user, pageable);
    }

    @GetMapping
    public Page<GeneralMessageDto> search(HoisUserDetails user, @Valid GeneralMessageSearchCommand criteria, Pageable pageable) {
        return generalMessageService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public GeneralMessageDto getGeneralMessage(HoisUserDetails user, @WithEntity("id") GeneralMessage generalMessage) {
        assertSameSchool(user, generalMessage);
        return GeneralMessageDto.of(generalMessage);
    }

    @PostMapping
    public GeneralMessageDto createGeneralMessage(HoisUserDetails user, @Valid @RequestBody GeneralMessageForm form) {
        GeneralMessage generalMessage = EntityUtil.bindToEntity(form, new GeneralMessage(), "targets");
        generalMessage.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return getGeneralMessage(user, generalMessageService.save(generalMessage, form.getTargets()));
    }

    @PutMapping("/{id:\\d+}")
    public GeneralMessageDto updateGeneralMessage(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) GeneralMessage generalMessage, @Valid @RequestBody GeneralMessageForm form) {
        assertSameSchool(user, generalMessage);
        EntityUtil.bindToEntity(form, generalMessage, "targets");
        return getGeneralMessage(user, generalMessageService.save(generalMessage, form.getTargets()));
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteGeneralMessage(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") GeneralMessage generalMessage, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertSameSchool(user, generalMessage);
        generalMessageService.delete(generalMessage);
    }

    private static void assertSameSchool(HoisUserDetails user, GeneralMessage generalMessage) {
        Long schoolId = user.getSchoolId();
        if(schoolId == null || !schoolId.equals(EntityUtil.getNullableId(generalMessage.getSchool()))) {
            throw new IllegalArgumentException();
        }
    }
}
