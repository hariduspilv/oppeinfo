package ee.hitsa.ois.domain.subject.studyperiod;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class SubjectStudyPeriodPlanCapacity extends BaseEntityWithId {

    private Boolean isContact;
    private Long hours;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="subject_study_period_plan_id", updatable = false, nullable = false)
    private SubjectStudyPeriodPlan plan;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Classifier capacityType;

    public Boolean getIsContact() {
        return isContact;
    }
    public void setIsContact(Boolean isContact) {
        this.isContact = isContact;
    }
    public Long getHours() {
        return hours;
    }
    public void setHours(Long hours) {
        this.hours = hours;
    }
    public SubjectStudyPeriodPlan getPlan() {
        return plan;
    }
    public void setPlan(SubjectStudyPeriodPlan plan) {
        this.plan = plan;
    }
    public Classifier getCapacityType() {
        return capacityType;
    }
    public void setCapacityType(Classifier capacityType) {
        this.capacityType = capacityType;
    }
}
