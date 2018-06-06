package ee.hitsa.ois.web.dto.studymaterial;

import java.util.List;

public class JournalLessonHoursDto {
    
    private Integer totalPlannedHours;
    private Integer totalUsedHours;
    private List<CapacityHoursDto> capacityHours;
    
    public Integer getTotalPlannedHours() {
        return totalPlannedHours;
    }
    
    public void setTotalPlannedHours(Integer totalPlannedHours) {
        this.totalPlannedHours = totalPlannedHours;
    }
    
    public Integer getTotalUsedHours() {
        return totalUsedHours;
    }
    
    public void setTotalUsedHours(Integer totalUsedHours) {
        this.totalUsedHours = totalUsedHours;
    }

    public List<CapacityHoursDto> getCapacityHours() {
        return capacityHours;
    }

    public void setCapacityHours(List<CapacityHoursDto> capacityHours) {
        this.capacityHours = capacityHours;
    }
    
}
