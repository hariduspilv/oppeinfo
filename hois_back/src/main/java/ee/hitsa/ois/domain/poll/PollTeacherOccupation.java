package ee.hitsa.ois.domain.poll;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;

@Entity
public class PollTeacherOccupation extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TeacherOccupation teacherOccupation;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Poll poll;
    
    public TeacherOccupation getTeacherOccupation() {
        return teacherOccupation;
    }
    public void setTeacherOccupation(TeacherOccupation teacherOccupation) {
        this.teacherOccupation = teacherOccupation;
    }
    public Poll getPoll() {
        return poll;
    }
    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
