package ee.hitsa.ois.web.dto.curriculum;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionOModulesAndThemesResult extends AutocompleteResult {

    private String assessment;
    private String gradeCode;
    private LocalDate gradeDate;
    private List<CurriculumVersionOccupationModuleThemeResult> themes;
    
    public CurriculumVersionOModulesAndThemesResult(Long id, String nameEt, String nameEn, String assessment,
            String gradeCode, LocalDate gradeDate, List<CurriculumVersionOccupationModuleThemeResult> themes) {
        super(id, nameEt, nameEn);
        this.assessment = assessment;
        this.gradeCode = gradeCode;
        this.gradeDate = gradeDate;
        this.themes = themes;
    }
    
    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
    
    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public List<CurriculumVersionOccupationModuleThemeResult> getThemes() {
        return themes;
    }

    public void setThemes(List<CurriculumVersionOccupationModuleThemeResult> themes) {
        this.themes = themes;
    }
    
}
