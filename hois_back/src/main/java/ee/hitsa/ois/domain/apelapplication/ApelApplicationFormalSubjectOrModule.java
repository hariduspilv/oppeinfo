package ee.hitsa.ois.domain.apelapplication;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.subject.Subject;

@Entity
public class ApelApplicationFormalSubjectOrModule extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplicationRecord apelApplicationRecord;

    private Boolean isMySchool;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private ApelSchool apelSchool;

    private Boolean isOptional;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Subject subject;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private CurriculumVersionHigherModule curriculumVersionHmodule;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private CurriculumVersionOccupationModule curriculumVersionOmodule;

    private LocalDate gradeDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier grade;

    private String teachers;

    private Boolean transfer = Boolean.FALSE;

    private String nameEt;

    private String nameEn;

    private String subjectCode;

    private BigDecimal credits;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier assessment;

    public ApelApplicationRecord getApelApplicationRecord() {
        return apelApplicationRecord;
    }

    public void setApelApplicationRecord(ApelApplicationRecord apelApplicationRecord) {
        this.apelApplicationRecord = apelApplicationRecord;
    }

    public Boolean getIsMySchool() {
        return isMySchool;
    }

    public void setIsMySchool(Boolean isMySchool) {
        this.isMySchool = isMySchool;
    }

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }

    public ApelSchool getApelSchool() {
        return apelSchool;
    }

    public void setApelSchool(ApelSchool apelSchool) {
        this.apelSchool = apelSchool;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public CurriculumVersionHigherModule getCurriculumVersionHmodule() {
        return curriculumVersionHmodule;
    }

    public void setCurriculumVersionHmodule(CurriculumVersionHigherModule curriculumVersionHmodule) {
        this.curriculumVersionHmodule = curriculumVersionHmodule;
    }

    public CurriculumVersionOccupationModule getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(CurriculumVersionOccupationModule curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public Classifier getGrade() {
        return grade;
    }

    public void setGrade(Classifier grade) {
        this.grade = grade;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Classifier getAssessment() {
        return assessment;
    }

    public void setAssessment(Classifier assessment) {
        this.assessment = assessment;
    }
    
}
