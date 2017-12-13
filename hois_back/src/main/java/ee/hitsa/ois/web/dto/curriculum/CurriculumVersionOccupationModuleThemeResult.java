package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleThemeCapacity;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionOccupationModuleThemeResult extends AutocompleteResult {
    
    private BigDecimal credits;
    private Map<String, Short> capacities = new HashMap<>();
    
    public CurriculumVersionOccupationModuleThemeResult (CurriculumVersionOccupationModuleTheme cvomt) {
        super(cvomt.getId(), cvomt.getNameEt(), cvomt.getNameEt());
        for(CurriculumVersionOccupationModuleThemeCapacity cap : cvomt.getCapacities()) {
            capacities.put(cap.getCapacityType().getCode(), cap.getHours());
        }
    }
    
    public CurriculumVersionOccupationModuleThemeResult (Long id, String nameEt, String nameEn, BigDecimal credits) {
        super(id, nameEt, nameEn);
        
        this.credits = credits;
    }

    public Map<String, Short> getCapacities() {
        return capacities;
    }

    public void setCapacities(Map<String, Short> capacities) {
        this.capacities = capacities;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

}
