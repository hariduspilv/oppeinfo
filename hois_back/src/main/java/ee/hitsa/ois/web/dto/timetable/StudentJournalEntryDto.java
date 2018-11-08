package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class StudentJournalEntryDto {
    private final Long id;
    private final Long journalId;
    private final String entryType;
    private final LocalDate entryDate;
    private final String content;
    private final String gradeValue;
    private final LocalDateTime gradeInserted;
    private final String gradeInsertedBy;
    private final String addInfo;
    private final String homework;
    private final LocalDate homeworkDueDate;
    private final String absence;
    private List<StudentJournalEntryLessonAbsenceDto> lessonAbsences;
    private List<StudentJournalEntryPreviousResultDto> previousResults;

    public StudentJournalEntryDto(Long id, Long journalId, String entryType, LocalDate entryDate, String content, String gradeValue,
            LocalDateTime gradeInserted, String gradeInsertedBy, String addInfo, String homework, LocalDate homeworkDueDate, String absence) {
        this.id = id;
        this.journalId = journalId;
        this.entryType = entryType;
        this.entryDate = entryDate;
        this.content = content;
        this.gradeValue = gradeValue;
        this.gradeInserted = gradeInserted;
        this.gradeInsertedBy = gradeInsertedBy;
        this.addInfo = addInfo;
        this.homework = homework;
        this.homeworkDueDate = homeworkDueDate;
        this.absence = absence;
    }

    public Long getId() {
        return id;
    }
    
    public Long getJournalId() {
        return journalId;
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
    
    public String getGradeInsertedBy() {
        return gradeInsertedBy;
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
    
    public String getAbsence() {
        return absence;
    }

    public List<StudentJournalEntryLessonAbsenceDto> getLessonAbsences() {
        return lessonAbsences;
    }

    public void setLessonAbsences(List<StudentJournalEntryLessonAbsenceDto> lessonAbsences) {
        this.lessonAbsences = lessonAbsences;
    }

    public List<StudentJournalEntryPreviousResultDto> getPreviousResults() {
        return previousResults;
    }

    public void setPreviousResults(List<StudentJournalEntryPreviousResultDto> previousResults) {
        this.previousResults = previousResults;
    }

}
