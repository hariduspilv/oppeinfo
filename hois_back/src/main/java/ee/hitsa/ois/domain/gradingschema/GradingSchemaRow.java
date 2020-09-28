package ee.hitsa.ois.domain.gradingschema;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GradingSchemaRow extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private GradingSchema gradingSchema;

    private String grade;
    private String gradeEn;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier gradeReal;

    private Boolean isValid;

    public GradingSchema getGradingSchema() {
        return gradingSchema;
    }

    public void setGradingSchema(GradingSchema gradingSchema) {
        this.gradingSchema = gradingSchema;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeEn() {
        return gradeEn;
    }

    public void setGradeEn(String gradeEn) {
        this.gradeEn = gradeEn;
    }

    public Classifier getGradeReal() {
        return gradeReal;
    }

    public void setGradeReal(Classifier gradeReal) {
        this.gradeReal = gradeReal;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
