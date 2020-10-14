package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.GradeDto;

public class JournalEntryStudentResultDto {

    private Long journalEntryStudentId;
    private Long journalStudentId;
    private GradeDto grade;
    private String verbalGrade;
    private String addInfo;
    private LocalDateTime inserted;
    private LocalDateTime gradeInserted;
    private String gradeInsertedBy;
    @ClassifierRestriction(MainClassCode.PUUDUMINE)
    private String absence;
    private LocalDateTime absenceInserted;
    private List<JournalEntryStudentLessonAbsenceDto> lessonAbsences;
    private List<JournalEntryStudentHistoryDto> journalEntryStudentHistories;
    private Boolean isRemark;

    private LocalDate gradeDate; // theme outcome result

    public Long getJournalEntryStudentId() {
        return journalEntryStudentId;
    }

    public void setJournalEntryStudentId(Long journalEntryStudentId) {
        this.journalEntryStudentId = journalEntryStudentId;
    }

    public Long getJournalStudentId() {
        return journalStudentId;
    }

    public void setJournalStudentId(Long journalStudentId) {
        this.journalStudentId = journalStudentId;
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

    public LocalDateTime getAbsenceInserted() {
        return absenceInserted;
    }

    public void setAbsenceInserted(LocalDateTime absenceInserted) {
        this.absenceInserted = absenceInserted;
    }

    public List<JournalEntryStudentLessonAbsenceDto> getLessonAbsences() {
        return lessonAbsences;
    }

    public void setLessonAbsences(List<JournalEntryStudentLessonAbsenceDto> lessonAbsences) {
        this.lessonAbsences = lessonAbsences;
    }

    public List<JournalEntryStudentHistoryDto> getJournalEntryStudentHistories() {
        return journalEntryStudentHistories;
    }

    public void setJournalEntryStudentHistories(List<JournalEntryStudentHistoryDto> journalEntryStudentHistories) {
        this.journalEntryStudentHistories = journalEntryStudentHistories;
    }

    public Boolean getIsRemark() {
        return isRemark;
    }

    public void setIsRemark(Boolean isRemark) {
        this.isRemark = isRemark;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

}
