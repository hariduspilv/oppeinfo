package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

public class StudentJournalEntryPreviousResultDto {
    private final String grade;
    private final LocalDateTime gradeInserted;
    private final String gradeInsertedBy;
    
    public StudentJournalEntryPreviousResultDto(String grade, LocalDateTime gradeInserted, String gradeInsertedBy) {
        this.grade = grade;
        this.gradeInserted = gradeInserted;
        this.gradeInsertedBy = gradeInsertedBy;
    }

    public String getGrade() {
        return grade;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }
    
    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

}
