package ee.hitsa.ois.web.commandobject;

import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.TimeRange;
import ee.hitsa.ois.web.dto.RoomDto;

@TimeRange(from = "startTime", thru = "endTime")
public class TimetableRoomAndTimeForm {
    @NotNull
    private Long timetableEventId;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<RoomDto> rooms;

    public Long getTimetableEventId() {
        return timetableEventId;
    }

    public void setTimetableEventId(Long timetableEventId) {
        this.timetableEventId = timetableEventId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

}
