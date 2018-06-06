package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;
import java.util.List;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalEntryStudentResultDto {

    private String grade;
    private String addInfo;
    private LocalDateTime inserted;
    private LocalDateTime gradeInserted;
    private String gradeInsertedBy;
    @ClassifierRestriction(MainClassCode.PUUDUMINE)
    private String absence;
    private List<JournalEntryStudentHistoryDto> journalEntryStudentHistories;

    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public String getAddInfo() {
        return addInfo;
    }
    
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    
    public LocalDateTime getInserted() {
        return inserted;
    }
    
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
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

    public String getAbsence() {
        return absence;
    }
    
    public void setAbsence(String absence) {
        this.absence = absence;
    }

    public List<JournalEntryStudentHistoryDto> getJournalEntryStudentHistories() {
        return journalEntryStudentHistories;
    }

    public void setJournalEntryStudentHistories(List<JournalEntryStudentHistoryDto> journalEntryStudentHistories) {
        this.journalEntryStudentHistories = journalEntryStudentHistories;
    }

}
