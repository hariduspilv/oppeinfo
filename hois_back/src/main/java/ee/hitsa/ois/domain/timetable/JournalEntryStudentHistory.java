package ee.hitsa.ois.domain.timetable;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class JournalEntryStudentHistory extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private JournalEntryStudent journalEntryStudent;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Classifier grade;

    @NotNull
    private LocalDateTime gradeInserted;

    public JournalEntryStudent getJournalEntryStudent() {
        return journalEntryStudent;
    }

    public void setJournalEntryStudent(JournalEntryStudent journalEntryStudent) {
        this.journalEntryStudent = journalEntryStudent;
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
