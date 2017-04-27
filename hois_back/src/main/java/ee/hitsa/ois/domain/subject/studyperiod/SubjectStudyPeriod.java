package ee.hitsa.ois.domain.subject.studyperiod;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;

@Entity
public class SubjectStudyPeriod extends BaseEntityWithId {
    //TODO: set of students who declared a subject

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Subject subject;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private StudyPeriod studyPeriod;
    
    @OneToMany(mappedBy = "subjectStudyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectStudyPeriodTeacher> teachers;

    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public List<SubjectStudyPeriodTeacher> getTeachers() {
        return teachers != null ? teachers : (teachers = new ArrayList<>());
    }
    public void setTeachers(List<SubjectStudyPeriodTeacher> teachers) {
        getTeachers().clear();
        if(teachers != null) {
            getTeachers().addAll(teachers);
        }
    }
}
