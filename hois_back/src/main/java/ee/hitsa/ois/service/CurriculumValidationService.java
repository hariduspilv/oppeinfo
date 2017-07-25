package ee.hitsa.ois.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.CurriculumValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;

@Service
public class CurriculumValidationService {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private Validator validator;
    
    /**
     * All schools which are joint parters have right to see curriculum in case of joint curriculum.
     */
    public void assertSameOrJoinSchool(HoisUserDetails user, Curriculum curriculum) {
        Set<String> ehisSchools = new HashSet<>();
        ehisSchools.add(EntityUtil.getCode(curriculum.getSchool().getEhisSchool()));
        ehisSchools.addAll(StreamUtil.toMappedList(it -> EntityUtil.getCode(it.getEhisSchool()), curriculum.getJointPartners().stream().filter(it -> it.getEhisSchool() != null)));

        AssertionFailedException.throwIf(!ehisSchools.contains(EntityUtil.getNullableCode(schoolRepository.getOne(user.getSchoolId()).getEhisSchool())), "EHIS school mismatch");
    }
    
    // CurriculumForm validation
    
    public void validateCurriculumFormWithStatusCheck(Curriculum curriculum, CurriculumForm form) {
        if(CurriculumStatus.OPPEKAVA_STAATUS_M.name().equals(EntityUtil.getCode(curriculum.getStatus())) || 
                CurriculumStatus.OPPEKAVA_STAATUS_K.name().equals(EntityUtil.getCode(curriculum.getStatus()))) {
            validateCurriculumForm(curriculum, form);
        }
    }
    
    public void validateCurriculumForm(Curriculum curriculum, CurriculumForm form) {
           
        Set<ConstraintViolation<CurriculumForm>> errors = 
                validator.validate(form, CurriculumValidator.Confirmed.class);
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
        
        boolean isHigher = Boolean.TRUE.equals(curriculum.getHigher());
        if(isHigher) {
            validateHigherCurriculumForm(curriculum, form);
        } else {
            validateVocationalCurriculumForm(curriculum, form);
        }
    }
    
    public void validateHigherCurriculumForm(Curriculum curriculum, CurriculumForm form) {
        Set<ConstraintViolation<CurriculumForm>> errors = validator.validate(form, CurriculumValidator.ConfirmedHigher.class);
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
        if(!hasAnyConfirmedVersion(curriculum)) {
            throw new ValidationFailedException("curriculum.error.noVersion");
        }      
    }

    /**
     * TODO: validate credits
     */
    public void validateVocationalCurriculumForm(Curriculum curriculum, CurriculumForm form) {
        
        Set<ConstraintViolation<CurriculumForm>> errors = validator.validate(form, CurriculumValidator.ConfirmedVocational.class);
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
        if(occupationRequired(curriculum) && !curriculumHasAnyOccupation(form)) {
            throw new ValidationFailedException("curriculum.error.noOccupation");
        }
        if(!anyModuleIsBasic(curriculum.getModules())) {
            throw new ValidationFailedException("curriculum.error.noBasicModule");
        }
        if(curriculumHasAnyOccupation(form)) {
            if(!allModulesHaveOccupation(curriculum.getModules())) {
                throw new ValidationFailedException("curriculum.error.notAllModulesHaveOccupation");
            }
            if(!allOccupationsHaveBasicModule(form, curriculum)) {
                throw new ValidationFailedException("curriculum.error.noPModules");
            }
        }
        if(!hasAnyConfirmedVersion(curriculum)) {
            throw new ValidationFailedException("curriculum.error.noImplementationPlan");
        }
    }
    
    private boolean allModulesHaveOccupation(Set<CurriculumModule> modules) {
        return modules.stream().allMatch(m -> !CollectionUtils.isEmpty(m.getOccupations()));
    }

    private boolean allOccupationsHaveBasicModule(CurriculumForm form, Curriculum curriculum) {
        for(CurriculumOccupationDto dto : form.getOccupations()) {
            if(!occupationHasBasicModule(dto.getOccupation(), curriculum.getModules())) {
                return false;
            }
        }
        return true;
    }

    private boolean occupationHasBasicModule(String occupationCode, Set<CurriculumModule> modules) {
        return getModulesByOccupation(occupationCode, modules).stream().anyMatch(this::moduleIsBasic);
    }
    
    private Set<CurriculumModule> getModulesByOccupation(String occupationCode, Set<CurriculumModule> modules) {
        return modules.stream().filter(m -> {
            Set<String> moduleOccupationCodes = StreamUtil.toMappedSet(o -> EntityUtil.getCode(o.getOccupation()), 
                    m.getOccupations());
            return moduleOccupationCodes.contains(occupationCode);
        }).collect(Collectors.toSet());
    }

    private boolean anyModuleIsBasic(Set<CurriculumModule> modules) {
        return modules.stream().anyMatch(this::moduleIsBasic);
    }
    
    private Boolean moduleIsBasic(CurriculumModule module) {
        return CurriculumModuleType.KUTSEMOODUL_P.name()
                .equals(EntityUtil.getCode(module.getModule()));
    }

    private boolean occupationRequired(Curriculum curriculum) {
        return Arrays.asList(CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_KUTSE.name(), 
                CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_RIIKLIK.name())
                .contains(EntityUtil.getNullableCode(curriculum.getDraft()));
    }
    
    private boolean curriculumHasAnyOccupation(CurriculumForm form) {
        return !CollectionUtils.isEmpty(form.getOccupations());
    }
    
    // Curriculum validation
    
    public void validateCurriculum(Curriculum curriculum) {
        Set<ConstraintViolation<Curriculum>> errors = 
                validator.validate(curriculum, CurriculumValidator.Confirmed.class);
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
        boolean isHigher = Boolean.TRUE.equals(curriculum.getHigher());
        
        if(isHigher) {
            validateHigherCurriculum(curriculum);
        } else {
            validateVocationalCurriculum(curriculum);
        }
    }
    
    private void validateHigherCurriculum(Curriculum curriculum) {
        Set<ConstraintViolation<Curriculum>> errors = 
                validator.validate(curriculum, CurriculumValidator.ConfirmedHigher.class);
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
        if(!hasAnyConfirmedVersion(curriculum)) {
            throw new ValidationFailedException("curriculum.error.noVersion");
        }        
    }
    /**
     * TODO: validate credits
     */
    public void validateVocationalCurriculum(Curriculum curriculum) {
        
        Set<ConstraintViolation<Curriculum>> errors = validator.validate(curriculum, CurriculumValidator.ConfirmedVocational.class);
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
        if(occupationRequired(curriculum) && !curriculumHasAnyOccupation(curriculum)) {
            throw new ValidationFailedException("curriculum.error.noOccupation");
        }
        if(!anyModuleIsBasic(curriculum.getModules())) {
            throw new ValidationFailedException("curriculum.error.noBasicModule");
        }
        if(curriculumHasAnyOccupation(curriculum)) {
            if(!allModulesHaveOccupation(curriculum.getModules())) {
                throw new ValidationFailedException("curriculum.error.notAllModulesHaveOccupation");
            }
            if(!allOccupationsHaveBasicModule(curriculum)) {
                throw new ValidationFailedException("curriculum.error.noPModules");
            }
        }
        if(!hasAnyConfirmedVersion(curriculum)) {
            throw new ValidationFailedException("curriculum.error.noImplementationPlan");
        }
    }
    
    private boolean curriculumHasAnyOccupation(Curriculum curriculum) {
        return !CollectionUtils.isEmpty(curriculum.getOccupations());
    }
    
    private boolean allOccupationsHaveBasicModule(Curriculum curriculum) {
        for(CurriculumOccupation curriculumOccupation : curriculum.getOccupations()) {
          if(!occupationHasBasicModule(EntityUtil.getCode(curriculumOccupation.getOccupation()), curriculum.getModules())) {
              return false;
          }
        }
        return true;
    } 

    public boolean hasAnyConfirmedVersion(Curriculum curriculum) {
        return curriculum.getVersions().stream().anyMatch(v -> 
        CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K.name().equals(EntityUtil.getCode(v.getStatus())));
    }
    
    // Curriculum Version & Implementation Plan validation
    
    public void validateCurriculumVersionWithStatus(CurriculumVersion curriculumVersion) {
        if(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K.name().equals(EntityUtil.getCode(curriculumVersion.getStatus()))) {
            validateCurriculumVersion(curriculumVersion);
        }
    }

    public void validateCurriculumVersion(CurriculumVersion curriculumVersion) {
        Boolean isHigherCurriculum = curriculumVersion.getCurriculum().getHigher();
        if(!isHigherCurriculum && !allOccupationModulesValid(curriculumVersion)) {
            throw new ValidationFailedException("curriculum.error.implementationPlanNotAllModules");
        }
    }
    
    public boolean allOccupationModulesValid(CurriculumVersion implementationPlan) {
        Curriculum curriculum = implementationPlan.getCurriculum();
        if(curriculum.getModules().size() != implementationPlan.getOccupationModules().size()) {
            return false;
        }
        for(CurriculumModule module : curriculum.getModules()) {
            if(!moduleHasOccupationModule(module, implementationPlan)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean moduleHasOccupationModule(CurriculumModule module, CurriculumVersion implementationPlan) {
        Optional<CurriculumVersionOccupationModule> occupationModule = implementationPlan.getOccupationModules().stream()
                .filter(om -> module.equals(om.getCurriculumModule())).findFirst();
        return occupationModule.isPresent();
    }
}
