package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.service.sais.SaisAdmissionService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.ArchiveForm;
import ee.hitsa.ois.web.commandobject.sais.SaisAdmissionImportForm;
import ee.hitsa.ois.web.commandobject.sais.SaisAdmissionSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisAdmissionDto;
import ee.hitsa.ois.web.dto.sais.SaisAdmissionSearchDto;

@RestController
@RequestMapping("/saisAdmissions")
public class SaisAdmissionController {

    @Autowired
    private SaisAdmissionService saisAdmissionService;

    @GetMapping
    public Page<SaisAdmissionSearchDto> search(SaisAdmissionSearchCommand command, Pageable pageable, HoisUserDetails user) {
        return saisAdmissionService.search(user.getSchoolId(), command, pageable);
    }
    
    @PostMapping("/archive")
    public void archive(@Valid @RequestBody ArchiveForm archiveForm, HoisUserDetails user) {
    	UserUtil.assertIsSchoolAdmin(user);
    	saisAdmissionService.updateArchive(user, archiveForm);
    }

    @GetMapping("/{id:\\d+}")
    public SaisAdmissionDto get(@WithEntity SaisAdmission saisAdmission, HoisUserDetails user) {
        UserUtil.assertSameSchool(user, saisAdmission.getCurriculumVersion().getCurriculum().getSchool());
        return SaisAdmissionDto.of(saisAdmission);
    }

    @PostMapping("/deArchive/{id:\\d+}")
    public SaisAdmissionDto deArchive(@WithEntity SaisAdmission saisAdmission, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, saisAdmission.getCurriculumVersion().getCurriculum().getSchool());
        return saisAdmissionService.updateArchive(user, saisAdmission);
    }
    
    @PostMapping("/saisImport")
    public Page<SaisAdmissionSearchDto> saisImport(@Valid @RequestBody SaisAdmissionImportForm form, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return saisAdmissionService.saisImport(form, user);
    }
}
