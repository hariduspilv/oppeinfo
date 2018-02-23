package ee.hitsa.ois.report.apelapplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModuleOutcomes;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class ApelApplicationInformalSubjectOrModuleReport {
    
    private final String name;
    private final String module;
    private final Boolean isCompulsory;
    private final String skills;
    private final String grade;
    private final Short hours;
    private final BigDecimal credits;
    private final List<String> outcomes;
    private final Boolean transfer;

    public ApelApplicationInformalSubjectOrModuleReport(ApelApplicationInformalSubjectOrModule informalSubjectOrModule, Language lang) {
        if (informalSubjectOrModule.getSubject() != null) {
            name = TranslateUtil.name(informalSubjectOrModule.getSubject(), lang);
            module = TranslateUtil.name(informalSubjectOrModule.getCurriculumVersionHmodule(), lang);
            credits = informalSubjectOrModule.getSubject().getCredits();
            hours = null;
            outcomes = null;
        } else if (informalSubjectOrModule.getCurriculumVersionOmodule() != null && informalSubjectOrModule.getCurriculumVersionOmoduleTheme() == null) {
            CurriculumModule curriculumModule = informalSubjectOrModule.getCurriculumVersionOmodule().getCurriculumModule();
            name = TranslateUtil.name(curriculumModule, lang);
            List<CurriculumVersionOccupationModuleTheme> themes = new ArrayList<>(informalSubjectOrModule.getCurriculumVersionOmodule().getThemes());
            credits = informalSubjectOrModule.getCurriculumVersionOmodule().getCurriculumModule().getCredits();
            hours = Short.valueOf((short) themes.stream().mapToInt(t -> t.getHours().intValue()).sum());
            outcomes = sortedOutcomes(informalSubjectOrModule.getOutcomes(), lang);
            module = null;
        } else {
            CurriculumModule curriculumModule = informalSubjectOrModule.getCurriculumVersionOmodule().getCurriculumModule();
            name = curriculumModule.getNameEt() + "/" + informalSubjectOrModule.getCurriculumVersionOmoduleTheme().getNameEt();
            credits = informalSubjectOrModule.getCurriculumVersionOmoduleTheme().getCredits();
            hours = informalSubjectOrModule.getCurriculumVersionOmoduleTheme().getHours();
            outcomes = sortedOutcomes(informalSubjectOrModule.getOutcomes(), lang);
            module = null;
        }
        isCompulsory = Boolean.valueOf(!informalSubjectOrModule.getIsOptional().booleanValue());
        skills = informalSubjectOrModule.getSkills();
        grade = informalSubjectOrModule.getGrade().getValue();
        transfer = informalSubjectOrModule.getTransfer();
    }

    public String getName() {
        return name;
    }

    public String getModule() {
        return module;
    }

    public Boolean getIsCompulsory() {
        return isCompulsory;
    }

    public String getSkills() {
        return skills;
    }

    public String getGrade() {
        return grade;
    }
    
    public Short getHours() {
        return hours;
    }

    public BigDecimal getCredits() {
        return credits;
    }
    

    public List<String> getOutcomes() {
        return outcomes;
    }

    public Boolean getTransfer() {
        return transfer;
    }

    private static String outcomeName(CurriculumModuleOutcome outcome, Language lang) {
        return  Language.EN.equals(lang) ? outcome.getOutcomeEn() : outcome.getOutcomeEt();
    }
    
    private static List<String> sortedOutcomes(List<ApelApplicationInformalSubjectOrModuleOutcomes> outcomes, Language lang) {
        List<CurriculumModuleOutcome> moduleOutcomes = StreamUtil.toMappedList(o -> o.getCurriculumModuleOutcomes(), outcomes);
        moduleOutcomes.sort(Comparator.comparing(CurriculumModuleOutcome::getOrderNr)
                .thenComparing(Language.EN.equals(lang) ? CurriculumModuleOutcome::getOutcomeEn : CurriculumModuleOutcome::getOutcomeEt));
        return StreamUtil.toMappedList(o -> outcomeName(o, lang), moduleOutcomes);
    }
}
