package ee.hitsa.ois.domain.subject.studyperiod;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;

@Entity
public class SubjectStudyPeriodTeacherCapacity extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private SubjectStudyPeriodCapacity subjectStudyPeriodCapacity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private SubjectStudyPeriodTeacher subjectStudyPeriodTeacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, name = "subject_study_period_subgroup_id")
    private SubjectStudyPeriodSubgroup subgroup;

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

    public SubjectStudyPeriodSubgroup getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(SubjectStudyPeriodSubgroup subgroup) {
        this.subgroup = subgroup;
    }
}
