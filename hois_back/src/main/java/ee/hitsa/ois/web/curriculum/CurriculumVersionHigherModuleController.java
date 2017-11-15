package ee.hitsa.ois.web.curriculum;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.curriculum.CurriculumValidationService;
import ee.hitsa.ois.service.curriculum.CurriculumVersionHigherModuleService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.curriculum.HigherModuleSubjectCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;

@RestController
@RequestMapping("higherModule")
public class CurriculumVersionHigherModuleController {
        
    @Autowired
    private CurriculumVersionHigherModuleService curriculumVersionHigherModuleService;
    @Autowired
    private CurriculumValidationService curriculumValidationService;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private EntityManager em;
    @Autowired
    private SchoolService schoolService;
    
    @GetMapping("{id:\\d+}")
    public CurriculumVersionHigherModuleDto get(HoisUserDetails user, @WithEntity(value = "id") CurriculumVersionHigherModule module) {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), module.getCurriculumVersion());
        return CurriculumVersionHigherModuleDto.of(module);
    }
    
    @PostMapping
    public CurriculumVersionHigherModuleDto create(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionHigherModuleDto form) {
        validateUserRights(user, form.getCurriculumVersion());
        return CurriculumVersionHigherModuleDto.onlyId(curriculumVersionHigherModuleService.create(form));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumVersionHigherModuleDto update(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionHigherModuleDto form,
            @WithEntity("id") CurriculumVersionHigherModule module) {
        validateUserRights(user, form.getCurriculumVersion());
        return get(user, curriculumVersionHigherModuleService.update(module, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestParam = "version") CurriculumVersionHigherModule module,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        validateUserRights(user, module.getCurriculumVersion().getId());
        curriculumValidationService.assertCurriculumVersionCanBeEdited(module.getCurriculumVersion());
        curriculumVersionHigherModuleService.delete(module);
    }
    
    @DeleteMapping("/subject/{id:\\d+}")
    public void deleteSubject(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestParam = "version") CurriculumVersionHigherModuleSubject subject,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        validateUserRights(user, subject.getModule().getCurriculumVersion().getId());
        curriculumValidationService.assertCurriculumVersionCanBeEdited(subject.getModule().getCurriculumVersion());
        curriculumVersionHigherModuleService.deleteSubject(subject);
    }
    
    /**
     * After deleting subject from curriculum version form,
     * only credits are changed and thus must be updated
     */
    @GetMapping("/credits/{id:\\d+}")
    public CurriculumVersionHigherModuleDto getCredits(@WithEntity(value = "id") CurriculumVersionHigherModule module) {
        return CurriculumVersionHigherModuleDto.credits(module);
    }
    
    @GetMapping("/possibleSubjects")
    public List<CurriculumVersionHigherModuleSubjectDto> getSubjects(HigherModuleSubjectCommand command) {
        return curriculumVersionHigherModuleService.getSubjectsForHigherModule(command);
    }
    
    @GetMapping("/minorSpeciality/possibleSubjects")
    public Set<CurriculumVersionHigherModuleSubjectDto> getSubjectsForMinorSpeciality(HigherModuleSubjectCommand command) {
        return curriculumVersionHigherModuleService.getSubjectsForMinorSpeciality(command);
    }

    @GetMapping("/versionHmoduleTypes/{id:\\d+}")
    public List<ClassifierSelection> getCurriculumVersionHmoduleTypes(@WithEntity(value = "id") CurriculumVersion version) {
        /*
         * is_valid and is_higher is not considered at
         * autocompleteService.classifiers()
         */
        List<ClassifierSelection> classifiers = autocompleteService
                .classifiers(Collections.singletonList(MainClassCode.KORGMOODUL.name()));
        List<ClassifierSelection> otherTypes = curriculumVersionHigherModuleService.getCurriculumVersionHmoduleTypes(
                EntityUtil.getId(version.getCurriculum().getSchool()));
        classifiers.addAll(otherTypes);
        return classifiers;
    }
    
    @GetMapping("/version/{id:\\d+}/specialities")
    public List<AutocompleteResult> getSpecialities(@WithEntity(value = "id") CurriculumVersion version) {
        return curriculumVersionHigherModuleService.getSpecialities(version);
    }
    
    public void validateUserRights(HoisUserDetails user, Long curriculumVersionId) {
        CurriculumVersion version = em.getReference(CurriculumVersion.class, curriculumVersionId);
        Curriculum curriculum = version.getCurriculum();
        String myEhisShool = schoolService.getEhisSchool(user.getSchoolId());
        CurriculumUtil.assertCanChange(user, myEhisShool, curriculum);
    }

}
