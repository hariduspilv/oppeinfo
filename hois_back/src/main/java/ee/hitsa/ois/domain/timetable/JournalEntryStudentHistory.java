package ee.hitsa.ois.domain.timetable;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;

@Entity
public class JournalEntryStudentHistory extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private JournalEntryStudent journalEntryStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Classifier grade;

    @NotNull
    private LocalDateTime gradeInserted;
    
    private String gradeInsertedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private GradingSchemaRow gradingSchemaRow;

    private String verbalGrade;

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

    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

    public void setGradeInsertedBy(String gradeInsertedBy) {
        this.gradeInsertedBy = gradeInsertedBy;
    }

    public GradingSchemaRow getGradingSchemaRow() {
        return gradingSchemaRow;
    }

    public void setGradingSchemaRow(GradingSchemaRow gradingSchemaRow) {
        this.gradingSchemaRow = gradingSchemaRow;
    }

    public String getVerbalGrade() {
        return verbalGrade;
    }

    public void setVerbalGrade(String verbalGrade) {
        this.verbalGrade = verbalGrade;
    }
}
