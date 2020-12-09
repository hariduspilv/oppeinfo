package ee.hitsa.ois.web.dto.api;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TimetableEventRoomDto {
	
	public TimetableEventRoomDto(Long roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}
	
	private Long roomId;
	private String roomName;
	
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

}
