package ee.hitsa.ois.web;

import ee.hitsa.ois.concurrent.AsyncManager;
import ee.hitsa.ois.concurrent.AsyncMemoryManager;
import ee.hitsa.ois.concurrent.request.SaisClassifierRequest;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.sais.SaisClassifierService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.sais.SaisClassifierSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/saisClassifier")
public class SaisClassifierController {

    @Autowired
    private SaisClassifierService saisClassifierService;
    @Autowired
    private AsyncManager asyncManager;

    @GetMapping
    public Page<SaisClassifierSearchDto> list(SaisClassifierSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsMainAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KLASSIFIKAATOR);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_SAIS);
        return saisClassifierService.list(command, pageable);
    }

    @GetMapping("/{parentCode}")
    public Page<SaisClassifierSearchDto> search(@Required @PathVariable("parentCode") String parentCode, SaisClassifierSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsMainAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KLASSIFIKAATOR);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_SAIS);
        return saisClassifierService.search(parentCode, command, pageable);
    }

    @PostMapping("/saisImport")
    public Map<String, Object> importSais(HoisUserDetails user) {
        UserUtil.assertIsMainAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KLASSIFIKAATOR);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_SAIS);
        String requestHash = asyncManager.generateKey(user);
        SaisClassifierRequest request = saisClassifierService.createRequest(user, requestHash);
        asyncManager.createRequest(user, AsyncMemoryManager.SAIS_CLASSIFIER, requestHash, request);
        asyncManager.processRequest(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", requestHash);
        return map;
    }

    @GetMapping("/saisImportStatus")
    public FutureStatusResponse importSaisStatus(HoisUserDetails user, @RequestParam() String key) {
        UserUtil.assertIsMainAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KLASSIFIKAATOR);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_SAIS);
        return asyncManager.getState(user, AsyncMemoryManager.SAIS_CLASSIFIER, key, true);
    }
}
