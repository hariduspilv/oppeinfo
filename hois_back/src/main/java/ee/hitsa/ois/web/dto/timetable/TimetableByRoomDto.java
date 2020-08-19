package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByRoomDto extends TimetableByDto{
    private final Long roomId;
    private final String roomCode;
    private final String buildingCode;
    
    public TimetableByRoomDto(String studyPeriods, List<TimetableEventSearchDto> timetableEvents,
            Long roomId, String roomCode, String buildingCode, Boolean isHigher) {
        super(studyPeriods, timetableEvents, isHigher);
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
