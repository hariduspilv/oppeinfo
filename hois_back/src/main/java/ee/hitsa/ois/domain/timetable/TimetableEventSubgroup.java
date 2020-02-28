package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class TimetableEventSubgroup extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private SubjectStudyPeriodSubgroup subjectStudyPeriodSubgroup;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private TimetableEventTime timetableEventTime;

    public SubjectStudyPeriodSubgroup getSubjectStudyPeriodSubgroup() {
        return subjectStudyPeriodSubgroup;
    }

    public void setSubjectStudyPeriodSubgroup(SubjectStudyPeriodSubgroup subjectStudyPeriodSubgroup) {
        this.subjectStudyPeriodSubgroup = subjectStudyPeriodSubgroup;
    }

    public TimetableEventTime getTimetableEventTime() {
        return timetableEventTime;
    }

    public void setTimetableEventTime(TimetableEventTime timetableEventTime) {
        this.timetableEventTime = timetableEventTime;
    }
}
