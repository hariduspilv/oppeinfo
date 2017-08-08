package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.List;

public class TimetablePlanDto {
    private List<TimetableStudentGroupDto> studentGroups;
    private List<TimetableStudentGroupCapacityDto> studentGroupCapacities;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TimetableJournalDto> journals;
    private List<TimetableEventDto> plannedLessons;
    private List<LessonTimeDto> lessonTimes;

    public List<TimetableStudentGroupDto> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<TimetableStudentGroupDto> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<TimetableStudentGroupCapacityDto> getStudentGroupCapacities() {
        return studentGroupCapacities;
    }

    public void setStudentGroupCapacities(List<TimetableStudentGroupCapacityDto> studentGroupCapacities) {
        this.studentGroupCapacities = studentGroupCapacities;
    }

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

    public List<TimetableJournalDto> getJournals() {
        return journals;
    }

    public void setJournals(List<TimetableJournalDto> journals) {
        this.journals = journals;
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

}
