package ee.hitsa.ois.report.curriculum;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.StreamUtil;

public class CurriculumModulesReport {
    
    public static final String VOCATIONAL_TEMPLATE_NAME = "curriculum.modules.vocational.xhtml";
    
    private final List<CurriculumModulesTypeReport> moduleTypes = new ArrayList<>();
    

    public CurriculumModulesReport(Curriculum curriculum) {
        this(curriculum, Language.ET);
    }
    
    public CurriculumModulesReport(Curriculum curriculum, Language lang) {
        List<String> curriculumModuleTypes = EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_P,
                CurriculumModuleType.KUTSEMOODUL_Y, CurriculumModuleType.KUTSEMOODUL_V,
                CurriculumModuleType.KUTSEMOODUL_L);
        
        for (String type : curriculumModuleTypes) {
            List<CurriculumModule> modules = StreamUtil.toFilteredList(
                    m -> EntityUtil.getCode(m.getModule()).equals(type), curriculum.getModules());
            moduleTypes.add(new CurriculumModulesTypeReport(type, modules, lang));
        }
    }

    public List<CurriculumModulesTypeReport> getModuleTypes() {
        return moduleTypes;
    }
    
}
