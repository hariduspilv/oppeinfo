package ee.hitsa.ois.web.commandobject.juhan;

import ee.hitsa.ois.validation.ZonedDateTimeRange;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ZonedDateTimeRange(from = "eventStart", thru = "eventEnd")
public class JuhanEventForm {

    @NotNull
    private Long schoolId;
    @NotNull
    private Long eventId;
    private String eventName;
    private List<JuhanEventTeacherForm> teachers;
    private List<JuhanEventRoomForm> rooms;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime eventStart;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime eventEnd;

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<JuhanEventTeacherForm> getTeachers() {
        return teachers != null ? teachers : (teachers = new ArrayList<>());
    }

    public void setTeachers(List<JuhanEventTeacherForm> teachers) {
        this.teachers = teachers;
    }

    public List<JuhanEventRoomForm> getRooms() {
        return rooms != null ? rooms : (rooms = new ArrayList<>());
    }

    public void setRooms(List<JuhanEventRoomForm> rooms) {
        this.rooms = rooms;
    }

    public ZonedDateTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(ZonedDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public ZonedDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(ZonedDateTime eventEnd) {
        this.eventEnd = eventEnd;
    }
}
