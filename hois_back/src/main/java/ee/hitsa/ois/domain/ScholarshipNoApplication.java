package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.school.School;

@Entity
public class ScholarshipNoApplication extends BaseEntityWithId {

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier scholarshipEhis;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Classifier getScholarshipEhis() {
        return scholarshipEhis;
    }

    public void setScholarshipEhis(Classifier scholarshipEhis) {
        this.scholarshipEhis = scholarshipEhis;
    }
}
