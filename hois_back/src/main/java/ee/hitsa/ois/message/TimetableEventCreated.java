package ee.hitsa.ois.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ee.hitsa.ois.util.DateUtils;

public class TimetableEventCreated {
    
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final String name;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimetableEventCreated() {
        this.name = null;
        this.start = null;
        this.end = null;
    }

    public TimetableEventCreated(String name, LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getSundmuseNimetus() {
        return name;
    }

    public String getSundmuseAeg() {
        if (start == null || end == null) {
            return null;
        }
        return DateUtils.date(start.toLocalDate()) + " " + start.format(TIME_FORMATTER) + " - " + end.format(TIME_FORMATTER);
    }
}
