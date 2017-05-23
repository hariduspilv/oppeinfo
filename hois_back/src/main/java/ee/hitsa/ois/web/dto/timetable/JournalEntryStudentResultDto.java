package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalEntryStudentResultDto {

    private String grade;
    private String addInfo;
    private LocalDateTime inserted;
    private LocalDateTime gradeInserted;
    @ClassifierRestriction(MainClassCode.PUUDUMINE)
    private String absence;

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
    public String getAbsence() {
        return absence;
    }
    public void setAbsence(String absence) {
        this.absence = absence;
    }


}
