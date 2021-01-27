package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class MidtermTaskStudentResultHistory extends BaseEntityWithId {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private MidtermTaskStudentResult midtermTaskStudentResult;
    private BigDecimal points;
    private String pointsTxt;

    public MidtermTaskStudentResult getMidtermTaskStudentResult() {
        return midtermTaskStudentResult;
    }

    public void setMidtermTaskStudentResult(MidtermTaskStudentResult midtermTaskStudentResult) {
        this.midtermTaskStudentResult = midtermTaskStudentResult;
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
}
