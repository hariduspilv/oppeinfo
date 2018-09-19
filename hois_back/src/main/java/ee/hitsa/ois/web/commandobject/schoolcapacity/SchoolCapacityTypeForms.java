package ee.hitsa.ois.web.commandobject.schoolcapacity;

import java.util.List;

public class SchoolCapacityTypeForms {

    private Boolean isHigher;
    private List<SchoolCapacityTypeForm> capacities;
    
    public Boolean getIsHigher() {
        return isHigher;
    }
    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
    
    public List<SchoolCapacityTypeForm> getCapacities() {
        return capacities;
    }
    public void setCapacities(List<SchoolCapacityTypeForm> capacities) {
        this.capacities = capacities;
    }
    
}
