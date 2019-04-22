package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentJournalEntryResultDto {

    private AutocompleteResult journal;
    private Long studentEntryId;
    private String entryType;
    private LocalDate entryDate;
    private String grade;
    private LocalDate gradeInserted;
    private String gradeInsertedBy;
    private String addInfo;

    public StudentJournalEntryResultDto() {
        
    }

    public StudentJournalEntryResultDto(StudentJournalEntryResultDto result) {
        this.journal = result.getJournal();
        this.studentEntryId = result.getStudentEntryId();
        this.entryType = result.getEntryType();
        this.entryDate = result.getEntryDate();
        this.grade = result.getGrade();
        this.gradeInserted = result.getGradeInserted();
        this.gradeInsertedBy = result.getGradeInsertedBy();
        this.addInfo = result.getAddInfo();
    }

    public AutocompleteResult getJournal() {
        return journal;
    }

    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
    }

    public Long getStudentEntryId() {
        return studentEntryId;
    }

    public void setStudentEntryId(Long studentEntryId) {
        this.studentEntryId = studentEntryId;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDate gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

    public void setGradeInsertedBy(String gradeInsertedBy) {
        this.gradeInsertedBy = gradeInsertedBy;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

}
