package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentJournalEntryAbsenceDto {

    private AutocompleteResult journal;
    private Long studentEntryId;
    private LocalDate entryDate;
    private String absence;
    private LocalDate absenceInserted;
    private Long lessonNr;
    private Long lessons;

    public StudentJournalEntryAbsenceDto() {

    }

    public StudentJournalEntryAbsenceDto(StudentJournalEntryAbsenceDto absence) {
        this.journal = absence.getJournal();
        this.studentEntryId = absence.getStudentEntryId();
        this.entryDate = absence.getEntryDate();
        this.absence = absence.getAbsence();
        this.absenceInserted = absence.getAbsenceInserted();
        this.lessonNr = absence.getLessonNr();
        this.lessons = absence.getLessons();
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

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
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
