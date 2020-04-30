package ee.hitsa.ois.web.commandobject.juhan;

import ee.hitsa.ois.validation.ZonedDateTimeRange;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@ZonedDateTimeRange(from = "eventStart", thru = "eventEnd")
public class JuhanRoomCommand {

    @NotNull
    private Long buildingId;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime eventStart;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime eventEnd;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
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
