package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionOModulesAndThemesResult extends AutocompleteResult {

    private BigDecimal credits;
    private String assessment;
    private String gradeCode;
    private LocalDate gradeDate;
    private String teachers;
    private List<CurriculumVersionOccupationModuleThemeResult> themes;
    
    public CurriculumVersionOModulesAndThemesResult(Long id, String nameEt, String nameEn, BigDecimal credits, String assessment,
            String gradeCode, LocalDate gradeDate, String teachers, List<CurriculumVersionOccupationModuleThemeResult> themes) {
        super(id, nameEt, nameEn);
        this.credits = credits;
        this.assessment = assessment;
        this.gradeCode = gradeCode;
        this.gradeDate = gradeDate;
        this.teachers = teachers;
        this.themes = themes;
    }
    
    public BigDecimal getCredits() {
        return credits;
    }
    
    public void setCredits(BigDecimal credits) {
        this.credits = credits;
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
    
    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public List<CurriculumVersionOccupationModuleThemeResult> getThemes() {
        return themes;
    }

    public void setThemes(List<CurriculumVersionOccupationModuleThemeResult> themes) {
        this.themes = themes;
    }
    
}
