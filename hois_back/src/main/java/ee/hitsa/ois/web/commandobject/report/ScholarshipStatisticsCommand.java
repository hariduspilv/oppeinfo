package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class ScholarshipStatisticsCommand {
    
    @NotNull
    private LocalDate from;
    @NotNull
    private LocalDate thru;
    private Set<String> types;
    
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
    
    public Set<String> getTypes() {
        return types;
    }
    
    public void setTypes(Set<String> types) {
        this.types = types;
    }

}
