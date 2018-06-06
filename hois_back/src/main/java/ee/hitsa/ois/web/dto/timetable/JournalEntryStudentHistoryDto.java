package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.timetable.JournalEntryStudentHistory;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;

public class JournalEntryStudentHistoryDto {

    private String grade;
    private LocalDateTime gradeInserted;
    private String gradeInsertedBy;

    public JournalEntryStudentHistoryDto(JournalEntryStudentHistory journalEntryStudentHistory) {
        grade = EntityUtil.getNullableCode(journalEntryStudentHistory.getGrade());
        gradeInserted = journalEntryStudentHistory.getGradeInserted();
        
        String insertedBy;
        if (journalEntryStudentHistory.getGradeInsertedBy() != null) {
            insertedBy = journalEntryStudentHistory.getGradeInsertedBy();
        } else if (journalEntryStudentHistory.getChangedBy() != null) {
            insertedBy = journalEntryStudentHistory.getChangedBy();
        } else {
            insertedBy = journalEntryStudentHistory.getInsertedBy();
        }
        gradeInsertedBy = PersonUtil.stripIdcodeFromFullnameAndIdcode(insertedBy);
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

    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

    public void setGradeInsertedBy(String gradeInsertedBy) {
        this.gradeInsertedBy = gradeInsertedBy;
    }

}
