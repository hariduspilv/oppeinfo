package ee.hitsa.ois.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleThemeRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.CurriculumValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumModuleForm;
import ee.hitsa.ois.web.commandobject.CurriculumVersionHigherModuleForm;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeCapacityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

@Service
public class CurriculumValidationService {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private Validator validator;
    @Autowired 
    private CurriculumService curriculumService;
    @Autowired
    private CurriculumVersionOccupationModuleThemeRepository curriculumVersionOccupationModuleThemeRepository;

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
    
    public void validateCreateCurriculumForm(CurriculumForm form) {
        if(Boolean.FALSE.equals(form.getHigher())) {
            ValidationFailedException.throwOnError(validator.validate(form, CurriculumValidator.Vocational.class));
        }
    }

    public void assertCodeAndMerCodeAreUnique(HoisUserDetails user, CurriculumForm form, Curriculum curriculum) {
        Long id = EntityUtil.getNullableId(curriculum);

        UniqueCommand codeUniqueCommand = new UniqueCommand();
        codeUniqueCommand.setParamName("code");
        codeUniqueCommand.setParamValue(form.getCode());
        codeUniqueCommand.setId(id);

        UniqueCommand merCodeUniqueCommand = new UniqueCommand();
        merCodeUniqueCommand.setParamName("merCode");
        merCodeUniqueCommand.setParamValue(form.getMerCode());
        merCodeUniqueCommand.setId(id);

        if(!curriculumService.isCodeUnique(user.getSchoolId(), codeUniqueCommand) || 
                !curriculumService.isMerCodeUnique(codeUniqueCommand)) {
            throw new ValidationFailedException("main.messages.error.mustBeUnique");
        }
    }

    public void validateCurriculumFormWithStatusCheck(Curriculum curriculum, CurriculumForm form) {
        if(ClassifierUtil.oneOf(curriculum.getStatus(), CurriculumStatus.OPPEKAVA_STAATUS_M, CurriculumStatus.OPPEKAVA_STAATUS_K)) {
            validateCurriculumForm(curriculum, form);
        }
    }

    public void validateCurriculumForm(Curriculum curriculum, CurriculumForm form) {
        ValidationFailedException.throwOnError(validator.validate(form, CurriculumValidator.Confirmed.class));

        if(Boolean.TRUE.equals(curriculum.getJoint())) {
            ValidationFailedException.throwOnError(validator.validate(form, CurriculumValidator.Joint.class));
        }

        boolean isHigher = Boolean.TRUE.equals(curriculum.getHigher());
        if(isHigher) {
            validateHigherCurriculumForm(curriculum, form);
        } else {
            validateVocationalCurriculumForm(curriculum, form);
        }
    }

    public void validateHigherCurriculumForm(Curriculum curriculum, CurriculumForm form) {
        ValidationFailedException.throwOnError(validator.validate(form, CurriculumValidator.ConfirmedHigher.class));

        if(!hasAnyConfirmedVersion(curriculum)) {
            throw new ValidationFailedException("curriculum.error.noVersion");
        }
    }

    /**
     * TODO: validate credits
     */
    public void validateVocationalCurriculumForm(Curriculum curriculum, CurriculumForm form) {
        ValidationFailedException.throwOnError(validator.validate(form, CurriculumValidator.ConfirmedVocational.class));

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

    private static boolean allModulesHaveOccupation(Set<CurriculumModule> modules) {
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

    private static Set<CurriculumModule> getModulesByOccupation(String occupationCode, Set<CurriculumModule> modules) {
        return modules.stream().filter(m -> {
            Set<String> moduleOccupationCodes = StreamUtil.toMappedSet(o -> EntityUtil.getCode(o.getOccupation()), 
                    m.getOccupations());
            return moduleOccupationCodes.contains(occupationCode);
        }).collect(Collectors.toSet());
    }

    private boolean anyModuleIsBasic(Set<CurriculumModule> modules) {
        return modules.stream().anyMatch(this::moduleIsBasic);
    }

    private boolean moduleIsBasic(CurriculumModule module) {
        return ClassifierUtil.equals(CurriculumModuleType.KUTSEMOODUL_P, module.getModule());
    }

    private static boolean occupationRequired(Curriculum curriculum) {
        // TODO use static constant
        return EnumUtil.toNameSet(CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_KUTSE,
                CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_RIIKLIK)
                .contains(EntityUtil.getNullableCode(curriculum.getDraft()));
    }

    private static boolean curriculumHasAnyOccupation(CurriculumForm form) {
        return !CollectionUtils.isEmpty(form.getOccupations());
    }

    // Curriculum validation
    public void validateCurriculum(Curriculum curriculum) {
        ValidationFailedException.throwOnError(validator.validate(curriculum, CurriculumValidator.Confirmed.class));

        if(Boolean.TRUE.equals(curriculum.getJoint())) {
            ValidationFailedException.throwOnError(validator.validate(curriculum, CurriculumValidator.Joint.class));
        }

        if(Boolean.TRUE.equals(curriculum.getHigher())) {
            validateHigherCurriculum(curriculum);
        } else {
            validateVocationalCurriculum(curriculum);
        }
    }
    
    private void validateHigherCurriculum(Curriculum curriculum) {
        ValidationFailedException.throwOnError(validator.validate(curriculum, CurriculumValidator.ConfirmedHigher.class));
        if(!hasAnyConfirmedVersion(curriculum)) {
            throw new ValidationFailedException("curriculum.error.noVersion");
        }        
    }
    /**
     * TODO: validate credits
     */
    public void validateVocationalCurriculum(Curriculum curriculum) {
        ValidationFailedException.throwOnError(validator.validate(curriculum, CurriculumValidator.ConfirmedVocational.class));

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
    
    private static boolean curriculumHasAnyOccupation(Curriculum curriculum) {
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

    private static boolean hasAnyConfirmedVersion(Curriculum curriculum) {
        return curriculum.getVersions().stream().anyMatch(v -> ClassifierUtil.equals(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K, v.getStatus()));
    }

    public void assertCurriculumCanBeDeleted(Curriculum curriculum) {
        if(!ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_S, curriculum.getStatus()) && 
                !ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_M, curriculum.getStatus())) {
            throw new ValidationFailedException("curriculum.error.cannotBeDeleted");
        }
    }
    
    public void assertCurriculumCanBeEdited(Curriculum curriculum) {
        if(ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_C, curriculum.getStatus())) {
            throw new ValidationFailedException("curriculum.error.cannotBeEdited");
        }
    }
    
    // Curriculum Version & Implementation Plan validation
    
    public void validateCurriculumVersionForm(CurriculumVersion curriculumVersion, CurriculumVersionDto form) {
        Boolean isHigherCurriculum = curriculumVersion.getCurriculum().getHigher();
        if(Boolean.TRUE.equals(isHigherCurriculum)) {
            assertCurriculumVersionSpecialitiesHaveModules(form, curriculumVersion);
            assertMinorSpecialitiesHaveSubjects(form);
            assertHigherModulesHaveSubjects(form);
            assertHigherModulesHaveSpecialities(form);
        } else {
            assertAllOccupationModulesValid(curriculumVersion);
            assertAllOccupationModuleThemesValid(form);
        }
    }

    public void validateCurriculumVersion(CurriculumVersion curriculumVersion) {
        Boolean isHigherCurriculum = curriculumVersion.getCurriculum().getHigher();
        if(Boolean.TRUE.equals(isHigherCurriculum)) {
            CurriculumVersionDto dto = CurriculumVersionDto.of(curriculumVersion);
            assertCurriculumVersionSpecialitiesHaveModules(dto, curriculumVersion);
            assertMinorSpecialitiesHaveSubjects(dto);
            assertHigherModulesHaveSubjects(dto);
            assertHigherModulesHaveSpecialities(dto);
        } else {
            assertAllOccupationModulesValid(curriculumVersion);
        }
    }
    
    public void validateCurriculumVersionFormWithStatus(CurriculumVersion curriculumVersion, CurriculumVersionDto dto) {
        if(ClassifierUtil.equals(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K, curriculumVersion.getStatus())) {
            validateCurriculumVersionForm(curriculumVersion, dto);
        }
    }
    
    // Higher Curriculum version validation
    
    
    private static void assertCurriculumVersionSpecialitiesHaveModules(CurriculumVersionDto dto, CurriculumVersion version) {
        for(Long speciality : dto.getSpecialitiesReferenceNumbers()) {
            Set<CurriculumVersionHigherModule> specialitiesModules = getSpecialitiesModules(speciality, version);
            if(CollectionUtils.isEmpty(specialitiesModules)) {
                throw new ValidationFailedException("curriculum.error.noModules");
            }
        }
    }

    private static Set<CurriculumVersionHigherModule> getSpecialitiesModules(Long speciality, CurriculumVersion version) {
        return version.getModules().stream().filter(m -> {
            Set<Long> specialities = StreamUtil.toMappedSet(s -> EntityUtil.getId(s.getSpeciality()
                    .getCurriculumSpeciality()), m.getSpecialities());
            return specialities.contains(speciality);
        }).collect(Collectors.toSet());
    }

    private static void assertMinorSpecialitiesHaveSubjects(CurriculumVersionDto dto) {
        Set<CurriculumVersionHigherModuleDto> minorSpecialities = dto.getModules().stream()
                .filter(m -> Boolean.TRUE.equals(m.getMinorSpeciality())).collect(Collectors.toSet());
        for(CurriculumVersionHigherModuleDto minorSpeciality : minorSpecialities) {
            if(CollectionUtils.isEmpty(minorSpeciality.getSubjects())) {
                throw new ValidationFailedException("curriculum.error.noSubject");
            }
        }
    }
    
    private static void assertHigherModulesHaveSubjects(CurriculumVersionDto dto) {
        Set<CurriculumVersionHigherModuleDto> modules = dto.getModules().stream()
                .filter(m -> !Boolean.TRUE.equals(m.getMinorSpeciality())).collect(Collectors.toSet());
        for(CurriculumVersionHigherModuleDto module : modules) {
            if(higherModuleMustHaveSubjects(module) && CollectionUtils.isEmpty(module.getSubjects())) {
                throw new ValidationFailedException("curriculum.error.noSubject");
            }
        }
    }
    
    private static boolean higherModuleMustHaveSubjects(CurriculumVersionHigherModuleDto module) {
        return !HigherModuleType.KORGMOODUL_V.name().equals(module.getType());
    }
    
    private void assertHigherModulesHaveSpecialities(CurriculumVersionDto form) {
        for(CurriculumVersionHigherModuleDto module : form.getModules()) {
            validateHigherModuleDto(module);
        }
    }
    
    // Vocational Curriculum Implementation Plan validation
    
    public void validateHigherModuleDto(CurriculumVersionHigherModuleDto module) {
        if(Boolean.FALSE.equals(module.getMinorSpeciality())) {
            ValidationFailedException.throwOnError(validator.validate(module, CurriculumValidator.HigherModule.class));
        }
    }

    public void validateHigherModuleForm(CurriculumVersionHigherModuleForm module) {
        if(Boolean.FALSE.equals(module.getMinorSpeciality())) {
            ValidationFailedException.throwOnError(validator.validate(module, CurriculumValidator.HigherModule.class));
        }
    }

    private static void assertAllOccupationModulesValid(CurriculumVersion implementationPlan) {
        Curriculum curriculum = implementationPlan.getCurriculum();
        if(curriculum.getModules().size() != implementationPlan.getOccupationModules().size()) {
            throw new ValidationFailedException("curriculum.error.implementationPlanNotAllModules");
        }
        for(CurriculumModule module : curriculum.getModules()) {
            if(!moduleHasOccupationModule(module, implementationPlan)) {
                throw new ValidationFailedException("curriculum.error.implementationPlanNotAllModules");
            }
        }
    }

    private static void assertAllOccupationModuleThemesValid(CurriculumVersionDto form) {
        for(CurriculumVersionOccupationModuleDto occupationModule : form.getOccupationModules()) {
            
            for(CurriculumVersionOccupationModuleThemeDto theme : occupationModule.getThemes()) {
                assertThemeIsValid(theme);
            }
        }
    }

    private static void assertThemeIsValid(CurriculumVersionOccupationModuleThemeDto theme) {
        if(!hoursAndCreditsMatch(theme)) {
            throw new ValidationFailedException("curriculum.error.themeCreditsAndHoursMismatch");
        }
        if(!hoursAndCapacitiesMatch(theme)) {
            throw new ValidationFailedException("curriculum.error.themeCapacitiesAndHoursMismatch");
        }
    }

    private static boolean hoursAndCreditsMatch(CurriculumVersionOccupationModuleThemeDto theme) {
        return theme.getCredits().multiply(CurriculumUtil.HOURS_PER_EKAP)
                .compareTo(BigDecimal.valueOf(theme.getHours().longValue())) == 0;
    }

    private static boolean hoursAndCapacitiesMatch(CurriculumVersionOccupationModuleThemeDto theme) {
        if(CollectionUtils.isEmpty(theme.getCapacities())) {
            return true;
        }
        return getSumOfThemesCapacities(theme).compareTo(theme.getHours()) == 0;
    }

    private static Short getSumOfThemesCapacities(CurriculumVersionOccupationModuleThemeDto theme) {
        int capacitiesSum = 0;
        for(CurriculumVersionOccupationModuleThemeCapacityDto capacity : theme.getCapacities()) {
            capacitiesSum += capacity.getHours().shortValue();
        }
        return Short.valueOf((short)capacitiesSum);
    }

    private static boolean moduleHasOccupationModule(CurriculumModule module, CurriculumVersion implementationPlan) {
        Optional<CurriculumVersionOccupationModule> occupationModule = implementationPlan.getOccupationModules().stream()
                .filter(om -> module.equals(om.getCurriculumModule())).findFirst();
        return occupationModule.isPresent();
    }

    // Common for higher & vocational curriculum versions
    public void assertCurriculumVersionCanDeleted(CurriculumVersion version) {
        if(ClassifierUtil.equals(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C, version.getStatus())) {
            throw new ValidationFailedException("curriculum.error.cannotBeDeleted");
        }
    }

    public void assertCurriculumVersionCanBeEdited(CurriculumVersion version) {
        if(ClassifierUtil.equals(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C, version.getStatus())) {
            throw new ValidationFailedException("curriculum.error.cannotBeEdited");
        }
    }

    public void assertVersionCodeIsUnique(HoisUserDetails user, CurriculumVersionDto dto, CurriculumVersion curriculumVersion) {
        Long id = EntityUtil.getNullableId(curriculumVersion);
        UniqueCommand command = new UniqueCommand();
        command.setParamValue(dto.getCode());
        command.setId(id);

        if(!curriculumService.isVersionUnique(user.getSchoolId(), command)) {
            throw new ValidationFailedException("main.messages.error.mustBeUnique");
        }
    }

    public void assertOutcomesNotBoundWithThemes(List<Long> outcomeIds) {
        if(curriculumVersionOccupationModuleThemeRepository.existsByCurriculumModuleOutcomeIds(outcomeIds)) {
            throw new ValidationFailedException("main.messages.record.referenced");
        }
    }

    public void assertOutcomesBoundWithThemesNotDeleted(CurriculumModule module, CurriculumModuleForm form) {
        List<Long> oldOutcomes = module.getOutcomes().stream()
                .map(EntityUtil::getId).collect(Collectors.toList());
        List<Long> newOutcomes = form.getCurriculumModule().getOutcomes().stream()
                .filter(o -> o.getId() != null)
                .map(o -> o.getId()).collect(Collectors.toList());
        
        List<Long> deletedOutcomes = getDeletedOutcomesIds(oldOutcomes, newOutcomes);
        if(!CollectionUtils.isEmpty(deletedOutcomes)) {
            assertOutcomesNotBoundWithThemes(deletedOutcomes);
        }
    }

    public List<Long> getDeletedOutcomesIds(List<Long> oldList, List<Long> newList) {
        List<Long> deletedList = new ArrayList<>(oldList);
        deletedList.removeAll(newList);
        return deletedList;
    }
}
