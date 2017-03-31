package ee.hitsa.ois.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.SaisAdmission;
import ee.hitsa.ois.service.SaisAdmissionService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SaisAdmissionSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SaisAdmissionDto;
import ee.hitsa.ois.web.dto.SaisAdmissionSearchDto;

@RestController
@RequestMapping("/saisAdmissions")
public class SaisAdmissionController {

    @Autowired
    private SaisAdmissionService saisAdmissionService;

    @GetMapping("")
    public Page<SaisAdmissionSearchDto> search(SaisAdmissionSearchCommand command, Pageable pageable, HoisUserDetails user) {
        return saisAdmissionService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SaisAdmissionDto get(@WithEntity("id") SaisAdmission saisAdmission) {
        return SaisAdmissionDto.of(saisAdmission);
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(@WithVersionedEntity(value = "id", versionRequestParam = "version") SaisAdmission saisAdmission, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        saisAdmissionService.delete(saisAdmission);
    }

    @GetMapping("/curriculumVersions")
    public List<AutocompleteResult> curriculumVersions(HoisUserDetails user) {
        return saisAdmissionService.curriculumVersionsinUsedInSaisAdmissions(user);
    }

}
