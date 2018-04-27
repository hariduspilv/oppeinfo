package ee.hitsa.ois.report.curriculum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;

public class CurriculumVersionModuleTypeReport {
    
    private final String code;
    private List<CurriculumVersionModuleTypeModuleReport> modules = new ArrayList<>();
    private Map<Short, BigDecimal> studyYearCredits = new HashMap<>();
    private final BigDecimal totalCredits;
    
    public CurriculumVersionModuleTypeReport(String code, List<CurriculumVersionOccupationModule> occupationModules, List<Short> studyYears, Language lang) {
        this.code = code;
        modules = StreamUtil.toMappedList(m -> new CurriculumVersionModuleTypeModuleReport(m, lang), occupationModules);
        modules.sort(Comparator.comparing(CurriculumVersionModuleTypeModuleReport::getName));
        
        for (Short year : studyYears) {
            studyYearCredits.put(year, BigDecimal.ZERO);
        }
        
        for (CurriculumVersionModuleTypeModuleReport module : modules) {
            Map<Short, BigDecimal> moduleStudyYearCredits = module.getStudyYearCredits();
            for (Short key : moduleStudyYearCredits.keySet()) {
                BigDecimal credits = moduleStudyYearCredits.get(key);
                if (credits != null) {
                    studyYearCredits.put(key, studyYearCredits.get(key).add(credits));
                }
            }
        }
        totalCredits = studyYearCredits.values().stream().collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add));
    }

    public String getCode() {
        return code;
    }

    public Map<Short, BigDecimal> getStudyYearCredits() {
        return studyYearCredits;
    }
    
    public void setStudyYearCredits(Map<Short, BigDecimal> studyYearCredits) {
        this.studyYearCredits = studyYearCredits;
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }

    public List<CurriculumVersionModuleTypeModuleReport> getModules() {
        return modules;
    }

    public void setModules(List<CurriculumVersionModuleTypeModuleReport> modules) {
        this.modules = modules;
    }
    
}
