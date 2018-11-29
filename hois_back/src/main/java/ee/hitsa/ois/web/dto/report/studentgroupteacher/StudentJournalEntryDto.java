package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentLessonAbsenceDto;

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
    private List<JournalEntryStudentLessonAbsenceDto> lessonAbsences = new ArrayList<>();
    private String addInfo;
    private String changedBy;
    // either gradeInserted or absenceInserted date, used for ordering entries outside of query
    private LocalDate orderDate;
    private Long lessonNr;
    private Long lessons;
    
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
        for (JournalEntryStudentLessonAbsenceDto lessonAbsence : entry.getLessonAbsences()) {
            this.lessonAbsences.add(new JournalEntryStudentLessonAbsenceDto(lessonAbsence));
        }
        
        this.addInfo = entry.getAddInfo();
        this.changedBy = entry.getChangedBy();
        this.orderDate = entry.getOrderDate();
        this.lessonNr = entry.getLessonNr();
        this.lessons = entry.getLessons();
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
    
    public List<JournalEntryStudentLessonAbsenceDto> getLessonAbsences() {
        return lessonAbsences;
    }
    
    public void setLessonAbsences(List<JournalEntryStudentLessonAbsenceDto> lessonAbsences) {
        this.lessonAbsences = lessonAbsences;
    }
    
    public String getAddInfo() {
        return addInfo;
    }
    
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Long getLessonNr() {
        return lessonNr;
    }

    public void setLessonNr(Long lessonNr) {
        this.lessonNr = lessonNr;
    }

    public Long getLessons() {
        return lessons;
    }

    public void setLessons(Long lessons) {
        this.lessons = lessons;
    }

}
