package ee.hitsa.ois.domain.scholarship;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class ScholarshipTermCourse extends BaseEntityWithId {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_term_id", nullable = false, updatable = false)
    private ScholarshipTerm scholarshipTerm;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier course;

    public ScholarshipTerm getScholarshipTerm() {
        return scholarshipTerm;
    }

    public void setScholarshipTerm(ScholarshipTerm scholarshipTerm) {
        this.scholarshipTerm = scholarshipTerm;
    }

    public Classifier getCourse() {
        return course;
    }

    public void setCourse(Classifier course) {
        this.course = course;
    }

}
