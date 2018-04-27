package ee.hitsa.ois.report.curriculum;

import java.math.BigDecimal;
import java.util.List;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class CurriculumVersionModuleReport {
    
    private final String name;
    private final BigDecimal credits;
    private final String teachers;
    private final String requirements;
    private final String objective;
    private final List<String> outcomes;
    private final String criterias;
    private final List<CurriculumVersionModuleThemeReport> themes;
    private final String independentStudy;
    private final String practice;
    private final String learningMethods;
    private final String assessment;
    private final String gradeCriteria3;
    private final String gradeCriteria4;
    private final String gradeCriteria5;
    private final String gradeCriteriaPass;
    private final String totalGradeDescription;
    private final String assessmentMethods;
    private final String studyMaterials;

    public CurriculumVersionModuleReport(CurriculumVersionOccupationModule occupationModule, Language lang) {
        CurriculumModule module = occupationModule.getCurriculumModule();
        name = TranslateUtil.name(module, lang);
        credits = module.getCredits();
        teachers = occupationModule.getSupervisor();
        requirements = occupationModule.getRequirementsEt();
        objective = Language.EN.equals(lang) ? module.getObjectivesEn() : module.getObjectivesEt();
        outcomes = StreamUtil.toMappedList(o -> Language.EN.equals(lang) ? o.getOutcomeEn() : o.getOutcomeEt(), module.getOutcomes());
        criterias = occupationModule.getAssessmentMethodsEt();
        themes = StreamUtil.toMappedList(t -> new CurriculumVersionModuleThemeReport(t), occupationModule.getThemes());
        independentStudy = occupationModule.getIndependentStudyEt();
        practice = null; //TODO remains empty until someone demands something
        learningMethods = occupationModule.getLearningMethodsEt();
        assessment = TranslateUtil.name(occupationModule.getAssessment(), lang);
        gradeCriteria3 = occupationModule.getGrade3Description();
        gradeCriteria4 = occupationModule.getGrade4Description();
        gradeCriteria5 = occupationModule.getGrade5Description();
        gradeCriteriaPass = occupationModule.getPassDescription();
        totalGradeDescription = occupationModule.getTotalGradeDescription();
        assessmentMethods = occupationModule.getAssessmentMethodsEt();
        studyMaterials = occupationModule.getStudyMaterials();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getTeachers() {
        return teachers;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getObjective() {
        return objective;
    }

    public List<String> getOutcomes() {
        return outcomes;
    }

    public String getCriterias() {
        return criterias;
    }

    public List<CurriculumVersionModuleThemeReport> getThemes() {
        return themes;
    }

    public String getIndependentStudy() {
        return independentStudy;
    }

    public String getPractice() {
        return practice;
    }

    public String getLearningMethods() {
        return learningMethods;
    }

    public String getAssessment() {
        return assessment;
    }
    
    public String getGradeCriteria3() {
        return gradeCriteria3;
    }

    public String getGradeCriteria4() {
        return gradeCriteria4;
    }

    public String getGradeCriteria5() {
        return gradeCriteria5;
    }

    public String getGradeCriteriaPass() {
        return gradeCriteriaPass;
    }

    public String getTotalGradeDescription() {
        return totalGradeDescription;
    }

    public String getAssessmentMethods() {
        return assessmentMethods;
    }

    public String getStudyMaterials() {
        return studyMaterials;
    }
    
}
