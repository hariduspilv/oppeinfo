package ee.hitsa.ois.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "midtermTaskStudentResult", fetch = FetchType.LAZY)
    private List<MidtermTaskStudentResultHistory> history;

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

    public List<MidtermTaskStudentResultHistory> getHistory() {
        return history != null ? history : (history = new ArrayList<>());
    }

    public void setHistory(List<MidtermTaskStudentResultHistory> history) {
        getHistory().clear();
        getHistory().addAll(history);
    }
}
