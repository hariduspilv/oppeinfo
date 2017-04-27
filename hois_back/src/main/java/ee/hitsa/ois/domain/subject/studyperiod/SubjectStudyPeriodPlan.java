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
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;

@Entity
public class SubjectStudyPeriodPlan extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private StudyPeriod studyPeriod;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Subject subject;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectStudyPeriodPlanCapacity> capacities;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectStudyPeriodPlanCurriculum> curriculums;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectStudyPeriodPlanStudyform> studyForms;

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public Set<SubjectStudyPeriodPlanCapacity> getCapacities() {
        return capacities != null ? capacities : (capacities = new HashSet<>());
    }
    public void setCapacities(Set<SubjectStudyPeriodPlanCapacity> capacities) {
        getCapacities().clear();
        getCapacities().addAll(capacities);
    }
    public Set<SubjectStudyPeriodPlanCurriculum> getCurriculums() {
        return curriculums != null ? curriculums : (curriculums = new HashSet<>());
    }
    public void setCurriculums(Set<SubjectStudyPeriodPlanCurriculum> curriculums) {
        getCurriculums().clear();
        getCurriculums().addAll(curriculums);
    }
    public Set<SubjectStudyPeriodPlanStudyform> getStudyForms() {
        return studyForms != null ? studyForms : (studyForms = new HashSet<>());
    }
    public void setStudyForms(Set<SubjectStudyPeriodPlanStudyform> studyForms) {
        getStudyForms().clear();
        getStudyForms().addAll(studyForms);
    }
}
