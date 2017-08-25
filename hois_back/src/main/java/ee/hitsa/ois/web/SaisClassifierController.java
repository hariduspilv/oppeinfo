package ee.hitsa.ois.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.SaisClassifierService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.sais.SaisClassifierSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;

@RestController
@RequestMapping("/saisClassifier")
public class SaisClassifierController {

    @Autowired
    private SaisClassifierService saisClassifierService;

    @GetMapping
    public Page<SaisClassifierSearchDto> list(SaisClassifierSearchCommand command, Pageable pageable, HoisUserDetails user) {
        assertIsMainAdmin(user);
        return saisClassifierService.list(command, pageable);
    }
    
    
    @GetMapping("/{parentCode}")
    public Page<SaisClassifierSearchDto> search(@NotEmpty @PathVariable("parentCode") String parentCode, SaisClassifierSearchCommand command, Pageable pageable, HoisUserDetails user) {
        assertIsMainAdmin(user);
        return saisClassifierService.search(parentCode, command, pageable);
    }

    @PostMapping("/saisImport")
    public Page<SaisClassifierSearchDto> saisImport(SaisClassifierSearchCommand command, Pageable pageable, HoisUserDetails user) {
        assertIsMainAdmin(user);
        return saisClassifierService.importClassifiers(command, pageable, user);
    }
    
    private static void assertIsMainAdmin(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isMainAdmin(), "User is not main admin");
    }

}
