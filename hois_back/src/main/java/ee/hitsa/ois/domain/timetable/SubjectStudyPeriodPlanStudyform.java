package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
@Table(name="subject_study_period_plan_studyform")
public class SubjectStudyPeriodPlanStudyForm extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="subject_study_period_plan_id", updatable = false, nullable = false)
    private SubjectStudyPeriodPlan plan;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyForm;

    public SubjectStudyPeriodPlan getPlan() {
        return plan;
    }

    public void setPlan(SubjectStudyPeriodPlan plan) {
        this.plan = plan;
    }

    public Classifier getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }
}
  