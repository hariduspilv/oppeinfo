package ee.hitsa.ois.web.dto;

import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;

public class CurriculumVersionOccupationModuleThemeResult extends AutocompleteResult {
    private List<String> capacities;
    
    public CurriculumVersionOccupationModuleThemeResult (CurriculumVersionOccupationModuleTheme cvomt) {
        super(cvomt.getId(), cvomt.getNameEt(), cvomt.getNameEt());
        this.capacities = cvomt.getCapacities().stream().map(it -> it.getCapacityType().getCode()).collect(Collectors.toList());
    }

    public List<String> getCapacities() {
        return capacities;
    }

    public void setCapacities(List<String> capacities) {
        this.capacities = capacities;
    }

}
