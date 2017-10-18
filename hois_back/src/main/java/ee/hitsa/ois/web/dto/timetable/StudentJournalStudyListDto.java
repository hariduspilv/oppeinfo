package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.StudyYear;

public class StudentJournalStudyListDto {

    private final Long studyYearId;
    private final LocalDate studyYearStartDate;
    private final LocalDate studyYearEndDate;
    private final List<StudentJournalStudyDto> entries;

    public StudentJournalStudyListDto(StudyYear studyYear, List<StudentJournalStudyDto> entries) {
        this.studyYearId = studyYear.getId();
        this.studyYearStartDate = studyYear.getStartDate();
        this.studyYearEndDate = studyYear.getEndDate();
        this.entries = entries;
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

    public List<StudentJournalStudyDto> getEntries() {
        return entries;
    }
}
