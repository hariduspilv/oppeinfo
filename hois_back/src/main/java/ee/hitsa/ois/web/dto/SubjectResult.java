package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class SubjectResult extends AutocompleteResult {

    private String gradeCode;
    private LocalDate gradeDate;
    private String teachers;
    
    public SubjectResult(Long id, String nameEt, String nameEn, String gradeCode, LocalDate gradeDate, String teachers) {
        super(id, nameEt, nameEn);
        this.gradeCode = gradeCode;
        this.gradeDate = gradeDate;
        this.teachers = teachers;
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
    
}
