package ee.hitsa.ois.web;

import ee.hitsa.ois.service.ehis.EhisLogService;
import ee.hitsa.ois.service.ekis.EkisLogService;
import ee.hitsa.ois.service.sais.SaisLogService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisLogCommand;
import ee.hitsa.ois.web.commandobject.sais.SaisLogCommand;
import ee.hitsa.ois.web.dto.EhisLogDto;
import ee.hitsa.ois.web.dto.EkisLogDto;
import ee.hitsa.ois.web.dto.sais.SaisLogDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private EhisLogService ehisLogService;
    @Autowired
    private EkisLogService ekisLogService;
    @Autowired
    private SaisLogService saisLogService;

    @GetMapping("/ehis")
    public Page<EhisLogDto> ehisSearch(HoisUserDetails user, @Valid EhisLogCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return ehisLogService.search(user.getSchoolId(), command, pageable);
    }

    @GetMapping("/ehis/{id:\\d+}")
    public EhisLogDto ehisGet(HoisUserDetails user, @PathVariable("id") Long id, @NotNull @RequestParam("messageType") String messageType) {
        return ehisLogService.get(user, id, messageType);
    }

    @GetMapping("/ekis")
    public Page<EkisLogDto> ekisSearch(HoisUserDetails user, @Valid EhisLogCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return ekisLogService.search(user.getSchoolId(), command, pageable);
    }

    @GetMapping("/ekis/{id:\\d+}")
    public EkisLogDto ekisGet(HoisUserDetails user, @PathVariable("id") Long id, @NotNull @RequestParam("messageType") String messageType) {
        return ekisLogService.get(user, id, messageType);
    }

    @GetMapping("/sais")
    public Page<SaisLogDto> saisSearch(HoisUserDetails user, @Valid SaisLogCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return saisLogService.search(user.getSchoolId(), command, pageable);
    }

    @GetMapping("/sais/{id:\\d+}")
    public SaisLogDto saisGet(HoisUserDetails user, @PathVariable("id") Long id, @NotNull @RequestParam("messageType") String messageType) {
        return saisLogService.get(user, id, messageType);
    }
}
