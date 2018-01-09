package ee.hitsa.ois.report.apelapplication;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.enums.Language;

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
            name = Language.EN.equals(lang) ? informalSubjectOrModule.getSubject().getNameEn() : informalSubjectOrModule.getSubject().getNameEt();
            module = Language.EN.equals(lang) ? informalSubjectOrModule.getCurriculumVersionHmodule().getNameEn() : informalSubjectOrModule.getCurriculumVersionHmodule().getNameEt();
            credits = informalSubjectOrModule.getSubject().getCredits();
            hours = null;
            outcomes = null;
        } else if (informalSubjectOrModule.getCurriculumVersionOmodule() != null && informalSubjectOrModule.getCurriculumVersionOmoduleTheme() == null) {
            CurriculumModule curriculumModule = informalSubjectOrModule.getCurriculumVersionOmodule().getCurriculumModule();
            name = Language.EN.equals(lang) ? curriculumModule.getNameEn() : curriculumModule.getNameEt();
            List<CurriculumVersionOccupationModuleTheme> themes = informalSubjectOrModule.getCurriculumVersionOmodule().getThemes().stream().collect(Collectors.toList());
            credits = themes.stream().map(t -> t.getCredits()).reduce(BigDecimal.ZERO, BigDecimal::add);
            hours = Short.valueOf((short) themes.stream().map(t -> t.getHours()).mapToInt(i -> i).sum());
            outcomes = Language.EN.equals(lang) ? 
                    informalSubjectOrModule.getOutcomes().stream().map(o -> o.getCurriculumModuleOutcomes().getOutcomeEn()).collect(Collectors.toList()) : 
                    informalSubjectOrModule.getOutcomes().stream().map(o -> o.getCurriculumModuleOutcomes().getOutcomeEt()).collect(Collectors.toList());
            module = null;
        } else {
            CurriculumModule curriculumModule = informalSubjectOrModule.getCurriculumVersionOmodule().getCurriculumModule();
            name = curriculumModule.getNameEt() + "/" + informalSubjectOrModule.getCurriculumVersionOmoduleTheme().getNameEt();
            credits = informalSubjectOrModule.getCurriculumVersionOmoduleTheme().getCredits();
            hours = informalSubjectOrModule.getCurriculumVersionOmoduleTheme().getHours();
            outcomes = Language.EN.equals(lang) ? 
                    informalSubjectOrModule.getOutcomes().stream().map(o -> o.getCurriculumModuleOutcomes().getOutcomeEn()).collect(Collectors.toList()) : 
                    informalSubjectOrModule.getOutcomes().stream().map(o -> o.getCurriculumModuleOutcomes().getOutcomeEt()).collect(Collectors.toList());
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
    
}
