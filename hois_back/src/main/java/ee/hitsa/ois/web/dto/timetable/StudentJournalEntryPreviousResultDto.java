package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

public class StudentJournalEntryPreviousResultDto {
    private final String gradeValue;
    private final LocalDateTime gradeInserted;
    private final String gradeInsertedBy;
    
    public StudentJournalEntryPreviousResultDto(String gradeValue, LocalDateTime gradeInserted, String gradeInsertedBy) {
        this.gradeValue = gradeValue;
        this.gradeInserted = gradeInserted;
        this.gradeInsertedBy = gradeInsertedBy;
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

}
