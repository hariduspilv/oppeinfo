package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class SubjectResult extends AutocompleteResult {

    private String gradeCode;
    private LocalDate gradeDate;
    
    public SubjectResult(Long id, String nameEt, String nameEn, String gradeCode, LocalDate gradeDate) {
        super(id, nameEt, nameEn);
        this.gradeCode = gradeCode;
        this.gradeDate = gradeDate;
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
