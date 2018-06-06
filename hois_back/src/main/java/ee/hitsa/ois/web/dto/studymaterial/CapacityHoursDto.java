package ee.hitsa.ois.web.dto.studymaterial;

public class CapacityHoursDto {
    
    private String capacity;
    private Integer plannedHours;
    private Integer usedHours;

    public String getCapacity() {
        return capacity;
    }
    
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
    
    public Integer getPlannedHours() {
        return plannedHours;
    }
    
    public void setPlannedHours(Integer plannedHours) {
        this.plannedHours = plannedHours;
    }
    
    public Integer getUsedHours() {
        return usedHours;
    }
    
    public void setUsedHours(Integer usedHours) {
        this.usedHours = usedHours;
    }
    
}
