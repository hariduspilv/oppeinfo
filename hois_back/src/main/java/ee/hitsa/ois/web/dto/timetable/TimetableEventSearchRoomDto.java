package ee.hitsa.ois.web.dto.timetable;

public class TimetableEventSearchRoomDto {
    private final Long id;
    private final String roomCode;
    private final String buildingCode;
    
    public TimetableEventSearchRoomDto(Long id, String roomCode, String buildingCode) {
        this.id = id;
        this.roomCode = roomCode;
        this.buildingCode = buildingCode;
    }

    public Long getId() {
        return id;
    }

    public String getRoomCode() {
        return roomCode;
    }
    
    public String getBuildingCode() {
        return buildingCode;
    }
    
}
