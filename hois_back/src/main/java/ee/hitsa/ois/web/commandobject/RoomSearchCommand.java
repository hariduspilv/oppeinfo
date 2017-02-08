package ee.hitsa.ois.web.commandobject;

public class RoomSearchCommand extends SearchCommand {

    private String buildingName;
    private String buildingCode;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }
}
