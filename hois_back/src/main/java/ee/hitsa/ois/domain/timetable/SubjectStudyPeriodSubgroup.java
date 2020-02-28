package ee.hitsa.ois.domain.timetable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;

@Entity
public class SubjectStudyPeriodSubgroup extends BaseEntityWithId {

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "subject_study_period_id")
    private SubjectStudyPeriod period;
    private String code;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "subject_study_period_teacher_id")
    private SubjectStudyPeriodTeacher teacher;
    private Short places;
    @OneToMany(mappedBy = "subgroup", fetch = FetchType.LAZY, orphanRemoval = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<DeclarationSubject> declarationSubjects;

    public SubjectStudyPeriod getPeriod() {
        return period;
    }

    public void setPeriod(SubjectStudyPeriod period) {
        this.period = period;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SubjectStudyPeriodTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(SubjectStudyPeriodTeacher teacher) {
        this.teacher = teacher;
    }

    public Short getPlaces() {
        return places;
    }

    public void setPlaces(Short places) {
        this.places = places;
    }
    
    public void addDeclarationSubject(DeclarationSubject ds) {
        if (ds != null) {
            getDeclarationSubjects().add(ds);
            ds.setSubgroup(this);
        }
    }

    public Set<DeclarationSubject> getDeclarationSubjects() {
        return declarationSubjects != null ? declarationSubjects : (declarationSubjects = new HashSet<>());
    }

    public void setDeclarationSubjects(Set<DeclarationSubject> declarationSubjects) {
        getDeclarationSubjects().clear();
        getDeclarationSubjects().addAll(declarationSubjects);
    }
}
