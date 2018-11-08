package ee.hitsa.ois.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.basemodule.BaseModule;
import ee.hitsa.ois.domain.basemodule.BaseModuleCapacity;
import ee.hitsa.ois.domain.basemodule.BaseModuleOutcomes;
import ee.hitsa.ois.domain.basemodule.BaseModuleTheme;
import ee.hitsa.ois.domain.basemodule.BaseModuleThemeCapacity;
import ee.hitsa.ois.domain.basemodule.BaseModuleThemeOutcomes;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleThemeCapacity;

public class BaseModuleUtil {
    
    /*
     * Capacity
     */
    
    public static CurriculumVersionOccupationModuleCapacity updateCapacity(BaseModuleCapacity source, CurriculumVersionOccupationModuleCapacity target) {
        target.setCapacityType(source.getCapacityType());
        target.setHours(source.getHours());
        target.setContact(source.getContact());
        return target;
    }
    
    public static BaseModuleCapacity updateCapacity(CurriculumVersionOccupationModuleCapacity source, BaseModuleCapacity target) {
        target.setCapacityType(source.getCapacityType());
        target.setHours(source.getHours());
        target.setContact(source.getContact());
        return target;
    }
    
    public static CurriculumVersionOccupationModuleCapacity capacityTransform(BaseModuleCapacity capacity, CurriculumVersionOccupationModule cv) {
        CurriculumVersionOccupationModuleCapacity cap = new CurriculumVersionOccupationModuleCapacity();
        cap.setModule(cv);
        return updateCapacity(capacity, cap);
    }
    
    public static BaseModuleCapacity capacityTransform(CurriculumVersionOccupationModuleCapacity capacity, BaseModule module) {
        BaseModuleCapacity cap = new BaseModuleCapacity();
        cap.setBaseModule(module);
        return updateCapacity(capacity, cap);
    }
    
    /*
     * Theme
     */

    public static CurriculumVersionOccupationModuleTheme updateTheme(BaseModuleTheme theme, CurriculumVersionOccupationModuleTheme cvTheme) {
        cvTheme.setNameEt(theme.getNameEt());
        cvTheme.setAssessment(theme.getAssessment());
        cvTheme.setCredits(theme.getCredits());
        cvTheme.setHours(theme.getHours());
        cvTheme.setSubthemes(theme.getSubthemes());
        cvTheme.setTotalGradeDescription(theme.getTotalGradeDescription());
        cvTheme.setPassDescription(theme.getPassDescription());
        cvTheme.setGrade3Description(theme.getGrade3Description());
        cvTheme.setGrade4Description(theme.getGrade4Description());
        cvTheme.setGrade5Description(theme.getGrade5Description());
        
        cvTheme.setCapacities(StreamUtil.toMappedSet(cap -> BaseModuleUtil.themeCapacityTransform(cap, cvTheme), theme.getCapacities()));
        cvTheme.setOutcomes(StreamUtil.toMappedSet(con -> BaseModuleUtil.themeOutcomeTransform(con.getBaseModuleOutcomes(), cvTheme), theme.getBaseModuleThemeOutcomes()));
        
        return cvTheme;
        
    }
    
    public static BaseModuleTheme updateTheme(CurriculumVersionOccupationModuleTheme theme, BaseModuleTheme bTheme) {
        bTheme.setNameEt(theme.getNameEt());
        bTheme.setCredits(theme.getCredits());
        bTheme.setHours(theme.getHours());
        bTheme.setSubthemes(theme.getSubthemes());
        bTheme.setTotalGradeDescription(theme.getTotalGradeDescription());
        bTheme.setPassDescription(theme.getPassDescription());
        bTheme.setGrade3Description(theme.getGrade3Description());
        bTheme.setGrade4Description(theme.getGrade4Description());
        bTheme.setGrade5Description(theme.getGrade5Description());
        bTheme.setAssessment(theme.getAssessment());
        
        bTheme.setCapacities(StreamUtil.toMappedSet(cap -> BaseModuleUtil.themeCapacityTransform(cap, bTheme), theme.getCapacities()));
        bTheme.setBaseModuleThemeOutcomes(StreamUtil.toMappedSet(out -> BaseModuleUtil.themeOutcomeTransform(out.getOutcome().getBaseModuleOutcomes(), bTheme), theme.getOutcomes()));
        
        return bTheme;
    }
    
    public static CurriculumVersionOccupationModuleTheme themeTransform(BaseModuleTheme theme, CurriculumVersionOccupationModule cv) {
        CurriculumVersionOccupationModuleTheme cvTheme = new CurriculumVersionOccupationModuleTheme();
        cvTheme.setModule(cv);
        updateTheme(theme, cvTheme);
        cv.getThemes().add(cvTheme);
        cvTheme.setBaseModuleTheme(theme);
        return cvTheme;
    }
    
    public static BaseModuleTheme themeTransform(CurriculumVersionOccupationModuleTheme theme, BaseModule module) {
        BaseModuleTheme bTheme = new BaseModuleTheme();
        updateTheme(theme, bTheme);
        bTheme.setBaseModule(module);
        theme.setBaseModuleTheme(bTheme);
        return bTheme;
    }
    
    /*
     * Theme <-> Outcome
     */
    
    public static CurriculumVersionOccupationModuleOutcome themeOutcomeTransform(BaseModuleOutcomes outcome, CurriculumVersionOccupationModuleTheme theme) {
        CurriculumVersionOccupationModuleOutcome con = new CurriculumVersionOccupationModuleOutcome();
        Optional<CurriculumModuleOutcome> out = outcome.getOutcomes().stream().filter(o -> o.getCurriculumModule().getId().equals(theme.getModule().getCurriculumModule().getId())).findFirst();
        con.setOutcome(out.orElseThrow(() -> new IllegalArgumentException()));
        theme.getOutcomes().add(con);
        return con;
    }
    
    public static CurriculumVersionOccupationModuleOutcome themeOutcomeTransform(CurriculumModuleOutcome outcome, CurriculumVersionOccupationModuleTheme theme) {
        CurriculumVersionOccupationModuleOutcome con = new CurriculumVersionOccupationModuleOutcome();
        con.setOutcome(outcome);
        theme.getOutcomes().add(con);
        return con;
    }
    
    public static BaseModuleThemeOutcomes themeOutcomeTransform(BaseModuleOutcomes outcome, BaseModuleTheme theme) {
        BaseModuleThemeOutcomes con = new BaseModuleThemeOutcomes();
        con.setBaseModuleOutcomes(outcome);
        con.setBaseModuleTheme(theme);
        return con;
    }
    
    public static CurriculumVersionOccupationModuleThemeCapacity themeCapacityTransform(BaseModuleThemeCapacity capacity, CurriculumVersionOccupationModuleTheme theme) {
        CurriculumVersionOccupationModuleThemeCapacity cap = new CurriculumVersionOccupationModuleThemeCapacity();
        cap.setCapacityType(capacity.getCapacityType());
        cap.setHours(capacity.getHours());
        cap.setContact(capacity.getContact());
        cap.setTheme(theme);
        return cap;
    }
    
    public static BaseModuleThemeCapacity themeCapacityTransform(CurriculumVersionOccupationModuleThemeCapacity capacity, BaseModuleTheme theme) {
        BaseModuleThemeCapacity cap = new BaseModuleThemeCapacity();
        cap.setCapacityType(capacity.getCapacityType());
        cap.setHours(capacity.getHours());
        cap.setContact(capacity.getContact());
        cap.setBaseModuleTheme(theme);
        return cap;
    }
    
    /*
     * Outcomes
     */
    
    public static CurriculumModuleOutcome updateOutcome(BaseModuleOutcomes source, CurriculumModuleOutcome target) {
        target.setOutcomeEt(source.getOutcomeEt());
        target.setOutcomeEn(source.getOutcomeEn());
        if (source.getOrderNr() != null) {
            target.setOrderNr(Long.valueOf(source.getOrderNr().longValue()));
        }
        return target;
    }
    
    public static BaseModuleOutcomes updateOutcome(CurriculumModuleOutcome source, BaseModuleOutcomes target) {
        target.setOutcomeEt(source.getOutcomeEt());
        target.setOutcomeEn(source.getOutcomeEn());
        if (source.getOrderNr() != null) {
            target.setOrderNr(Integer.valueOf(source.getOrderNr().intValue()));
        }
        return target;
    }
    
    public static CurriculumModuleOutcome outcomeTransform(BaseModuleOutcomes outcome, CurriculumModule module, EntityManager em) {
        CurriculumModuleOutcome out = new CurriculumModuleOutcome();
        module.getOutcomes().add(out);
        out.setCurriculumModule(module);
        outcome.getOutcomes().add(out);
        out.setBaseModuleOutcomes(outcome);
        EntityUtil.save(outcome, em);
        return updateOutcome(outcome, out);
    }
    
    public static BaseModuleOutcomes outcomeTransform(CurriculumModuleOutcome outcome, BaseModule module) {
        BaseModuleOutcomes out = new BaseModuleOutcomes();
        out.setBaseModule(module);
        outcome.setBaseModuleOutcomes(out);
        return updateOutcome(outcome, out);
    }
    
    public static void updateReferences(BaseModule module, Set<CurriculumModule> curriculumModules, Set<CurriculumVersionOccupationModule> curriculumVersions, EntityManager em) {
        curriculumModules.forEach(cm -> {
            cm.setNameEt(module.getNameEt());
            cm.setNameEn(module.getNameEn());
            cm.setCredits(module.getCredits());
            cm.setObjectivesEt(module.getObjectivesEt());
            cm.setObjectivesEn(module.getObjectivesEn());
            cm.setAssessmentsEt(module.getAssessmentsEt());
            cm.setAssessmentsEn(module.getAssessmentsEn());
            cm.setOutcomes(cm.getOutcomes().stream().filter(out -> out.getBaseModuleOutcomes() != null).collect(Collectors.toSet()));
            EntityUtil.bindEntityCollection(cOut -> EntityUtil.deleteEntity(cOut, em), cm.getOutcomes(), out -> out.getBaseModuleOutcomes().getId(),
                    module.getOutcomes(), out -> out.getId(), bOut -> BaseModuleUtil.outcomeTransform(bOut, cm, em));
            cm.getOutcomes().forEach(out -> BaseModuleUtil.updateOutcome(out.getBaseModuleOutcomes(), out));
        });
        
        curriculumVersions.forEach(cv -> {
            cv.setRequirementsEt(module.getCvRequirementsEt());
            cv.setAssessmentsEt(module.getCvAssessmentsEt());
            cv.setLearningMethodsEt(module.getCvLearningMethodsEt());
            cv.setAssessmentMethodsEt(module.getCvAssessmentMethodsEt());
            cv.setIndependentStudyEt(module.getCvIndependentStudyEt());
            cv.setStudyMaterials(module.getCvStudyMaterials());
            cv.setTotalGradeDescription(module.getCvTotalGradeDescription());
            cv.setPassDescription(module.getCvPassDescription());
            cv.setGrade3Description(module.getCvGrade3Description());
            cv.setGrade4Description(module.getCvGrade4Description());
            cv.setGrade5Description(module.getCvGrade5Description());
            cv.setAssessment(module.getCvAssessment());
            cv.setTeacher(module.getTeacher());
            cv.setSupervisor(module.getTeacher().getPerson().getFullname());
        });
    }
    
    public static void updateReferences(BaseModule module, EntityManager em) {
        updateReferences(module, module.getCurriculumModules(), module.getCurriculumVersionOModules(), em);
    }
    
    public static void updateReferences(BaseModuleTheme theme, boolean updateCapacity) {
        Set<CurriculumVersionOccupationModuleTheme> themes = theme.getThemes();
        Set<BaseModuleOutcomes> outcomes = StreamUtil.toMappedSet(out -> out.getBaseModuleOutcomes(), theme.getBaseModuleThemeOutcomes());
        themes.forEach(t -> {
            t.setNameEt(theme.getNameEt());
            if (updateCapacity) {
                t.setCredits(theme.getCredits());
                t.setHours(theme.getHours());
                t.setCapacities(StreamUtil.toMappedSet(cap -> BaseModuleUtil.themeCapacityTransform(cap, t), theme.getCapacities()));
            }
            t.setSubthemes(theme.getSubthemes());
            t.setAssessment(theme.getAssessment());
            t.setTotalGradeDescription(theme.getTotalGradeDescription());
            t.setPassDescription(theme.getPassDescription());
            t.setGrade3Description(theme.getGrade3Description());
            t.setGrade4Description(theme.getGrade4Description());
            t.setGrade5Description(theme.getGrade5Description());
            t.setOutcomes(StreamUtil.toMappedSet(out -> BaseModuleUtil.themeOutcomeTransform(out, t), outcomes));
        });
    }
    
    public static void updateBaseModuleCapacities(BaseModule module) {
        Set<BaseModuleTheme> themes = module.getThemes();

        HashMap<Classifier, BaseModuleCapacity> capacities = new HashMap<>();
        if (!themes.isEmpty()) {
            for (BaseModuleTheme theme : themes) {
                for (BaseModuleThemeCapacity cap : theme.getCapacities()) {
                    if (capacities.containsKey(cap.getCapacityType())) {
                        capacities.get(cap.getCapacityType()).setHours(
                            Short.valueOf((short)(capacities.get(cap.getCapacityType()).getHours().shortValue() + cap.getHours().shortValue()))
                        );
                    } else {
                        BaseModuleCapacity newCap = createModuleCapacity(cap.getCapacityType(), cap.getHours(), module);
                        capacities.put(cap.getCapacityType(), newCap);
                    }
                    if (cap.getContact().equals(Boolean.TRUE)) {
                        capacities.get(cap.getCapacityType()).setContact(cap.getContact());
                    }
                }
            }
        }
        module.setCapacities(new HashSet<>(capacities.values()));
    }
    
    private static BaseModuleCapacity createModuleCapacity(Classifier type, Short hours, BaseModule module) {
        BaseModuleCapacity newCapacity = new BaseModuleCapacity();
        newCapacity.setCapacityType(type);
        newCapacity.setContact(Boolean.FALSE);
        newCapacity.setHours(hours);
        newCapacity.setBaseModule(module);
        return newCapacity;
    }
}
