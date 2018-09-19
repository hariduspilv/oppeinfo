package ee.hitsa.ois.web.dto.schoolcapacity;

import java.util.List;

import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeForm;
import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeLoadForm;

public class SchoolCapacityTypeDto extends SchoolCapacityTypeForm {

    private Long id;
    private List<SchoolCapacityTypeLoadForm> loads;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SchoolCapacityTypeLoadForm> getLoads() {
        return loads;
    }

    public void setLoads(List<SchoolCapacityTypeLoadForm> loads) {
        this.loads = loads;
    }
    
}
