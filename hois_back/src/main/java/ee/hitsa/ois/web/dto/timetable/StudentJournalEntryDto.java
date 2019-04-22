package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StudentJournalEntryDto {
    private  Long id;
    private Long journalId;
    private String entryType;
    private LocalDate entryDate;
    private String content;
    private String gradeValue;
    private LocalDateTime gradeInserted;
    private String gradeInsertedBy;
    private String addInfo;
    private String homework;
    private LocalDate homeworkDueDate;
    private String absence;
    private List<StudentJournalEntryLessonAbsenceDto> lessonAbsences;
    private List<StudentJournalEntryPreviousResultDto> previousResults;
    private Boolean isRemark;
    private LocalDateTime remarkInserted;
    private String remarkInsertedBy;
    
    public StudentJournalEntryDto() { }

    public StudentJournalEntryDto(Long id, Long journalId, String entryType, LocalDate entryDate, String content,
            String gradeValue, LocalDateTime gradeInserted, String gradeInsertedBy, String addInfo, String homework,
            LocalDate homeworkDueDate, String absence, Boolean isRemark, LocalDateTime remarkInserted,
            String remarkInsertedBy) {
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
        this.isRemark = isRemark;
        this.remarkInserted = remarkInserted;
        this.remarkInsertedBy = remarkInsertedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDateTime gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

    public void setGradeInsertedBy(String gradeInsertedBy) {
        this.gradeInsertedBy = gradeInsertedBy;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public LocalDate getHomeworkDueDate() {
        return homeworkDueDate;
    }

    public void setHomeworkDueDate(LocalDate homeworkDueDate) {
        this.homeworkDueDate = homeworkDueDate;
    }

    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
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

    public Boolean getIsRemark() {
        return isRemark;
    }

    public void setIsRemark(Boolean isRemark) {
        this.isRemark = isRemark;
    }

    public LocalDateTime getRemarkInserted() {
        return remarkInserted;
    }

    public void setRemarkInserted(LocalDateTime remarkInserted) {
        this.remarkInserted = remarkInserted;
    }

    public String getRemarkInsertedBy() {
        return remarkInsertedBy;
    }

    public void setRemarkInsertedBy(String remarkInsertedBy) {
        this.remarkInsertedBy = remarkInsertedBy;
    }

}
