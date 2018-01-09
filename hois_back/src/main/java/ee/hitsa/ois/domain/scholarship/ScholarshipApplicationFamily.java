package ee.hitsa.ois.domain.scholarship;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class ScholarshipApplicationFamily extends BaseEntityWithId {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_application_id", nullable = false, updatable = false)
    private ScholarshipApplication scholarshipApplication;
    private String name;
    private BigDecimal netSalary;
    private BigDecimal pension;
    private BigDecimal stateBenefit;
    private BigDecimal otherIncome;
    private BigDecimal unemployedBenefit;

    public ScholarshipApplication getScholarshipApplication() {
        return scholarshipApplication;
    }

    public void setScholarshipApplication(ScholarshipApplication scholarshipApplication) {
        this.scholarshipApplication = scholarshipApplication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }

    public BigDecimal getPension() {
        return pension;
    }

    public void setPension(BigDecimal pension) {
        this.pension = pension;
    }

    public BigDecimal getStateBenefit() {
        return stateBenefit;
    }

    public void setStateBenefit(BigDecimal stateBenefit) {
        this.stateBenefit = stateBenefit;
    }

    public BigDecimal getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(BigDecimal otherIncome) {
        this.otherIncome = otherIncome;
    }

    public BigDecimal getUnemployedBenefit() {
        return unemployedBenefit;
    }

    public void setUnemployedBenefit(BigDecimal unemployedBenefit) {
        this.unemployedBenefit = unemployedBenefit;
    }

}
