package ee.hitsa.ois.web.commandobject.api;
import java.time.LocalDateTime;

import ee.hitsa.ois.validation.Required;

public class TimetableStudentCommand {
	
	@Required
	private LocalDateTime startFrom;
	@Required
	private LocalDateTime startThru;
	private String roomName;

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public LocalDateTime getStartFrom() {
		return startFrom;
	}

	public void setStartFrom(LocalDateTime startFrom) {
		this.startFrom = startFrom;
	}

	public LocalDateTime getStartThru() {
		return startThru;
	}

	public void setStartThru(LocalDateTime startThru) {
		this.startThru = startThru;
	}

}
