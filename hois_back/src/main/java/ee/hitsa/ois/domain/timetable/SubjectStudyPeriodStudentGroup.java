package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;

@Entity
public class SubjectStudyPeriodStudentGroup extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private StudentGroup studentGroup;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private SubjectStudyPeriod subjectStudyPeriod;

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
    public SubjectStudyPeriod getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
}
