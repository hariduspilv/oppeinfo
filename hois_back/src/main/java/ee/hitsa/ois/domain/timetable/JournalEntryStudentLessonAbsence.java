package ee.hitsa.ois.domain.timetable;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class JournalEntryStudentLessonAbsence extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private JournalEntryStudent journalEntryStudent;
    
    private Long lessonNr;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Classifier absence;

    private LocalDateTime absenceInserted;
    private LocalDateTime absenceAccepted;
    
    public JournalEntryStudent getJournalEntryStudent() {
        return journalEntryStudent;
    }
    
    public void setJournalEntryStudent(JournalEntryStudent journalEntryStudent) {
        this.journalEntryStudent = journalEntryStudent;
    }
    
    public Long getLessonNr() {
        return lessonNr;
    }
    
    public void setLessonNr(Long lessonNr) {
        this.lessonNr = lessonNr;
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
    
}
