package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.commandobject.RoomForm.RoomEquipmentCommand;

public class TimetableEventRoomSearchDto {

    private Long id;
    private String roomCode;
    private String roomName;
    private String buildingCode;
    private String buildingName;
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

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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
