package ee.hitsa.ois.web.curriculum;

import java.util.List;
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
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumModuleForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumModuleTypesCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;

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
    public CurriculumModuleDto get(HoisUserDetails user, @WithEntity CurriculumModule module) {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), module.getCurriculum());
        return CurriculumModuleDto.of(module);
    }
    
    @PostMapping
    public CurriculumModuleDto create(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumModuleForm form) {

        Curriculum curriculum = curriculumRepository.getOne(form.getCurriculum());
        
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);

        return get(user, curriculumModuleService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumModuleDto update(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumModuleForm form,
            @WithEntity CurriculumModule module) {
        
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), module.getCurriculum());
        
        curriculumValidationService.assertCurriculumCanBeEdited(module.getCurriculum());
        curriculumValidationService.assertOutcomesBoundWithThemesNotDeleted(module, form.getOutcomes());
        return get(user, curriculumModuleService.update(user, module, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity CurriculumModule module) {
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), module.getCurriculum());
        curriculumValidationService.assertCurriculumCanBeEdited(module.getCurriculum());

        curriculumModuleService.delete(user, module);
    }

    @GetMapping("/types")
    public Set<String> getPossibleModuleTypes(@Valid CurriculumModuleTypesCommand command) {
        return curriculumModuleService.getPossibleModuleTypes(command);
    }

    @GetMapping("/curriculum/{id:\\d+}")
    public CurriculumDto getCurriculum(@WithEntity Curriculum curriculum) {
        return CurriculumDto.forModuleForm(curriculum);
    }
    
    @GetMapping("/curriculumMin/{id:\\d+}")
    public CurriculumDto getCurriculumMin(@WithEntity Curriculum curriculum) {
        return CurriculumDto.forModuleMinimum(curriculum);
    }
    
    @GetMapping("/competences/curriculum/{id:\\d+}")
    public List<ClassifierSelection> getCompetences(@WithEntity Curriculum curriculum) {
        return curriculumModuleService.getCompetences(curriculum);
    }
}
