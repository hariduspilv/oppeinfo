package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.student.StudentGroup;

@Entity
public class TimetableEventStudentGroup extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private TimetableEventTime timetableEventTime;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private StudentGroup studentGroup;

    public TimetableEventTime getTimetableEventTime() {
        return timetableEventTime;
    }

    public void setTimetableEventTime(TimetableEventTime timetableEventTime) {
        this.timetableEventTime = timetableEventTime;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

}
