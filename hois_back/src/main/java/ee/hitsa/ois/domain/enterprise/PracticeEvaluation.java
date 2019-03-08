package ee.hitsa.ois.domain.enterprise;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;

@Entity
public class PracticeEvaluation extends BaseEntityWithId {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private School school;
	private String nameEt;
	private String addInfo;
	private Boolean isActive;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
	private Classifier target;
	
	@OneToMany(mappedBy="practiceEvaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PracticeEvaluationCriteria> criteria = new ArrayList<>();
	
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Classifier getTarget() {
        return target;
    }

    public void setTarget(Classifier target) {
        this.target = target;
    }

    public List<PracticeEvaluationCriteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<PracticeEvaluationCriteria> criteria) {
        this.criteria = criteria;
    }

}
