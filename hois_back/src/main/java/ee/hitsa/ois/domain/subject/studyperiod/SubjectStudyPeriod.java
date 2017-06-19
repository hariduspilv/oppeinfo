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
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;

@Entity
public class SubjectStudyPeriod extends BaseEntityWithId {
    //TODO: set of students who declared a subject

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Subject subject;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private StudyPeriod studyPeriod;
    
    private String addInfo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier declarationType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier groupProportion;
    
    @OneToMany(mappedBy = "subjectStudyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectStudyPeriodTeacher> teachers;

    @OneToMany(mappedBy = "subjectStudyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectStudyPeriodStudentGroup> studentGroups;

    @OneToMany(mappedBy = "subjectStudyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectStudyPeriodCapacity> capacities;

    public List<SubjectStudyPeriodCapacity> getCapacities() {
        return capacities != null ? capacities : (capacities = new ArrayList<>());
    }

    public void setCapacities(List<SubjectStudyPeriodCapacity> capacities) {
        getCapacities().clear();
        getCapacities().addAll(capacities);
    }

    public List<SubjectStudyPeriodStudentGroup> getStudentGroups() {
        return studentGroups != null ? studentGroups : (studentGroups = new ArrayList<>());
    }

    public void setStudentGroups(List<SubjectStudyPeriodStudentGroup> studentGroups) {
        getStudentGroups().clear();
        getStudentGroups().addAll(studentGroups);
    }

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

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Classifier getDeclarationType() {
        return declarationType;
    }

    public void setDeclarationType(Classifier declarationType) {
        this.declarationType = declarationType;
    }

    public Classifier getGroupProportion() {
        return groupProportion;
    }

    public void setGroupProportion(Classifier groupProportion) {
        this.groupProportion = groupProportion;
    }

    public void setTeachers(List<SubjectStudyPeriodTeacher> teachers) {
        getTeachers().clear();
        if(teachers != null) {
            getTeachers().addAll(teachers);
        }
    }
}
