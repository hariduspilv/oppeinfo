package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class RoomsAutocompleteCommand extends SearchCommand {

    private List<Long> buildingIds;
    private Boolean isStudy;

    public List<Long> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<Long> buildingIds) {
        this.buildingIds = buildingIds;
    }

    public Boolean getIsStudy() {
        return isStudy;
    }

    public void setIsStudy(Boolean isStudy) {
        this.isStudy = isStudy;
    }
    
}
