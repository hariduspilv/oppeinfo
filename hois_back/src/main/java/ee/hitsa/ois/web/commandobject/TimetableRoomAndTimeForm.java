package ee.hitsa.ois.web.commandobject;

import java.util.List;

import ee.hitsa.ois.web.dto.RoomDto;

public class TimetableRoomAndTimeForm {
    private Long timetableEventId;
    private String startTime;
    private String endTime;
    private List<RoomDto> rooms;

    public Long getTimetableEventId() {
        return timetableEventId;
    }

    public void setTimetableEventId(Long timetableEventId) {
        this.timetableEventId = timetableEventId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

}
