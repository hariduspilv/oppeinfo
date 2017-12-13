package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StudentJournalEntryDto {
    private final Long id;
    private final String entryType;
    private final LocalDate entryDate;
    private final String content;
    private final String gradeValue;
    private final LocalDateTime gradeInserted;
    private final String addInfo;
    private final String homework;
    private final LocalDate homeworkDueDate;
    private List<StudentJournalEntryPreviousResultDto> previousResults;

    public StudentJournalEntryDto(Long id, String entryType, LocalDate entryDate, String content, String gradeValue,
            LocalDateTime gradeInserted, String addInfo, String homework, LocalDate homeworkDueDate) {
        this.id = id;
        this.entryType = entryType;
        this.entryDate = entryDate;
        this.content = content;
        this.gradeValue = gradeValue;
        this.gradeInserted = gradeInserted;
        this.addInfo = addInfo;
        this.homework = homework;
        this.homeworkDueDate = homeworkDueDate;
    }

    public Long getId() {
        return id;
    }

    public String getEntryType() {
        return entryType;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public String getContent() {
        return content;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public String getHomework() {
        return homework;
    }

    public LocalDate getHomeworkDueDate() {
        return homeworkDueDate;
    }

    public List<StudentJournalEntryPreviousResultDto> getPreviousResults() {
        return previousResults;
    }

    public void setPreviousResults(List<StudentJournalEntryPreviousResultDto> previousResults) {
        this.previousResults = previousResults;
    }

}
