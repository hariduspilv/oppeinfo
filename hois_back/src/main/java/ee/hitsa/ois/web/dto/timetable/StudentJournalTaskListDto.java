package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.StudyYear;

public class StudentJournalTaskListDto {

    private final Long studyYearId;
    private final LocalDate studyYearStartDate;
    private final LocalDate studyYearEndDate;
    private final List<StudentJournalTaskDto> tasks;

    public StudentJournalTaskListDto(StudyYear studyYear, List<StudentJournalTaskDto> tasks) {
        this.studyYearId = studyYear.getId();
        this.studyYearStartDate = studyYear.getStartDate();
        this.studyYearEndDate = studyYear.getEndDate();
        this.tasks = tasks;
    }

    public Long getStudyYearId() {
        return studyYearId;
    }

    public LocalDate getStudyYearStartDate() {
        return studyYearStartDate;
    }

    public LocalDate getStudyYearEndDate() {
        return studyYearEndDate;
    }

    public List<StudentJournalTaskDto> getTasks() {
        return tasks;
    }
}
