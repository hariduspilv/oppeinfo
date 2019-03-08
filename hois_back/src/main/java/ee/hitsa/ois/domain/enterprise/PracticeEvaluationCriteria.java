package ee.hitsa.ois.domain.enterprise;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class PracticeEvaluationCriteria extends BaseEntityWithId {
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private PracticeEvaluation practiceEvaluation;
	private String nameEt;
	private String addInfo;
	private Long orderNr;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
	private Classifier type;
	
    public PracticeEvaluation getPracticeEvaluation() {
        return practiceEvaluation;
    }
    public void setPracticeEvaluation(PracticeEvaluation practiceEvaluation) {
        this.practiceEvaluation = practiceEvaluation;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    public Long getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
    }
    public Classifier getType() {
        return type;
    }
    public void setType(Classifier type) {
        this.type = type;
    }
	
}
