package ee.hitsa.ois.domain.scholarship;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class ScholarshipTermStudyLoad extends BaseEntityWithId {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_term_id", nullable = false, updatable = false)
    private ScholarshipTerm scholarshipTerm;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier studyLoad;

    public ScholarshipTerm getScholarshipTerm() {
        return scholarshipTerm;
    }

    public void setScholarshipTerm(ScholarshipTerm scholarshipTerm) {
        this.scholarshipTerm = scholarshipTerm;
    }

    public Classifier getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(Classifier studyLoad) {
        this.studyLoad = studyLoad;
    }

}
