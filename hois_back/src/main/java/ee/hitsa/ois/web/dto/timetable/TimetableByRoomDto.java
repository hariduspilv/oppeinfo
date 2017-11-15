package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByRoomDto extends TimetableByDto{
    private final Long roomId;
    private final String roomCode;
    private final String buildingCode;
    
    public TimetableByRoomDto(GeneralTimetableDto generalTimetable, List<TimetableEventSearchDto> timetableEvents,
            Long roomId, String roomCode, String buildingCode) {
        super(generalTimetable, timetableEvents);
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.buildingCode = buildingCode;
    }
    
    public Long getRoomId() {
        return roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getBuildingCode() {
        return buildingCode;
    }
    
}
