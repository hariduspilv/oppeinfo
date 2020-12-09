package ee.hitsa.ois.web.dto.api;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TimetableEventDto {
	
	private String nameEt;
	private LocalDateTime eventStart;
	private LocalDateTime eventEnd;
	private Long journalId;
	private Long subjectStudyPeriodId;
	private List<TimetableEventTeacherDto> teachers;
	private List<TimetableEventRoomDto> rooms;
	
	public String getNameEt() {
		return nameEt;
	}
	public void setNameEt(String nameEt) {
		this.nameEt = nameEt;
	}
	public Long getJournalId() {
		return journalId;
	}
	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}
	public Long getSubjectStudyPeriodId() {
		return subjectStudyPeriodId;
	}
	public void setSubjectStudyPeriodId(Long subjectStudyPeriodId) {
		this.subjectStudyPeriodId = subjectStudyPeriodId;
	}
	public List<TimetableEventTeacherDto> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<TimetableEventTeacherDto> teachers) {
		this.teachers = teachers;
	}
	public List<TimetableEventRoomDto> getRooms() {
		return rooms;
	}
	public void setRooms(List<TimetableEventRoomDto> rooms) {
		this.rooms = rooms;
	}
	public LocalDateTime getEventStart() {
		return eventStart;
	}
	public void setEventStart(LocalDateTime eventStart) {
		this.eventStart = eventStart;
	}
	public LocalDateTime getEventEnd() {
		return eventEnd;
	}
	public void setEventEnd(LocalDateTime eventEnd) {
		this.eventEnd = eventEnd;
	}
}
