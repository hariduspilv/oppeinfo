package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentVocationalResultByTimeDto {

    private AutocompleteResult name;
    private LocalDate date;
    private String grade;
    private String teachers;
    private Boolean isModule;
    private String journalName;
    private String entryType;
    private String studyYear;
    private LocalDate studyYearStartDate;
    
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
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
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
    
}
