package ee.hitsa.ois.report.curriculum;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class CurriculumVersionModuleTypeModuleReport {

    private final String name;
    private Map<Short, BigDecimal> studyYearCredits = new HashMap<>();
    private final BigDecimal totalCredits;

    public CurriculumVersionModuleTypeModuleReport(CurriculumVersionOccupationModule occupationModule, Language lang) {
        name = TranslateUtil.name(occupationModule.getCurriculumModule(), lang);
        studyYearCredits = StreamUtil.toMap(om -> om.getStudyYearNumber(), om -> om.getCredits(), occupationModule.getYearCapacities());
        totalCredits = studyYearCredits.values().stream().collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add));
    }

    public String getName() {
        return name;
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
    
}
