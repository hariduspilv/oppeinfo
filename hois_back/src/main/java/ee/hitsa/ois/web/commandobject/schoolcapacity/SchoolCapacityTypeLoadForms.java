package ee.hitsa.ois.web.commandobject.schoolcapacity;

import javax.validation.Valid;
import java.util.List;

public class SchoolCapacityTypeLoadForms {

    private Long id;
    @Valid
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
