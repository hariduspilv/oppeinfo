package ee.hitsa.ois.service.basemodule;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ee.hitsa.ois.web.dto.PersonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.basemodule.BaseModule;
import ee.hitsa.ois.domain.basemodule.BaseModuleCapacity;
import ee.hitsa.ois.domain.basemodule.BaseModuleOutcomes;
import ee.hitsa.ois.domain.basemodule.BaseModuleTheme;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.VocationalGradeType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.BaseModuleUserRights;
import ee.hitsa.ois.util.BaseModuleUtil;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.OccupationModuleCapacitiesUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleForm;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleReplaceCommand;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleReplaceForm;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleCapacityDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleOutcomesDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleSearchDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleThemeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

@Transactional
@Service
public class BaseModuleService {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private BaseModuleThemeService baseModuleThemeService;
    @Autowired
    private ClassifierRepository classifierRepository;
    
    /*
     * Main methods
     */

    public Page<BaseModuleSearchDto> search(HoisUserDetails user, BaseModuleSearchCommand cmd, Pageable pageable) {
        StringBuilder from = new StringBuilder();
        from.append("from base_module b ");
        from.append("left join curriculum_module cm on cm.base_module_id = b.id ");
        from.append("left join curriculum c on c.id = cm.curriculum_id ");
        from.append("left join curriculum_version_omodule cvom on cvom.base_module_id = b.id ");
        from.append("left join curriculum_version cv on cv.id = cvom.curriculum_version_id ");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort(pageable).groupBy("b.id");
        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains(Arrays.asList("b.name_et", "b.name_en"), "searchName", cmd.getName());
        qb.optionalContains("b.add_name_et", "additionalName", cmd.getAddNameEt());
        qb.optionalCriteria("b.valid_from >= :from", "from", cmd.getValidFrom());
        qb.optionalCriteria("b.valid_thru <= :thru", "thru", cmd.getValidThru());
        qb.optionalCriteria("c.id = :curriculumId", "curriculumId", (cmd.getCurriculum() != null ? cmd.getCurriculum().getId() : null));
        qb.optionalCriteria("cv.id = :curriculumVersionId", "curriculumVersionId", (cmd.getCurriculumVersion() != null ? cmd.getCurriculumVersion().getId() : null));
        // Here we have 2 ways. This is the first one to get every curriculum and curriculumVersion data as well. But we have a problem with duplicated values
        // It might be not a problem when we use Set to remove duplicates, but there is a possibility to have 2 items with the same name, but they are different.
        // The second way to deal with it: request from DB separately information about curriculums and curriculum versions. Might be a problem with performance.
        return JpaQueryUtil.pagingResult(qb, "b.id, b.name_et, b.name_en, b.credits, string_agg(c.code, ',') as curriculums,"
                + " string_agg(cv.code, ',') as curriculum_versions, b.school_id, b.add_name_et", em, pageable).map(r -> {
            BaseModuleSearchDto dto = new BaseModuleSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setNameEn(resultAsString(r, 2));
            dto.setCredits(resultAsDecimal(r, 3));
            String curriculums = resultAsString(r, 4);
            if (curriculums != null) {
                dto.setCurriculums(new LinkedHashSet<>(Arrays.asList(curriculums.split(","))));
            }
            String cVersions = resultAsString(r, 5);
            if (cVersions != null) {
                dto.setCurriculumVersions(new LinkedHashSet<>(Arrays.asList(cVersions.split(","))));
            }
            dto.setCanEdit(Boolean.valueOf(BaseModuleUserRights.canEdit(user, em.getReference(School.class, resultAsLong(r, 6)))));
            dto.setAddNameEt(resultAsString(r, 7));
            return dto;
        });
    }

    public BaseModuleDto get(BaseModule module) {
        BaseModuleDto dto = EntityUtil.bindToDto(module, new BaseModuleDto(),
                "school", "teacher", "outcomes", "capacities", "themes", "curriculumVersionOModules", "curriculumModules");
        
        dto.setTeacher(BaseModuleDto.Teacher.of(module.getTeacher()));
        dto.setOutcomes(StreamUtil.toMappedSet(BaseModuleOutcomesDto::of, module.getOutcomes()));
        dto.setCapacities(StreamUtil.toMappedSet(BaseModuleCapacityDto::of, module.getCapacities()));
        dto.setThemes(StreamUtil.toMappedSet(baseModuleThemeService::get, module.getThemes()));
        
        Set<AutocompleteResult> curriculums = new HashSet<>();
        Map<Long, Set<AutocompleteResult>> cvs = new HashMap<>();
        
        module.getCurriculumModules().forEach(cm -> {
            AutocompleteResult of = AutocompleteResult.of(cm.getCurriculum());
            curriculums.add(of);
            if (cvs.containsKey(of.getId())) {
                cvs.get(of.getId()).addAll(cm.getCurriculumVersionOccupationModules().stream()
                        .filter(cv -> cv.getBaseModule() != null && cv.getBaseModule().getId() == cm.getBaseModule().getId())
                        .map(cv -> AutocompleteResult.of(cv.getCurriculumVersion())).collect(Collectors.toSet()));
            } else {
                cvs.put(of.getId(), cm.getCurriculumVersionOccupationModules().stream()
                        .filter(cv -> cv.getBaseModule() != null && cv.getBaseModule().getId() == cm.getBaseModule().getId())
                        .map(cv -> AutocompleteResult.of(cv.getCurriculumVersion())).collect(Collectors.toSet())
                );
            }
        });
        dto.setCurriculums(curriculums);
        dto.setCurriculumVersions(cvs);
        return dto;
    }
    
    public BaseModule create(HoisUserDetails user, BaseModuleForm baseModuleForm) {
        BaseModule baseModule = new BaseModule();
        baseModule.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(user, baseModuleForm, baseModule);
    }
    
    public BaseModule save(HoisUserDetails user, BaseModuleForm form, BaseModule module) {
        EntityUtil.setUsername(user.getUsername(), em);
        module.setTeacher(em.getReference(Teacher.class, form.getTeacher().getId()));
        EntityUtil.bindToEntity(form, module, classifierRepository, "school", "teacher", "outcomes", "capacities", "themes");
        
        if (!ClassifierUtil.equals(VocationalGradeType.KUTSEHINDAMISVIIS_E, module.getCvAssessment())) {
            module.setCvGrade3Description(null);
            module.setCvGrade4Description(null);
            module.setCvGrade5Description(null);
        } 
       
        if (!ClassifierUtil.equals(VocationalGradeType.KUTSEHINDAMISVIIS_M, module.getCvAssessment())) {
            module.setCvPassDescription(null);
        }
        
        if (module.getCvAssessment() == null) {
            module.setCvTotalGradeDescription(null);
        }
        
        updateOutcomes(module, form.getOutcomes());
        if (module.getThemes().isEmpty()) {
            updateCapacities(module, form.getCapacities().stream().filter(cap -> cap.getHours() != null).collect(Collectors.toSet()));
        }
        
        BaseModuleUtil.updateReferences(module, em);
        
        return EntityUtil.save(module, em);
    }

    public void delete(BaseModule module) {
        module.getCurriculumModules().forEach(cm -> cm.setBaseModule(null));
        module.getCurriculumVersionOModules().forEach(cv -> cv.setBaseModule(null));
        module.getOutcomes().forEach(out -> out.getOutcomes().forEach(outcome -> outcome.setBaseModuleOutcomes(null)));
        module.getThemes().forEach(t -> t.getThemes().forEach(theme -> theme.setBaseModuleTheme(null)));
        EntityUtil.deleteEntity(module, em);
    }

    public Set<AutocompleteResult> getExpired(HoisUserDetails user, boolean hasCurriculumModules) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from base_module b");
        if (hasCurriculumModules) {
            qb.requiredCriteria("b.school_id = :schoolId and exists (select c.id from curriculum_module c where c.base_module_id = b.id) ", "schoolId", user.getSchoolId());
        } else {
            qb.requiredCriteria("b.school_id = :schoolId", "schoolId", user.getSchoolId());
        }
        qb.requiredCriteria("b.valid_thru < :now", "now", LocalDate.now());
        List<?> results = qb.select("b.id, b.name_et, b.name_en", em).getResultList();
        return StreamUtil.toMappedSet(r -> {
            return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2));
        }, results);
    }
    
    /*
     * Collection methods
     */
    
    /*
     * Capacities
     */
    
    public void updateCapacities(BaseModule module, Set<BaseModuleCapacityDto> capacities) {
        EntityUtil.bindEntityCollection(module.getCapacities(), BaseModuleCapacity::getId,
                capacities, BaseModuleCapacityDto::getId, dto -> createCapacity(module, dto), this::updateCapacity);
    }
    
    private BaseModuleCapacity createCapacity(BaseModule module, BaseModuleCapacityDto dto) {
        BaseModuleCapacity capacity = new BaseModuleCapacity();
        capacity.setBaseModule(module);
        return updateCapacity(dto, capacity);
    }
    
    private BaseModuleCapacity updateCapacity(BaseModuleCapacityDto dto, BaseModuleCapacity capacity) {
        return EntityUtil.bindToEntity(dto, capacity, classifierRepository);
    }
    
    /*
     * Outcomes
     */
    
    public void updateOutcomes(BaseModule module, Set<BaseModuleOutcomesDto> outcomes) {
        EntityUtil.bindEntityCollection(module.getOutcomes(), BaseModuleOutcomes::getId,
            outcomes, BaseModuleOutcomesDto::getId, dto -> createOutcome(module, dto), this::updateOutcome,
            baseOut -> {
                List<CurriculumModuleOutcome> currOuts = baseOut.getOutcomes();
                currOuts.forEach(outcome -> {
                    JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_module_outcomes cmo").limit(1);
                    qb.requiredCriteria("cmo.id = :outcomeId and ("
                            + "exists (select je.id from journal_entry je where je.curriculum_module_outcomes_id = cmo.id) "
                            + "or exists (select a.id from apel_application_informal_subject_or_module_outcomes a where a.curriculum_module_outcomes_id = cmo.id)"
                            + ")"
                            , "outcomeId", outcome.getId());
                    if (!qb.select("cmo.id", em).getResultList().isEmpty()) {
                        throw new ValidationFailedException("basemodule.errors.outcomehasconnections");
                    }
                    
                    outcome.getThemeOutcomes().forEach(con -> EntityUtil.deleteEntity(con, em));
                    EntityUtil.deleteEntity(outcome, em);
                });
            }
        );
    }
    
    private BaseModuleOutcomes createOutcome(BaseModule module, BaseModuleOutcomesDto dto) {
        BaseModuleOutcomes outcome = new BaseModuleOutcomes();
        outcome.setBaseModule(module);
        updateOutcome(dto, outcome);
        module.getCurriculumModules().forEach(cm -> BaseModuleUtil.outcomeTransform(outcome, cm, em));
        return outcome;
    }
    
    private BaseModuleOutcomes updateOutcome(BaseModuleOutcomesDto dto, BaseModuleOutcomes outcome) {
        return EntityUtil.bindToEntity(dto, outcome, classifierRepository);
    }
    
    /*
     * Additional methods
     */

    public List<PersonResult> getTeachers(Long schoolId, TeacherAutocompleteCommand lookup) {
        return autocompleteService.teachers(schoolId, lookup, true);
    }

    public void releaseReferences(CurriculumModule cModule) {
        cModule.setBaseModule(null);
        cModule.getOutcomes().forEach(out -> out.setBaseModuleOutcomes(null));
        cModule.getCurriculumVersionOccupationModules().forEach(cv -> cv.setBaseModule(null));
        cModule.getCurriculumVersionOccupationModules().forEach(cv -> cv.getThemes().forEach(t -> t.setBaseModuleTheme(null)));
        EntityUtil.save(cModule, em);
    }
    
    public void releaseReferences(CurriculumVersionOccupationModule oModule) {
        oModule.setBaseModule(null);
        oModule.getThemes().forEach(t -> t.setBaseModuleTheme(null));
        EntityUtil.save(oModule, em);
    }

    /**
     * Create {@link BaseModule} using {@link CurriculumModule} and {@link CurriculumVersionOccupationModule}
     * 
     * @param user
     * @param cModule
     * @param oModule
     * @return
     */
    public BaseModule generate(HoisUserDetails user, CurriculumModule cModule,
            CurriculumVersionOccupationModule oModule) {
        EntityUtil.setUsername(user.getUsername(), em);
        
        BaseModule module = new BaseModule();
        
        module.setSchool(em.getReference(School.class, user.getSchoolId()));
        module.setValidFrom(LocalDate.now());

        copyCurriculumModuleData(cModule, module);
        copyCurriculumVersionOccupationModuleData(oModule, module);
        
        cModule.setBaseModule(module);
        oModule.setBaseModule(module);
        
        BaseModuleUtil.updateBaseModuleCapacities(module);
        return EntityUtil.save(module, em);
    }
    
    public BaseModuleReplaceForm getReplaceForm(BaseModule bModule, CurriculumModule cModule, CurriculumVersionOccupationModule oModule) {
        BaseModuleReplaceForm form = new BaseModuleReplaceForm();
        form.setBaseModule(AutocompleteResult.of(bModule));
        form.setBaseModuleThemes(StreamUtil.toMappedSet(BaseModuleThemeDto::ofMin, bModule.getThemes()));
        form.setCurriculumModule(AutocompleteResult.of(cModule));
        Set<CurriculumVersionOccupationModule> oModules = (oModule != null ? Collections.singleton(oModule) : new HashSet<>());
        Map<Long, Set<CurriculumVersionOccupationModuleThemeDto>> themes = new HashMap<>();
        if (oModule == null) {
            oModules = cModule.getCurriculumVersionOccupationModules().stream().filter(cvom -> cvom.getBaseModule() == null).collect(Collectors.toSet());
        }
        oModules.forEach(cvom -> {
            if (cvom.getThemes().size() <= bModule.getThemes().size()) {
                themes.put(cvom.getId(), StreamUtil.toMappedSet(CurriculumVersionOccupationModuleThemeDto::of, cvom.getThemes()));
            }
        });
        form.setOccupationModules(StreamUtil.toMappedSet(CurriculumVersionOccupationModuleDto::ofMin, oModules));
        form.setThemes(themes);
        if (bModule.getOutcomes().size() >= cModule.getOutcomes().size()) {
            form.setBaseModuleOutcomes(StreamUtil.toMappedSet(AutocompleteResult::of, bModule.getOutcomes()));
            form.setOutcomes(StreamUtil.toMappedSet(AutocompleteResult::of, cModule.getOutcomes()));
        }
        return form;
    }
    
    /**
     * NB! Works only for vocational type.
     * 
     * @param user
     * @param cmd
     * @return
     */
    public BaseModule replaceByBaseModule(HoisUserDetails user, BaseModuleReplaceCommand cmd) {
        EntityUtil.setUsername(user.getUsername(), em);
        BaseModule bModule = em.getReference(BaseModule.class, cmd.getBaseModule());
        CurriculumModule cModule = em.getReference(CurriculumModule.class, cmd.getCurriculumModule());
        
        if (cModule.getBaseModule() != null && !cModule.getBaseModule().getId().equals(cmd.getBaseModule())) {
            throw new ValidationFailedException("basemodule.errors.differentids");
        }
        
        if (cModule.getBaseModule() == null) {
            cModule.setBaseModule(bModule);
            Map<Long, BaseModuleOutcomes> currentOutcomes = bModule.getOutcomes().stream().collect(Collectors.toMap(BaseModuleOutcomes::getId, o -> o));
            cmd.getOutcomeReferences().entrySet().forEach(entry -> { // key = old outcome, value = base outcome
                BaseModuleOutcomes baseModuleOutcomes = currentOutcomes.remove(entry.getValue());
                if (baseModuleOutcomes == null) {
                    throw new ValidationFailedException("basemodule.errors.oneToOneOutcome");
                }
                CurriculumModuleOutcome outcome = em.getReference(CurriculumModuleOutcome.class, entry.getKey());
                outcome.setBaseModuleOutcomes(baseModuleOutcomes);
                baseModuleOutcomes.getOutcomes().add(outcome);
                BaseModuleUtil.updateOutcome(baseModuleOutcomes, outcome);
                EntityUtil.save(outcome, em);
            });
            if (!currentOutcomes.isEmpty()) {
                currentOutcomes.values().forEach(outcome -> {
                    BaseModuleUtil.outcomeTransform(outcome, cModule, em);
                });
            }
            EntityUtil.save(cModule, em);
        }

        Set<CurriculumVersionOccupationModule> cvs = StreamUtil.toMappedSet(id -> em.getReference(CurriculumVersionOccupationModule.class, id), cmd.getCurriculumVersionOModules());
        cvs.forEach(cv -> {
            cv.setBaseModule(bModule);
            Map<Long, BaseModuleTheme> currentThemes = bModule.getThemes().stream().collect(Collectors.toMap(BaseModuleTheme::getId, t -> t)); 
            cmd.getThemeReferences().get(cv.getId()).forEach((oldThemeId, baseThemeId) -> {
                BaseModuleTheme baseModuleTheme = currentThemes.remove(baseThemeId);
                if (baseModuleTheme == null) {
                    throw new ValidationFailedException("basemodule.errors.oneToOneTheme"); // Can be made also if there is no such element
                }
                CurriculumVersionOccupationModuleTheme theme = em.getReference(CurriculumVersionOccupationModuleTheme.class, oldThemeId);
                theme.setBaseModuleTheme(baseModuleTheme);
                BaseModuleUtil.updateTheme(baseModuleTheme, theme);
            });
            if (!currentThemes.isEmpty()) {
                currentThemes.values().forEach(theme -> {
                    BaseModuleUtil.themeTransform(theme, cv);
                });
            }
            if (bModule.getThemes().isEmpty()) {
                BaseModuleUtil.updateCapacities(bModule, cv);
            } else {
                OccupationModuleCapacitiesUtil.updateModuleCapacities(cv, baseModuleThemeService.getCapacityTypes(bModule.getSchool().getId()));
            }
            EntityUtil.save(cv, em);
        });
        
        BaseModuleUtil.updateReferences(bModule, Collections.singleton(cModule), cvs, em);
        EntityUtil.save(bModule, em);
        
        return bModule;
    }
    
    private static void copyCurriculumModuleData(CurriculumModule cModule, BaseModule module) {
        module.setNameEt(cModule.getNameEt() == null ? "-" : cModule.getNameEt());
        module.setNameEn(cModule.getNameEn());
        module.setCredits(cModule.getCredits());
        module.setObjectivesEt(cModule.getObjectivesEt() == null ? "-" : cModule.getObjectivesEt());
        module.setObjectivesEn(cModule.getObjectivesEn());
        module.setAssessmentsEt(cModule.getAssessmentsEt() == null ? "-" : cModule.getAssessmentsEt());
        module.setAssessmentsEn(cModule.getAssessmentsEn());
        module.setOutcomes(StreamUtil.toMappedSet(outcome -> BaseModuleUtil.outcomeTransform(outcome, module), cModule.getOutcomes()));
    }
    
    private static void copyCurriculumVersionOccupationModuleData(CurriculumVersionOccupationModule oModule, BaseModule module) {
        module.setCvRequirementsEt(oModule.getRequirementsEt() == null ? "-" : oModule.getRequirementsEt());
        module.setCvAssessmentsEt(oModule.getAssessmentsEt() == null ? "-" : oModule.getAssessmentsEt());
        module.setCvLearningMethodsEt(oModule.getLearningMethodsEt());
        module.setCvAssessmentMethodsEt(oModule.getAssessmentMethodsEt());
        module.setCvAssessment(oModule.getAssessment());
        module.setCvTotalGradeDescription(oModule.getTotalGradeDescription() == null ? "-" : oModule.getTotalGradeDescription());
        module.setCvPassDescription(oModule.getPassDescription());
        module.setCvGrade3Description(oModule.getGrade3Description());
        module.setCvGrade4Description(oModule.getGrade4Description());
        module.setCvGrade5Description(oModule.getGrade5Description());
        module.setCvIndependentStudyEt(oModule.getIndependentStudyEt());
        module.setCvStudyMaterials(oModule.getStudyMaterials());
        module.setTeacher(oModule.getTeacher());
        if (module.getTeacher() == null) {
            throw new ValidationFailedException("basemodule.errors.noteacher");
        }
        
        module.setCapacities(StreamUtil.toMappedSet(cap -> BaseModuleUtil.capacityTransform(cap, module), oModule.getCapacities()));
        module.setThemes(StreamUtil.toMappedSet(theme -> BaseModuleUtil.themeTransform(theme, module), oModule.getThemes()));
    }
}
