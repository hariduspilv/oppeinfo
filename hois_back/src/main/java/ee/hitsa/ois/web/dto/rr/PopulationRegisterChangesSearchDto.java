package ee.hitsa.ois.web.dto.rr;

import java.time.LocalDate;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class PopulationRegisterChangesSearchDto {
    
    private EntityConnectionCommand student;
    private EntityConnectionCommand group;
    private LocalDate from;
    private LocalDate thru;
    
    public EntityConnectionCommand getStudent() {
        return student;
    }
    public void setStudent(EntityConnectionCommand student) {
        this.student = student;
    }
    public EntityConnectionCommand getGroup() {
        return group;
    }
    public void setGroup(EntityConnectionCommand group) {
        this.group = group;
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
}
