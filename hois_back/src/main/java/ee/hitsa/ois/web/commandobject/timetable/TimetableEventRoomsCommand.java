package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import ee.hitsa.ois.validation.Required;

public class TimetableEventRoomsCommand {

    private String building;
    private String room;
    @Required
    private LocalDate from;
    @Required
    private LocalDate thru;
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private LocalTime startTime;
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private LocalTime endTime;

    private Boolean isDormitory;
    private Boolean isFreeRoom;
    private Boolean isBusyRoom;
    private Boolean isPartlyBusyRoom;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime.toLocalTime();
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsDormitory() {
        return isDormitory;
    }

    public void setIsDormitory(Boolean isDormitory) {
        this.isDormitory = isDormitory;
    }

    public Boolean getIsFreeRoom() {
        return isFreeRoom;
    }

    public void setIsFreeRoom(Boolean isFreeRoom) {
        this.isFreeRoom = isFreeRoom;
    }

    public Boolean getIsBusyRoom() {
        return isBusyRoom;
    }

    public void setIsBusyRoom(Boolean isBusyRoom) {
        this.isBusyRoom = isBusyRoom;
    }

    public Boolean getIsPartlyBusyRoom() {
        return isPartlyBusyRoom;
    }

    public void setIsPartlyBusyRoom(Boolean isPartlyBusyRoom) {
        this.isPartlyBusyRoom = isPartlyBusyRoom;
    }
}
