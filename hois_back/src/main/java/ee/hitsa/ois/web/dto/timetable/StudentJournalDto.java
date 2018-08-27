package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentJournalDto {
    private Long id;
    private String nameEt;
    private Long studyYearId;
    private String yearCode;
    private Boolean moduleOutcomesAsEntries;
    private Long absenceH;
    private Long absenceP;
    private Long absencePV;
    private String teachers;
    private AutocompleteResult modules;
    private List<StudentJournalEntryDto> journalEntries;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNameEt() {
        return nameEt;
    }
    
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    
    public Long getStudyYearId() {
        return studyYearId;
    }
    
    public void setStudyYearId(Long studyYearId) {
        this.studyYearId = studyYearId;
    }
    
    public String getYearCode() {
        return yearCode;
    }
    
    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }
    
    public Boolean getModuleOutcomesAsEntries() {
        return moduleOutcomesAsEntries;
    }
    
    public void setModuleOutcomesAsEntries(Boolean moduleOutcomesAsEntries) {
        this.moduleOutcomesAsEntries = moduleOutcomesAsEntries;
    }
    
    public Long getAbsenceH() {
        return absenceH;
    }
    
    public void setAbsenceH(Long absenceH) {
        this.absenceH = absenceH;
    }
    
    public Long getAbsenceP() {
        return absenceP;
    }
    
    public void setAbsenceP(Long absenceP) {
        this.absenceP = absenceP;
    }
    
    public Long getAbsencePV() {
        return absencePV;
    }
    
    public void setAbsencePV(Long absencePV) {
        this.absencePV = absencePV;
    }
    
    public String getTeachers() {
        return teachers;
    }
    
    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }
    
    public AutocompleteResult getModules() {
        return modules;
    }
    
    public void setModules(AutocompleteResult modules) {
        this.modules = modules;
    }
    
    public List<StudentJournalEntryDto> getJournalEntries() {
        return journalEntries;
    }
    
    public void setJournalEntries(List<StudentJournalEntryDto> journalEntries) {
        this.journalEntries = journalEntries;
    }

}
