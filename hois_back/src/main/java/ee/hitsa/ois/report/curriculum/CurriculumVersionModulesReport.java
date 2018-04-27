package ee.hitsa.ois.report.curriculum;

import java.util.Comparator;
import java.util.List;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class CurriculumVersionModulesReport {
    
    public static final String VOCATIONAL_TEMPLATE_NAME = "curriculum.version.modules.vocational.xhtml";
    
    private final String school;
    private final String curriculumName;
    private final String targetGroup;
    private final String studyForm;
    private final List<CurriculumVersionModuleReport> modules;
    
    public CurriculumVersionModulesReport(CurriculumVersion curriculumVersion) {
        this(curriculumVersion, Language.ET);
    }
    
    public CurriculumVersionModulesReport(CurriculumVersion curriculumVersion, Language lang) {
        Curriculum curriculum = curriculumVersion.getCurriculum();
        school = TranslateUtil.name(curriculum.getSchool(), lang);
        curriculumName = TranslateUtil.name(curriculum, lang);
        targetGroup = curriculumVersion.getTargetGroup();
        studyForm = TranslateUtil.name(curriculumVersion.getCurriculumStudyForm().getStudyForm(), lang);
        modules = StreamUtil.toMappedList(m -> new CurriculumVersionModuleReport(m, lang), curriculumVersion.getOccupationModules());
        modules.sort(Comparator.comparing(CurriculumVersionModuleReport::getName));
    }

    public String getSchool() {
        return school;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public List<CurriculumVersionModuleReport> getModules() {
        return modules;
    }
}
