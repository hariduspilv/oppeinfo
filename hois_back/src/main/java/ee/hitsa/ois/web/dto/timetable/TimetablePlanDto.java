package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.ClassifierDto;

public class TimetablePlanDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TimetableEventDto> plannedLessons;
    private List<LessonTimeDto> lessonTimes;
    private List<ClassifierDto> timetableCapacities;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<TimetableEventDto> getPlannedLessons() {
        return plannedLessons;
    }

    public void setPlannedLessons(List<TimetableEventDto> plannedLessons) {
        this.plannedLessons = plannedLessons;
    }

    public List<LessonTimeDto> getLessonTimes() {
        return lessonTimes;
    }

    public void setLessonTimes(List<LessonTimeDto> lessonTimes) {
        this.lessonTimes = lessonTimes;
    }

    public List<ClassifierDto> getTimetableCapacities() {
        return timetableCapacities;
    }

    public void setTimetableCapacities(List<ClassifierDto> timetableCapacities) {
        this.timetableCapacities = timetableCapacities;
    }

}
