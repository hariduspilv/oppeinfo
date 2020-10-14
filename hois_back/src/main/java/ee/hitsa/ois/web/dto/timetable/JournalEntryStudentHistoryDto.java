package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.timetable.JournalEntryStudentHistory;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.GradeDto;

public class JournalEntryStudentHistoryDto {

    private GradeDto grade;
    private String verbalGrade;
    private LocalDateTime gradeInserted;
    private String gradeInsertedBy;

    public JournalEntryStudentHistoryDto(JournalEntryStudentHistory journalEntryStudentHistory) {
        grade = GradeDto.of(journalEntryStudentHistory);
        verbalGrade = journalEntryStudentHistory.getVerbalGrade();
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

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public String getVerbalGrade() {
        return verbalGrade;
    }

    public void setVerbalGrade(String verbalGrade) {
        this.verbalGrade = verbalGrade;
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
