package ee.hitsa.ois.domain.subject.studyperiod;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;

@Entity
public class SubjectStudyPeriodTeacherCapacity extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private SubjectStudyPeriodCapacity subjectStudyPeriodCapacity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private SubjectStudyPeriodTeacher subjectStudyPeriodTeacher;

    private Short hours;

    public SubjectStudyPeriodCapacity getSubjectStudyPeriodCapacity() {
        return subjectStudyPeriodCapacity;
    }

    public void setSubjectStudyPeriodCapacity(SubjectStudyPeriodCapacity subjectStudyPeriodCapacity) {
        this.subjectStudyPeriodCapacity = subjectStudyPeriodCapacity;
    }

    public SubjectStudyPeriodTeacher getSubjectStudyPeriodTeacher() {
        return subjectStudyPeriodTeacher;
    }

    public void setSubjectStudyPeriodTeacher(SubjectStudyPeriodTeacher subjectStudyPeriodTeacher) {
        this.subjectStudyPeriodTeacher = subjectStudyPeriodTeacher;
    }

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }
}
