package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.kutseregister.KutseregisterService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.OccupationCertificateImportForm;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;

@RestController
@RequestMapping("/occupationcertificates")
public class OccupationCertificateController {

    @Autowired
    private KutseregisterService kutseregisterService;

    @PostMapping("/import")
    public List<StudentOccupationCertificateDto> importFromKutseregister(HoisUserDetails user, @Valid @RequestBody OccupationCertificateImportForm criteria) {
        UserUtil.assertIsSchoolAdmin(user);
        return kutseregisterService.importFromKutseregister(user.getSchoolId(), criteria);
    }
}
