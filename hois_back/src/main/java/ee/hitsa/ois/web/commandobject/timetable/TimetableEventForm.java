package ee.hitsa.ois.web.commandobject.timetable;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class TimetableEventForm {

    @NotNull
    private Long timetable;
    private Long oldEventId;
    @ClassifierRestriction(MainClassCode.MAHT)
    private String capacityType;

    public Long getTimetable() {
        return timetable;
    }

    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }

    public Long getOldEventId() {
        return oldEventId;
    }

    public void setOldEventId(Long oldEventId) {
        this.oldEventId = oldEventId;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

}
