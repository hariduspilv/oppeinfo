package ee.hitsa.ois.web.dto.schoolcapacity;

import java.util.List;
import java.util.Map;

import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeForm;
import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeLoadForm;

public class SchoolCapacityTypeDto extends SchoolCapacityTypeForm {

    private Long id;
    private List<SchoolCapacityTypeLoadForm> loads;
    private Map<String, List<SchoolCapacityTypeLoadForm>> mappedLoads;

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

    public Map<String, List<SchoolCapacityTypeLoadForm>> getMappedLoads() {
        return mappedLoads;
    }

    public void setMappedLoads(Map<String, List<SchoolCapacityTypeLoadForm>> mappedLoads) {
        this.mappedLoads = mappedLoads;
    }
}
