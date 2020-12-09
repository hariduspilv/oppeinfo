package ee.hitsa.ois.report.apelapplication;

import java.math.BigDecimal;
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

    public ApelApplicationFormalReplacedSubjectOrModuleReport(
            ApelApplicationFormalReplacedSubjectOrModule subjectOrModule, Language lang) {
        if (subjectOrModule.getSubject() != null) {
            Subject subject = subjectOrModule.getSubject();
            isVocational = Boolean.FALSE;
            name = TranslateUtil.name(subject, lang);
            credits = subject.getCredits();
            code = subject.getCode();
        } else {
            isVocational = Boolean.TRUE;
            CurriculumModule curriculumModule = subjectOrModule.getCurriculumVersionOmodule().getCurriculumModule();
            CurriculumVersionOccupationModuleTheme theme = subjectOrModule.getCurriculumVersionOmoduleTheme();
            name = TranslateUtil.name(curriculumModule, lang) + (theme != null ? "/" + theme.getNameEt() : "") ;
            code = null;
            credits = theme != null ? theme.getCredits() : curriculumModule.getCredits();
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

}
