package ee.hitsa.ois.web.commandobject.scholarship;

import java.time.LocalDate;
import java.util.List;

public class ScholarshipCommitteeSearchCommand {
    private LocalDate validDate;
    private List<Long> curriclumIds;
    private Long id;
    
    public LocalDate getValidDate() {
        return validDate;
    }
    public void setValidDate(LocalDate validDate) {
        this.validDate = validDate;
    }
    
    public List<Long> getCurriclumIds() {
        return curriclumIds;
    }
    public void setCurriclumIds(List<Long> curriclumIds) {
        this.curriclumIds = curriclumIds;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
