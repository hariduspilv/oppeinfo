package ee.hitsa.ois.web.dto.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

public class RoomTimetableDto {
    private Long roomId;
    private String code;
    
    public RoomTimetableDto(Object[] row) {
        this.roomId =  resultAsLong(row, 0);
        this.code = (String) row[1] + "-" + row[2];
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
