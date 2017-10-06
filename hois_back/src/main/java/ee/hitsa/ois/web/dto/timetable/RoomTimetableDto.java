package ee.hitsa.ois.web.dto.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

public class RoomTimetableDto {
    private Long roomId;
    private String roomCode;
    
    public RoomTimetableDto(Object[] row) {
        this.roomId =  resultAsLong(row, 0);
        this.roomCode = (String) row[1];
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
    
}
