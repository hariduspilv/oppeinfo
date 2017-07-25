package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.StudyPeriodWithYearDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;

public class TimetableDto {
    private Long id;
    private List<StudyYearSearchDto> studyYears;
    private List<StudyPeriodWithYearDto> studyPeriods;
    private Long currentStudyPeriod;
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<JournalForTimetableDto> journals;
    private List<SubjectTeacherPairDto> pairs;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<StudyYearSearchDto> getStudyYears() {
        return studyYears;
    }

    public void setStudyYears(List<StudyYearSearchDto> studyYears) {
        this.studyYears = studyYears;
    }

    public List<StudyPeriodWithYearDto> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(List<StudyPeriodWithYearDto> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public Long getCurrentStudyPeriod() {
        return currentStudyPeriod;
    }

    public void setCurrentStudyPeriod(Long currentStudyPeriod) {
        this.currentStudyPeriod = currentStudyPeriod;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<JournalForTimetableDto> getJournals() {
        return journals;
    }

    public void setJournals(List<JournalForTimetableDto> journals) {
        this.journals = journals;
    }

    public List<SubjectTeacherPairDto> getPairs() {
        return pairs;
    }

    public void setPairs(List<SubjectTeacherPairDto> pairs) {
        this.pairs = pairs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
