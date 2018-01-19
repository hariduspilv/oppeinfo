package ee.hitsa.ois.report.apelapplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalReplacedSubjectOrModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.TranslateUtil;

public class ApelApplicationFormalReplacedSubjectOrModuleReport {

    private final Boolean isVocational;
    private final String name;
    private final String code;
    private final BigDecimal credits;
    private final String module;

    public ApelApplicationFormalReplacedSubjectOrModuleReport(ApelApplicationFormalReplacedSubjectOrModule formalReplacedSubjectOrModule, Language lang) {
        if (formalReplacedSubjectOrModule.getSubject() != null) {
            Subject subject = formalReplacedSubjectOrModule.getSubject();
            CurriculumVersion cv = formalReplacedSubjectOrModule.getApelApplicationRecord().getApelApplication().getStudent().getCurriculumVersion();
            List<CurriculumVersionHigherModuleSubject> higherModuleSubjects = cv.getModules().stream().flatMap(m -> m.getSubjects().stream()).collect(Collectors.toList());
            isVocational = Boolean.FALSE;
            name = TranslateUtil.name(subject, lang);
            credits = subject.getCredits();
            code = subject.getCode();
            CurriculumVersionHigherModuleSubject higherModuleSubject = higherModuleSubjects.stream().filter(s -> s.getSubject().getCode().equals(code)).findFirst().orElse(null);
            module = TranslateUtil.name(higherModuleSubject.getModule(), lang);
        } else {
            isVocational = Boolean.TRUE;
            CurriculumModule curriculumModule = formalReplacedSubjectOrModule.getCurriculumVersionOmodule().getCurriculumModule();
            name = TranslateUtil.name(curriculumModule, lang);
            code = null;
            credits = curriculumModule.getCredits();
            module = null;
        }
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public Boolean isVocational() {
        return isVocational;
    }

    public String getName() {
        return name;
    }
    
    public String getCode() {
        return code;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getModule() {
        return module;
    }
}
