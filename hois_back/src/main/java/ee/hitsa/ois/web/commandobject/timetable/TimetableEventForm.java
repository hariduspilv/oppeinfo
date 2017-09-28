package ee.hitsa.ois.web.commandobject.timetable;

import java.time.DayOfWeek;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class TimetableEventForm {
    private Long lessonTime;
    private String selectedDay;
    private Long timetable;
    private Long oldEventId;
    
    @ClassifierRestriction(MainClassCode.MAHT)
    private String capacityType;

    public Long getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(Long lessonTime) {
        this.lessonTime = lessonTime;
    }

    public DayOfWeek getSelectedDay() {
        DayOfWeek day;
        switch (selectedDay.toLowerCase()) {
        case "daymon":
            day = DayOfWeek.MONDAY;
            break;
        case "daytue":
            day = DayOfWeek.TUESDAY;
            break;
        case "daywed":
            day = DayOfWeek.WEDNESDAY;
            break;
        case "daythu":
            day = DayOfWeek.THURSDAY;
            break;
        case "dayfri":
            day = DayOfWeek.FRIDAY;
            break;
        case "daysat":
            day = DayOfWeek.SATURDAY;
            break;
        case "daysun":
            day = DayOfWeek.SUNDAY;
            break;
        default:
            day = null;
            break;
        }
        return day;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

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
