package ee.hitsa.ois.web.dto.curriculum;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionOModulesAndThemesResult extends AutocompleteResult {

    private String assessment;
    private List<CurriculumVersionOccupationModuleThemeResult> themes;
    
    public CurriculumVersionOModulesAndThemesResult(Long id, String nameEt, String nameEn, String assessment, List<CurriculumVersionOccupationModuleThemeResult> themes) {
        super(id, nameEt, nameEn);
        this.assessment = assessment;
        this.themes = themes;
    }
    
    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public List<CurriculumVersionOccupationModuleThemeResult> getThemes() {
        return themes;
    }

    public void setThemes(List<CurriculumVersionOccupationModuleThemeResult> themes) {
        this.themes = themes;
    }
    
}
