package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.CertificateService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.commandobject.CertificateSearchCommand;
import ee.hitsa.ois.web.dto.CertificateSearchDto;

@RestController
@RequestMapping("/certificate")
public class CertificateController {
    
    @Autowired
    private CertificateService certificateService;
    
    @GetMapping
    public Page<CertificateSearchDto> search(HoisUserDetails user, @Valid CertificateSearchCommand criteria, Pageable pageable) {
        return certificateService.search(user.getSchoolId(), criteria, pageable);
    }
}
