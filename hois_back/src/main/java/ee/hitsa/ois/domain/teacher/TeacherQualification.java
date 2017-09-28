package ee.hitsa.ois.domain.teacher;

import java.time.LocalDate;

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier language;
    private String qualificationOther;
    private Short year;
    private String schoolOther;
    private String diplomaNr;
    private LocalDate endDate;
    private String specialty;
    private String addInfo;

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
    
    public Classifier getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(Classifier studyLevel) {
        this.studyLevel = studyLevel;
    }

    public Classifier getLanguage() {
        return language;
    }

    public void setLanguage(Classifier language) {
        this.language = language;
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

    public String getDiplomaNr() {
        return diplomaNr;
    }

    public void setDiplomaNr(String diplomaNr) {
        this.diplomaNr = diplomaNr;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    
}
