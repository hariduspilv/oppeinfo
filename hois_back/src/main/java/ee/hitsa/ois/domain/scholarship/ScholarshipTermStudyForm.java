package ee.hitsa.ois.domain.scholarship;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class ScholarshipTermStudyForm extends BaseEntityWithId {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_term_id", nullable = false, updatable = false)
    private ScholarshipTerm scholarshipTerm;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier studyForm;

    public ScholarshipTerm getScholarshipTerm() {
        return scholarshipTerm;
    }

    public void setScholarshipTerm(ScholarshipTerm scholarshipTerm) {
        this.scholarshipTerm = scholarshipTerm;
    }

    public Classifier getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }

}
