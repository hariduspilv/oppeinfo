package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;

@Entity
public class Journal extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    private String nameEt;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudyYear studyYear;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier assessment;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier groupProportion;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public Classifier getAssessment() {
        return assessment;
    }

    public void setAssessment(Classifier assessment) {
        this.assessment = assessment;
    }

    public Classifier getGroupProportion() {
        return groupProportion;
    }

    public void setGroupProportion(Classifier groupProportion) {
        this.groupProportion = groupProportion;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }
}
