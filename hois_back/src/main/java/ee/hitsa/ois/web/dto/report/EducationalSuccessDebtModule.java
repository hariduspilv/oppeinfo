package ee.hitsa.ois.web.dto.report;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class EducationalSuccessDebtModule {
    
    private AutocompleteResult module;
    private String studyYear;
    private AutocompleteResult resultType;
    private String teachers;
    private String grade;
    private LocalDate gradeDate;
    private Boolean isModule = Boolean.FALSE;
    
    public AutocompleteResult getModule() {
        return module;
    }
    public void setModule(AutocompleteResult module) {
        this.module = module;
    }
    public String getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }
    public AutocompleteResult getResultType() {
        return resultType;
    }
    public void setResultType(AutocompleteResult resultType) {
        this.resultType = resultType;
    }
    public String getTeachers() {
        return teachers;
    }
    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }
    public LocalDate getGradeDate() {
        return gradeDate;
    }
    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public Boolean getIsModule() {
        return isModule;
    }
    public void setIsModule(Boolean isModule) {
        this.isModule = isModule;
    }

}
