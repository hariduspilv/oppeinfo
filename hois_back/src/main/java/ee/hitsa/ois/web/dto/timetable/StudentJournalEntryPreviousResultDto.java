package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

public class StudentJournalEntryPreviousResultDto {
    private final String gradeValue;
    private final LocalDateTime gradeInserted;
    
    public StudentJournalEntryPreviousResultDto(String gradeValue, LocalDateTime gradeInserted) {
        this.gradeValue = gradeValue;
        this.gradeInserted = gradeInserted;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

}
