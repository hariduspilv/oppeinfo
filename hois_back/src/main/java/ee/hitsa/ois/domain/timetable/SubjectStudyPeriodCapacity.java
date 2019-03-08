package ee.hitsa.ois.domain.timetable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacherCapacity;

@Entity
public class SubjectStudyPeriodCapacity extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private SubjectStudyPeriod subjectStudyPeriod;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Classifier capacityType;

    @OneToMany(mappedBy = "subjectStudyPeriodCapacity")
    private List<SubjectStudyPeriodTeacherCapacity> teacherCapacities;

    private Short hours;

    public SubjectStudyPeriod getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Classifier getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(Classifier capacityType) {
        this.capacityType = capacityType;
    }

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }

    public List<SubjectStudyPeriodTeacherCapacity> getTeacherCapacities() {
        return teacherCapacities != null ? teacherCapacities : (teacherCapacities = new ArrayList<>());
    }

    public void setTeacherCapacities(List<SubjectStudyPeriodTeacherCapacity> teacherCapacities) {
        this.teacherCapacities = teacherCapacities;
    }

}
