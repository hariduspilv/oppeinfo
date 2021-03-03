package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import ee.hitsa.ois.concurrent.AsyncManager;
import ee.hitsa.ois.concurrent.AsyncMemoryManager;
import ee.hitsa.ois.concurrent.request.SaisApplicationRequest;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.service.sais.SaisApplicationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationImportCsvCommand;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationImportForm;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisApplicationDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationSearchDto;

@RestController
@RequestMapping("/saisApplications")
public class SaisApplicationController {

    @Autowired
    private SaisApplicationService saisApplicationService;
    @Autowired
    private AsyncManager asyncManager;

    @GetMapping
    public Page<SaisApplicationSearchDto> search(SaisApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return saisApplicationService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SaisApplicationDto get(@WithEntity SaisApplication saisApplication, HoisUserDetails user) {
        UserUtil.assertSameSchool(user, saisApplication.getSaisAdmission().getCurriculumVersion().getCurriculum().getSchool());
        return saisApplicationService.getById(saisApplication);
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(@WithEntity SaisApplication saisApplication, HoisUserDetails user) {
    	UserUtil.assertIsSchoolAdmin(user);
        saisApplicationService.delete(user, saisApplication);
    }

    @PostMapping("importCsv")
    public SaisApplicationImportResultDto importCsv(@RequestBody SaisApplicationImportCsvCommand command, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return saisApplicationService.importCsv(command.getFile().getFdata(), user);
    }

    @GetMapping("sample.csv")
    public void csvSampleFile(HttpServletResponse response) throws IOException {
        HttpUtil.csvUtf8WithBom(response, "sample.csv", saisApplicationService.sampleCsvFile());
    }

    @GetMapping("classifiers.csv")
    public void classifiersFile(HttpServletResponse response) throws IOException {
        HttpUtil.csvUtf8WithBom(response, "classifiers.csv", saisApplicationService.classifiersFile());
    }

    @PostMapping("/importSais")
    public Map<String, Object> importSais(HoisUserDetails user, @Valid @RequestBody SaisApplicationImportForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        String requestHash = asyncManager.generateKey(user);
        SaisApplicationRequest request = saisApplicationService.createRequest(user, requestHash, form);
        asyncManager.createRequest(user, AsyncMemoryManager.SAIS_APPLIACTION, requestHash, request);
        asyncManager.processRequest(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", requestHash);
        return map;
    }

    @GetMapping("/importSaisStatus")
    public FutureStatusResponse importSaisStatus(HoisUserDetails user, @RequestParam() String key) {
        UserUtil.assertIsSchoolAdmin(user);
        return asyncManager.getState(user, AsyncMemoryManager.SAIS_APPLIACTION, key, true);
    }
}
