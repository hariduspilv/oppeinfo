package ee.hitsa.ois.report.curriculum;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class CurriculumVersionReport {
    
    public static final String VOCATIONAL_TEMPLATE_NAME = "curriculum.version.vocational.xhtml";
    
    private final String school;
    private final String curriculumName;
    private List<Short> studyYears = new ArrayList<>();
    private final List<CurriculumVersionModuleTypeReport> moduleTypes = new ArrayList<>();
    
    public CurriculumVersionReport(CurriculumVersion curriculumVersion) {
        this(curriculumVersion, Language.ET);
    }
    
    public CurriculumVersionReport(CurriculumVersion curriculumVersion, Language lang) {
        Curriculum curriculum = curriculumVersion.getCurriculum();
        school = TranslateUtil.name(curriculum.getSchool(), lang);
        curriculumName = TranslateUtil.name(curriculum, lang);
        
        short studyYearsCount = (short)CurriculumUtil.studyYears(curriculumVersion.getCurriculum());
        for (short i = 1; i <= studyYearsCount; i++) {
            studyYears.add(Short.valueOf(i));
        }
        
        List<String> curriculumModuleTypes = EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_P,
                CurriculumModuleType.KUTSEMOODUL_Y, CurriculumModuleType.KUTSEMOODUL_V,
                CurriculumModuleType.KUTSEMOODUL_L);
        
        for (String type : curriculumModuleTypes) {
            List<CurriculumVersionOccupationModule> modules = StreamUtil.toFilteredList(
                    om -> EntityUtil.getCode(om.getCurriculumModule().getModule()).equals(type),
                    curriculumVersion.getOccupationModules());
            moduleTypes.add(new CurriculumVersionModuleTypeReport(type, modules, studyYears, lang));
        }
    }
    
    public String getSchool() {
        return school;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public List<Short> getStudyYears() {
        return studyYears;
    }
    
    public void setStudyYears(List<Short> studyYears) {
        this.studyYears = studyYears;
    }

    public List<CurriculumVersionModuleTypeReport> getModuleTypes() {
        return moduleTypes;
    }
    
}
