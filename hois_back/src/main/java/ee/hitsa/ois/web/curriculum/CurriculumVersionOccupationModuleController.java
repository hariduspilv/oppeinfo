package ee.hitsa.ois.web.curriculum;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.curriculum.CurriculumValidationService;
import ee.hitsa.ois.service.curriculum.CurriculumVersionOccupationModuleService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

@RestController
@RequestMapping("occupationModule")
public class CurriculumVersionOccupationModuleController {
    
    @Autowired
    private CurriculumVersionOccupationModuleService curriculumVersionOccupationModuleService;
    @Autowired
    private CurriculumValidationService curriculumValidationService;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private SchoolService schoolService;
    
    @GetMapping("/{id:\\d+}")
    public CurriculumVersionOccupationModuleDto get(HoisUserDetails user, @WithEntity CurriculumVersionOccupationModule module) {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), module.getCurriculumVersion());
        return curriculumVersionOccupationModuleService.get(module);
        
    }
    
    @PostMapping
    public CurriculumVersionOccupationModuleDto create(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionOccupationModuleDto dto) {
        validate(user, dto.getCurriculumVersion());
        return get(user, curriculumVersionOccupationModuleService.create(dto));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumVersionOccupationModuleDto update(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionOccupationModuleDto dto,
            @WithEntity CurriculumVersionOccupationModule occupationModule) {
        validate(user, EntityUtil.getId(occupationModule.getCurriculumVersion()));
        return get(user, curriculumVersionOccupationModuleService.update(dto, occupationModule));
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity CurriculumVersionOccupationModule occupationModule) {
        CurriculumVersion curriculumVersion = curriculumVersionRepository.getOne(EntityUtil.getId(occupationModule.getCurriculumVersion()));
        CurriculumUtil.assertCanDelete(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion.getCurriculum());
        curriculumValidationService.assertCurriculumVersionCanBeDeleted(curriculumVersion);
        curriculumVersionOccupationModuleService.delete(user, occupationModule);
    }


    @GetMapping("/curriculumModule/{id:\\d+}")
    public CurriculumModuleDto getCurriculumModule(@WithEntity CurriculumModule module) {
        return CurriculumModuleDto.forOccupationModule(module);
    }
    
    @GetMapping("/curriculumVersion/{id:\\d+}")
    public AutocompleteResult getCurriculumVersionMin(@WithEntity CurriculumVersion version) {
        return AutocompleteResult.of(version);
    }

    @PostMapping("/theme")
    public CurriculumVersionOccupationModuleThemeDto createTheme(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionOccupationModuleThemeDto dto) {
        validate(user, EntityUtil.getId(em.getReference(CurriculumVersionOccupationModule.class, dto.getModule()).getCurriculumVersion()));
        return curriculumVersionOccupationModuleService.createTheme(dto);
    }

    @GetMapping("/theme/{id:\\d+}")
    public CurriculumVersionOccupationModuleThemeDto getTheme(@WithEntity CurriculumVersionOccupationModuleTheme theme) {
        return CurriculumVersionOccupationModuleThemeDto.of(theme);
    }

    @PutMapping("/theme/{id:\\d+}")
    public CurriculumVersionOccupationModuleThemeDto updateTheme(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionOccupationModuleThemeDto dto,
            @WithEntity CurriculumVersionOccupationModuleTheme theme) {
        validate(user, EntityUtil.getId(theme.getModule().getCurriculumVersion()));
        return curriculumVersionOccupationModuleService.updateTheme(theme, dto);
    }

    @DeleteMapping("/theme/{id:\\d+}")
    public void deleteTHeme(HoisUserDetails user,
            @WithEntity CurriculumVersionOccupationModuleTheme theme) {
        validate(user, EntityUtil.getId(theme.getModule().getCurriculumVersion()));
        curriculumVersionOccupationModuleService.deleteTheme(user, theme);
    }

    @GetMapping("/theme/{id:\\d+}/outcomes")
    public List<CurriculumModuleOutcomeDto> getThemeOutcomes(@WithEntity CurriculumVersionOccupationModuleTheme theme) {
        return curriculumVersionOccupationModuleService.themeOutcomes(theme);
    }

    private void validate(HoisUserDetails user, Long curriculumVersionId) {
        CurriculumVersion curriculumVersion = curriculumVersionRepository.getOne(curriculumVersionId);
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion.getCurriculum());
        curriculumValidationService.assertCurriculumVersionCanBeEdited(curriculumVersion);
    }
}
