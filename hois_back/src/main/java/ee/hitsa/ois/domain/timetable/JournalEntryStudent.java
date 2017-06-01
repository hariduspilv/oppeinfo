package ee.hitsa.ois.domain.timetable;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class JournalEntryStudent extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private JournalEntry journalEntry;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private JournalStudent journalStudent;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Classifier absence;

    private LocalDateTime absenceInserted;
    private LocalDateTime absenceAccepted;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Classifier grade;

    private LocalDateTime gradeInserted;
    private String addInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_entry_student_id", nullable = false, updatable = false, insertable = true)
    private Set<JournalEntryStudentHistory> journalEntryStudentHistories = new HashSet<>();


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

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Set<JournalEntryStudentHistory> getJournalEntryStudentHistories() {
        return journalEntryStudentHistories;
    }

    public void setJournalEntryStudentHistories(Set<JournalEntryStudentHistory> journalEntryStudentHistories) {
        this.journalEntryStudentHistories = journalEntryStudentHistories;
    }

}
