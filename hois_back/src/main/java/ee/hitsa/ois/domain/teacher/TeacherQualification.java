package ee.hitsa.ois.domain.teacher;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class TeacherQualification extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Teacher teacher;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier qualification;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier qualificationName;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier state;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier school;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier exSchool;
    private String qualificationOther;
    private Short year;
    private String schoolOther;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Classifier getQualification() {
        return qualification;
    }

    public void setQualification(Classifier qualification) {
        this.qualification = qualification;
    }

    public Classifier getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(Classifier qualificationName) {
        this.qualificationName = qualificationName;
    }

    public Classifier getState() {
        return state;
    }

    public void setState(Classifier state) {
        this.state = state;
    }

    public Classifier getSchool() {
        return school;
    }

    public void setSchool(Classifier school) {
        this.school = school;
    }

    public Classifier getExSchool() {
        return exSchool;
    }

    public void setExSchool(Classifier exSchool) {
        this.exSchool = exSchool;
    }

    public String getQualificationOther() {
        return qualificationOther;
    }

    public void setQualificationOther(String qualificationOther) {
        this.qualificationOther = qualificationOther;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getSchoolOther() {
        return schoolOther;
    }

    public void setSchoolOther(String schoolOther) {
        this.schoolOther = schoolOther;
    }
}
