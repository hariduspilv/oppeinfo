package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JournalEntryLessonInfoDto {

    private List<LocalDate> lessonPlanDates = new ArrayList<>();
    private Integer startLessonNr;
    private Integer lessons;

    public List<LocalDate> getLessonPlanDates() {
        return lessonPlanDates;
    }
    public void setLessonPlanDates(List<LocalDate> lessonPlanDates) {
        this.lessonPlanDates = lessonPlanDates;
    }
    public Integer getStartLessonNr() {
        return startLessonNr;
    }
    public void setStartLessonNr(Integer startLessonNr) {
        this.startLessonNr = startLessonNr;
    }
    public Integer getLessons() {
        return lessons;
    }
    public void setLessons(Integer lessons) {
        this.lessons = lessons;
    }

}
