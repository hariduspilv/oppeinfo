package ee.hitsa.ois.web.commandobject.boardingschool;

import java.time.LocalDate;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class BoardingSchoolSearchCommand {

    private EntityConnectionCommand studentGroup;
    private String name;
    private String idcode;
    private String buildingRoomCode;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean showValid;
    private Boolean showNeighbours = Boolean.FALSE;

    public EntityConnectionCommand getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(EntityConnectionCommand studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getBuildingRoomCode() {
        return buildingRoomCode;
    }

    public void setBuildingRoomCode(String buildingRoomCode) {
        this.buildingRoomCode = buildingRoomCode;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public Boolean getShowValid() {
        return showValid;
    }

    public void setShowValid(Boolean showValid) {
        this.showValid = showValid;
    }

    public Boolean getShowNeighbours() {
        return showNeighbours;
    }

    public void setShowNeighbours(Boolean showNeighbours) {
        this.showNeighbours = showNeighbours;
    }

}
