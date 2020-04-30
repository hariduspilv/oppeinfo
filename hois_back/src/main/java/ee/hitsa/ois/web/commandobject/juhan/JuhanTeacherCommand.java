package ee.hitsa.ois.web.commandobject.juhan;

import ee.hitsa.ois.validation.Conditional;
import ee.hitsa.ois.validation.ZonedDateTimeRange;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Conditional(selected = "idcode", values = {"null"}, required = {"uqcode"})
@Conditional(selected = "uqcode", values = {"null"}, required = {"idcode"})
@ZonedDateTimeRange(from = "eventStart", thru = "eventEnd")
public class JuhanTeacherCommand {

    private String idcode;
    private String uqcode;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime eventStart;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime eventEnd;

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getUqcode() {
        return uqcode;
    }

    public void setUqcode(String uqcode) {
        this.uqcode = uqcode;
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
