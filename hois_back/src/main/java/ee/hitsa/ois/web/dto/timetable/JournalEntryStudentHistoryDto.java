package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.timetable.JournalEntryStudentHistory;
import ee.hitsa.ois.util.EntityUtil;

public class JournalEntryStudentHistoryDto {

    private String grade;
    private LocalDateTime gradeInserted;

    public JournalEntryStudentHistoryDto(JournalEntryStudentHistory journalEntryStudentHistory) {
        grade = EntityUtil.getNullableCode(journalEntryStudentHistory.getGrade());
        gradeInserted = journalEntryStudentHistory.getGradeInserted();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDateTime gradeInserted) {
        this.gradeInserted = gradeInserted;
    }
}
