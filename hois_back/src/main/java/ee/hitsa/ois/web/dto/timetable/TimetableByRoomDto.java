package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class TimetableByRoomDto {
    private final Long roomId;
    private final String roomCode;
    private final String buildingCode;
    private final GeneralTimetableDto generalTimetable;
    private final List<TimetableEventSearchDto> timetableEvents;
    
    public TimetableByRoomDto(Long roomId, String roomCode, String buildingCode, GeneralTimetableDto generalTimetable,
            List<TimetableEventSearchDto> timetableEvents) {
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.buildingCode = buildingCode;
        this.generalTimetable = generalTimetable;
        this.timetableEvents = timetableEvents;
    }
    
    public GeneralTimetableDto getGeneralTimetable() {
        return generalTimetable;
    }
    
    public List<TimetableEventSearchDto> getTimetableEvents() {
        return timetableEvents;
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
