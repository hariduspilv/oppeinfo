package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class RoomsAutocompleteCommand extends SearchCommand {

    private List<Long> buildingIds;

    public List<Long> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<Long> buildingIds) {
        this.buildingIds = buildingIds;
    }
}
