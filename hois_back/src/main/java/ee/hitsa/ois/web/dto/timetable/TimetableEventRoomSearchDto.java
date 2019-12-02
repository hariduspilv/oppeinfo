package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.commandobject.RoomForm.RoomEquipmentCommand;

public class TimetableEventRoomSearchDto {

    private Long id;
    private String room;
    private String building;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> times;
    private Integer places;
    private Boolean isUsedInStudy;
    private Boolean isDormitoryRoom;
    private List<RoomEquipmentCommand> equipment;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public Boolean getIsUsedInStudy() {
        return isUsedInStudy;
    }

    public void setIsUsedInStudy(Boolean isUsedInStudy) {
        this.isUsedInStudy = isUsedInStudy;
    }

    public Boolean getIsDormitoryRoom() {
        return isDormitoryRoom;
    }

    public void setIsDormitoryRoom(Boolean isDormitoryRoom) {
        this.isDormitoryRoom = isDormitoryRoom;
    }

    public List<RoomEquipmentCommand> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<RoomEquipmentCommand> equipment) {
        this.equipment = equipment;
    }
}
