package ee.hitsa.ois.domain.scholarship;

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
    private Long netSalary;
    private Long pension;
    private Long stateBenefit;
    private Long otherIncome;
    private Long unemployedBenefit;

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

    public Long getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Long netSalary) {
        this.netSalary = netSalary;
    }

    public Long getPension() {
        return pension;
    }

    public void setPension(Long pension) {
        this.pension = pension;
    }

    public Long getStateBenefit() {
        return stateBenefit;
    }

    public void setStateBenefit(Long stateBenefit) {
        this.stateBenefit = stateBenefit;
    }

    public Long getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Long otherIncome) {
        this.otherIncome = otherIncome;
    }

    public Long getUnemployedBenefit() {
        return unemployedBenefit;
    }

    public void setUnemployedBenefit(Long unemployedBenefit) {
        this.unemployedBenefit = unemployedBenefit;
    }

}
