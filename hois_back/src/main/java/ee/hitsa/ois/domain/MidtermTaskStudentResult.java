package ee.hitsa.ois.domain;

import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MidtermTaskStudentResult extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private MidtermTask midtermTask;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private DeclarationSubject declarationSubject;

    private BigDecimal points;
    private String pointsTxt;

    @ManyToOne(fetch = FetchType.LAZY)
    private GradingSchemaRow gradingSchemaRow;

    private String verbalGrade;

    public MidtermTask getMidtermTask() {
        return midtermTask;
    }

    public void setMidtermTask(MidtermTask midtermTask) {
        this.midtermTask = midtermTask;
    }

    public DeclarationSubject getDeclarationSubject() {
        return declarationSubject;
    }

    public void setDeclarationSubject(DeclarationSubject declarationSubject) {
        this.declarationSubject = declarationSubject;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public String getPointsTxt() {
        return pointsTxt;
    }

    public void setPointsTxt(String pointsTxt) {
        this.pointsTxt = pointsTxt;
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
