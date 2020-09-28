package ee.hitsa.ois.domain.student;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class StudentCurriculumModuleOutcomesResultHistory extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private StudentCurriculumModuleOutcomesResult studentCurriculumModuleOutcomesResult;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier grade;

    private LocalDate gradeDate;
    private LocalDateTime gradeInserted;
    private String gradeInsertedBy;
    private String addInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private GradingSchemaRow gradingSchemaRow;

    private String verbalGrade;

    public StudentCurriculumModuleOutcomesResult getStudentCurriculumModuleOutcomesResult() {
        return studentCurriculumModuleOutcomesResult;
    }

    public void setStudentCurriculumModuleOutcomesResult(StudentCurriculumModuleOutcomesResult studentCurriculumModuleOutcomesResult) {
        this.studentCurriculumModuleOutcomesResult = studentCurriculumModuleOutcomesResult;
    }

    public Classifier getGrade() {
        return grade;
    }

    public void setGrade(Classifier grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
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

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
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
