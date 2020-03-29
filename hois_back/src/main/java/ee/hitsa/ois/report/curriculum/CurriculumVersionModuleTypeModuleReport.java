package ee.hitsa.ois.report.curriculum;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class CurriculumVersionModuleTypeModuleReport {

    private final String name;
    private Map<Short, BigDecimal> studyYearCredits = new HashMap<>();
    private final BigDecimal totalCredits;

    public CurriculumVersionModuleTypeModuleReport(CurriculumVersionOccupationModule occupationModule, List<Short> studyYears, Language lang) {
        name = TranslateUtil.name(occupationModule.getCurriculumModule(), lang);
        studyYearCredits = StreamUtil.toMap(om -> om.getStudyYearNumber(), om -> om.getCredits(), StreamUtil
                .toFilteredList(om -> studyYears.contains(om.getStudyYearNumber()), occupationModule.getYearCapacities()));
        totalCredits = StreamUtil.sumBigDecimals(sy -> sy, studyYearCredits.values());
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
