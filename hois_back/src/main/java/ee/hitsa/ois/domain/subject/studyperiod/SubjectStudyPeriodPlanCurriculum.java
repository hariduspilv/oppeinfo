package ee.hitsa.ois.domain.subject.studyperiod;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.Curriculum;

@Entity
public class SubjectStudyPeriodPlanCurriculum extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Curriculum curriculum;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="subject_study_period_plan_id", updatable = false, nullable = false)
    private SubjectStudyPeriodPlan plan;

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public SubjectStudyPeriodPlan getPlan() {
        return plan;
    }

    public void setPlan(SubjectStudyPeriodPlan plan) {
        this.plan = plan;
    }
}
