package ee.hitsa.ois.web.dto;

public class BusyTimeDto {

    private String eventStart;
    private String eventEnd;

    public BusyTimeDto(String eventStart, String eventEnd) {
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    public String getEventStart() {
        return eventStart;
    }

    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }
}
