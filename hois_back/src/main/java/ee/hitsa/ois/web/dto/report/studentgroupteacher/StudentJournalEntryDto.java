package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentJournalEntryDto {

    private Long id;
    private AutocompleteResult journal;
    private String entryType;
    private LocalDate entryDate;
    private String grade;
    private LocalDate gradeInserted;
    private String gradeInsertedBy;
    private String absence;
    private LocalDate absenceInserted;
    private String addInfo;
    // either gradeInserted or absenceInserted date, used for ordering entries outside of query
    private LocalDate orderDate;
    
    public StudentJournalEntryDto() {
        
    }
    
    public StudentJournalEntryDto(StudentJournalEntryDto entry) {
        this.id = entry.getId();
        this.journal = entry.getJournal();
        this.entryType = entry.getEntryType();
        this.entryDate = entry.getEntryDate();
        this.grade = entry.getGrade();
        this.gradeInserted = entry.getGradeInserted();
        this.gradeInsertedBy = entry.getGradeInsertedBy();
        this.absence = entry.getAbsence();
        this.absenceInserted = entry.getAbsenceInserted();
        this.addInfo = entry.getAddInfo();
        this.orderDate = entry.getOrderDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getJournal() {
        return journal;
    }

    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
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

    public String getAbsence() {
        return absence;
    }
    
    public void setAbsence(String absence) {
        this.absence = absence;
    }
    
    public LocalDate getAbsenceInserted() {
        return absenceInserted;
    }
    
    public void setAbsenceInserted(LocalDate absenceInserted) {
        this.absenceInserted = absenceInserted;
    }
    
    public String getAddInfo() {
        return addInfo;
    }
    
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    
}
