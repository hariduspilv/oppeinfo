package ee.hitsa.ois.web.commandobject.timetable;

import java.time.DayOfWeek;

public class TimetableEventForm {
    private Long journal;
    private Long lessonTime;
    private String selectedDay;
    private Long timetable;

    public Long getJournal() {
        return journal;
    }

    public void setJournal(Long journal) {
        this.journal = journal;
    }

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

}
