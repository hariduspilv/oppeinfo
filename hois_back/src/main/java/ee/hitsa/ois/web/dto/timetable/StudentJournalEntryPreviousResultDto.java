package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.web.dto.GradeDto;

import java.time.LocalDateTime;

public class StudentJournalEntryPreviousResultDto {
    private final GradeDto grade;
    private final String verbalGrade;
    private final LocalDateTime gradeInserted;
    private final String gradeInsertedBy;
    
    public StudentJournalEntryPreviousResultDto(String gradeCode, Long gradingSchemaRowId, String verbalGrade,
            LocalDateTime gradeInserted, String gradeInsertedBy) {
        this.grade = new GradeDto(gradeCode, gradingSchemaRowId);
        this.verbalGrade = verbalGrade;
        this.gradeInserted = gradeInserted;
        this.gradeInsertedBy = gradeInsertedBy;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public String getVerbalGrade() {
        return verbalGrade;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }
    
    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

}
