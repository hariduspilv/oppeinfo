package ee.hitsa.ois.web.commandobject.document;

import java.time.LocalDate;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class DiplomaSearchForm {

    private String student;
    private EntityConnectionCommand curriculum;
    private LocalDate from;
    private LocalDate thru;
    private Boolean isHigher;
    
    public String getStudent() {
        return student;
    }
    public void setStudent(String student) {
        this.student = student;
    }
    
    public EntityConnectionCommand getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(EntityConnectionCommand curriculum) {
        this.curriculum = curriculum;
    }
    
    public LocalDate getFrom() {
        return from;
    }
    public void setFrom(LocalDate from) {
        this.from = from;
    }
    
    public LocalDate getThru() {
        return thru;
    }
    public void setThru(LocalDate thru) {
        this.thru = thru;
    }
    
    public Boolean getIsHigher() {
        return isHigher;
    }
    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
    
}
