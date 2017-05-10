package ee.hitsa.ois.domain.timetable;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class JournalEntryStudent extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private JournalEntry journalEntry;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private JournalStudent journalStudent;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, updatable = false)
    private Classifier absence;

    private LocalDateTime absenceInserted;
    private LocalDateTime absenceAccepted;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, updatable = false)
    private Classifier grade;

    private LocalDateTime gradeInserted;

    public JournalEntry getJournalEntry() {
        return journalEntry;
    }

    public void setJournalEntry(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
    }

    public JournalStudent getJournalStudent() {
        return journalStudent;
    }

    public void setJournalStudent(JournalStudent journalStudent) {
        this.journalStudent = journalStudent;
    }

    public Classifier getAbsence() {
        return absence;
    }

    public void setAbsence(Classifier absence) {
        this.absence = absence;
    }

    public LocalDateTime getAbsenceInserted() {
        return absenceInserted;
    }

    public void setAbsenceInserted(LocalDateTime absenceInserted) {
        this.absenceInserted = absenceInserted;
    }

    public LocalDateTime getAbsenceAccepted() {
        return absenceAccepted;
    }

    public void setAbsenceAccepted(LocalDateTime absenceAccepted) {
        this.absenceAccepted = absenceAccepted;
    }

    public Classifier getGrade() {
        return grade;
    }

    public void setGrade(Classifier grade) {
        this.grade = grade;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDateTime gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

}
