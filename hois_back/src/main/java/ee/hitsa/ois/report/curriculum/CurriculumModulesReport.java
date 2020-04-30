package ee.hitsa.ois.report.curriculum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.util.EntityUtil;

public class CurriculumModulesReport {
    
    public static final String VOCATIONAL_TEMPLATE_NAME = "curriculum.modules.vocational.xhtml";
    
    private final List<CurriculumModulesTypeReport> moduleTypes = new ArrayList<>();
    

    public CurriculumModulesReport(Curriculum curriculum) {
        this(curriculum, Language.ET);
    }
    
    public CurriculumModulesReport(Curriculum curriculum, Language lang) {
        for (String type : ReportUtil.CURRICULUM_MODULE_ORDER) {
            List<CurriculumModule> modules = curriculum.getModules().stream()
                    .filter(m -> EntityUtil.getCode(m.getModule()).equals(type))
                    .sorted(Comparator
                            .comparing(CurriculumModule::getOrderNr, Comparator.nullsLast(Comparator.naturalOrder()))
                            .thenComparing(
                                    Comparator.comparing(CurriculumModule::getNameEt, String.CASE_INSENSITIVE_ORDER)))
                    .collect(Collectors.toList());
            moduleTypes.add(new CurriculumModulesTypeReport(type, modules, lang));
        }
    }

    public List<CurriculumModulesTypeReport> getModuleTypes() {
        return moduleTypes;
    }
    
}
