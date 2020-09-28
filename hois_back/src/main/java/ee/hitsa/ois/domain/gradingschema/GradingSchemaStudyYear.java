package ee.hitsa.ois.domain.gradingschema;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.StudyYear;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GradingSchemaStudyYear extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private GradingSchema gradingSchema;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyYear studyYear;

    public GradingSchema getGradingSchema() {
        return gradingSchema;
    }

    public void setGradingSchema(GradingSchema gradingSchema) {
        this.gradingSchema = gradingSchema;
    }

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }
}
