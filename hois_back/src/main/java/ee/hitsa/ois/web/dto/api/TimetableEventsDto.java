package ee.hitsa.ois.web.dto.api;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TimetableEventsDto {
	
	private List<TimetableEventDto> timetableEvents;

	public List<TimetableEventDto> getTimetableEvents() {
		return timetableEvents;
	}

	public void setTimetableEvents(List<TimetableEventDto> timetableEvents) {
		this.timetableEvents = timetableEvents;
	}

}
