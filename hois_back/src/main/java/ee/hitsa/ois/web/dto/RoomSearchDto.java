package ee.hitsa.ois.web.dto;

public class RoomSearchDto extends RoomDto {

    private String buildingName;
    private String buildingCode;
    private String buildingAddress;
    private Boolean isBoardingSchool;

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

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public Boolean getIsBoardingSchool() {
        return isBoardingSchool;
    }

    public void setIsBoardingSchool(Boolean isBoardingSchool) {
        this.isBoardingSchool = isBoardingSchool;
    }

}
