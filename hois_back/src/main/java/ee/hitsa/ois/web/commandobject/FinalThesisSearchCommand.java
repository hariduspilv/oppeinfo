package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

public class FinalThesisSearchCommand {

    private String studentName;
    private String theme;
    private Long curriculumVersion;
    private Long studentGroup;
    private String status;
    private String supervisor;
    private LocalDate insertedFrom;
    private LocalDate insertedThru;
    private LocalDate confirmedFrom;
    private LocalDate confirmedThru;
    
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTheme() {
        return theme;
    }
    
    public void setTheme(String theme) {
        this.theme = theme;
    }
    
    public Long getCurriculumVersion() {
        return curriculumVersion;
    }
    
    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    
    public Long getStudentGroup() {
        return studentGroup;
    }
    
    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public LocalDate getInsertedFrom() {
        return insertedFrom;
    }
    
    public void setInsertedFrom(LocalDate insertedFrom) {
        this.insertedFrom = insertedFrom;
    }
    
    public LocalDate getInsertedThru() {
        return insertedThru;
    }
    
    public void setInsertedThru(LocalDate insertedThru) {
        this.insertedThru = insertedThru;
    }

    public LocalDate getConfirmedFrom() {
        return confirmedFrom;
    }

    public void setConfirmedFrom(LocalDate confirmedFrom) {
        this.confirmedFrom = confirmedFrom;
    }

    public LocalDate getConfirmedThru() {
        return confirmedThru;
    }

    public void setConfirmedThru(LocalDate confirmedThru) {
        this.confirmedThru = confirmedThru;
    }
    
}
