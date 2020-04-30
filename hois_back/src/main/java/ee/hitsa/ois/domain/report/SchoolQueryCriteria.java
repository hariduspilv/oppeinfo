package ee.hitsa.ois.domain.report;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class SchoolQueryCriteria extends BaseEntityWithId {
    
    private String criteriaCode;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private SchoolQuery schoolQuery;
    private Boolean showInResults = Boolean.FALSE;
    private String criteriaVal1;
    private String criteriaVal2;
    private String criteriaCondition;
    
    public String getCriteriaCode() {
        return criteriaCode;
    }
    public void setCriteriaCode(String criteriaCode) {
        this.criteriaCode = criteriaCode;
    }
    public SchoolQuery getSchoolQuery() {
        return schoolQuery;
    }
    public void setSchoolQuery(SchoolQuery schoolQuery) {
        this.schoolQuery = schoolQuery;
    }
    public Boolean getShowInResults() {
        return showInResults;
    }
    public void setShowInResults(Boolean showInResults) {
        this.showInResults = showInResults;
    }
    public String getCriteriaVal1() {
        return criteriaVal1;
    }
    public void setCriteriaVal1(String criteriaVal1) {
        this.criteriaVal1 = criteriaVal1;
    }
    public String getCriteriaVal2() {
        return criteriaVal2;
    }
    public void setCriteriaVal2(String criteriaVal2) {
        this.criteriaVal2 = criteriaVal2;
    }
    public String getCriteriaCondition() {
        return criteriaCondition;
    }
    public void setCriteriaCondition(String criteriaCondition) {
        this.criteriaCondition = criteriaCondition;
    }
    
}
