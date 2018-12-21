package ee.hitsa.ois.web.curriculum;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.curriculum.CurriculumValidationService;
import ee.hitsa.ois.service.curriculum.CurriculumVersionCopyService;
import ee.hitsa.ois.service.curriculum.CurriculumVersionService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;

@RestController
@RequestMapping("curriculumVersion")
public class CurriculumVersionController {

    @Autowired
    private CurriculumVersionCopyService curriculumVersionCopyService;
    @Autowired
    private CurriculumVersionService curriculumVersionService;
    @Autowired
    private CurriculumValidationService curriculumValidationService;
    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private SchoolService schoolService;
    
    @GetMapping("/{id:\\d+}")
    public CurriculumVersionDto get(HoisUserDetails user, @WithEntity CurriculumVersion curriculumVersion) {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion);
        return curriculumVersionService.get(user, curriculumVersion);
    }
    
    @GetMapping("/curriculum/{id:\\d+}")
    public CurriculumDto getCurriculum(@WithEntity Curriculum curriculum) {
        return CurriculumDto.forVersionForm(curriculum);
    }
    
    @GetMapping("/schoolDepartments/curriculum/{id:\\d+}")
    public List<AutocompleteResult> getSchoolDepartments(@WithEntity Curriculum curriculum) {
        return curriculumVersionService.getSchoolDepartments(curriculum);
    }
    
    @PutMapping("/copy/{id:\\d+}")
    public CurriculumVersionDto copy(HoisUserDetails user, @WithEntity CurriculumVersion curriculumVersion) {
        Curriculum curriculum = curriculumVersion.getCurriculum();
        
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        
        return CurriculumVersionDto.created(curriculumVersionCopyService.copy(curriculumVersion));
    }
    
    @GetMapping("/unique/code")
    public boolean isCodeUnique(HoisUserDetails user, UniqueCommand command) {
        return curriculumVersionService.isCodeUnique(user.getSchoolId(), command);
    }

    @GetMapping("/valid/hourscredits/{id:\\d+}")
    public boolean validHoursCredits(HoisUserDetails user, @WithEntity CurriculumVersion curriculumVersion) {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion);
        return curriculumValidationService.validOccupationModuleThemesHoursAndCredits(curriculumVersion);
    }
    
    @PostMapping
    public CurriculumVersionDto create(HoisUserDetails user, @Valid @RequestBody CurriculumVersionDto dto) {
        Curriculum curriculum = curriculumRepository.findOne(dto.getCurriculum());
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumValidationService.assertVersionCodeIsUnique(user.getSchoolId(), dto.getCode(), null);
        return CurriculumVersionDto.created(curriculumVersionService.createVersion(dto));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumVersionDto save(HoisUserDetails user,
            @WithEntity CurriculumVersion curriculumVersion,
            @Valid @RequestBody CurriculumVersionDto curriculumVersionDto) {
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion.getCurriculum());

        curriculumValidationService.assertCurriculumVersionCanBeEdited(curriculumVersion);
        curriculumValidationService.validateCurriculumVersionFormWithStatus(curriculumVersion, curriculumVersionDto);
        curriculumValidationService.assertVersionCodeIsUnique(user.getSchoolId(), curriculumVersionDto.getCode(), curriculumVersion);
        return get(user, curriculumVersionService.save(user, curriculumVersion, curriculumVersionDto));
    }

    @PutMapping("/close/{id:\\d+}")
    public CurriculumVersionDto close(HoisUserDetails user,
            @WithEntity CurriculumVersion curriculumVersion) {
        CurriculumUtil.assertCanClose(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion.getCurriculum());
        return get(user, curriculumVersionService.close(curriculumVersion));
    }
    
    @PutMapping("/underrevision/{id:\\d+}")
    public CurriculumVersionDto setUnderRevision(HoisUserDetails user, @WithEntity CurriculumVersion curriculumVersion) {
        CurriculumUtil.assertCanSetUnderRevision(user, curriculumVersion);
        return get(user, curriculumVersionService.setUnderRevision(user, curriculumVersion));
    }

    @PutMapping("/confirm/{id:\\d+}")
    public CurriculumVersionDto confirm(HoisUserDetails user,
            @WithEntity CurriculumVersion curriculumVersion) {
        CurriculumUtil.assertCanConfirm(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion.getCurriculum());

        curriculumValidationService.validateCurriculumVersion(curriculumVersion);
        return get(user, curriculumVersionService.confirm(curriculumVersion));
    }

    @PutMapping("/saveAndConfirm/{id:\\d+}")
    public CurriculumVersionDto saveAndConfirm(HoisUserDetails user,
            @WithEntity CurriculumVersion curriculumVersion,
            @Valid @RequestBody CurriculumVersionDto curriculumVersionDto) {
        
        String myEhisSchool = schoolService.getEhisSchool(user.getSchoolId());
        CurriculumUtil.assertCanChange(user, myEhisSchool, curriculumVersion.getCurriculum());
        CurriculumUtil.assertCanConfirm(user, myEhisSchool, curriculumVersion.getCurriculum());

        curriculumValidationService.validateCurriculumVersionForm(curriculumVersion, curriculumVersionDto);
        curriculumValidationService.assertCurriculumVersionCanBeEdited(curriculumVersion);
        curriculumValidationService.assertVersionCodeIsUnique(user.getSchoolId(), curriculumVersionDto.getCode(), curriculumVersion);
        return get(user, curriculumVersionService.saveAndConfirm(user, curriculumVersion, curriculumVersionDto));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity CurriculumVersion curriculumVersion) {
        CurriculumUtil.assertCanDelete(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion.getCurriculum());
        curriculumValidationService.assertCurriculumVersionCanBeDeleted(curriculumVersion);

        curriculumVersionService.delete(user, curriculumVersion);
    }
}
