package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.GradeDto;

public class StudentVocationalResultByTimeDto {

    private AutocompleteResult name;
    private LocalDate date;
    private GradeDto grade;
    private String verbalGrade;
    private String teachers;
    private Boolean isModule;
    private String journalName;
    private String entryType;
    private String studyYear;
    private LocalDate studyYearStartDate;
    private Boolean isApel;
    private Boolean isInformal;
    private Boolean isFormal;
    private Boolean isPractice;
    
    public AutocompleteResult getName() {
        return name;
    }
    
    public void setName(AutocompleteResult name) {
        this.name = name;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public GradeDto getGrade() {
        return grade;
    }
    
    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public String getVerbalGrade() {
        return verbalGrade;
    }

    public void setVerbalGrade(String verbalGrade) {
        this.verbalGrade = verbalGrade;
    }

    public String getTeachers() {
        return teachers;
    }
    
    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }
    
    public Boolean getIsModule() {
        return isModule;
    }
    
    public void setIsModule(Boolean isModule) {
        this.isModule = isModule;
    }
    
    public String getJournalName() {
        return journalName;
    }
    
    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }
    
    public String getEntryType() {
        return entryType;
    }
    
    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }
    
    public String getStudyYear() {
        return studyYear;
    }
    
    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }
    
    public LocalDate getStudyYearStartDate() {
        return studyYearStartDate;
    }
    
    public void setStudyYearStartDate(LocalDate studyYearStartDate) {
        this.studyYearStartDate = studyYearStartDate;
    }

    public Boolean getIsApel() {
        return isApel;
    }

    public void setIsApel(Boolean isApel) {
        this.isApel = isApel;
    }

    public Boolean getIsInformal() {
        return isInformal;
    }

    public void setIsInformal(Boolean isInformal) {
        this.isInformal = isInformal;
    }

    public Boolean getIsFormal() {
        return isFormal;
    }

    public void setIsFormal(Boolean isFormal) {
        this.isFormal = isFormal;
    }

    public Boolean getIsPractice() {
        return isPractice;
    }

    public void setIsPractice(Boolean isPractice) {
        this.isPractice = isPractice;
    }

}
