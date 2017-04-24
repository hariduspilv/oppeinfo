package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDateTime;
import java.util.Set;

public class LessonTimeSearchCommand {

    private LocalDateTime from;
    private LocalDateTime thru;
    private Set<String> day;
    private Set<Long> building;


    public LocalDateTime getFrom() {
        return from;
    }
    public void setFrom(LocalDateTime from) {
        this.from = from;
    }
    public LocalDateTime getThru() {
        return thru;
    }
    public void setThru(LocalDateTime thru) {
        this.thru = thru;
    }
    public Set<String> getDay() {
        return day;
    }
    public void setDay(Set<String> day) {
        this.day = day;
    }
    public Set<Long> getBuilding() {
        return building;
    }
    public void setBuilding(Set<Long> building) {
        this.building = building;
    }

}
