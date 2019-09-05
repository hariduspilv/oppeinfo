package ee.hitsa.ois.web.dto.boardingschool;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.RoomAutocompleteResult;

public class BoardingSchoolManagementCheckDto extends BoardingSchoolResidentDto {

    private RoomAutocompleteResult roomObject;
    private List<BoardingSchoolResidentDto> residents = new ArrayList<>();
    private Boolean occupied;
    private Boolean duplicateStudents;

    public RoomAutocompleteResult getRoomObject() {
        return roomObject;
    }

    public void setRoomObject(RoomAutocompleteResult roomObject) {
        this.roomObject = roomObject;
    }

    public List<BoardingSchoolResidentDto> getResidents() {
        return residents;
    }

    public void setResidents(List<BoardingSchoolResidentDto> residents) {
        this.residents = residents;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Boolean getDuplicateStudents() {
        return duplicateStudents;
    }

    public void setDuplicateStudents(Boolean duplicateStudents) {
        this.duplicateStudents = duplicateStudents;
    }

}
