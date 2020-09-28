package ee.hitsa.ois.domain.timetable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.student.Student;

@Entity
public class JournalStudent extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Journal journal;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    
    private Boolean isMoodleRegistered;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_student_id", nullable = false, updatable = false, insertable = false)
    private Set<JournalEntryStudent> journalEntryStudents = new HashSet<>();

    public static JournalStudent of(Student student) {
        JournalStudent journalStudent = new JournalStudent();
        journalStudent.setStudent(student);
        return journalStudent;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Boolean getIsMoodleRegistered() {
        return isMoodleRegistered;
    }

    public void setIsMoodleRegistered(Boolean isMoodleRegistered) {
        this.isMoodleRegistered = isMoodleRegistered;
    }

    public Set<JournalEntryStudent> getJournalEntryStudents() {
        return journalEntryStudents != null ? journalEntryStudents : (journalEntryStudents = new HashSet<>());
    }

    public void setJournalEntryStudents(Set<JournalEntryStudent> journalEntryStudents) {
        this.journalEntryStudents = journalEntryStudents;
    }

}
