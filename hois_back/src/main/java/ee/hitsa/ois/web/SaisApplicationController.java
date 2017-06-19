package ee.hitsa.ois.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.service.SaisApplicationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.SaisApplicationImportForm;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationImportCsvCommand;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisApplicationDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationSearchDto;

@RestController
@RequestMapping("/saisApplications")
public class SaisApplicationController {

    @Autowired
    private SaisApplicationService saisApplicationService;

    @GetMapping("")
    public Page<SaisApplicationSearchDto> search(SaisApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return saisApplicationService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SaisApplicationDto get(@WithEntity("id") SaisApplication saisApplication, HoisUserDetails user) {
        UserUtil.assertSameSchool(user, saisApplication.getSaisAdmission().getCurriculumVersion().getCurriculum().getSchool());
        return SaisApplicationDto.of(saisApplication);
    }

    @PostMapping("importCsv")
    public SaisApplicationImportResultDto importCsv(@RequestBody SaisApplicationImportCsvCommand command, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return saisApplicationService.importCsv(command.getFile().getFdata(), user);
    }

    @PostMapping("importSais")
    public SaisApplicationImportResultDto importSais(@RequestBody SaisApplicationImportForm form, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return saisApplicationService.importFromSais(form,  user);
    }


    @GetMapping(value = "sample.csv", produces = HttpUtil.TEXT_CSV_UTF8)
    public InputStreamResource csvSampleFile() {
        return HttpUtil.csvUtf8WithBom(saisApplicationService.getSampleCsvFile());
    }

    @GetMapping(value = "classifiers.csv", produces = HttpUtil.TEXT_CSV_UTF8)
    public InputStreamResource classifiersFile() {
        return HttpUtil.csvUtf8WithBom(saisApplicationService.classifiersFile());
    }
}
