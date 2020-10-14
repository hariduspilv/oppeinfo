package ee.hitsa.ois.web.commandobject.juhan;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class JuhanEventsForm {

    @Valid
    private List<JuhanEventForm> events;

    public List<JuhanEventForm> getEvents() {
        return events != null ? events : (events = new ArrayList<>());
    }

    public void setEvents(List<JuhanEventForm> events) {
        this.events = events;
    }
}
