package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;

public class StudentGroupAbsenceDto {

    private Long student;
    private LocalDate entryDate;
    private Long journal;
    private Long journalStudentEntry;
    private Long journalEntryStudentLessonAbsence;
    private Long lessonNr;
    private String absence;
    
    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Long getJournal() {
        return journal;
    }

    public void setJournal(Long journal) {
        this.journal = journal;
    }

    public Long getJournalStudentEntry() {
        return journalStudentEntry;
    }

    public void setJournalStudentEntry(Long journalStudentEntry) {
        this.journalStudentEntry = journalStudentEntry;
    }

    public Long getJournalEntryStudentLessonAbsence() {
        return journalEntryStudentLessonAbsence;
    }

    public void setJournalEntryStudentLessonAbsence(Long journalEntryStudentLessonAbsence) {
        this.journalEntryStudentLessonAbsence = journalEntryStudentLessonAbsence;
    }

    public Long getLessonNr() {
        return lessonNr;
    }

    public void setLessonNr(Long lessonNr) {
        this.lessonNr = lessonNr;
    }

    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }

}
