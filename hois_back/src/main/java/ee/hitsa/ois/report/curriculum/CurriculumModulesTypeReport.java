package ee.hitsa.ois.report.curriculum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;

public class CurriculumModulesTypeReport {
    
    
    private final String code;
    private List<CurriculumModulesTypeModuleReport> modules = new ArrayList<>();
    private final BigDecimal totalCredits;
    
    public CurriculumModulesTypeReport(String code, List<CurriculumModule> curriculumModules, Language lang) {
        this.code = code;
        modules = StreamUtil.toMappedList(cm -> new CurriculumModulesTypeModuleReport(cm, lang), curriculumModules);
        totalCredits = StreamUtil.sumBigDecimals(CurriculumModulesTypeModuleReport::getCredits, modules);
    }

    public String getCode() {
        return code;
    }

    public List<CurriculumModulesTypeModuleReport> getModules() {
        return modules;
    }
    
    public void setModules(List<CurriculumModulesTypeModuleReport> modules) {
        this.modules = modules;
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }

}