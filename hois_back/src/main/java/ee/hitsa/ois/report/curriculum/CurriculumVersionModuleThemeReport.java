package ee.hitsa.ois.report.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;

public class CurriculumVersionModuleThemeReport {
    
    private final String name;
    private final Short hours;
    private final String subThemes;
    
    public CurriculumVersionModuleThemeReport(CurriculumVersionOccupationModuleTheme theme) {
        name = theme.getNameEt();
        hours = theme.getHours();
        subThemes = theme.getSubthemes();
    }

    public String getName() {
        return name;
    }

    public Short getHours() {
        return hours;
    }

    public String getSubThemes() {
        return subThemes;
    }
    
}
