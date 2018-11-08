package ee.hitsa.ois.domain.subject.studyperiod;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class SubjectStudyPeriodTeacher extends BaseEntityWithId {

    private Boolean isSignatory;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Teacher teacher;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private SubjectStudyPeriod subjectStudyPeriod;
    
    @OneToMany(mappedBy="subjectStudyPeriodTeacher", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<SubjectProgram> subjectPrograms = new HashSet<>();

    public Boolean getIsSignatory() {
        return isSignatory;
    }

    public void setIsSignatory(Boolean isSignatory) {
        this.isSignatory = isSignatory;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public SubjectStudyPeriod getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Set<SubjectProgram> getSubjectPrograms() {
        return subjectPrograms == null ? (subjectPrograms = new HashSet<>()) : subjectPrograms;
    }

    public void setSubjectPrograms(Set<SubjectProgram> subjectPrograms) {
        getSubjectPrograms().clear();
        getSubjectPrograms().addAll(subjectPrograms);
    }
}
