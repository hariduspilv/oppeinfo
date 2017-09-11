package ee.hitsa.ois.web;

import ee.hitsa.ois.service.LogsService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisLogCommand;
import ee.hitsa.ois.web.dto.EhisLogDto;
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
    private LogsService logsService;

    @GetMapping("/ehis")
    public Page<EhisLogDto> ehisSearch(HoisUserDetails user, @Valid EhisLogCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return logsService.ehisSearch(user.getSchoolId(), command, pageable);
    }

    @GetMapping("/ehis/{id:\\d+}")
    public EhisLogDto ehisGet(HoisUserDetails user, @PathVariable("id") Long id, @NotNull @RequestParam("messageType") String messageType) {
        return logsService.ehisGet(user, id, messageType);
    }
}
