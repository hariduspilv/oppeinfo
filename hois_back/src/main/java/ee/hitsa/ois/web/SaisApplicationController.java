package ee.hitsa.ois.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.SaisApplicationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.SaisApplicationSearchDto;

@RestController
@RequestMapping("/saisApplications")
public class SaisApplicationController {

    @Autowired
    private SaisApplicationService saisApplicationService;

    @GetMapping("")
    public Page<SaisApplicationSearchDto> search(SaisApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        return saisApplicationService.search(user, command, pageable);
    }

    @PostMapping("importCsv")
    //TODO: permission checks
    public SaisApplicationImportResultDto importCsv(@RequestBody SaisApplicationImportCsvCommand command, HoisUserDetails user) {
        return saisApplicationService.importCsv(command.getFile().getFdata());
    }

}
