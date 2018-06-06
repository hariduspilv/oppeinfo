package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class FinalThesisSearchDto {
    
    private Long id;
    private String themeEt;
    private String themeEn;
    private AutocompleteResult student;
    private String idcode;
    private String supervisors;
    private AutocompleteResult curriculumVersion;
    private String studentGroup;
    private LocalDate inserted;
    private LocalDate confirmed;
    
    private Boolean canBeEdited;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getThemeEt() {
        return themeEt;
    }

    public void setThemeEt(String themeEt) {
        this.themeEt = themeEt;
    }

    public String getThemeEn() {
        return themeEn;
    }

    public void setThemeEn(String themeEn) {
        this.themeEn = themeEn;
    }

    public AutocompleteResult getStudent() {
        return student;
    }
    
    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }
    
    public String getIdcode() {
        return idcode;
    }
    
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    
    public String getSupervisors() {
        return supervisors;
    }
    
    public void setSupervisors(String supervisors) {
        this.supervisors = supervisors;
    }
    
    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }
    
    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    
    public String getStudentGroup() {
        return studentGroup;
    }
    
    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
    
    public LocalDate getInserted() {
        return inserted;
    }
    
    public void setInserted(LocalDate inserted) {
        this.inserted = inserted;
    }
    
    public LocalDate getConfirmed() {
        return confirmed;
    }
    
    public void setConfirmed(LocalDate confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getCanBeEdited() {
        return canBeEdited;
    }

    public void setCanBeEdited(Boolean canBeEdited) {
        this.canBeEdited = canBeEdited;
    }
    
}
