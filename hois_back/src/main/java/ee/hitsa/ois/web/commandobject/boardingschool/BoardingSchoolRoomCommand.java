package ee.hitsa.ois.web.commandobject.boardingschool;

import java.time.LocalDate;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class BoardingSchoolRoomCommand {

    private EntityConnectionCommand building;
    private EntityConnectionCommand room;
    private LocalDate from;
    private LocalDate thru;
    private Boolean showValid;
    private Boolean showFreeRooms;
    private Boolean showResidents;

    public EntityConnectionCommand getBuilding() {
        return building;
    }

    public void setBuilding(EntityConnectionCommand building) {
        this.building = building;
    }

    public EntityConnectionCommand getRoom() {
        return room;
    }

    public void setRoom(EntityConnectionCommand room) {
        this.room = room;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

    public Boolean getShowValid() {
        return showValid;
    }

    public void setShowValid(Boolean showValid) {
        this.showValid = showValid;
    }

    public Boolean getShowFreeRooms() {
        return showFreeRooms;
    }

    public void setShowFreeRooms(Boolean showFreeRooms) {
        this.showFreeRooms = showFreeRooms;
    }

    public Boolean getShowResidents() {
        return showResidents;
    }

    public void setShowResidents(Boolean showResidents) {
        this.showResidents = showResidents;
    }

}
