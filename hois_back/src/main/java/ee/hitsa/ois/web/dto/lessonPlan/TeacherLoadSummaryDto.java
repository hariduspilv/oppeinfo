package ee.hitsa.ois.web.dto.lessonPlan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TeacherLoadSummaryDto {
    
    private Long capacitiesSum;
    private Long contactCapacitiesSum;
    private BigDecimal sumWithLoad;
    private List<LessonPlanXlsCapacityDto> capacities = new ArrayList<>();
    
    public Long getCapacitiesSum() {
        return capacitiesSum;
    }
    public void setCapacitiesSum(Long capacitiesSum) {
        this.capacitiesSum = capacitiesSum;
    }
    public Long getContactCapacitiesSum() {
        return contactCapacitiesSum;
    }
    public void setContactCapacitiesSum(Long contactCapacitiesSum) {
        this.contactCapacitiesSum = contactCapacitiesSum;
    }
    public BigDecimal getSumWithLoad() {
        return sumWithLoad;
    }
    public void setSumWithLoad(BigDecimal sumWithLoad) {
        this.sumWithLoad = sumWithLoad;
    }
    public List<LessonPlanXlsCapacityDto> getCapacities() {
        return capacities;
    }
    public void setCapacities(List<LessonPlanXlsCapacityDto> capacities) {
        this.capacities = capacities;
    }

}
