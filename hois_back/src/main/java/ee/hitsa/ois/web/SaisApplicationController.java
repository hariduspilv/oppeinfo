package ee.hitsa.ois.web;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationImportCsvCommand;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisApplicationDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationSearchDto;

@RestController
@RequestMapping("/saisApplications")
public class SaisApplicationController {

    private static final Logger log = LoggerFactory.getLogger(SaisApplicationController.class);
    private static final byte[] UTF8_BOM = new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

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


    @GetMapping(value = "sample.csv", produces = {"text/csv; Charset=UTF-8"})
    public byte[] csvSampleFile() {
        return getFileBytes(saisApplicationService.getSampleCsvFile());
    }

    @GetMapping(value = "classifiers.csv", produces = {"text/csv; Charset=UTF-8"})
    public byte[] classifiersFile() {
        return getFileBytes(saisApplicationService.classifiersFile());
    }

    private static byte[] getFileBytes(String fileContent) {
        try {
            //XXX: find cleaner solution
            //Excel 2003 strictly requires the BOM in UTF-8 encoded CSV
            byte[] content = fileContent.getBytes(StandardCharsets.UTF_8);
            byte[] result = new byte[content.length + UTF8_BOM.length];
            System.arraycopy(UTF8_BOM, 0, result, 0, UTF8_BOM.length);
            System.arraycopy(content, 0, result, UTF8_BOM.length, content.length);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return fileContent.getBytes();
        }
    }

}
