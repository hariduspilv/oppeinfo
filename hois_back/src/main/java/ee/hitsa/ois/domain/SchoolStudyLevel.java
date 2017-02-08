package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SchoolStudyLevel extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_level_code")
    private Classifier studyLevel;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;

    public Classifier getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(Classifier studyLevel) {
        this.studyLevel = studyLevel;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
