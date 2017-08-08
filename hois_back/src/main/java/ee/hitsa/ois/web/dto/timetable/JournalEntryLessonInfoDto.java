package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JournalEntryLessonInfoDto {

    private List<LocalDateTime> lessonPlanDates = new ArrayList<>();
    private Integer startLessonNr;
    private Integer lessons;

    public List<LocalDateTime> getLessonPlanDates() {
        return lessonPlanDates;
    }

    public void setLessonPlanDates(List<LocalDateTime> lessonPlanDates) {
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
