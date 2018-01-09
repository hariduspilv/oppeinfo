package ee.hitsa.ois.web.dto.curriculum;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionOccupationModuleResult  extends AutocompleteResult {

    private String assessment;
    private String gradeCode;
    private LocalDate gradeDate;
    
    public CurriculumVersionOccupationModuleResult(Long id, String nameEt, String nameEn, String assessment,
            String gradeCode, LocalDate gradeDate) {
        super(id, nameEt, nameEn);
        this.assessment = assessment;
        this.gradeCode = gradeCode;
        this.gradeDate = gradeDate;
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
    
}
