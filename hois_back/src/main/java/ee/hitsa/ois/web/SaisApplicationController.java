package ee.hitsa.ois.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.SaisApplication;
import ee.hitsa.ois.service.SaisApplicationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.SaisApplicationImportCsvCommand;
import ee.hitsa.ois.web.commandobject.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.SaisApplicationDto;
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

    @GetMapping("/{id:\\d+}")
    public SaisApplicationDto get(@WithEntity("id") SaisApplication saisApplication, HoisUserDetails user) {
        UserUtil.assertSameSchool(user, saisApplication.getSaisAdmission().getCurriculumVersion().getCurriculum().getSchool());
        return SaisApplicationDto.of(saisApplication);
    }

    @PostMapping("importCsv")
    public SaisApplicationImportResultDto importCsv(@RequestBody SaisApplicationImportCsvCommand command, HoisUserDetails user) {
        return saisApplicationService.importCsv(command.getFile().getFdata(), user);
    }

}
