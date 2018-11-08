package ee.hitsa.ois.service.basemodule;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.basemodule.BaseModule;
import ee.hitsa.ois.domain.basemodule.BaseModuleOutcomes;
import ee.hitsa.ois.domain.basemodule.BaseModuleTheme;
import ee.hitsa.ois.domain.basemodule.BaseModuleThemeCapacity;
import ee.hitsa.ois.domain.basemodule.BaseModuleThemeOutcomes;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.enums.VocationalGradeType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.BaseModuleUtil;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.OccupationModuleCapacitiesUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.BaseModuleThemeForm;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleOutcomesDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleThemeCapacityDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleThemeDto;

@Transactional
@Service
public class BaseModuleThemeService {


    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private AutocompleteService autocompleteService;
    
    public List<Classifier> getCapacityTypes(Long schoolId) {
        SchoolCapacityTypeCommand command = new SchoolCapacityTypeCommand();
        command.setIsHigher(Boolean.FALSE);
        return autocompleteService.schoolCapacityTypes(schoolId, command);
    }
    
    public BaseModuleThemeDto get(BaseModuleTheme theme) {
        BaseModuleThemeDto dto = EntityUtil.bindToDto(theme, new BaseModuleThemeDto(),
                "baseModule", "capacities", "baseModuleThemeOutcomes");
        dto.setCapacities(StreamUtil.toMappedSet(BaseModuleThemeCapacityDto::of, theme.getCapacities()));
        dto.setOutcomes(StreamUtil.toMappedSet(connection -> BaseModuleOutcomesDto.of(connection.getBaseModuleOutcomes()),
                theme.getBaseModuleThemeOutcomes()));
        
        // Give base module id and outcomes.
        BaseModuleDto baseModule = new BaseModuleDto();
        baseModule.setId(theme.getBaseModule().getId());
        baseModule.setOutcomes(StreamUtil.toMappedSet(BaseModuleOutcomesDto::of, theme.getBaseModule().getOutcomes()));
        dto.setBaseModule(baseModule);
        return dto;
    }
    
    public BaseModuleThemeDto getEmptyTheme(BaseModule module) {
        BaseModuleThemeDto dto = new BaseModuleThemeDto();
        BaseModuleDto moduleDto = new BaseModuleDto();
        moduleDto.setId(module.getId());
        moduleDto.setOutcomes(StreamUtil.toMappedSet(BaseModuleOutcomesDto::of, module.getOutcomes()));
        dto.setBaseModule(moduleDto);
        return dto;
    }

    public BaseModuleTheme save(HoisUserDetails user, BaseModuleThemeForm form, BaseModuleTheme theme) {

        EntityUtil.bindToEntity(form, theme, classifierRepository, "baseModule", "capacities", "outcomes", "credits", "hours");
        
        if (!ClassifierUtil.equals(VocationalGradeType.KUTSEHINDAMISVIIS_E, theme.getAssessment())) {
            theme.setGrade3Description(null);
            theme.setGrade4Description(null);
            theme.setGrade5Description(null);
        }
       
        if (!ClassifierUtil.equals(VocationalGradeType.KUTSEHINDAMISVIIS_M, theme.getAssessment())) {
            theme.setPassDescription(null);
        }
        
        if (theme.getAssessment() == null) {
            theme.setTotalGradeDescription(null);
        }

        updateCapacities(theme, form.getCapacities().stream().filter(cap -> cap.getHours() != null).collect(Collectors.toSet()));
        updateOutcomes(theme, form.getOutcomes());

        if ((theme.getCredits() != null && form.getCredits().compareTo(theme.getCredits()) == 0) && form.getHours() == theme.getHours()) {
            BaseModuleUtil.updateReferences(theme, false);
        } else {
            theme.setCredits(form.getCredits());
            theme.setHours(form.getHours());
            BaseModuleUtil.updateReferences(theme, true);
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(theme, em);
        
        BaseModuleUtil.updateBaseModuleCapacities(theme.getBaseModule());
        theme.getBaseModule().getCurriculumVersionOModules().forEach(cv -> {
            if (!cv.getThemes().stream().filter(t -> t.getBaseModuleTheme().getId().equals(theme.getId())).findFirst().isPresent()) {
                BaseModuleUtil.themeTransform(theme, cv);
            }
            OccupationModuleCapacitiesUtil.updateModuleCapacities(cv, getCapacityTypes(user.getSchoolId()));
            EntityUtil.save(cv, em);
        });
        EntityUtil.save(theme.getBaseModule(), em);
        
        return theme;
    }

    public BaseModuleTheme create(HoisUserDetails user, BaseModuleThemeForm form) {
        BaseModuleTheme theme = new BaseModuleTheme();
        BaseModule module = em.getReference(BaseModule.class, form.getBaseModule().getId());
        theme.setBaseModule(module);
        module.getThemes().add(theme);
        return save(user, form, theme);
    }
    
    public void delete(BaseModuleTheme theme) {

        theme.getThemes().forEach(t -> {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version_omodule_theme t").limit(1);
            qb.requiredCriteria("t.id = :themeId and ("
                    + "exists (select jot.id from journal_omodule_theme jot where jot.curriculum_version_omodule_theme_id = t.id) "
                    + "or exists (select c.id from contract c where c.curriculum_version_omodule_theme_id = t.id) "
                    + "or exists (select pj.id from practice_journal pj where pj.curriculum_version_omodule_theme_id = t.id) "
                    + "or exists (select a.id from apel_application_informal_subject_or_module a where a.curriculum_version_omodule_theme_id = t.id)"
                    + ")"
                    , "themeId", t.getId());
            if (!qb.select("t.id", em).getResultList().isEmpty()) {
                throw new ValidationFailedException("basemodule.errors.themehasconnections");
            }
        });
        
        Long moduleId = theme.getBaseModule().getId();
        theme.getThemes().forEach(t -> EntityUtil.deleteEntity(t, em));
        EntityUtil.deleteEntity(theme, em);
        BaseModule module = em.getReference(BaseModule.class, moduleId);
        BaseModuleUtil.updateBaseModuleCapacities(module);
        EntityUtil.save(module, em);
    }
    
    /*
     * Capacities
     */
    
    public void updateCapacities(BaseModuleTheme theme, Set<BaseModuleThemeCapacityDto> capacities) {
        EntityUtil.bindEntityCollection(theme.getCapacities(), BaseModuleThemeCapacity::getId,
                capacities, BaseModuleThemeCapacityDto::getId,
                dto -> createCapacity(theme, dto), this::updateCapacity);
    }
    
    private BaseModuleThemeCapacity createCapacity(BaseModuleTheme theme, BaseModuleThemeCapacityDto dto) {
        BaseModuleThemeCapacity capacity = new BaseModuleThemeCapacity();
        capacity.setBaseModuleTheme(theme);
        return updateCapacity(dto, capacity);
    }
    
    private BaseModuleThemeCapacity updateCapacity(BaseModuleThemeCapacityDto dto, BaseModuleThemeCapacity capacity) {
        return EntityUtil.bindToEntity(dto, capacity, classifierRepository);
    }
    
    /*
     * Outcomes
     */
    
    /**
     * Logically, there is no way for {@link CurriculumVersionOccupationModuleTheme} to have any other
     * {@link CurriculumVersionOccupationModuleOutcome} which is not duplicated in {@link BaseModuleThemeOutcomes}.
     * So we will only remove these connection which are removed from {@link BaseModuleTheme}.
     * If we need to make a new {@link BaseModuleThemeOutcomes} then we will make new {@link CurriculumVersionOccupationModuleOutcome}
     * for every {@link CurriculumVersionOccupationModuleTheme} which is connected to {@link BaseModuleTheme} 
     * 
     * @param theme
     * @param outcomes
     */
    public void updateOutcomes(BaseModuleTheme theme, Set<BaseModuleOutcomesDto> outcomes) {
        EntityUtil.bindEntityCollection(
            oldConnection -> {
                oldConnection.getBaseModuleTheme().getThemes().forEach(cvTheme -> {
                    cvTheme.setOutcomes(cvTheme.getOutcomes().stream()
                            .filter(cvCon -> !cvCon.getOutcome().getBaseModuleOutcomes().getId().equals(oldConnection.getBaseModuleOutcomes().getId()))
                            .collect(Collectors.toSet()));
                });
            },
            theme.getBaseModuleThemeOutcomes(), con -> con.getBaseModuleOutcomes().getId(),
            outcomes, outcome -> outcome.getId(),
            outcome -> {
                BaseModuleThemeOutcomes con = new BaseModuleThemeOutcomes();
                con.setBaseModuleTheme(theme);
                con.setBaseModuleOutcomes(em.getReference(BaseModuleOutcomes.class, outcome.getId()));
                theme.getThemes().forEach(cvTheme -> {
                    Optional<CurriculumVersionOccupationModuleOutcome> optCon = cvTheme.getOutcomes().stream().filter(cvCon -> cvCon.getOutcome().getBaseModuleOutcomes().getId().equals(outcome.getId())).findFirst();
                    if (optCon.isPresent()) {
                        cvTheme.getOutcomes().add(BaseModuleUtil.themeOutcomeTransform(optCon.get().getOutcome(), cvTheme));
                    }
                });
                return con;
            }
        );
    }
    
    
}
