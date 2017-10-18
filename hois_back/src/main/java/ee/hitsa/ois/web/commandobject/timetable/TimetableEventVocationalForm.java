package ee.hitsa.ois.web.commandobject.timetable;

import java.time.DayOfWeek;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.NotEmpty;

public class TimetableEventVocationalForm extends TimetableEventForm {
    @NotNull
    private Long journal;
    @NotNull
    private Long lessonTime;
    @NotEmpty
    private String selectedDay;

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

}
