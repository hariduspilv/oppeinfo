package ee.hitsa.ois.report.curriculum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        modules.sort(Comparator.comparing(CurriculumModulesTypeModuleReport::getName));
        totalCredits = modules.stream().map(m -> m.getCredits()).collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add));
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