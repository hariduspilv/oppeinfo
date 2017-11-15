package ee.hitsa.ois.web.curriculum;

import java.util.Set;

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

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.curriculum.CurriculumModuleService;
import ee.hitsa.ois.service.curriculum.CurriculumValidationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumModuleForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumModuleTypesCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;

@RestController
@RequestMapping("curriculumModule")
public class CurriculumModuleController {
    
    @Autowired
    private CurriculumModuleService curriculumModuleService;
    @Autowired
    private CurriculumValidationService curriculumValidationService;
    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private SchoolService schoolService;
    
    @GetMapping("/{id:\\d+}")
    public CurriculumModuleDto get(HoisUserDetails user, @WithEntity("id") CurriculumModule module) {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), module.getCurriculum());
        return CurriculumModuleDto.of(module);
    }
    
    @PostMapping
    public CurriculumModuleDto create(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumModuleForm form) {

        Curriculum curriculum = curriculumRepository.getOne(form.getCurriculum());
        
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);

        return get(user, curriculumModuleService.create(form));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumModuleDto update(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumModuleForm form,
            @WithEntity("id") CurriculumModule module) {
        
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), module.getCurriculum());
        
        curriculumValidationService.assertCurriculumCanBeEdited(module.getCurriculum());
        curriculumValidationService.assertOutcomesBoundWithThemesNotDeleted(module, form.getOutcomes());
        return get(user, curriculumModuleService.update(module, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity("id") CurriculumModule module) {
        
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), module.getCurriculum());
        curriculumValidationService.assertCurriculumCanBeEdited(module.getCurriculum());

        curriculumModuleService.delete(module);
    }
    
    @GetMapping("/curriculumOccupations/{id:\\d+}")
    public Set<CurriculumOccupationDto> getCurriculumOccupations(@WithEntity("id") Curriculum curriculum) {
        return StreamUtil.toMappedSet(CurriculumOccupationDto::of, curriculum.getOccupations());
    }

    @GetMapping("/types")
    public Set<String> getPossibleModuleTypes(@Valid CurriculumModuleTypesCommand command) {
        return curriculumModuleService.getPossibleModuleTypes(command);
    }
    
}
