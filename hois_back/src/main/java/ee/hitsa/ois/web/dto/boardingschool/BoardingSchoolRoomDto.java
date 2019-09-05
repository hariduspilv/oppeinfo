package ee.hitsa.ois.web.dto.boardingschool;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.RoomAutocompleteResult;

public class BoardingSchoolRoomDto {

    private AutocompleteResult building;
    private RoomAutocompleteResult room;
    private List<BoardingSchoolResidentDto> residents;
    private Long freeSeats;
    private Boolean occupied;

    public AutocompleteResult getBuilding() {
        return building;
    }

    public void setBuilding(AutocompleteResult building) {
        this.building = building;
    }

    public RoomAutocompleteResult getRoom() {
        return room;
    }

    public void setRoom(RoomAutocompleteResult room) {
        this.room = room;
    }

    public List<BoardingSchoolResidentDto> getResidents() {
        return residents;
    }

    public void setResidents(List<BoardingSchoolResidentDto> residents) {
        this.residents = residents;
    }

    public Long getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Long freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

}
