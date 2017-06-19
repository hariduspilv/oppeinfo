package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.service.CertificateService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.CertificateForm;
import ee.hitsa.ois.web.commandobject.CertificateSearchCommand;
import ee.hitsa.ois.web.dto.CertificateDto;
import ee.hitsa.ois.web.dto.CertificateSearchDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping
    public Page<CertificateSearchDto> search(HoisUserDetails user, @Valid CertificateSearchCommand criteria, Pageable pageable) {
        return certificateService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public CertificateDto get(HoisUserDetails user, @WithEntity("id") Certificate certificate) {
        UserUtil.assertSameSchool(user, certificate.getSchool());
        return CertificateDto.of(certificate);
    }

    @PostMapping
    public CertificateDto create(HoisUserDetails user, @Valid @RequestBody CertificateForm form) {
        return get(user, certificateService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public CertificateDto update(HoisUserDetails user, 
            @WithVersionedEntity(value = "id", versionRequestBody = true) Certificate certificate, 
            @Valid @RequestBody CertificateForm form) {
        UserUtil.assertSameSchool(user, certificate.getSchool());
        return get(user, certificateService.save(certificate, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Certificate certificate, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, certificate.getSchool());
        certificateService.delete(certificate);
    }

    @GetMapping("/otherStudent")
    public StudentSearchDto getOtherPerson(HoisUserDetails user, String idcode) {
        // TODO validation of idcode
        return certificateService.getOtherPerson(user.getSchoolId(), idcode);
    }
}
